package com.yh.infra.svc.gov.sdk.util;

/**
 * Created with IntelliJ IDEA.
 * User: HSH7849
 * Date: 2019/3/14
 * Time: 10:07
 * To change this template use File | Settings | File Templates.
 * Description: 工具类
 */


public class UrlCheckUtil {

    public UrlCheckUtil() {
        throw new IllegalStateException("UrlCheckUtil class unable to instance");
    }

    /**
     * url空验证方法
     * @param url
     */
    public static void checkUrl(String url) {
        if (StringUtils.isBlank(url)) {
            throw new IllegalArgumentException("task callback url is empty!");
        }
    }
}