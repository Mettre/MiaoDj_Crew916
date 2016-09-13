package cn.chenhai.miaodj_monitor.presenter;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.TestResult;
import cn.chenhai.miaodj_monitor.model.entity.Account;
import cn.chenhai.miaodj_monitor.model.entity.BackLogEntity;
import cn.chenhai.miaodj_monitor.model.entity.BackLogNewMsgEntity;
import cn.chenhai.miaodj_monitor.model.entity.BargainEntity;
import cn.chenhai.miaodj_monitor.model.entity.BargainPayEntity;
import cn.chenhai.miaodj_monitor.model.entity.BuildDiaryEntity;
import cn.chenhai.miaodj_monitor.model.entity.BuildPhotoEntity;
import cn.chenhai.miaodj_monitor.model.entity.CheckPictureEntity;
import cn.chenhai.miaodj_monitor.model.entity.CheckWorkersEntity;
import cn.chenhai.miaodj_monitor.model.entity.ChooseWorkerEntity;
import cn.chenhai.miaodj_monitor.model.entity.ConfirmDrawPicEntity;
import cn.chenhai.miaodj_monitor.model.entity.EmptyEntity;
import cn.chenhai.miaodj_monitor.model.entity.MyProjectsDetailEntity;
import cn.chenhai.miaodj_monitor.model.entity.MyProjectsEntity;
import cn.chenhai.miaodj_monitor.model.entity.NewVersionEntity;
import cn.chenhai.miaodj_monitor.model.entity.PointEntity;
import cn.chenhai.miaodj_monitor.model.entity.PointProgressDetailEntity;
import cn.chenhai.miaodj_monitor.model.entity.ProjectWorkersInfoEntity;
import cn.chenhai.miaodj_monitor.model.entity.ProvinceCityDistrictBean;
import cn.chenhai.miaodj_monitor.model.entity.RecommendWorkerDetailEntity;
import cn.chenhai.miaodj_monitor.model.entity.RecommendWorkerListEntity;
import cn.chenhai.miaodj_monitor.model.entity.SelectionListAuxilaryEntity;
import cn.chenhai.miaodj_monitor.model.entity.SelectionListEntity;
import cn.chenhai.miaodj_monitor.model.entity.SelectionListMainEntity;
import cn.chenhai.miaodj_monitor.model.entity.UserEntity;
import cn.chenhai.miaodj_monitor.model.entity.UserInfoEntity;
import cn.chenhai.miaodj_monitor.model.entity.WorkerTypesEntity;
import cn.chenhai.miaodj_monitor.presenter.api.ServiceApi;
import cn.chenhai.miaodj_monitor.presenter.log.LoggerInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.Subject;

/**
 * Created by ChenHai--霜华 on 2016/7/1. 10:27
 * 邮箱：248866527@qq.com
 */
public class HttpMethods {

    //public static final String BASE_ROOT_URL = "http://release.miaodj.cn/";
    public static final String BASE_ROOT_URL = "http://api.miaodj.cn/";
//    public static final String BASE_ROOT_URL = "http://test.miaodj.cn/";

    //public static final String BASE_URL = "http://release.miaodj.cn/index.php/";
    public static final String BASE_URL = "http://api.miaodj.cn/index.php/";
//    public static final String BASE_URL = "http://test.miaodj.cn/index.php/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Activity mContext;

