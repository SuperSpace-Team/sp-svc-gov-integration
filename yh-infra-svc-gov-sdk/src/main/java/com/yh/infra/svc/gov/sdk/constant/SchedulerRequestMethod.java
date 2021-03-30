package com.yh.infra.svc.gov.sdk.constant;

/**
 * task_http_url
 * @author MSH8244
 * @since 2.1
 * @from springMVC
 */
public enum SchedulerRequestMethod {

	GET(1), HEAD(2), POST(3), PUT(4), PATCH(5), DELETE(6), OPTIONS(7), TRACE(8);
    
    /**
     * 字典值
     */
    private int value;
    
    private SchedulerRequestMethod(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
    /**
     * 根据字典值获得enum
     * @param value
     * @return
     */
    public static SchedulerRequestMethod valueOf(int value) {
        switch (value) {
            case 0:
                throw new IllegalArgumentException("value must between 1 and 8");
            case 1:
                return GET;
            case 2:
                return HEAD;
            case 3:
                return POST;
            case 4:
                return PUT;
            case 5:
                return PATCH;
            case 6:
                return DELETE;
            case 7:
                return OPTIONS;
            case 8:
                return TRACE;
            default:
                return GET;

        }
    }
    /**
     * 根据枚举名称获得枚举类型
     * @param name
     * @return
     */
    public static SchedulerRequestMethod nameOf(String name) {
        for (SchedulerRequestMethod element : SchedulerRequestMethod.values()) {
            if (name.equals(element.toString())) {
                return element;
            }
        }
        return null;
    }
    
}
