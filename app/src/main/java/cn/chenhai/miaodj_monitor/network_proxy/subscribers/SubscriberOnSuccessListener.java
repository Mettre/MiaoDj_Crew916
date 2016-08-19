package cn.chenhai.miaodj_monitor.network_proxy.subscribers;

/**
 *
 */
public interface SubscriberOnSuccessListener<T> {
    void onSuccess(T t);
    void onCompleted();
    void onError();
}
