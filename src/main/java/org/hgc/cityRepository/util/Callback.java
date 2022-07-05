package org.hgc.cityRepository.util;

import java.io.IOException;

/*
网络请求回调接口
 */
public interface Callback {

    void onFailure(IOException e);

    void onResponse(String response);
}
