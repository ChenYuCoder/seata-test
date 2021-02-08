package com.vichen.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chenyu
 * @date 2020/8/26
 */
public class HttpUtils {

    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
      .connectTimeout(10, TimeUnit.SECONDS)
      .writeTimeout(10, TimeUnit.SECONDS)
      .readTimeout(30, TimeUnit.SECONDS)
      .build();

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 同步请求
     *
     * @param request
     * @return
     */
    public static String syncCall(Request request) {
        Call call = CLIENT.newCall(request);
        try (Response response = call.execute()) {
            if (!response.isSuccessful()) {
                logger.error("doCall request has error, url: {}, params: {}", request.url(),
                  getRequestParams(request));
                return null;
            }

            return Objects.requireNonNull(response.body()).string();
        } catch (Exception e) {
            logger
              .error("doCall request has error, url: {}, params: {}", request.url(),
                getRequestParams(request), e);
        }
        return null;
    }

    /**
     * 异步请求
     *
     * @param request
     * @return
     */
    public static void asyncCall(Request request) {
        Call call = CLIENT.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                logger.error("doCall request has error, url: {}, params: {}", request.url(),
                  getRequestParams(request), e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                ResponseBody body = response.body();
                if (response.isSuccessful()) {
                    //暂时不需要处理
                } else {
                    logger.error("doCall request failed, url: {}, params: {}", request.url(),
                      getRequestParams(request));
                }
                if(body !=null){
                    body.close();
                }
            }
        });
    }

    /**
     * post请求带参数
     *
     * @param url    地址
     * @param params 参数
     * @return
     */
    public static Request createPostRequest(String url, Map<String, String> params) {
        return createPostRequest(url, params, null);
    }

    /**
     * post请求（参数，header）
     *
     * @param url     地址
     * @param params  参数
     * @param headers 请求头
     * @return
     */
    public static Request createPostRequest(String url, Map<String, String> params,
      Map<String, String> headers) {
        //添加请求体
        FormBody.Builder formBodyBuild = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formBodyBuild.add(entry.getKey(), entry.getValue());
            }
        }
        //添加请求头
        Headers.Builder headerBuild = new Headers.Builder();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                headerBuild.add(entry.getKey(), entry.getValue());
            }
        }
        FormBody mFormBody = formBodyBuild.build();
        Headers mHeader = headerBuild.build();

        return new Request.Builder().url(url).
          post(mFormBody).
          headers(mHeader)
          .build();
    }

    private static String getRequestParams(Request request) {
        RequestBody requestBody = request.body();
        if (requestBody == null) {
            return null;
        }
        Buffer buffer = new Buffer();
        try {
            requestBody.writeTo(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("requestBody.writeTo has error", e);
            return null;
        }
        Charset charset = StandardCharsets.UTF_8;
        MediaType contentType = requestBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(StandardCharsets.UTF_8);
        }
        assert charset != null;
        return buffer.readString(charset);
    }

}
