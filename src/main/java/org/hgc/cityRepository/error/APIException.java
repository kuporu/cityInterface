package org.hgc.cityRepository.error;

import lombok.Getter;

@Getter
public class APIException extends RuntimeException{

    private String msg;

    public APIException(String msg) {
        this.msg = msg;
    }
}
