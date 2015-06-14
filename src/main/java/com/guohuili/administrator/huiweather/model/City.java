package com.guohuili.administrator.huiweather.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/6/11 0011.
 */
public class City implements Serializable{

    private int id;
    private String cityName;
    private String cityCode;
    private int provinceId;

    public City() {

    }

    public City(int id, String cityName, String cityCode, int provinceId) {
        this.id = id;
        this.cityName = cityName;
        this.cityCode = cityCode;
        this.provinceId = provinceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
