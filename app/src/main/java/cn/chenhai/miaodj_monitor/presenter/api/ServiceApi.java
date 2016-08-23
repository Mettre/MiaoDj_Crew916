package cn.chenhai.miaodj_monitor.presenter.api;

import org.json.JSONObject;

import java.util.HashMap;

import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.TestResult;
import cn.chenhai.miaodj_monitor.model.entity.ProvinceCityDistrictBean;
import cn.chenhai.miaodj_monitor.model.entity.Account;
import cn.chenhai.miaodj_monitor.model.entity.BackLogEntity;
import cn.chenhai.miaodj_monitor.model.entity.BackLogNewMsgEntity;
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
import cn.chenhai.miaodj_monitor.model.entity.RecommendWorkerDetailEntity;
import cn.chenhai.miaodj_monitor.model.entity.RecommendWorkerListEntity;
import cn.chenhai.miaodj_monitor.model.entity.SelectionListAuxilaryEntity;
import cn.chenhai.miaodj_monitor.model.entity.SelectionListEntity;
import cn.chenhai.miaodj_monitor.model.entity.SelectionListMainEntity;
import cn.chenhai.miaodj_monitor.model.entity.UserEntity;
import cn.chenhai.miaodj_monitor.model.entity.UserInfoEntity;
import cn.chenhai.miaodj_monitor.model.entity.WorkerTypesEntity;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ChenHai--霜华 on 2016/7/1. 14:57
 * 邮箱：248866527@qq.com
 */
public interface ServiceApi {

    /**测试*/
    @POST("App/Test/index")
    Observable<TestResult> doTest(@Body JSONObject jsonObject);

    /********************************------公用接口-----*********************************/

    /**登录*/
    @POST("App/Login/login")
    //Observable<HttpResult<Account>> getLogin(@Body JSONObject jsonObject);
    Observable<HttpResult<Account>> doLogin(@Body HashMap<String, String> map);

    /**注册*/
    @POST("App/Login/register")
    Observable<HttpResult<Account>> doRegister(@Body HashMap<String, String> map);

    /**获取验证码*/
    @POST("App/Login/sendmsg")
    Observable<HttpResult<Account>> getSendMsgCode(@Body HashMap<String, String> map);

    /**找回密码*/
    @POST("App/Public/getback_password")
    Observable<HttpResult<Account>> findPassWord(@Body HashMap<String, String> map);

    /**身份登出,注销*/
    @POST("App/Common/logout")
    Observable<HttpResult<Account>> doLogout(@Body HashMap<String, String> map);

    /**获取省份的名称与code*/
    @POST("App/Public/get_province")
    Observable<HttpResult<Account>> getProvince(@Body HashMap<String, String> map);

    /**通过省的code获取市*/
    @POST("App/Public/get_city")
    Observable<HttpResult<Account>> getCity(@Body HashMap<String, String> map);

    /**通过市的code获取区县*/
    @POST("App/Public/get_area")
    Observable<HttpResult<Account>> getCityArea(@Body HashMap<String, String> map);

    /**获取所有的省份，市，区*/
    @POST("App/Public/get_address")
    Observable<HttpResult<ProvinceCityDistrictBean>> getAllProvinceCityDistrict();


    /**获取所有工种列表*/
    @POST("App/Public/achieve_worktype")
    Observable<HttpResult<WorkerTypesEntity>> getAllWorkerTypes();


    /********************************------项目接口-----*********************************/


    /**获取选品单列表*/
    @POST("App/Project/get_choose_materials")
    Observable<HttpResult<SelectionListEntity>> getSelectionList(@Body HashMap<String, String> map);

    /**主材配送详情*/
    @POST("App/Project/get_material_deliver_detail")
    Observable<HttpResult<SelectionListMainEntity>> getMaterialDeliverDetail(@Body HashMap<String, String> map);

    /**发起配送*/
    @POST("App/Project/start_deliver")
    Observable<HttpResult<EmptyEntity>> doStartDeliver(@Body HashMap<String, String> map);

    /**签收单上传-图片上传*/
    @POST("App/Project/upload_sign")
    Observable<HttpResult<Account>> doUploadSignPic(@Body HashMap<String, String> map);

    /**签收货物*/
    @POST("App/Project/take_goods")
    Observable<HttpResult<Account>> signForMainMaterial(@Body HashMap<String, String> map);


