package cn.chenhai.miaodj_monitor.presenter.subscribers;

/**
 *
 */
public interface SubscriberOnSuccessListener<T> {
    void onSuccess(T t);
    void onCompleted();
    void onError();
}
