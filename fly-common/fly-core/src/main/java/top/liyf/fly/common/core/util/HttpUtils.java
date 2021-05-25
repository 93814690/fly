package top.liyf.fly.common.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import top.liyf.fly.common.core.exception.BusinessException;
import top.liyf.fly.common.core.result.ResultCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author liyf
 * Created in 2021-05-20
 */
@Slf4j
public class HttpUtils {

    /**
     * 编码格式。发送编码格式统一用UTF-8
     */
    private static final String UTF8 = "UTF-8";


    public static HttpClientResult doGet(String url, Map<String, String> headers, Map<String, String> params) throws Exception {

        // record request
        long start = System.currentTimeMillis();

        // 创建访问的地址
        URIBuilder uriBuilder = new URIBuilder(url);
        if (params != null) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue());
            }
        }

        // 创建http对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        packageHeader(headers, httpGet);

        HttpClientResult httpClientResult;
        // 自动释放资源
        try (CloseableHttpResponse httpResponse = HttpClient.getClient().execute(httpGet)) {
            // 执行请求并获得响应结果
            httpClientResult = getHttpClientResult(httpResponse);
        }

        long end = System.currentTimeMillis();
        log.info("used time: " + (end - start) + " ms");
        if (httpClientResult.getCode() != 200) {
            throw new BusinessException(ResultCode.NET_ERROR);
        }
        return httpClientResult;
    }

    public static HttpClientResult doPostForm(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
        List<NameValuePair> parameters = new ArrayList<>(2);
        Set<Map.Entry<String, String>> entrySet = params.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);

        return doPost(url, headers, formEntity);
    }

    public static HttpClientResult doPostJson(String url, Map<String, String> headers, Map<String, String> params) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(params);
        StringEntity stringEntity = new StringEntity(s);

        return doPost(url, headers, stringEntity);
    }


    public static HttpClientResult doPost(String url, Map<String, String> headers, HttpEntity entity) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        packageHeader(headers, httpPost);
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(entity);

        HttpClientResult httpClientResult;
        try (CloseableHttpResponse httpResponse = HttpClient.getClient().execute(httpPost)) {
            // 执行请求并获得响应结果
            httpClientResult = getHttpClientResult(httpResponse);
        }

        return httpClientResult;
    }

    private static void packageHeader(Map<String, String> params, HttpRequestBase httpMethod) {
        if (params != null) {
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                // 设置到请求头到HttpRequestBase对象中
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    private static HttpClientResult getHttpClientResult(CloseableHttpResponse response)
            throws Exception {

        String res = EntityUtils.toString(response.getEntity(), UTF8);
        EntityUtils.consume(response.getEntity());
        StatusLine statusLine = response.getStatusLine();
        return new HttpClientResult(statusLine.getStatusCode(), statusLine.toString(), res);

    }

}