    /**辅材配送单*/
    @POST("App/Project/auxiliary_deliver_order")
    Observable<HttpResult<SelectionListAuxilaryEntity>> getSelectionAuxiliaryDeliverOrder(@Body HashMap<String, String> map);

    /**辅材发起配送*/
    @POST("App/Project/start_deliver_assistant")
    Observable<HttpResult<EmptyEntity>> doStartDeliverAuxilary(@Body HashMap<String, String> map);

    /**辅材配送单 签收*/
    @POST("App/Project/take_goods_assistant")
    Observable<HttpResult<EmptyEntity>> signForAuxilaryMaterial(@Body HashMap<String, String> map);

    /** 确认/不确认 辅材用量申请 */
    @POST("App/Project/check_auxiliary")
    Observable<HttpResult<EmptyEntity>> doConfirmAuxiliary(@Body HashMap<String, String> map);

    /**查看图纸*/
    @POST("App/Project/get_drawings")
    Observable<HttpResult<CheckPictureEntity>> getDrawPicture(@Body HashMap<String, String> map);

    /**项目详情*/
    @POST("App/Project/get_projects")
    Observable<HttpResult<MyProjectsDetailEntity>> getProjectDetail(@Body HashMap<String, String> map);

    /**查看合同*/
    @POST("App/Project/get_bargain")
    Observable<HttpResult<Account>> getBargain(@Body HashMap<String, String> map);

    /**查看付款信息*/
    @POST("App/Project/get_bargain_pay")
    Observable<HttpResult<Account>> getBargainPay(@Body HashMap<String, String> map);

    /**查看施工工人*/
    @POST("App/Project/get_project_worker")
    Observable<HttpResult<Account>> getProjectWorkers(@Body HashMap<String, String> map);

    /**查看施工日志*/
    @POST("App/Project/get_project_log")
    Observable<HttpResult<BuildDiaryEntity>> getBuildDiary(@Body HashMap<String, String> map);

    /**查看施工图片*/
    @POST("App/Project/get_project_album")
    Observable<HttpResult<BuildPhotoEntity>> getBuildDiaryPicture(@Body HashMap<String, String> map);

    /**申请施工进场*/
    @POST("App/Project/apply_project_start")
    Observable<HttpResult<Account>> applyBuildStart(@Body HashMap<String, String> map);

    /**查看项目节点列表*/
    @POST("App/Project/get_nodes")
    Observable<HttpResult<PointEntity>> getNodesList(@Body HashMap<String, String> map);

    /**查看项目节点详情*/
    @POST("App/Project/get_node_detail")
    Observable<HttpResult<PointProgressDetailEntity>> getNodeDetail(@Body HashMap<String, String> map);

    /**项目需要的工种列表*/
    @POST("App/Project/project_worker_types")
    Observable<HttpResult<CheckWorkersEntity>> getProjectWorkerTypeList(@Body HashMap<String, String> map);

    /**查看工人名片*/
    @POST("App/Project/show_worker_card")
    Observable<HttpResult<ProjectWorkersInfoEntity>> getShowWorkerCard(@Body HashMap<String, String> map);

    /**选择项目工人*/
    @POST("App/Project/choose_project_worker")
    Observable<HttpResult<Account>> chooseProjectWorker(@Body HashMap<String, String> map);

    /**选择驻厂工人*/
    @POST("App/Project/choose_project_factory_worker")
    Observable<HttpResult<Account>> chooseProjectWorkerFactory(@Body HashMap<String, String> map);

    /**施工员-确认进场-进场节点*/
    @POST("App/Project/node_start_in")
    Observable<HttpResult<Object>> doNodeStartIn(@Body HashMap<String, String> map);

    /**开始施工*/
    @POST("App/Project/node_start_working")
    Observable<HttpResult<Object>> doNodeStartWorking(@Body HashMap<String, String> map);

    /**完成施工*/
    @POST("App/Project/node_finish_working")
    Observable<HttpResult<Object>> doNodeFinishWorking(@Body HashMap<String, String> map);

    /**施工员验收节点*/
    @POST("App/Project/crew_check_node_finish")
    Observable<HttpResult<Object>> doCrewCheckNodeFinish(@Body HashMap<String, String> map);



