package com.liugeng.cloud.entity;

import java.io.Serializable;

public class ApiResult implements Serializable {

        private static final long serialVersionUID = 895972860305039752L;

        private String code;          //响应代码

        private String msg;           //响应描述

        private Object data;          //结果数据

        public ApiResult(){}

        public ApiResult(String code,String msg){
            this.code = code;
            this.msg = msg;
        }

        public ApiResult(String code, String msg, Object data){
            this.code = code;
            this.msg = msg;
            this.data = data;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
}
