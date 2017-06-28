package com.ishow.plugin.insertcode.utils;

import org.apache.http.util.TextUtils;

/**
 * Created by yuhaiyang on 2017/6/28.
 * String
 */
public class StringUtils {

    public static final String EMPTY = "";
    /**
     * 换行
     */
    @SuppressWarnings("unused")
    public static final String NEW_LINE = "\n";
    /**
     * 制表符
     */
    @SuppressWarnings("unused")
    public static final String TAB = "\t";
    /**
     * 人民币
     */
    @SuppressWarnings("unused")
    public static final String MONEY = "￥";
    /**
     * 字符串0通常用来计算
     */
    @SuppressWarnings("unused")
    public static final String ZERO = "0";

    /**
     * 字符串累加
     */
    public static String plusString(Object... strs) {
        StringBuilder builder = new StringBuilder();
        for (Object str : strs) {
            String _str = String.valueOf(str);
            if (!TextUtils.isEmpty(_str)) {
                builder.append(str);
            }
        }
        return builder.toString();
    }


    public static String format(String content) {
        String username = System.getenv("USERNAME");
        String date = DateUtils.format(System.currentTimeMillis(), DateUtils.FORMAT_YMD);
        content = content.replaceAll("\\$\\{USER}", username);
        content = content.replaceAll("\\$\\{DATE}", date);
        return content;
    }
}
