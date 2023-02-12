package com.lakala.pos.common;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import androidx.annotation.RequiresApi;
import com.lakala.pos.utils.LogUtil;
import java.util.UUID;

public class DeviceInfo {



    public static void initDevice(Context context) {
        try {
            // 获取WIFI服务
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            // 判断WIFI是否开启
            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
            }
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (null != wifiInfo) {
                int ipAddress = wifiInfo.getIpAddress();
                Global.IP = intToIp(ipAddress);
                LogUtil.i("ip = " +   Global.IP );
            }
        } catch (Throwable e) {
            LogUtil.i("DeviceInfo" + e.getMessage());
        }

        Global.DEVICE_ID = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        if (TextUtils.isEmpty(Global.DEVICE_ID)) {
            Global.DEVICE_ID = "DN:" + UUID.randomUUID().toString();
        }

        LogUtil.i("DeviceID: " + Global.DEVICE_ID);
    }

    public static String getAndroidId(Context context) {
        String androidId = "";
        androidId = android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        return androidId == null ? "" : androidId;
    }



    private static String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }


}
