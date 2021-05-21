package top.liyf.fly.common.core.exception;

import lombok.Getter;
import lombok.Setter;
import top.liyf.fly.common.core.result.ResultCode;

import java.io.Serializable;

/**
 * @author liyf
 * Created in 2021-05-20
 */
@Getter
@Setter
public class BusinessException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private String msg;

    public BusinessException(String msg) {
        super(msg);
        this.code = ResultCode.SYSTEM_ERROR.getCode();
        this.msg = msg;
    }

    public BusinessException(ResultCode code) {
        super(code.getMsg());
        this.code = code.getCode();
        this.msg = code.getMsg();
    }

    public BusinessException(ResultCode code, String msg) {
        super(msg);
        this.code = code.getCode();
        this.msg = msg;
    }
}

