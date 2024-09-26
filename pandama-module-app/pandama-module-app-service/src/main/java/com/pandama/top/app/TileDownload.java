package com.pandama.top.app;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TileDownload {

    //不同的瓦片采用不同的下载地址，一般用的比较多的还是矢量地图
    //矢量（行政） - 等经纬度
    public static String vec_c = "http://{server}.tianditu.gov.cn/vec_c/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=vec&STYLE=default&TILEMATRIXSET=c&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&tk={tk}";
    //矢量（行政） - 墨卡托
    public static String vec_w = "http://{server}.tianditu.gov.cn/vec_w/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=vec&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&tk={tk}";
    //矢量注记（行政） - 等经纬度
    public static String cva_c = "http://{server}.tianditu.gov.cn/cva_c/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=cva&STYLE=default&TILEMATRIXSET=c&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&tk={tk}";
    //矢量注记（行政） - 墨卡托
    public static String cva_w = "http://{server}.tianditu.gov.cn/cva_w/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=cva&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&tk={tk}";

//    //影像 - 等经纬度
//    public static String img_c = "http://{server}.tianditu.gov.cn/img_c/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=img&STYLE=default&TILEMATRIXSET=c&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&tk={tk}";
//    //影像 - 墨卡托
//    public static String img_w = "http://{server}.tianditu.gov.cn/img_w/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=img&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&tk={tk}";
//    //影像注记 - 等经纬度
//    public static String cia_c = "http://{server}.tianditu.gov.cn/cia_c/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=cia&STYLE=default&TILEMATRIXSET=c&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&tk={tk}";
//    //影像注记 - 墨卡托
//    public static String cia_w = "http://{server}.tianditu.gov.cn/cia_w/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=cia&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&tk={tk}";
//
//    //地形 - 等经纬度
//    public static String ter_c = "http://{server}.tianditu.gov.cn/ter_c/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=ter&STYLE=default&TILEMATRIXSET=c&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&tk={tk}";
//    //地形 - 墨卡托
//    public static String ter_w = "http://{server}.tianditu.gov.cn/ter_w/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=ter&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&tk={tk}";
//    //地形注记 - 等经纬度
//    public static String cta_c = "http://{server}.tianditu.gov.cn/cta_c/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=cta&STYLE=default&TILEMATRIXSET=c&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&tk={tk}";
//    //地形注记 - 墨卡托
//    public static String cta_w = "http://{server}.tianditu.gov.cn/cta_w/wmts?SERVICE=WMTS&REQUEST=GetTile&VERSION=1.0.0&LAYER=cta&STYLE=default&TILEMATRIXSET=w&FORMAT=tiles&TILEMATRIX={z}&TILEROW={y}&TILECOL={x}&tk={tk}";

    public static String[] servers = {"t0", "t1", "t2", "t3", "t4", "t5", "t6", "t7"};


    public static void main(String[] args){
        //设置下载的路径
        String basePath = "lianyungangtianditu";

        //这里放你的天地图开发者秘钥，注意天地图API访问次数限制
        String tk = "d93541df9cfda6fdc90bf3f98b89ea3f";

        //将需要下载的图层地址放到一个数组中，不需要下载的可以去掉
        String[] urlArr = {vec_c, vec_w, cva_c, cva_w};

        //天地图一共有18个图层，从1-18，越往后，瓦片数据量越大
        int minZoom = 1;
        int maxZoom = 15;
        //中国的经纬度
        //double startLat = 53.58;//开始纬度（从北到南）
        //double endLat = 2.7;//结束纬度（从北到南）
        //double startLon = 73.2;//开始经度（从西到东）
        //double endLon = 135.15;//结束经度（从西到东）
        //江苏连云港的经纬度，这边经纬度可以根据自己需要下载的地市或者省份去百度查询经纬度信息，我这边只下载连云港
        double startLat = 35.07;//开始纬度（从北到南）
        double endLat = 34.12;//结束纬度（从北到南）
        double startLon = 118.24;//开始经度（从西到东）
        double endLon = 119.48;//结束经度（从西到东）

        ExecutorService exe = Executors.newFixedThreadPool(15);
        for(int i=0; i<urlArr.length; i++){
            String url = urlArr[i].replace("{tk}", tk);
            System.out.println(url);
            String layerName = url.split("tianditu.gov.cn/")[1].split("/wmts?")[0];
            System.out.println(layerName);
            if(layerName.endsWith("c")){

                for(int z=minZoom; z<=maxZoom; z++){
                    double deg = 360.0 / Math.pow(2, z) / 256;//一个像素点代表多少度
                    int startX = (int)((startLon + 180) / deg / 256);//减数取整
                    int endX = (int)((endLon + 180) / deg / 256);//加数取整
                    int startY = (int)((90 - startLat) / deg / 256);
                    int endY = (int)((90 - endLat) / deg / 256);
                    for(int y=startY; y<=endY; y++){
                        for(int x=startX; x<=endX; x++){
                            final String newUrl = url.replace("{server}", servers[(int) (Math.random()*servers.length)]).replace("{z}", z+"").replace("{y}", y+"").replace("{x}", x+"");
                            final String filePath = basePath + "/" + layerName + "/" + z + "/" + y + "/" + x + ".png";
                            exe.execute(new Runnable() {
                                @Override
                                public void run() {
                                    File file = new File(filePath);
                                    if(!file.exists()){
                                        if(!file.getParentFile().exists()){
                                            file.getParentFile().mkdirs();
                                        }
                                        boolean loop = true;
                                        int count = 0;
                                        while(loop && count<5){
                                            count++;
                                            try {
                                                InputStream in = getFileInputStream(newUrl);
                                                OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
                                                byte[] b = new byte[8192];
                                                int len = 0;
                                                while((len = in.read(b)) > -1){
                                                    out.write(b, 0, len);
                                                    out.flush();
                                                }
                                                out.close();
                                                in.close();
                                                loop = false;
                                            } catch (Exception e) {
                                                loop = true;
                                            }
                                        }
                                        if(loop){
                                            System.out.println("下载失败："+newUrl);
                                            System.exit(0);//下载失败后停止任务
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            }else{
                //墨卡托
                if(startLat > 85.051128){
                    startLat = 85.051128;
                }
                if(endLat <- 85.051128){
                    endLat = -85.051128;
                }

                for(int z=minZoom; z<=maxZoom; z++){
                    double deg = 360.0 / Math.pow(2, z) / 256;
                    int startX = (int)((startLon + 180) / deg / 256);
                    int endX = (int)((endLon + 180) / deg / 256);
                    int startY = (((int)Math.pow(2, z) * 256 / 2) - (int)((Math.log(Math.tan((90 + startLat) * Math.PI / 360)) / (Math.PI / 180)) / (360/Math.pow(2, z)/256) + 0.5)) / 256;
                    int endY = (((int)Math.pow(2, z) * 256 / 2) - (int)((Math.log(Math.tan((90 + endLat) * Math.PI / 360)) / (Math.PI / 180)) / (360/Math.pow(2, z)/256) + 0.5)) / 256;
                    for(int y=startY; y<=endY; y++){
                        for(int x=startX; x<=endX; x++){
                            final String newUrl = url.replace("{server}", servers[(int) (Math.random()*servers.length)]).replace("{z}", z+"").replace("{y}", y+"").replace("{x}", x+"");
                            final String filePath = basePath + "/" + layerName + "/" + z + "/" + y + "/" + x + ".png";
                            exe.execute(new Runnable() {
                                @Override
                                public void run() {
                                    File file = new File(filePath);
                                    if(!file.exists()){
                                        if(!file.getParentFile().exists()){
                                            file.getParentFile().mkdirs();
                                        }
                                        boolean loop = true;
                                        int count = 0;
                                        while(loop && count<5){
                                            count++;
                                            try {
                                                InputStream in = getFileInputStream(newUrl);
                                                OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
                                                byte[] b = new byte[8192];
                                                int len = 0;
                                                while((len = in.read(b)) > -1){
                                                    out.write(b, 0, len);
                                                    out.flush();
                                                }
                                                out.close();
                                                in.close();
                                                loop = false;
                                            } catch (Exception e) {
                                                loop = true;
                                            }
                                        }
                                        if(loop){
                                            System.out.println("下载失败："+newUrl);
                                            System.exit(0);//下载失败后停止任务
                                        }
                                    }
                                }
                            });
                        }
                    }
                }
            }
        }
        exe.shutdown();
        while (true) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
            }
            if (exe.isTerminated()) {
                break;
            }
        }
    }


    public static InputStream getFileInputStream(String url) throws Exception{
        InputStream is = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        request.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        HttpResponse response = httpclient.execute(request);
        response.setHeader("Content-Type", "application/octet-stream");
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        }
        return is;
    }

}
