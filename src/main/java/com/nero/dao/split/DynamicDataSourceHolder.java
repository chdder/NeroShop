package com.nero.dao.split;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/6/2 0002
 * Time: 14:48
 */
public class DynamicDataSourceHolder {
    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceHolder.class);
    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();
    public static final String DB_MASTER = "master";
    public static final String DB_SLAVE = "slave";

    /**
     * 获取数据源类型
     * @return
     */
    public static String getDbType() {
        String db = contextHolder.get();
        if (db == null) {
            db = DB_MASTER;
        }
        return db;
    }

    /**
     * 设置数据源
     * @param str
     */
    public static void setDbType(String str) {
        logger.debug("所用数据源是: " + str);
        contextHolder.set(str);
    }

    /**
     * 清理数据源类型
     */
    public static void clearDbType() {
        contextHolder.remove();
    }
}
