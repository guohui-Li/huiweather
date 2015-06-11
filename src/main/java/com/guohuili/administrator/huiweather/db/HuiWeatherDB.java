package com.guohuili.administrator.huiweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.guohuili.administrator.huiweather.model.City;
import com.guohuili.administrator.huiweather.model.County;
import com.guohuili.administrator.huiweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/6/11 0011.
 */
public class HuiWeatherDB {

    /*
     *  数据库名
     */
    public static final String DB_NAME = "hui_weather";

    /*
     *  数据库版本
     */
    public static final int VERSION = 1;

    private static HuiWeatherDB huiWeatherDB;
    private SQLiteDatabase db;

    /*
     *  将构造方法私有化
     */
    private HuiWeatherDB(Context context){
        HuiWeatherOpenHelper huiWeatherOpenHelper = new HuiWeatherOpenHelper(context,DB_NAME,null,VERSION);
        db = huiWeatherOpenHelper.getWritableDatabase();
    }

    /*
     *  获取HuiWeatherDB的实例
     */
    public synchronized static HuiWeatherDB getInstance(Context context){
        if(huiWeatherDB == null){
            huiWeatherDB = new HuiWeatherDB(context);
        }
        return huiWeatherDB;
    }

    /*
     *  将Province实例存储到数据库
     */
    public void saveProvince(Province province){
        if(province != null){
            ContentValues values = new ContentValues();
            values.put("province_name",province.getProvinceName());
            values.put("province_code",province.getProvinceCode());
            db.insert("Province",null,values);
        }
    }

    /*
     *  从数据库中读取全国所有的省份信息
     */
    public List<Province> loadProvinces(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province",null,null,null,null,null,null);
        if(cursor != null && cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }
        }
        return list;
    }

    /*
     *  将City实例存储到数据库
     */
    public void saveCity(City city){
        if(city != null){
            ContentValues values = new ContentValues();
            values.put("city_name",city.getCityName());
            values.put("city_code",city.getCityCode());
            values.put("province_id",city.getProvinceId());
            db.insert("City",null,values);
        }
    }

    /*
     *  从数据库读取某省下所有的城市信息。
     */
    public List<City> loadCities(int provinceId){
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City",null,"provinceId = ?",new String[]{String.valueOf(provinceId)},null,null,null);
        if (cursor != null && cursor.getCount() > 0){
            while (cursor.moveToNext()){
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(provinceId);
                list.add(city);
            }
        }
        return list;
    }

    /*
     *  将County实例存储到数据库
     */
    public void saveCounty(County county){
        if (county != null){
            ContentValues values = new ContentValues();
            values.put("county_name",county.getCountyName());
            values.put("county_code",county.getCountyCode());
            values.put("city_id",county.getCityId());
            db.insert("County",null,values);
        }
    }

    /*
     *  从数据库读取某市下所有的县信息。
     */
    public List<County> loadCounties(int cityId){
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County",null,"city_id = ?",new String[]{String.valueOf(cityId)},null,null,null);
        if (cursor != null && cursor.getCount() > 0){
            while (cursor.moveToNext()){
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityId(cityId);
                list.add(county);
            }
        }
        return list;
    }
}