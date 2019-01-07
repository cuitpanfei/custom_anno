package cn.com.pfinfo.customanno.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 错误级别的异常,抛出此异常的同时,将要向指定的邮箱发出日志邮件，所以在抛出异常的同时，需要指定邮箱地址。
 */
public class ErrorMsgException extends AbstractException{

    private String emailAddress;

    @Override
    public int getCode() {
        return ERROR_CODE;
    }

    public ErrorMsgException(String emailAddress) {
        this.emailAddress=emailAddress;
    }

    public ErrorMsgException(String emailAddress,String msg) {
        super(msg);
        this.emailAddress=emailAddress;
    }

    public ErrorMsgException(String emailAddress,String msg, Throwable cause) {
        super(msg, cause);
        this.emailAddress=emailAddress;
    }

    public ErrorMsgException(String emailAddress,Throwable cause) {
        super(cause);
        this.emailAddress=emailAddress;
    }

    public ErrorMsgException(String emailAddress,String msg, Throwable cause,
                             boolean enableSuppression,
                             boolean writableStackTrace) {
        super(msg, cause, enableSuppression, writableStackTrace);
        this.emailAddress=emailAddress;
    }

    /**
     * 返回堆栈信息
     * @return
     */
    public String print(){
        try(StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw)){
            printStackTrace(pw);
            return sw.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return this.toString();
        }
    }

    /**
     * 发生异常的时候，需要发送邮件，通过调用此方法获取收件方的地址
     * @return 收件方邮箱地址
     */
    public String email(){
        return emailAddress;
    }
}
