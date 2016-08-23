package cn.chenhai.miaodj_monitor.presenter;

/**
 *
 */
public class ApiException extends RuntimeException {

    public static final int USER_NOT_EXIST = 100;
    public static final int WRONG_PASSWORD = 101;


    public ApiException(int resultCode,String returnMsg) {
        //this(getApiExceptionMessage(resultCode) + returnMsg);
        this(returnMsg);
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code){
        String message = "";
        switch (code) {
            case 200:
                message = "提交成功";
                break;
            case 3001:
                message = "帐号或密码不能为空";
                break;
            case 3003:
                message = "帐号或密码错误";
                break;
            case 3015:
                message = "用户access_token已失效，请重新登录！";
                break;


            default:
                message = "未知错误";

        }
        return message;
    }
}

