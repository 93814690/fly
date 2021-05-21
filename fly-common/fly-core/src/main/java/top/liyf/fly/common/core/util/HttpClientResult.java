package top.liyf.fly.common.core.util;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liyf
 * Created in 2021-05-20
 */
@Data
public class HttpClientResult implements Serializable {

    /**
     * 响应状态码
     */
    private int code;

    private String statusLine;

    /**
     * 响应数据
     */
    private String content;

    public HttpClientResult(int code, String statusLine, String content) {
        this.code = code;
        this.statusLine = statusLine;
        this.content = content;
    }

}
