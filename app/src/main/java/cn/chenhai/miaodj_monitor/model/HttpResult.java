package cn.chenhai.miaodj_monitor.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ChenHai--霜华 on 2016/7/1. 11:21
 * 邮箱：248866527@qq.com
 */
public class HttpResult<T> implements Serializable {

    /**
     * code : 200
     * msg : 操作成功
     * info : {}
     * user_code : CRE201606000001
     * access_token : Q1JFMjAxNjA2MDAwMDAxMTQ2NzM0MjY0MS40Mzc5
     */

    private int code;
    private String msg;
    private String user_code;
    private String access_token;
    private int verify_code;

    //private List<T> info;
    private T info;

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUser_code() {
        return user_code;
    }
    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getAccess_token() {
        return access_token;
    }
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getVerify_code() {
        return verify_code;
    }
    public void setVerify_code(int verify_code) {
        this.verify_code = verify_code;
    }

//    public List<T> getInfo() {
//        return info;
//    }
//    public void setInfo(List<T> info) {
//        this.info = info;
//    }
    public T getInfo() {
    return info;
}
    public void setInfo(T info) {
        this.info = info;
    }


}
