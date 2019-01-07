package cn.com.pfinfo.customanno.exception;

/**
 * 抽象异常类,是自定义的基础异常类,所有子类的异常信息都将通过这个类输出异常信息
 * @author cuitpanfei
 */
public abstract class AbstractException extends RuntimeException {
    protected  static final int ERROR_CODE = 3000;
    protected  static final int WARN_CODE = 2000;
    protected  static final int DEFAULT_CODE = 1000;
    public abstract int getCode();

    public AbstractException() {
    }

    public AbstractException(String msg) {
        super(msg);
    }

    public AbstractException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AbstractException(Throwable cause) {
        super(cause);
    }

    public AbstractException(String msg, Throwable cause,
                             boolean enableSuppression,
                             boolean writableStackTrace) {
        super(msg, cause, enableSuppression, writableStackTrace);
    }
}
