package com.guohuili.administrator.huiweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.guohuili.administrator.huiweather.service.AutoUpdateService;

/**
 * Created by Administrator on 2015/6/14 0014.
 */
public class AutoUpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AutoUpdateService.class);
        context.startService(i);
    }
}
