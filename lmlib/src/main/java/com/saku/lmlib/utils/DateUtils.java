package com.saku.lmlib.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by liumin on 2017/9/8.
 */

public class DateUtils {

    public static String long2Date(long milliSeconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return sdf.format(new Date(milliSeconds));

    }
}
