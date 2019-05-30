package com.nero.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/5/15 0015
 * Time: 17:20
 */
public class ShopOperationException extends RuntimeException {
    /**
     * 序列化ID
     */
    private static final long serialVersionUID = -3952503368422261769L;

    public ShopOperationException(String msg) {
        super(msg);
    }

}
