package com.demo.clean;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public final class Utils {

    private static final String DEFAULT_MAC_ADDRESS = "02:00:00:00:00:00";

    private Utils() {

    }

    @SuppressLint("ApplySharedPref")
    public static void saveDefaultDeviceId(Context context, String deviceId) {
        if (TextUtils.isEmpty(getDefaultDeviceId(context))) {
            SharedPreferences sp = context.getSharedPreferences("clean_application", Context.MODE_PRIVATE);
            sp.edit().putString("default_device_id", deviceId).commit();
        }
    }

    public static String getDefaultDeviceId(Context context) {
        SharedPreferences sp = context.getSharedPreferences("clean_application", Context.MODE_PRIVATE);
        return sp.getString("default_device_id", "");
    }

    public static String getIMEI(Context context) {
        if (PackageManager.PERMISSION_GRANTED !=
                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)) {
            return "";
        }
        String imei = null;
        TelephonyManager telephonyManager = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                imei = telephonyManager.getImei();
//            } else {
//                imei = telephonyManager.getDeviceId();
//            }
            String a = telephonyManager.getImei();
            Log.e("test", "=======getImei=====" + a);
            imei = telephonyManager.getDeviceId();
            Log.e("test", "=======getDeviceId=======" + imei);
        }
        return imei != null ? imei : "";
    }

    public static String getSimSerialNumber(Context context) {
        if (PackageManager.PERMISSION_GRANTED !=
                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)) {
            return "";
        }
        String simNumber = null;
        TelephonyManager telephonyManager = (TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            simNumber = telephonyManager.getSimSerialNumber();
        }
        return simNumber != null ? simNumber : "";
    }

    // 295a4fbf716094ee
    public static String getAndroidId(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId != null ? androidId : "";
    }

    // WTK7N16923005607
    public static String getSerialNo(Context context) {
        String serialNumber = "";
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    serialNumber = Build.getSerial();
                } else {
                    serialNumber = Build.SERIAL;
                }
            } catch (Exception e) {
                //
            }
        } else {
            serialNumber = Build.SERIAL;
        }
        return serialNumber;
    }

    public static String getMacAddress(Context context) {
        if (PackageManager.PERMISSION_GRANTED !=
                ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)) {
            return "";
        }
        String mac = null;
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0"))
                    continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    mac = "";
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (byte b : macBytes) {
                        sb.append(byteToHexString(b) + ":");
                    }

                    if (sb.length() > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    mac = sb.toString().toLowerCase();
                    if (!isMacAddressValid(mac)) {
                        mac = "";
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return DEFAULT_MAC_ADDRESS.equals(mac) ? "" : mac;
    }

    private final static String[] hexDigits = {
            "0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "a", "b", "c", "d", "e", "f"};

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n >>> 4 & 0xf;
        int d2 = n & 0xf;
        return hexDigits[d1] + hexDigits[d2];
    }

    private static boolean isMacAddressValid(String macAddress) {
        if (TextUtils.isEmpty(macAddress)) {
            return false;
        }

        Pattern pattern = Pattern.compile("([A-Fa-f0-9]{2}:){5}[A-Fa-f0-9]{2}");
        return pattern.matcher(macAddress).matches();
    }
}
