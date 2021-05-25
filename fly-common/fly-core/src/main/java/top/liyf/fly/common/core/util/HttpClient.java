package top.liyf.fly.common.core.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author liyf
 * Created in 2021-05-20
 */
@Slf4j
public class HttpClient {

    @Getter
    private CloseableHttpClient httpclient;

    @Getter
    private PoolingHttpClientConnectionManager manager;

    private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);


    private HttpClient() {

        manager = new PoolingHttpClientConnectionManager();
        // 总连接数
        manager.setMaxTotal(200);
        // 默认同路由的并发数
        manager.setDefaultMaxPerRoute(100);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(3000)
                .setSocketTimeout(10000)
                .build();

        HttpClientBuilder builder = HttpClients.custom();
        builder.setConnectionManager(manager);
        builder.setDefaultRequestConfig(requestConfig);
        // 保持长连接配置
        builder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());

        this.httpclient = builder.build();

        // 开启监控线程,对异常和空闲线程进行关闭
        executorService.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // 关闭异常连接
                manager.closeExpiredConnections();
                // 关闭10s空闲的连接
                manager.closeIdleConnections(10, TimeUnit.MILLISECONDS);
            }
        }, 20, 5, TimeUnit.SECONDS);

    }

    private static class SingletonHolder {
        private static final HttpClient INSTANCE = new HttpClient();
    }

    public static CloseableHttpClient getClient() {
        return SingletonHolder.INSTANCE.getHttpclient();
    }

}
