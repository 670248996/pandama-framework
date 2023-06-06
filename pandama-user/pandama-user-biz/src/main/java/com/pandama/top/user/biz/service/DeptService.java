package com.pandama.top.user.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pandama.top.user.api.pojo.dto.DeptCreateDTO;
import com.pandama.top.user.api.pojo.dto.DeptSearchDTO;
import com.pandama.top.user.api.pojo.dto.DeptUpdateDTO;
import com.pandama.top.user.api.pojo.vo.DeptCreateResultVO;
import com.pandama.top.user.api.pojo.vo.DeptDetailResultVO;
import com.pandama.top.user.api.pojo.vo.DeptSearchResultVO;
import com.pandama.top.user.api.pojo.vo.DeptTreeVO;
import com.pandama.top.user.biz.entity.SysDept;

import java.util.List;
import java.util.Map;

/**
 * @description: 部门信息接口类
 * @author: 白剑民
 * @dateTime: 2022/10/21 16:00
 */
public interface DeptService extends IService<SysDept> {

    /**
     * @description: 生成部门编号
     * @author: hyj
     * @date: 2022-11-14 13:48:23
     * @return: java.lang.String
     * @version: 1.0
     */
    String getNo();

    /**
     * @param dto 创建部门传参
     * @description: 创建部门
     * @author: 白剑民
     * @date: 2022-10-24 17:32:08
     * @return: com.gientech.iot.user.entity.vo.DepartmentCreateResultVO
     * @version: 1.0
     */
    DeptCreateResultVO create(DeptCreateDTO dto);

    /**
     * @param deptId 部门id
     * @description: 部门详情
     * @author: 王强
     * @date: 2023-05-23 14:32:05
     * @return: DepartmentDetailResultVO
     * @version: 1.0
     */
    DeptDetailResultVO detail(Long deptId);

    /**
     * @param dto 部门信息更新传参
     * @description: 部门信息更新
     * @author: 白剑民
     * @date: 2022-10-25 09:47:35
     * @version: 1.0
     */
    void update(DeptUpdateDTO dto);

    /**
     * @param departmentIds 部门id列表
     * @description: 删除部门信息
     * @author: 白剑民
     * @date: 2022-10-26 14:23:49
     * @version: 1.0
     */
    void delete(List<Long> departmentIds);

    /**
     * @param dto 部门信息传参
     * @description: 部门信息列表
     * @author: 白剑民
     * @date: 2022-10-24 17:32:08
     * @return: com.gientech.iot.user.entity.vo.DepartmentCreateResultVO
     * @version: 1.0
     */
    List<DeptSearchResultVO> list(DeptSearchDTO dto);

    /**
     * @param userIds 用户id列表
     * @description: 根据指定用户id列表查询部门信息列表
     * @author: 白剑民
     * @date: 2022-10-24 17:32:08
     * @return: com.gientech.iot.user.entity.vo.DepartmentCreateResultVO
     * @version: 1.0
     */
    List<DeptSearchResultVO> listByUserIds(List<Long> userIds);

    /**
     * @param userIds 用户id列表
     * @description: 根据指定用户id列表查询用户部门信息Map
     * @author: 白剑民
     * @date: 2022-10-24 17:32:08
     * @return: com.gientech.iot.user.entity.vo.DepartmentCreateResultVO
     * @version: 1.0
     */
    Map<Long, DeptSearchResultVO> mapByUserIds(List<Long> userIds);

    /**
     * @param dto 部门信息传参
     * @description: 部门信息树
     * @author: 白剑民
     * @date: 2022-10-24 17:32:08
     * @return: com.gientech.iot.user.entity.vo.DepartmentCreateResultVO
     * @version: 1.0
     */
    List<DeptTreeVO> tree(DeptSearchDTO dto);

    /**
     * @param enterpriseId 企业/机构id
     * @description: 根据企业/机构id获取部门列表
     * @author: 白剑民
     * @date: 2022-10-21 16:01:15
     * @return: java.util.List<com.gientech.iot.user.entity.vo.DepartmentSearchResultVO>
     * @version: 1.0
     */
    List<DeptSearchResultVO> getListByEnterpriseId(Long enterpriseId);

    /**
     * @param enterpriseId 企业/机构id
     * @description: 根据企业/机构id获取部门树
     * @author: 白剑民
     * @date: 2022-10-21 16:01:15
     * @return: java.util.List<com.gientech.iot.user.entity.vo.DepartmentTreeVO>
     * @version: 1.0
     */
    List<DeptTreeVO> getTreeByEnterpriseId(Long enterpriseId);

    /**
     * @param deptId 父级部门id
     * @description: 根据父级部门id获取部门树
     * @author: 白剑民
     * @date: 2022-10-24 16:17:16
     * @return: java.util.List<com.gientech.iot.user.entity.vo.DepartmentTreeVO>
     * @version: 1.0
     */
    List<DeptTreeVO> getTreeByDepartmentId(Long deptId);
}
