package com.guohuili.administrator.huiweather.util;

/**
 * Created by Administrator on 2015/6/11 0011.
 */
public interface HttpCallBackListener {
    void onFinish(String response);
    void onError(Exception e);
}
