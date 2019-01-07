package cn.com.pfinfo.customanno.exception;

public class CodingException extends AbstractException {
    @Override
    public int getCode() {
        return WARN_CODE;
    }
    public CodingException() {
    }

    public CodingException(String msg) {
        super(msg);
    }

}
