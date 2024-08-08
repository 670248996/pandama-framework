package com.pandama.top.milvus.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.pandama.top.milvus.constant.Constant;
import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.*;
import io.milvus.param.R;
import io.milvus.param.RpcStatus;
import io.milvus.param.collection.*;
import io.milvus.param.dml.DeleteParam;
import io.milvus.param.dml.InsertParam;
import io.milvus.param.dml.QueryParam;
import io.milvus.param.dml.SearchParam;
import io.milvus.param.highlevel.collection.ListCollectionsParam;
import io.milvus.param.highlevel.collection.response.ListCollectionsResponse;
import io.milvus.param.index.CreateIndexParam;
import io.milvus.param.index.DescribeIndexParam;
import io.milvus.param.index.DropIndexParam;
import io.milvus.param.partition.*;
import io.milvus.response.*;
import io.milvus.v2.service.vector.request.InsertReq;
import io.milvus.v2.service.vector.response.InsertResp;
import org.apache.commons.lang3.StringUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * milvus向量数据库操作工具
 *
 * @author 王强
 * @date 2024-04-17 15:59:03
 */
public class MilvusUtils {

    private final MilvusServiceClient milvusClient;

    public MilvusUtils(MilvusServiceClient milvusClient) {
        this.milvusClient = milvusClient;
    }

    /**
     * 处理响应结果，失败则抛出运行异常
     *
     * @param r 响应结果
     * @author 王强
     */
    private void handleResponseStatus(R<?> r) {
        if (r.getStatus() != R.Status.Success.getCode()) {
            throw new RuntimeException(r.getMessage());
        }
    }

    /**
     * 校验名称是否满足命名规范
     *
     * @param name partition名称
     * @return java.lang.Boolean
     * @author 王强
     */
    public Boolean checkNaming(String name) {
        Pattern compile = Pattern.compile(Constant.MILVUS_NAME_PATTERN);
        return compile.matcher(name).matches();
    }

