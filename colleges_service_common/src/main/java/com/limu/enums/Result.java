package com.limu.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO 类描述
 *
 * @author: LiMu
 * @createTime: 2023年06月07日 18:14
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    public static final String SERVER_ERROR = "服务器内部错误";
    private Integer code;
    private String message;
    private T data;


    //重载
    public static <T> Result<T> success(){
        return new Result<>(20000, "success", null);
    }

    public static <T> Result<T> success(T data){
        return new Result<>(20000, "success", data);
    }

    public static <T> Result<T> success(String message, T data){
        return new Result<>(20000, message, data);
    }

    public static <T> Result<T> success(String message){
        return new Result<>(20000, message, null);
    }

    public static <T> Result<T> fail(){
        return new Result<>(20001, "fail", null);
    }

    public static <T> Result<T> fail(Integer code){
        return new Result<>(code, "fail", null);
    }

    public static <T> Result<T> fail(Integer code,String message){
        return new Result<>(code, message, null);
    }

    public static <T> Result<T> fail(String message){
        return new Result<>(20001, message, null);
    }
}