    /**施工员确认图纸*/
    @POST("App/Project/crew_check_drawing")
    Observable<HttpResult<ConfirmDrawPicEntity>> doCrewCheckDrawingOK(@Body HashMap<String, String> map);




    /*****************************------施工员接口-----***********************************/


    /**获取施工员个人信息*/
    @POST("App/Crew/get_profile")
    Observable<HttpResult<UserInfoEntity>> getUserProfileInfo(@Body HashMap<String, String> map);


    /**修改头像*/
    @POST("App/Crew/update_headimg")
    Observable<HttpResult<Account>> doUpdateHeadimgPic(@Body HashMap<String, String> map);

    /**修改密码*/
    @POST("App/Crew/update_password")
    Observable<HttpResult<Account>> doChangePassword(@Body HashMap<String, String> map);

    /**修改姓名*/
    @POST("App/Crew/update_name")
    Observable<HttpResult<Account>> doChangeName(@Body HashMap<String, String> map);

    /**	修改性别*/
    @POST("App/Crew/update_sex")
    Observable<HttpResult<Account>> doChangeSex(@Body HashMap<String, String> map);


    /**修改手机号码*/
    @POST("App/Crew/update_phone")
    Observable<HttpResult<Account>> doChangePhone(@Body HashMap<String, String> map);

    /**修改所属地*/
    @POST("App/Crew/update_area")
    Observable<HttpResult<Account>> doChangeArea(@Body HashMap<String, String> map);


    /**修改详细地址*/
    @POST("App/Crew/update_address")
    Observable<HttpResult<Account>> doChangeAddressDetail(@Body HashMap<String, String> map);

    /**我的推荐工人列表*/
    @POST("App/Crew/my_recommend_workers")
    Observable<HttpResult<RecommendWorkerListEntity>> getRecommendWorkersList(@Body HashMap<String, String> map);


    /**我的推荐工人佣金详情*/
    @POST("App/Crew/my_recommend_worker_detail")
    Observable<HttpResult<RecommendWorkerDetailEntity>> getRecommendWorkerDetail(@Body HashMap<String, String> map);

    /**推荐工人*/
    @POST("App/Crew/recommend_worker")
    Observable<HttpResult<Account>> doRecommendWorker(@Body HashMap<String, String> map);

    /**待办事项*/
    @POST("App/Crew/my_todo")
    Observable<HttpResult<BackLogEntity>> getMyTodo(@Body HashMap<String, String> map);

    /**消息通知*/
    @POST("App/Crew/my_message")
    Observable<HttpResult<BackLogNewMsgEntity>> getMyMessage(@Body HashMap<String, String> map);

    /**添加施工日志*/
    @POST("App/Project/add_project_log")
    Observable<HttpResult<Account>> doAddProjectDiary(@Body HashMap<String, String> map);

    /**成为施工员*/
    @POST("App/Crew/become_crew")
    Observable<HttpResult<Account>> doBecomeToCrew(@Body HashMap<String, String> map);

    /**搜索我的项目工地*/
    @POST("App/Crew/search_projects")
    Observable<HttpResult<MyProjectsEntity>> doSearchMyProjects(@Body HashMap<String, String> map);

    /**工人列表与搜索*/
    @POST("App/Crew/search_workers")
    Observable<HttpResult<ChooseWorkerEntity>> doSearchWorkers(@Body HashMap<String, String> map);

    /**获取施工员个人中心信息*/
    @POST("App/Crew/get_personal_page")
    Observable<HttpResult<UserEntity>> doGetPersonalInfos(@Body HashMap<String, String> map);


    /**获取android最新版本*/
    @POST("App/ExceptLogin/get_android_version")
    Observable<HttpResult<NewVersionEntity>> getNewVersion(@Body HashMap<String, String> body);

    /** 更改主材消息通知（关闭或开启）*/
    @POST("App/Communal/change_push_material_message")
    Observable<HttpResult<EmptyEntity>> changePushMaterialMessage(@Body HashMap<String, String> body);

    /**更改节点消息通知（关闭或开启）*/
    @POST("App/Communal/change_push_nodes_message")
    Observable<HttpResult<EmptyEntity>> changePushNodesMessage(@Body HashMap<String, String> body);


    /**意见反馈*/
    @POST("App/Project/push_opinion")
    Observable<HttpResult<EmptyEntity>> doFeedBack(@Body HashMap<String, String> body);

}
