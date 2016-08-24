package cn.chenhai.miaodj_monitor.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    /**
     * 服务器返回的是毫秒，转换成格式化时间字符串
     */
    public static String getFormatTime(String longTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");

        try {
            return sdf.format(Long.parseLong(longTime));
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 格式化时间为yyyy-MM-dd
     *
     * @param longTime
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getFormat(String longTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return sdf.format(Long.parseLong(longTime));
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 格式化时间为yyyy-MM-dd HH:mm
     *
     * @param longTime
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getFormatAll(String longTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            return sdf.format(Long.parseLong(longTime));
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 服务器返回的yyyy-MM-dd HH:mm:ss，转换为yyyy-MM-dd
     *
     * @param str
     * @return
     */
    public static String getDate(String str) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM-dd");

        Date d = null;
        try {
            if (TextUtils.isEmpty(str)) {
                return "";
            }
            d = sd.parse(str);
            return sd2.format(d);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 服务器返回的yyyy-MM-dd HH:mm:ss，转换为yyyy-MM-dd HH:mm
     *
     * @param str
     * @return
     */
    public static String getDateForMin(String str) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sd2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date d = null;
        try {
            if (TextUtils.isEmpty(str)) {
                return "";
            }
            d = sd.parse(str);
            return sd2.format(d);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";

    }

}
