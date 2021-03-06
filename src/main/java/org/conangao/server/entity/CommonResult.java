package org.conangao.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用的数据响应类
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    /*状态码*/
    private Integer code;
    /*提示信息*/
    private String message;
    /*携带的数据*/
    private T data;

    public CommonResult(Integer code, String message){
        this.code = code;
        this.message = message;
        this.data = null;
    }
}
