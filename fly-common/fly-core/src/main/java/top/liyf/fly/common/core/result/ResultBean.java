package top.liyf.fly.common.core.result;

import lombok.Data;
import top.liyf.fly.common.core.exception.BusinessException;

import java.io.Serializable;

/**
 * @author liyf
 * Created in 2021-05-20
 */
@Data
public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private String msg;

    private T data;

    public ResultBean() {
        this(ResultCode.SUCCESS);
    }

    public ResultBean(T data) {
        this();
        this.data = data;
    }

    public ResultBean(ResultCode code) {
        this.code = code.getCode();
        this.msg = code.getMsg();
    }

    public ResultBean(BusinessException e) {
        this.code = e.getCode();
        this.msg = e.getMsg();
    }
}
