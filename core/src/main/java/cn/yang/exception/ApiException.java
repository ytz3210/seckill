package cn.yang.exception;


/**
 * @author ZhangJun
 * @Description: 统一异常处理类
 * @date 2019年8月21日 下午4:07:50
 */
public class ApiException extends Exception {

    public ApiException(String message) {
        super(message);
    }
}
