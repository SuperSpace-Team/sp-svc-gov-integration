package com.yh.infra.svc.gov.sdk.task.command;

/**
 * @Desc:
 * @Author: Bill
 * @Date: created in 16:57 2020/1/17
 * @Modified by:
 */
public class TaskEmuns {

    /**
     * 任务生命周期
     */
    public enum LIFECYCLE{
        DISABLE(0),ENABLE(1),DELETE(2);

        private final Integer code;

        LIFECYCLE(Integer code){
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }

    /**
     * http请求方法类型
     */
    public enum HTTP_REQUEST_METHOD{
        GET(1),PUT(2),POST(3);

        private final Integer code;

        HTTP_REQUEST_METHOD(Integer code){
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }

    /**
     * 任务类型
     */
    public enum TYPE{
        BEAN(1),HTTP(2);

        private final Integer code;

        TYPE(Integer code){
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }

    /**
     * 补偿级别
     */
    public enum COMPENSATE_TYPE{
        ONE_MINUTES(1),FIVE_MINUTES(2),DISABLE(3);

        private final Integer code;

        COMPENSATE_TYPE(Integer code){
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }
    }


}
