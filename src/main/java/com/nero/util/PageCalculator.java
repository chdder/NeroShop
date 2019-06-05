package com.nero.util;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/6/4 0004
 * Time: 15:26
 */
public class PageCalculator {
    public static int calculateRowIndex(int pageIndex, int pageSize) {
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }
}
