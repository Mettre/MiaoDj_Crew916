package cn.chenhai.miaodj_monitor.model;

import java.util.List;

/**
 * Created by ChenHai--霜华 on 2016/7/1. 18:10
 * 邮箱：248866527@qq.com
 */
public class TestResult {


    /**
     * HTTP_RAW_POST_DATA : null
     * input :
     * get : []
     * post : []
     * request : {"PHPSESSID":"neor50rodhntrrab0sr2njsts6"}
     */

    private Object HTTP_RAW_POST_DATA;
    private String input;
    /**
     * PHPSESSID : neor50rodhntrrab0sr2njsts6
     */

    private List<RequestBean> request;
    private List<?> get;
    private List<?> post;

    public Object getHTTP_RAW_POST_DATA() {
        return HTTP_RAW_POST_DATA;
    }

    public void setHTTP_RAW_POST_DATA(Object HTTP_RAW_POST_DATA) {
        this.HTTP_RAW_POST_DATA = HTTP_RAW_POST_DATA;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public List<RequestBean> getRequest() {
        return request;
    }

    public void setRequest(List<RequestBean> request) {
        this.request = request;
    }

    public List<?> getGet() {
        return get;
    }

    public void setGet(List<?> get) {
        this.get = get;
    }

    public List<?> getPost() {
        return post;
    }

    public void setPost(List<?> post) {
        this.post = post;
    }

    public static class RequestBean {
        private String PHPSESSID;

        public String getPHPSESSID() {
            return PHPSESSID;
        }

        public void setPHPSESSID(String PHPSESSID) {
            this.PHPSESSID = PHPSESSID;
        }
    }



}
