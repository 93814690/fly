package top.liyf.fly.common.core.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liyf
 * Created in 2021-05-20
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    /**
     * 正确执行后的返回
     */
    SUCCESS("00000", "成功"),

    CLIENT_ERROR("A0001", "用户端错误"),

    NOT_ALLOWED("A0301", "没有权限"),

    NOT_FOUND("A0404", "未找到"),

    SYSTEM_ERROR("B0001", "系统执行出错"),

    NET_ERROR("B0101", "网络错误");

    private final String code;

    private final String msg;

}
