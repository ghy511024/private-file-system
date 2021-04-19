package com.bj58.ctf2021.privatefilesystem.common.utils.client;


import com.bj58.ctf2021.privatefilesystem.common.enums.code.AppErrorCode;
import com.bj58.ctf2021.privatefilesystem.common.enums.code.CommonResultCode;
import com.bj58.ctf2021.privatefilesystem.common.exception.BizExceptionBuilder;
import com.bj58.ctf2021.privatefilesystem.common.utils.json.JacksonUtils;
import com.bj58.ctf2021.privatefilesystem.controller.entity.response.PlainResult;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.Setter;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 抽象web服务客户端实现
 *
 * @author me
 * @date 2021/4/1
 */
public abstract class AbstractWebApiClientImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractWebApiClientImpl.class);

    protected RestTemplate restTemplate;

    /**
     * webApi服务域名
     */
    @Setter
    protected String apiDomain;
    /**
     * 服务连接超时时长(毫秒)
     */
    @Setter
    protected int connectTimeout = 3000;
    /**
     * 服务读取超时时长(毫秒)
     */
    @Setter
    protected int readTimeout = 5000;

    @Bean
    public HttpComponentsClientHttpRequestFactory requestFactory() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
        connectionManager.setMaxTotal(200);
        connectionManager.setDefaultMaxPerRoute(20);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .evictIdleConnections(30, TimeUnit.SECONDS)
                .disableAutomaticRetries()
                .disableCookieManagement()
                .build();
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    public AbstractWebApiClientImpl(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.requestFactory(this::requestFactory)
                .setBufferRequestBody(false)
                .setConnectTimeout(Duration.ofMillis(connectTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

    /**
     * 检查响应状态
     *
     * @param response webApi接口响应
     */
    public void checkWebApiResponse(ResponseEntity<String> response) {
        // 检查http请求状态
        if (response.getStatusCode() != HttpStatus.OK) {
            throw BizExceptionBuilder.build(AppErrorCode.API_INVOKE_HTTP_ERROR);
        }
    }

    public void checkWebApiData(ResponseEntity<String> response) {
        // 校验返回值
        PlainResult plainResult = getPlainResult(response);
        assert plainResult != null;
        if (!plainResult.isSuccess() || plainResult.getCode() != CommonResultCode.SUCCESS.code) {
            throw BizExceptionBuilder.build(AppErrorCode.API_INVOKE_BIZ_ERROR, plainResult.getCode() + ":" + plainResult.getMessage());
        }
    }

    /**
     * 获取简单返回结果
     *
     * @param response webApi接口响应
     * @return 简单返回结果
     */
    public PlainResult getPlainResult(ResponseEntity<String> response) {
        return JacksonUtils.toObj(response.getBody(), PlainResult.class);
    }

    /**
     * 获取带Data类型的简单返回结果
     *
     * @param response webApi接口响应
     * @param target   PlainResult的Data属性包装的类型
     * @return 简单返回结果
     */
    public <T> PlainResult<T> getPlainResult(ResponseEntity<String> response, Class<T> target) {
        //获取PlainResult的Data属性包装的类型
        JavaType javaType = (new ObjectMapper()).getTypeFactory().constructParametricType(PlainResult.class, target);
        return JacksonUtils.toObj(response.getBody(), javaType);
    }

    /**
     * 获取带Data类型的列表返回结果
     *
     * @param response webApi接口响应
     * @param target   PlainResult的Data属性List包装的类型
     * @return 简单返回结果
     */
    public <T> PlainResult<List<T>> getListResult(ResponseEntity<String> response, Class<T> target) {
        CollectionType collectionType = (new ObjectMapper()).getTypeFactory().constructCollectionType(List.class, target);
        JavaType javaType = (new ObjectMapper()).getTypeFactory().constructParametricType(PlainResult.class, collectionType);
        return JacksonUtils.toObj(response.getBody(), javaType);
    }

    /**
     * 构造完整的get请求url
     *
     * @param apiUrl       webApi接口
     * @param uriVariables get参数
     * @return get请求url
     */
    public String buildFullGetUrl(String apiUrl, HashMap<String, Object> uriVariables) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl);
        uriVariables.forEach((k, v) -> {
            if (v != null) {
                builder.queryParam(k, v);
            }
        });
        return builder.toUriString();
    }


}
