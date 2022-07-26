package org.hgc.cityRepository.model.vo;

import lombok.Getter;
import org.hgc.cityRepository.nums.ResultCode;

@Getter
public class ResponseResult<T> {
    /**
     * 响应状态码
     */
    private int code;
    /**
     * 响应信息
     */
    private String msg;
    /**
     * 响应数据
     */
    private T data;

    public ResponseResult(T data) {
        this(ResultCode.SUCCESS, data);
    }

    public ResponseResult(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }
}
