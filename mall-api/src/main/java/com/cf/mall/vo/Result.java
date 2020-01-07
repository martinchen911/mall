package com.cf.mall.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author chen
 * @Date 2020/1/3
 */
@Data
@AllArgsConstructor
public class Result implements Serializable {

    private String code;
    private String msg;
    private Object extend;

    public static Result Success(Object obj) {
        Result result = new Result(Status.Success.code,"",obj);
        return result;
    }

    public static Result Fail(String msg) {
        Result result = new Result(Status.Fail.code,msg,null);
        return result;
    }



    public enum Status {
        /**
         * 成功
         */
        Success("0"),
        /**
         * 失败
         */
        Fail("1");

        private final String code;

        Status(String code) {
            this.code = code;
        }
    }
}
