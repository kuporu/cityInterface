package org.hgc.cityRepository.error;

import org.hgc.cityRepository.model.vo.ResponseResult;
import org.hgc.cityRepository.nums.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(APIException.class)
    public ResponseResult APIExceptionHandler (APIException e) {
        return new ResponseResult(ResultCode.FAILED, e.getMsg());
    }
}
