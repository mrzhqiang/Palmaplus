package com.palmaplus.nagrand.demo.constance;

import com.palmaplus.nagrand.data.Param;

/**
 * Created by zhang on 2015/4/29.
 */
public class Constant {
    public static final String LUR_NAME = "Nagrand/lua"; // lua地址
    public static int DEFAULT_MALL_ID = 1671; // 商场默认ID
    public static String SERVER_URL = "http://api.ipalmap.com/"; // 地图数据服务器地址
    //public static String SERVER_URL = "http://172.16.10.239:8080/nagrand-service/";
    //public static String SERVER_URL = "http://172.16.10.75:8080/nagrand-service/";
    public static Param<String> FLOOR_SHOW_FIELD = new Param<String>("address", String.class); // 楼层下拉菜单中显示字段
    //public static String APP_KEY = "3474b094bcbb4279a2f2c1fa2945cae4"; // appKey，可以从图聚的开发者平台上获取
    public static String APP_KEY = "3313e3d0712f48eda7e2a855b0048dee";
    //public static String APP_KEY ="2a7a769a09e64156a03bde39039be857";
    //public static String APP_KEY = "599f230f734640e4863dd815816f50c1";
}
