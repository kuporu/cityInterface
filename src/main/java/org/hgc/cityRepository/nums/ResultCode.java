package org.hgc.cityRepository.nums;

import lombok.Getter;

@Getter
public enum ResultCode {

    SUCCESS(1001, "操作成功"),
    FAILED(1002, "响应失败"),
    ERROR(1004, "未知错误");

    private int code;

    private String msg;

    private ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
