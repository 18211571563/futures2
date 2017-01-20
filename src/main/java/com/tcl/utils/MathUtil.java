package com.tcl.utils;

/**
 * Created by yangqj on 2016/12/7.
 */
public class MathUtil {

    /**
     * 保留两位小数
     * @param d
     * @return
     */
    public static double formatDouble(double d) {
        return (double)Math.round(d*100)/100;
    }

}