    /**
     * 获取id
     *
     * @param str 字符串
     * @return java.lang.Long
     * @author 王强
     */
    @Deprecated
    public static Long getId(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(str.getBytes());
            // 截取前8个字节作为64位整数
            return ByteBuffer.wrap(hashBytes, 0, 8).order(ByteOrder.BIG_ENDIAN).getLong();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建collection
     *
     * @param param     collection创建参数
     * @return io.milvus.param.RpcStatus
     * @author 王强
     */
    public RpcStatus createCollection(CreateCollectionParam param) {
        R<RpcStatus> response = milvusClient.createCollection(param);
        handleResponseStatus(response);
        return response.getData();
    }

    /**
     * 拷贝collection
     *
     * @param sourceCollectionName 原始collection名称
     * @param targetCollectionName 目标collection名称
     * @return io.milvus.param.RpcStatus
     * @author 王强
     */
    public RpcStatus copyCollection(String sourceCollectionName, String targetCollectionName) {
        DescCollResponseWrapper wrapper = describeCollection(sourceCollectionName);
        // 创建collection
        createCollection(CreateCollectionParam.newBuilder()
                .withCollectionName(targetCollectionName)
                .withFieldTypes(wrapper.getFields())
                .withDescription(wrapper.getCollectionDescription())
                .withEnableDynamicField(wrapper.getEnableDynamicField())
                .withShardsNum(wrapper.getShardNumber())
                .build());
        FieldType vectorField = wrapper.getVectorField();
        // 获取向量索引
        DescIndexResponseWrapper.IndexDesc indexDesc = describeIndex(sourceCollectionName, vectorField.getName());
        // 创建向量索引
        return createIndex(CreateIndexParam.newBuilder()
                .withCollectionName(targetCollectionName)
                .withFieldName(indexDesc.getFieldName())
                .withMetricType(indexDesc.getMetricType())
                .withIndexType(indexDesc.getIndexType())
                .withExtraParam(StringUtils.isBlank(indexDesc.getExtraParam()) ? indexDesc.getExtraParam() : "{}")
                .build());
    }

    /**
     * 删除特征库
     *
     * @param collectionName collection名称
     * @return io.milvus.param.R<io.milvus.param.RpcStatus>
     * @author 王强
     */
    public RpcStatus dropCollection(String collectionName) {
        // milvus释放collection，释放后才能删除
        releaseCollection(collectionName);
        R<RpcStatus> response = milvusClient.dropCollection(DropCollectionParam.newBuilder()
                .withCollectionName(collectionName)
                .build());
        handleResponseStatus(response);
        return response.getData();
    }

    /**
     * 删除特征库
     *
     * @param collectionNameList 集合名称列表
     * @return io.milvus.param.R<io.milvus.param.RpcStatus>
     * @author 王强
     */
    public boolean dropCollections(List<String> collectionNameList) {
        collectionNameList.stream().filter(this::hasCollection).forEach(this::dropCollection);
        return true;
    }

    /**
     * 判断collection是否存在
     *
     * @param collectionName collection名称
     * @return boolean
     * @author 王强
     */
    public boolean hasCollection(String collectionName) {
        R<Boolean> response = milvusClient.hasCollection(HasCollectionParam.newBuilder()
                .withCollectionName(collectionName)
                .build());
        handleResponseStatus(response);
        return response.getData();
    }

    /**
     * 加载collection到内存中，加载到内存中到数据才能被查到
     *
     * @param collectionName collection名称
     * @return io.milvus.param.RpcStatus
     * @author 王强
     */
    public RpcStatus loadCollection(String collectionName) {
        R<RpcStatus> response = milvusClient.loadCollection(LoadCollectionParam.newBuilder()
                .withCollectionName(collectionName)
                // 开启同步模式
                .withSyncLoad(true)
                // 检测间隔时间
                .withSyncLoadWaitingInterval(200L)
                // 检测超时时间
                .withSyncLoadWaitingTimeout(3L)
                .build());
        handleResponseStatus(response);
        return response.getData();
    }

    /**
     * 释放collection，将内存中的数据释放
     *
     * @param collectionName collection名称
     * @return io.milvus.param.RpcStatus
     * @author 王强
     */
    public RpcStatus releaseCollection(String collectionName) {
        R<RpcStatus> response = milvusClient.releaseCollection(ReleaseCollectionParam.newBuilder()
                .withCollectionName(collectionName)
                .build());
        handleResponseStatus(response);
        return response.getData();
    }

    /**
     * 重命名collection
     *
     * @param oldCollectionName oldcollection名称
     * @param newCollectionName newcollection名称
     * @return io.milvus.param.RpcStatus
     * @author 王强
     */
    public RpcStatus renameCollection(String oldCollectionName, String newCollectionName) {
        R<RpcStatus> response = milvusClient.renameCollection(RenameCollectionParam.newBuilder()
                .withOldCollectionName(oldCollectionName)
                .withNewCollectionName(newCollectionName)
                .build());
        handleResponseStatus(response);
        return response.getData();
    }

    /**
     * 获取collection统计信息
     *
     * @param collectionName    collection名称
     * @return long             数据条数
     * @author 王强
     */
    public long getCollectionStatistics(String collectionName) {
        R<GetCollectionStatisticsResponse> response = milvusClient.getCollectionStatistics(GetCollectionStatisticsParam.newBuilder()
                .withCollectionName(collectionName)
                .build());
        handleResponseStatus(response);
        // 包装结果
        GetCollStatResponseWrapper wrapper = new GetCollStatResponseWrapper(response.getData());
        return wrapper.getRowCount();
    }

    /**
     * 显示所有collection
     *
     * @return java.util.List<io.milvus.response.ShowCollResponseWrapper.CollectionInfo>
     * @author 王强
     */
    public List<ShowCollResponseWrapper.CollectionInfo> showCollections() {
        R<ShowCollectionsResponse> response = milvusClient.showCollections(ShowCollectionsParam.newBuilder().build());
        handleResponseStatus(response);
        // 包装结果
        ShowCollResponseWrapper wrapper = new ShowCollResponseWrapper(response.getData());
        return wrapper.getCollectionsInfo();
    }

    /**
     * 获取collection信息
     *
     * @param collectionName collection名称
     * @return io.milvus.response.DescCollResponseWrapper
     * @author 王强
     */
    public DescCollResponseWrapper describeCollection(String collectionName) {
        R<DescribeCollectionResponse> response = milvusClient.describeCollection(DescribeCollectionParam.newBuilder()
                .withCollectionName(collectionName)
                .build());
        handleResponseStatus(response);
        return new DescCollResponseWrapper(response.getData());
    }

    /**
     * 列出所有collection
     *
     * @return java.util.List<java.lang.String>
     * @author 王强
     */
    public List<String> listCollections() {
        R<ListCollectionsResponse> response = milvusClient.listCollections(ListCollectionsParam.newBuilder().build());
        handleResponseStatus(response);
        return response.getData().collectionNames;
    }

    /**
     * 创建index
     *
     * @param param     index创建参数
     * @return io.milvus.param.RpcStatus
     * @author 王强
     */
    public RpcStatus createIndex(CreateIndexParam param) {
        R<RpcStatus> response = milvusClient.createIndex(param);
        handleResponseStatus(response);
        return response.getData();
    }

    /**
     * 获取索引信息
     *
     * @param collectionName collection名称
     * @param indexName      index名称
     * @return io.milvus.response.DescIndexResponseWrapper.IndexDesc
     * @author 王强
     */
    public DescIndexResponseWrapper.IndexDesc describeIndex(String collectionName, String indexName) {
        R<DescribeIndexResponse> response = milvusClient.describeIndex(DescribeIndexParam.newBuilder()
                .withCollectionName(collectionName)
                .withIndexName(indexName)
                .build());
        handleResponseStatus(response);
        DescIndexResponseWrapper wrapper = new DescIndexResponseWrapper(response.getData());
        return wrapper.getIndexDescriptions().get(0);
    }

    /**
     * 删除索引
     *
     * @param collectionName collection名称
     * @param indexName      index名称
     * @return io.milvus.param.RpcStatus
     * @author 王强
     */
    public RpcStatus dropIndex(String collectionName, String indexName) {
        R<RpcStatus> response = milvusClient.dropIndex(DropIndexParam.newBuilder()
                .withCollectionName(collectionName)
                .withIndexName(indexName)
                .build());
        handleResponseStatus(response);
        return response.getData();
    }

    /**
     * 创建partition
     *
     * @param collectionName collection名称
     * @param partitionName  partition名称
     * @return io.milvus.param.RpcStatus
     * @author 王强
     */
    public RpcStatus createPartition(String collectionName, String partitionName) {
        R<RpcStatus> response = milvusClient.createPartition(CreatePartitionParam.newBuilder()
                .withCollectionName(collectionName)
                .withPartitionName(partitionName)
                .build());
        handleResponseStatus(response);
        return response.getData();
    }

    /**
     * 删除partition
     *
     * @param collectionName collection名称
     * @param partitionName  partition名称
     * @return io.milvus.param.RpcStatus
     * @author 王强
     */
    public RpcStatus dropPartition(String collectionName, String partitionName) {
        // milvus释放partition，释放后才能删除
        releasePartition(collectionName, partitionName);
        R<RpcStatus> response = milvusClient.dropPartition(DropPartitionParam.newBuilder()
                .withCollectionName(collectionName)
                .withPartitionName(partitionName)
                .build());
        handleResponseStatus(response);
        return response.getData();
    }

    /**
     * 判断partition是否存在
     *
     * @param collectionName collection名称
     * @param partitionName  partition名称
     * @return java.lang.Boolean
     * @author 王强
     */
    public Boolean hasPartition(String collectionName, String partitionName) {
        R<Boolean> response = milvusClient.hasPartition(HasPartitionParam.newBuilder()
                .withCollectionName(collectionName)
                .withPartitionName(partitionName)
                .build());
        handleResponseStatus(response);
        return response.getData();
    }

    /**
     * 加载partition到内存中，加载到内存中到数据才能被查到
     *
     * @param collectionName collection名称
     * @param partitionName  partition名称
     * @return io.milvus.param.RpcStatus
     * @author 王强
     */
    public RpcStatus loadPartition(String collectionName, String partitionName) {
        R<RpcStatus> response = milvusClient.loadPartitions(LoadPartitionsParam.newBuilder()
                .withCollectionName(collectionName)
                .addPartitionName(partitionName)
                // 开启同步模式
                .withSyncLoad(true)
                // 检测间隔时间
                .withSyncLoadWaitingInterval(200L)
                // 检测超时时间
                .withSyncLoadWaitingTimeout(3L)
                .build());
        handleResponseStatus(response);
        return response.getData();
    }

    /**
     * 释放partition，将内存中的数据释放
     *
     * @param collectionName collection名称
     * @param partitionName  partition名称
     * @return io.milvus.param.RpcStatus
     * @author 王强
     */
    public RpcStatus releasePartition(String collectionName, String partitionName) {
        R<RpcStatus> response = milvusClient.releasePartitions(ReleasePartitionsParam.newBuilder()
                .withCollectionName(collectionName)
                .withPartitionNames(Collections.singletonList(partitionName))
                .build());
        handleResponseStatus(response);
        return response.getData();
    }

    /**
     * 获取partition统计信息
     *
     * @param collectionName    collection名称
     * @param partitionName     partition名称
     * @return long             数据条数
     * @author 王强
     */
    public long getPartitionStatistics(String collectionName, String partitionName) {
        R<GetPartitionStatisticsResponse> response = milvusClient.getPartitionStatistics(GetPartitionStatisticsParam.newBuilder()
                .withCollectionName(collectionName)
                .withPartitionName(partitionName)
                .build());
        handleResponseStatus(response);
        // 包装结果
        GetPartStatResponseWrapper wrapper = new GetPartStatResponseWrapper(response.getData());
        return wrapper.getRowCount();
    }

    /**
     * 显示所有partition
     *
     * @param collectionName collection名称
     * @return java.util.List<io.milvus.response.ShowPartResponseWrapper.PartitionInfo>
     * @author 王强
     */
    public List<ShowPartResponseWrapper.PartitionInfo> showPartitions(String collectionName) {
        R<ShowPartitionsResponse> response = milvusClient.showPartitions(ShowPartitionsParam.newBuilder()
                .withCollectionName(collectionName)
                .build());
        handleResponseStatus(response);
        // 包装结果
        ShowPartResponseWrapper wrapper = new ShowPartResponseWrapper(response.getData());
        return wrapper.getPartitionsInfo();
    }


    public boolean insert(String COLLECTION_NAME, String partitionName, JsonObject o) {
        List<JsonObject> rowsData = new ArrayList<>();
        // 将User对象转换为JSONObject
        rowsData.add(o);
        InsertParam insertParam = InsertParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withPartitionName(partitionName)
                .withRows(rowsData)
                .build();
        R<MutationResult> response = milvusClient.insert(insertParam);
        handleResponseStatus(response);
        return true;
    }

    /**
     * 新增行数据
     *
     * @param collectionName collection名称
     * @param dataList 新增的行数据
     * @author 王强
     */
    public MutationResult insertRows(String collectionName, List<JsonObject> dataList) {
        InsertParam insertParam = InsertParam.newBuilder()
                .withCollectionName(collectionName)
                .withRows(dataList)
                .build();
        R<MutationResult> response = milvusClient.insert(insertParam);
        handleResponseStatus(response);
        return response.getData();
    }

    /**
     * 新增行数据
     *
     * @param collectionName collection名称
     * @param dataList 新增的行数据
     * @author 王强
     */
    public MutationResult insertRows(String collectionName, String partitionName, List<JsonObject> dataList) {
        InsertParam insertParam = InsertParam.newBuilder()
                .withCollectionName(collectionName)
                .withPartitionName(partitionName)
                .withRows(dataList)
                .build();
        R<MutationResult> response = milvusClient.insert(insertParam);
        handleResponseStatus(response);
        return response.getData();
    }

    /**
     * 查询
     *
     * @param callBack 查询参数
     * @return java.util.List<io.milvus.response.QueryResultsWrapper.RowRecord>
     * @author 王强
     */
    public List<QueryResultsWrapper.RowRecord> search(Consumer<SearchParam.Builder> callBack) {
        SearchParam.Builder builder = SearchParam.newBuilder();
        callBack.accept(builder);
        R<SearchResults> response = milvusClient.search(builder.build());
        handleResponseStatus(response);
        // 包装结果
        SearchResultsWrapper wrapper = new SearchResultsWrapper(response.getData().getResults());
        return wrapper.getRowRecords(0);
    }

    /**
     * 查询
     *
     * @param callBack 配置查询参数的回调函数
     * @return java.util.List<io.milvus.response.QueryResultsWrapper.RowRecord> 查询结果的列表
     * @author 王强
     */
    public List<QueryResultsWrapper.RowRecord> query(Consumer<QueryParam.Builder> callBack) {
        QueryParam.Builder builder = QueryParam.newBuilder();
        callBack.accept(builder);

        // 假设 milvusClient 提供了一个 query 方法
        R<QueryResults> response = milvusClient.query(builder.build());

        // 处理响应状态，确保调用成功
        handleResponseStatus(response);

        // 封装并处理查询结果
        QueryResultsWrapper wrapper = new QueryResultsWrapper(response.getData());
        return wrapper.getRowRecords();
    }


    /**
     * 获取主键字段
     *
     * @param collectionName collection名称
     * @return io.milvus.param.collection.FieldType
     * @author 王强
     */
    public FieldType getPrimaryField(String collectionName) {
        DescCollResponseWrapper wrapper = describeCollection(collectionName);
        return wrapper.getPrimaryField();
    }

    /**
     * 删除
     *
     * @param collectionName 集合名称
     * @param expr           删除条件
     * @return io.milvus.grpc.MutationResult
     * @author 王强
     */
    public MutationResult delete(String collectionName, String expr) {
        DeleteParam build = DeleteParam.newBuilder()
                .withCollectionName(collectionName)
                .withExpr(expr)
                .build();
        R<MutationResult> delete = milvusClient.delete(build);
        handleResponseStatus(delete);
        return delete.getData();
    }

}
