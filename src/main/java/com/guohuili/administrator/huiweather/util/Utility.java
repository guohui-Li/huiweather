package com.guohuili.administrator.huiweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.guohuili.administrator.huiweather.activity.ChooseAreaActivity;
import com.guohuili.administrator.huiweather.db.HuiWeatherDB;
import com.guohuili.administrator.huiweather.model.City;
import com.guohuili.administrator.huiweather.model.County;
import com.guohuili.administrator.huiweather.model.Province;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2015/6/11 0011.
 */
public class Utility {

    /**
    * 解析和处理服务器返回的省级数据
    */
    public synchronized static boolean handleProvincesResponse(HuiWeatherDB huiWeatherDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    // 将解析出来的数据存储到Province表
                    huiWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }
    /**
    * 解析和处理服务器返回的市级数据
    */
    public static boolean handleCitiesResponse(HuiWeatherDB huiWeatherDB, String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length > 0) {
                for (String c : allCities) {
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    // 将解析出来的数据存储到City表
                    huiWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }
    /**
    * 解析和处理服务器返回的县级数据
    */
    public static boolean handleCountiesResponse(HuiWeatherDB huiWeatherDB, String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCounties = response.split(",");
            if (allCounties != null && allCounties.length > 0) {
                for (String c : allCounties) {
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    // 将解析出来的数据存储到County表
                    huiWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析服务器返回的JSON数据，并将解析出的数据保存在本地
     */
    public static void handleWeatherResponse(Context context,String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDesp = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("ptime");
            saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,publishTime);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    /**
     * 将服务器返回的所有天气信息存储到SharedPreferences 文件中
     */
    private static void saveWeatherInfo(Context context, String cityName, String weatherCode, String temp1, String temp2, String weatherDesp, String publishTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年m月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather_desp", weatherDesp);
        editor.putString("publish_time", publishTime);
        editor.putString("current_date", simpleDateFormat.format(new Date()));
        editor.commit();

    }

    /**
     * 将临时City数据存储到SharedPreferences 文件中
     */
    public static void saveCityData(Context context,City city){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putInt("temp_city_id", city.getId());
        editor.putString("temp_city_name", city.getCityName());
        editor.putString("temp_city_code",city.getCityCode());
        editor.putInt("temp_province_id", city.getProvinceId());
        editor.commit();
    }

    /**
     * 将临时City数据从SharedPreferences 文件中取出
     */
    public static City getCityData(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int id = prefs.getInt("temp_city_id", 0);
        String cityName = prefs.getString("temp_city_name", "");
        String cityCode = prefs.getString("temp_city_code","");
        int provinceId = prefs.getInt("temp_province_id", 0);
        City city = new City(id,cityName,cityCode,provinceId);
        return city;
    }


    /**
     * 将临时Province数据存储到SharedPreferences 文件中
     */
    public static void saveProvinceData(Context context,Province province){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putInt("temp_province_id", province.getId());
        editor.putString("temp_province_name", province.getProvinceName());
        editor.putString("temp_province_code", province.getProvinceCode());
        editor.commit();
    }

    /**
     * 将临时Province数据从SharedPreferences 文件中取出
     */
    public static Province getProvinceData(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int id = prefs.getInt("temp_province_id", 0);
        String provinceName = prefs.getString("temp_province_name", "");
        String provinceCode = prefs.getString("temp_province_code", "");
        Province province = new Province(id,provinceName,provinceCode);
        return province;
    }
}
