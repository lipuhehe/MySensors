package com.snnu.mysensors.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    public static String getTime() {
        //long time = System.currentTimeMillis();//long now = android.os.SystemClock.uptimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        Date current = new Date();
        String currentTime = format.format(current);
        return currentTime;
    }

}
