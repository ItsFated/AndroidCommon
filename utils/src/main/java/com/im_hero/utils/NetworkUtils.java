package com.im_hero.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Jason
 * @version 1.0
 */

public final class NetworkUtils {

    public static boolean isWifiApEnabled(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        try {
            Method isWifiApEnabled = WifiManager.class.getDeclaredMethod("isWifiApEnabled");
            isWifiApEnabled.setAccessible(true);
            return  (boolean) isWifiApEnabled.invoke(wifiManager);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isWifiConnected(Context context) {
        boolean isWifiConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo info;
            for (Network network : networks) {
                info = connectivityManager.getNetworkInfo(network);
                if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                    isWifiConnected = info.isConnected();
                    break;
                }
            }
        } else {
            isWifiConnected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
        }
        return isWifiConnected;
    }

    private NetworkUtils() {}
}