    private Retrofit retrofit;
    private ServiceApi serviceInterface;

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //设置拦截器
        builder.addInterceptor(new LoggerInterceptor("HttpLog", true));

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        serviceInterface = retrofit.create(ServiceApi.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.getCode() != 200) {
                throw new ApiException(httpResult.getCode(), httpResult.getMsg());
            }
            //return httpResult.getInfo().get(0);
            return httpResult.getInfo();
        }
    }

    private class HttpResultAll<T> implements Func1<HttpResult<T>, HttpResult<T>> {
        @Override
        public HttpResult<T> call(HttpResult<T> httpResult) {
            if (httpResult.getCode() != 200 && httpResult.getCode() != 3015) {
                throw new ApiException(httpResult.getCode(), httpResult.getMsg());
            }
            return httpResult;
        }
    }

    private class HttpResultTest<T> implements Func1<T, T> {
        @Override
        public T call(T test) {

            return test;
        }
    }

    private <T> void toSubscribe(Observable<T> observable, Subscriber<T> s) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    /**
     * 测试返回值
     */
    public void doTest(Subscriber<TestResult> subscriber, String telephone, String password) {
        JSONObject json = new JSONObject();
        try {
            json.put("login_type", "crew");
            json.put("telephone", telephone);
            json.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Observable observable = serviceInterface.doTest(json)
                .map(new HttpResultTest<TestResult>());

        toSubscribe(observable, subscriber);
    }

    /*********************************************------公用接口-----*****************************************************/
    /**
     * 登录
     */
    public void doLogin(Subscriber<Account> subscriber, String telephone, String password) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("login_type", "crew");
        map.put("telephone", telephone);
        map.put("password", password);

        Observable observable = serviceInterface.doLogin(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 注册
     */
    public void doRegister(Subscriber<List<Subject>> subscriber, String telephone, String password, String verify_code) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("source", "APP");
        map.put("register_type", "crew");
        map.put("telephone", telephone);
        map.put("password", password);
        map.put("verify_code", verify_code);
        map.put("register_source", "android");

        //        movieService.getTopMovie(start, count)
//                .map(new HttpResultFunc<List<Subject>>())
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);

        Observable observable = serviceInterface.doRegister(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 获取验证码
     */
    public void getSendMsgCode(Subscriber<Account> subscriber, String phone) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("phone", phone);

        Observable observable = serviceInterface.getSendMsgCode(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 找回密码
     */
    public void findPassWord(Subscriber<Account> subscriber, String phone, String verify_code, String new_password) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_type", "crew");
        map.put("phone", phone);
        map.put("verify_code", verify_code);
        map.put("new_password", new_password);

        Observable observable = serviceInterface.findPassWord(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 登出 注销
     */
    public void doLogout(Subscriber<Account> subscriber, String user_code, String access_token) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);

        Observable observable = serviceInterface.doLogout(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 获取省份
     */
    public void getProvince(Subscriber<Account> subscriber) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_type", "crew");

        Observable observable = serviceInterface.getProvince(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 通过省的code获取市
     */
    public void getCity(Subscriber<Account> subscriber, String province_code) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("province_code", province_code);

        Observable observable = serviceInterface.getCity(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 通过市的code获取区县
     */
    public void getCityArea(Subscriber<Account> subscriber, String city_code) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("city_code", city_code);

        Observable observable = serviceInterface.getCityArea(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 获取所有的省份，市，区
     */
    public void getAllProvinceCityDistrict(Subscriber<ProvinceCityDistrictBean> subscriber) {
        Observable observable = serviceInterface.getAllProvinceCityDistrict()
                .map(new HttpResultAll<ProvinceCityDistrictBean>());

        toSubscribe(observable, subscriber);
    }


    /**
     * 获取所有工种列表
     */
    public void getAllWorkerTypes(Subscriber<WorkerTypesEntity> subscriber) {
        Observable observable = serviceInterface.getAllWorkerTypes()
                .map(new HttpResultAll<WorkerTypesEntity>());

        toSubscribe(observable, subscriber);
    }

    /*********************************************------项目接口-----*****************************************************/

    /**
     * 获取选品单列表
     */
    public void getSelectionList(Subscriber<SelectionListEntity> subscriber, String user_code, String access_token, String customer_code) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("customer_code", customer_code);

        Observable observable = serviceInterface.getSelectionList(map)
                .map(new HttpResultAll<SelectionListEntity>());

        toSubscribe(observable, subscriber);
    }


    /**
     * 主材配送详情
     */
    public void getMaterialDeliverDetail(Subscriber<SelectionListMainEntity> subscriber, String user_code, String access_token, String order_code, String material_code, String space_id) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("order_code", order_code);
        map.put("material_code", material_code);
        map.put("space_id", space_id);

        Observable observable = serviceInterface.getMaterialDeliverDetail(map)
                .map(new HttpResultAll<SelectionListMainEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 发起配送
     */
    public void doStartDeliver(Subscriber<EmptyEntity> subscriber, String user_code, String access_token, String order_code, String material_code, String space_id, String material_type, String expect_arrive_time) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("order_code", order_code);
        map.put("material_code", material_code);
        map.put("space_id", space_id);
        map.put("material_type", material_type);
        map.put("expect_arrive_time", expect_arrive_time);

        Observable observable = serviceInterface.doStartDeliver(map)
                .map(new HttpResultAll<EmptyEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 签收单上传-图片上传
     */
    public void doUploadSignPic(Subscriber<Account> subscriber, String user_code, String access_token, String project_code) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);

        Observable observable = serviceInterface.doUploadSignPic(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 签收货物
     */
    public void signForMainMaterial(Subscriber<Account> subscriber, String user_code, String access_token, String order_code,
                                    String material_code, String space_id, String material_type, String sign_amount, String torn_amount, String lack_amount, String sign_pic) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("order_code", order_code);
        map.put("material_code", material_code);
        map.put("space_id", space_id);
        map.put("material_type", material_type);
        map.put("sign_amount", sign_amount);
        map.put("torn_amount", torn_amount);
        map.put("lack_amount", lack_amount);
        map.put("sign_pic", sign_pic);

        Observable observable = serviceInterface.signForMainMaterial(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 辅材配送单
     */
    public void getSelectionAuxiliaryDeliverOrder(Subscriber<SelectionListAuxilaryEntity> subscriber, String user_code, String access_token, String deliver_code) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("deliver_code", deliver_code);

        Observable observable = serviceInterface.getSelectionAuxiliaryDeliverOrder(map)
                .map(new HttpResultAll<SelectionListAuxilaryEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 辅材发起配送
     */
    public void doStartDeliverAuxilary(Subscriber<EmptyEntity> subscriber, String user_code, String access_token, String order_code, String material_codes, String expect_arrive_time) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("order_code", order_code);
        map.put("material_codes", material_codes);
        map.put("expect_arrive_time", expect_arrive_time);

        Observable observable = serviceInterface.doStartDeliverAuxilary(map)
                .map(new HttpResultAll<EmptyEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 辅材配送单 签收
     */
    public void signForAuxilaryMaterial(Subscriber<EmptyEntity> subscriber, String user_code, String access_token, String deliver_code, String sign_pic) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("deliver_code", deliver_code);
        map.put("sign_pic", sign_pic);

        Observable observable = serviceInterface.signForAuxilaryMaterial(map)
                .map(new HttpResultAll<EmptyEntity>());

        toSubscribe(observable, subscriber);
    }


    /**
     * 确认/不确认 辅材用量申请
     */
    public void doConfirmAuxiliary(Subscriber<EmptyEntity> subscriber, String user_code, String access_token, String order_code, String check_confirm, String disagree_reason) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("order_code", order_code);
        map.put("check_confirm", check_confirm);
        map.put("disagree_reason", disagree_reason);

        Observable observable = serviceInterface.doConfirmAuxiliary(map)
                .map(new HttpResultAll<EmptyEntity>());

        toSubscribe(observable, subscriber);
    }


    /**
     * 查看图纸
     */
    public void getDrawPicture(Subscriber<CheckPictureEntity> subscriber, String user_code, String access_token, String project_code) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("project_code", project_code);

        Observable observable = serviceInterface.getDrawPicture(map)
                .map(new HttpResultAll<CheckPictureEntity>());

        toSubscribe(observable, subscriber);
    }


    /**
     * 项目详情
     */
    public void getProjectDetail(Subscriber<MyProjectsDetailEntity> subscriber, String user_code, String access_token, String project_code) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("project_code", project_code);

        Observable observable = serviceInterface.getProjectDetail(map)
                .map(new HttpResultAll<MyProjectsDetailEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 查看合同
     */
    public void getBargain(Subscriber<BargainEntity> subscriber, String user_code, String access_token, String bargain_code) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("bargain_code", bargain_code);

        Observable observable = serviceInterface.getBargain(map)
                .map(new HttpResultAll<BargainEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 查看付款信息
     */
    public void getBargainPay(Subscriber<BargainPayEntity> subscriber, String user_code, String access_token, String bargain_code) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("bargain_code", bargain_code);

        Observable observable = serviceInterface.getBargainPay(map)
                .map(new HttpResultAll<BargainPayEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 查看施工工人
     */
    public void getProjectWorkers(Subscriber<Account> subscriber, String user_code, String access_token, String project_code) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("project_code", project_code);

        Observable observable = serviceInterface.getProjectWorkers(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 查看施工日志
     */
    public void getBuildDiary(Subscriber<BuildDiaryEntity> subscriber, String user_code, String access_token, String project_code, int page, int page_size) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("project_code", project_code);
        map.put("page", page + "");
        map.put("page_size", page_size + "");

        Observable observable = serviceInterface.getBuildDiary(map)
                .map(new HttpResultAll<BuildDiaryEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 查看施工图片
     */
    public void getBuildDiaryPicture(Subscriber<BuildPhotoEntity> subscriber, String user_code, String access_token, String project_code, int page, int page_size) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("project_code", project_code);
        map.put("page", page + "");
        map.put("page_size", page_size + "");

        Observable observable = serviceInterface.getBuildDiaryPicture(map)
                .map(new HttpResultAll<BuildPhotoEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 申请施工进场
     */
    public void applyBuildStart(Subscriber<Account> subscriber, String user_code, String access_token, String project_code, String apply_start_date) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("project_code", project_code);
        map.put("apply_start_date", apply_start_date);


        Observable observable = serviceInterface.applyBuildStart(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 查看项目节点列表
     */
    public void getNodesList(Subscriber<PointEntity> subscriber, String user_code, String access_token, String project_code) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("project_code", project_code);

        Observable observable = serviceInterface.getNodesList(map)
                .map(new HttpResultAll<PointEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 查看项目节点详情
     */
    public void getNodeDetail(Subscriber<PointProgressDetailEntity> subscriber, String user_code, String access_token, String nid) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("nid", nid);

        Observable observable = serviceInterface.getNodeDetail(map)
                .map(new HttpResultAll<PointProgressDetailEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 项目需要的工种列表
     */
    public void getProjectWorkerTypeList(Subscriber<CheckWorkersEntity> subscriber, String user_code, String access_token, String project_code) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("project_code", project_code);

        Observable observable = serviceInterface.getProjectWorkerTypeList(map)
                .map(new HttpResultAll<CheckWorkersEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 查看工人名片
     */
    public void getShowWorkerCard(Subscriber<ProjectWorkersInfoEntity> subscriber, String user_code, String access_token, String worker_code) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("worker_code", worker_code);

        Observable observable = serviceInterface.getShowWorkerCard(map)
                .map(new HttpResultAll<ProjectWorkersInfoEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 选择项目工人
     */
    public void chooseProjectWorker(Subscriber<Account> subscriber, String user_code, String access_token, String project_code, String worker_code, String worker_type) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("project_code", project_code);
        map.put("worker_code", worker_code);
        map.put("worker_type", worker_type);

        Observable observable = serviceInterface.chooseProjectWorker(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 选择驻厂工人
     */
    public void chooseProjectWorkerFactory(Subscriber<Account> subscriber, String user_code, String access_token, String project_code, String worker_type) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("project_code", project_code);
        map.put("worker_type", worker_type);

        Observable observable = serviceInterface.chooseProjectWorkerFactory(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 施工员-确认进场-进场节点
     */
    public void doNodeStartIn(Subscriber<Object> subscriber, String user_code, String access_token, String nid) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("nid", nid);

        Observable observable = serviceInterface.doNodeStartIn(map)
                .map(new HttpResultAll<Object>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 开始施工
     */
    public void doNodeStartWorking(Subscriber<Object> subscriber, String user_code, String access_token, String nid) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("nid", nid);

        Observable observable = serviceInterface.doNodeStartWorking(map)
                .map(new HttpResultAll<Object>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 完成施工
     */
    public void doNodeFinishWorking(Subscriber<Object> subscriber, String user_code, String access_token, String nid) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("nid", nid);

        Observable observable = serviceInterface.doNodeFinishWorking(map)
                .map(new HttpResultAll<Object>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 施工员验收节点
     */
    public void doCrewCheckNodeFinish(Subscriber<Object> subscriber, String user_code, String access_token, String nid, String check_confirm, String reason, String worker_code, String price, String memo) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("nid", nid);
        map.put("check_confirm", check_confirm);
        map.put("reason", reason);
        map.put("worker_code", worker_code);
        map.put("price", price);
        map.put("memo", memo);

        Observable observable = serviceInterface.doCrewCheckNodeFinish(map)
                .map(new HttpResultAll<Object>());

        toSubscribe(observable, subscriber);
    }


    /**
     * 施工员确认图纸
     */
    public void doCrewCheckDrawingOK(Subscriber<Account> subscriber, String user_code, String access_token, String project_code,
                                     String check_confirm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("project_code", project_code);
        map.put("check_confirm", check_confirm);

        Observable observable = serviceInterface.doCrewCheckDrawingOK(map)
                .map(new HttpResultAll<ConfirmDrawPicEntity>());

        toSubscribe(observable, subscriber);
    }


    /*********************************************------施工员接口-----*****************************************************/

    /**
     * 获取施工员个人信息
     */
    public void getUserProfileInfo(Subscriber<UserInfoEntity> subscriber, String user_code, String access_token) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);


        Observable observable = serviceInterface.getUserProfileInfo(map)
                .map(new HttpResultAll<UserInfoEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 修改头像
     */
    public void doUpdateHeadimgPic(Subscriber<Account> subscriber, String user_code, String access_token, String headimg) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("headimg", headimg);

        Observable observable = serviceInterface.doUpdateHeadimgPic(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 修改密码
     */
    public void doChangePassword(Subscriber<Account> subscriber, String user_code, String access_token, String old_password, String password) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("old_password", old_password);
        map.put("password", password);

        Observable observable = serviceInterface.doChangePassword(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 修改姓名
     */
    public void doChangeName(Subscriber<Account> subscriber, String user_code, String access_token, String name) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("name", name);

        Observable observable = serviceInterface.doChangeName(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 修改性别
     */
    public void doChangeSex(Subscriber<Account> subscriber, String user_code, String access_token, String sex) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("sex", sex);

        Observable observable = serviceInterface.doChangeSex(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 修改手机号码
     */
    public void doChangePhone(Subscriber<Account> subscriber, String user_code, String access_token, String phone, String verify_code) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("phone", phone);
        map.put("verify_code", verify_code);

        Observable observable = serviceInterface.doChangePhone(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 修改所属地
     */
    public void doChangeArea(Subscriber<Account> subscriber, String user_code, String access_token, String work_province, String work_city) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("work_province", work_province);
        map.put("work_city", work_city);

        Observable observable = serviceInterface.doChangeArea(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 修改详细地址
     */
    public void doChangeAddressDetail(Subscriber<Account> subscriber, String user_code, String access_token, String province, String city, String area, String address) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("province", province);
        map.put("city", city);
        map.put("area", area);
        map.put("address", address);

        Observable observable = serviceInterface.doChangeAddressDetail(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 我的推荐工人列表
     */
    public void getRecommendWorkersList(Subscriber<RecommendWorkerListEntity> subscriber, String user_code, String access_token) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);

        Observable observable = serviceInterface.getRecommendWorkersList(map)
                .map(new HttpResultAll<RecommendWorkerListEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 我的推荐工人佣金详情
     */
    public void getRecommendWorkerDetail(Subscriber<RecommendWorkerDetailEntity> subscriber, String user_code, String access_token, String recommended_code) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("recommended_code", recommended_code);

        Observable observable = serviceInterface.getRecommendWorkerDetail(map)
                .map(new HttpResultAll<RecommendWorkerDetailEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 推荐工人
     */
    public void doRecommendWorker(Subscriber<Account> subscriber, String user_code, String access_token, String worker_name, String telephone) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("worker_name", worker_name);
        map.put("telephone", telephone);

        Observable observable = serviceInterface.doRecommendWorker(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 待办事项
     */
    public void getMyTodo(Subscriber<BackLogEntity> subscriber, String user_code, String access_token) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);

        Observable observable = serviceInterface.getMyTodo(map)
                .map(new HttpResultAll<BackLogEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 消息通知
     */
    public void getMyMessage(Subscriber<BackLogNewMsgEntity> subscriber, String user_code, String access_token, int page, int page_size) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("page", page + "");
        map.put("page_size", page_size + "");

        Observable observable = serviceInterface.getMyMessage(map)
                .map(new HttpResultAll<BackLogNewMsgEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 添加施工日志
     */
    public void doAddProjectDiary(Subscriber<Account> subscriber, String user_code, String access_token, String project_code, String info) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("project_code", project_code);
        map.put("info", info);

        Observable observable = serviceInterface.doAddProjectDiary(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 成为施工员
     */
    public void doBecomeToCrew(Subscriber<Account> subscriber, String user_code, String access_token, String project_code, String check_confirm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("project_code", project_code);
        map.put("check_confirm", check_confirm);

        Observable observable = serviceInterface.doBecomeToCrew(map)
                .map(new HttpResultAll<Account>());

        toSubscribe(observable, subscriber);
    }


    /**
     * 搜索我的项目工地
     */
    public void doSearchMyProjects(Subscriber<MyProjectsEntity> subscriber, String user_code, String access_token, String project_type, String search_condition, int page, int page_size) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("project_type", project_type);
        map.put("search_condition", search_condition);
        map.put("page", page + "");
        map.put("page_size", page_size + "");

        Observable observable = serviceInterface.doSearchMyProjects(map)
                .map(new HttpResultAll<MyProjectsEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 工人列表与搜索
     */
    public void doSearchWorkers(Subscriber<ChooseWorkerEntity> subscriber, String user_code, String access_token, String project_code, String worker_type,
                                String worker_age, String worker_score, String search_condition) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("project_code", project_code);
        map.put("worker_type", worker_type);
        map.put("worker_age", worker_age);
        map.put("worker_score", worker_score);
        map.put("search_condition", search_condition);

        Observable observable = serviceInterface.doSearchWorkers(map)
                .map(new HttpResultAll<ChooseWorkerEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 获取施工员个人中心信息
     */
    public void doGetPersonalInfos(Subscriber<UserEntity> subscriber, String user_code, String access_token) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);


        Observable observable = serviceInterface.doGetPersonalInfos(map)
                .map(new HttpResultAll<UserEntity>());

        toSubscribe(observable, subscriber);
    }


    /**
     * 获取android最新版本
     */
    public void getNewVersion(Subscriber<NewVersionEntity> subscriber) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("type", "crew");

        Observable observable = serviceInterface.getNewVersion(map)
                .map(new HttpResultAll<NewVersionEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 更改主材消息通知（关闭或开启）
     */
    public void changePushMaterialMessage(Subscriber<EmptyEntity> subscriber, String user_code, String access_token, String check_confirm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("check_confirm", check_confirm);

        Observable observable = serviceInterface.changePushMaterialMessage(map)
                .map(new HttpResultAll<EmptyEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 更改节点消息通知（关闭或开启）
     */
    public void changePushNodesMessage(Subscriber<EmptyEntity> subscriber, String user_code, String access_token, String check_confirm) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("check_confirm", check_confirm);

        Observable observable = serviceInterface.changePushNodesMessage(map)
                .map(new HttpResultAll<EmptyEntity>());

        toSubscribe(observable, subscriber);
    }

    /**
     * 意见反馈
     */
    public void doFeedBack(Subscriber<EmptyEntity> subscriber, String user_code, String access_token, String content) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("user_code", user_code);
        map.put("access_token", access_token);
        map.put("content", content);

        Observable observable = serviceInterface.doFeedBack(map)
                .map(new HttpResultAll<EmptyEntity>());

        toSubscribe(observable, subscriber);
    }


}
