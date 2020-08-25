package com.example.demo.util;

/**
 * @author mulj
 * @date 2020/8/7 17:02
 * @Email:mlj@citycloud.com.cn
 */
public class StringUtil {
    private static final String STR_NULL = "null";
    private static final String UNDEFINED_NULL = "undefined";
    /**
     * 字符串转化成为16进制字符串
     * @param s
     * @return
     */
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }
    /**
     * 数组拼接字符串
     */
    public static String joinArray(String flag,String[] arry){
        String ret = "";//拼接之后的字符串
        for (int i = 0; i < arry.length; i++) {
            if (i < arry.length - 1){
                ret += arry[i] + flag; //加上特定符号
            }else {
                ret += arry[i];
            }
        }
        return ret;
    }
    /**
     * 空值转换
     *
     * @param value
     * @return
     */
    public static String null2Empty(Object value) {
        if (value == null) {
            return "";
        } else if (STR_NULL.equals(value)) {
            return "";
        } else if (UNDEFINED_NULL.equals(value)) {
            return "";
        } else {
            return value.toString();
        }
    }
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    public static String checkType(String xxxx) {

        switch (xxxx) {
            case "FFD8FF": return "jpg";
            case "89504E": return "png";
            case "474946": return "jif";

            default: return "0000";
        }
    }
}
