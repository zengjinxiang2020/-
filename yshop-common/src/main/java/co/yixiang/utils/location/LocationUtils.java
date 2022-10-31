package co.yixiang.utils.location;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import co.yixiang.exception.BadRequestException;
import co.yixiang.utils.RedisUtil;
import co.yixiang.utils.ShopKeyUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 具体查看 https://lbs.qq.com/service/webService/webServiceGuide/webServiceGeocoder
 * @author ：Shuo Xing
 * @date ：Created in 2020-09-10 11:46
 * @description：
 * @modified By：
 * @version:
 */
public class LocationUtils {

    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 腾讯地图地址解析（地址转坐标）
     */
    private static final String QQ_MAP_GEOCODER_URL = "https://apis.map.qq.com/ws/geocoder/v1/";

    /**
     * 腾讯地图距离计算
     */
    private static final String QQ_MAP_DISTANCE_URL = "https://apis.map.qq.com/ws/direction/v1/ebicycling/";



    /**
     * 通过经纬度获取距离(单位：千米)
     *
     * @param userLat
     * @param userLng
     * @param shopLat
     * @param shopLng
     * @return
     */
    public static double getDistance(double userLat, double userLng, double shopLat,
                                     double shopLng) {
        double radLat1 = rad(userLat);
        double radLat2 = rad(shopLat);
        double a = radLat1 - radLat2;
        double b = rad(userLng) - rad(shopLng);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        distance = distance * EARTH_RADIUS;
        distance = Math.round(distance * 10000d) / 10000d;
        distance = distance * 1000;
        return NumberUtil.round(distance, 2).doubleValue();
        //return s;
    }
//    public static double getDistance(double userLat, double userLng, double shopLat,
//                                     double shopLng) {
//        double radLat1 = rad(userLat);
//        double radLat2 = rad(userLng);
//        double a = radLat1 - radLat2;
//        double b = rad(shopLat) - rad(shopLng);
//        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
//                + Math.cos(radLat1) * Math.cos(radLat2)
//                * Math.pow(Math.sin(b / 2), 2)));
//        distance = distance * EARTH_RADIUS;
//        distance = Math.round(distance * 10000d) / 10000d;
//        System.out.println("getDistance公里： " + distance);
//        distance = distance * 1000;
//        return NumberUtil.round(distance, 0).doubleValue();
//    }

    /**
     *  计算用户和商家的距离（调用腾讯地图路线规划接口）
     * @param userLat  用户位置纬度
     * @param userLng  用户位置经度
     * @param shopLat  商家位置纬度
     * @param shopLng  商家位置经度
     */
    public static double calculateDistance(double userLat, double userLng, double shopLat,
                                     double shopLng) {
        String key = RedisUtil.get(ShopKeyUtils.getTengXunMapKey());
        if (StrUtil.isBlank(key)) {
            throw new BadRequestException("请先配置腾讯地图key");
        }
        String url = StrUtil.format("?from={},{}&to={},{}&key={}", userLat,userLng, shopLat,shopLng,key);
        String distanceJson = HttpUtil.get(QQ_MAP_DISTANCE_URL + url);
        System.out.println(distanceJson);
        JSONObject jsonObject = JSON.parseObject(distanceJson);
        JSONObject result = jsonObject.getJSONObject("result");
        JSONArray routes = result.getJSONArray("routes");
        double distance = routes.getJSONObject(0).getDoubleValue("distance");
        System.out.println(userLat);
        System.out.println(userLng);
        System.out.println(shopLat);
        System.out.println(shopLng);
        System.out.println(distance);
        return distance;   //TODO:返回距离
    }


    /**
     * 腾讯地图地址解析（地址转坐标）
     * @param addr
     * @return
     */
    public static GetTencentLocationVO geocoder(String addr) {
        String key = RedisUtil.get(ShopKeyUtils.getTengXunMapKey());
        if (StrUtil.isBlank(key)) {
            throw new BadRequestException("请先配置腾讯地图key");
        }
        String url = StrUtil.format("?address={}&key={}", addr, key);
        String json = HttpUtil.get(QQ_MAP_GEOCODER_URL + url);
        return JSONObject.parseObject(json, GetTencentLocationVO.class);
    }
}
