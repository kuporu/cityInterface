package org.hgc.cityRepository.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    public static final Logger LOG = LogManager.getLogger(HttpUtil.class);

    /**
     * 返回url服务器资源（字符串）
     * @param urlString
     * @return
     */
    public static void request (String urlString, Callback callback) {
        BufferedReader reader = null;
        StringBuilder response = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = ((HttpURLConnection) url.openConnection());
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);

            // 处理返回数据
            InputStream in = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            callback.onResponse(response.toString());
        } catch (IOException e) {
            LOG.error("请求网络资源失败", e);
            callback.onFailure(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    LOG.error("写入内存关闭失败", e);
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
