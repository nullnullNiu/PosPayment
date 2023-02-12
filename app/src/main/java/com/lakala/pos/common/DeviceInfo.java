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

//    public static String ip;
    public static String deviceId;


    @RequiresApi(api = Build.VERSION_CODES.P)
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

        deviceId = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = "DN:" + UUID.randomUUID().toString();
        }

        LogUtil.i("DeviceInfo: " + deviceId);
    }


    private static String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }


}
