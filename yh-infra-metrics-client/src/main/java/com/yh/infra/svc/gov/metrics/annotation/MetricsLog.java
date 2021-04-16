package com.yh.infra.svc.gov.metrics.annotation;

import java.lang.annotation.*;

/**
 * @description:
 * @author: luchao
 * @date: Created in 4/16/21 7:37 PM
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MetricsLog {
    /**
     * 指标名称
     */
    String name();

    /**
     * 是否标准接口
     * 若是则忽略failExp,直接根据接口规范判断是否失败
     * @return
     */
    boolean stdApi() default false;

    /**
     * 用于判断方法执行失败的SPEL表达式,计算结果必须是boolean类型
     * @return
     */
    String failExp() default "";

    /**
     * 方法执行前打印的日志级别
     * 1-DEBUG,2-INFO,3-WARN,4-ERROR
     * @return
     */
    String logLevel() default "INFO";

    /**
     * 用于方法执行前打印日志内容的SPEL表达式,计算结果必须是String类型
     * @return
     */
    String logExp() default "";

    /**
     * 租户号的SPEL表达式,计算结果必须是String类型
     * @return
     */
    String tenantExp() default "";

    /**
     * 自定义的tag,以逗号分隔,格式为[tag key1,value1,key2,value2,...]
     * @return
     */
    String tagsExp() default "";
}
