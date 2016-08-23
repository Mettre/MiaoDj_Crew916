package cn.chenhai.miaodj_monitor.ui.fragment.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.ui.adapter.DetailBuildDiaryPager2Adapter;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.service.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.service.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.BuildPhotoEntity;
import cn.chenhai.miaodj_monitor.model.info.BuildPhoto_Info;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.ui.base.BaseFragment;
import cn.chenhai.miaodj_monitor.ui.module.preview.ImageInfo;

/**
 * Created by ChenHai--霜华 on 2016/6/24. 15:22
 * 邮箱：248866527@qq.com
 */
public class DetailBuildDiaryPager2 extends BaseFragment {
    private static final String ARG_FROM = "arg_from";
    private int mFrom;

    private String mProjectCode;

    private SubscriberOnSuccessListener mOnSuccessInit;

    private RecyclerView mRecycler;
    private LinearLayoutManager mLLmanager;
    private DetailBuildDiaryPager2Adapter mAdapter;


    public static DetailBuildDiaryPager2 newInstance(int from , String projectCode) {
        Bundle args = new Bundle();
        args.putInt(ARG_FROM, from);
        args.putString("projectCode",projectCode);

        DetailBuildDiaryPager2 fragment = new DetailBuildDiaryPager2();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mFrom = args.getInt(ARG_FROM);
            mProjectCode = args.getString("projectCode");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_build_diary_pager, container, false);

        initView(view);
        //initDataTemp();

        return view;
    }

    private void initView(View view) {
        mRecycler = (RecyclerView) view.findViewById(R.id.recycler);

        mAdapter = new DetailBuildDiaryPager2Adapter(_mActivity);
        mLLmanager = new LinearLayoutManager(_mActivity);
        mRecycler.setLayoutManager(mLLmanager);
        mRecycler.setAdapter(mAdapter);

//        mAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(int position, View view) {
//                //((MainActivity)getActivity()).getResideLayout().setIfSlide(true);
//                TimerTask task = new TimerTask(){
//                    public void run(){
//                        //execute the task
//                        if (getParentFragment() instanceof PersonalBacklogFragment) {
//                            //((HomeFragment) getParentFragment()).start(DetailAgreeFragment.newInstance("测试单号111"));
//                            ((PersonalBacklogFragment) getParentFragment()).start(CycleFragment.newInstance(1));
//                        }
//                    }
//                };
//                Timer timer = new Timer();
//                timer.schedule(task, 260);
//            }
//        });
        mAdapter.setOnItemBtnClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                TimerTask task = new TimerTask(){
                    public void run(){
//                        if (getParentFragment() instanceof PersonalBacklogFragment) {
//                            ((PersonalBacklogFragment) getParentFragment()).start(CycleFragment.newInstance(1));
//                        }
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 50);
            }
        });


        mOnSuccessInit = new SubscriberOnSuccessListener<HttpResult<BuildPhotoEntity>>() {
            @Override
            public void onSuccess(HttpResult<BuildPhotoEntity> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(_mActivity,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    List<BuildPhotoEntity.DrawingsBean> projects = result.getInfo().getDrawings();
                    List<BuildPhoto_Info> list = new ArrayList<>();
                    for (int i=0 ;i<projects.size() ;i++){
                        BuildPhoto_Info pageInfo = new BuildPhoto_Info();
                        BuildPhotoEntity.DrawingsBean nodeInfo = projects.get(i);

                        String headPath = "";
                        if(nodeInfo.getWorker_headimg() != null) {
                            if(!nodeInfo.getWorker_headimg().equals("")) {
                                headPath = HttpMethods.BASE_ROOT_URL + nodeInfo.getWorker_headimg();
                            }
                        }
                        pageInfo.setImg_portraitPath(headPath);

                        pageInfo.setDate(nodeInfo.getCreatetime());
                        pageInfo.setDayNum(String.valueOf(nodeInfo.getAfter_start()));

//                        if(nodeInfo.getCan_edit() != null){
//                            if(nodeInfo.getCan_edit().equals("Y")){
//                                pageInfo.setIfEdit(true);
//                            } else if(nodeInfo.getCan_edit().equals("N")){
//                                pageInfo.setIfEdit(false);
//                            }
//                        }

                        pageInfo.setWorker_name(nodeInfo.getWorker_name());
                        pageInfo.setWorker_type(nodeInfo.getWorker_type_name());

                        String pic1 = "";
                        if(nodeInfo.getPic_one() != null) {
                            if(!nodeInfo.getPic_one().equals("")) {
                                pic1 = HttpMethods.BASE_ROOT_URL + nodeInfo.getPic_one();
                            }
                        }

                        String pic2 = "";
                        if(nodeInfo.getPic_two() != null) {
                            if(!nodeInfo.getPic_two().equals("")) {
                                pic2 = HttpMethods.BASE_ROOT_URL + nodeInfo.getPic_two();
                            }
                        }

                        String pic3 = "";
                        if(nodeInfo.getPic_three() != null) {
                            if(!nodeInfo.getPic_three().equals("")) {
                                pic3 = HttpMethods.BASE_ROOT_URL + nodeInfo.getPic_three();
                            }
                        }

                        String pic4 = "";
                        if(nodeInfo.getPic_four() != null) {
                            if(!nodeInfo.getPic_four().equals("")) {
                                pic4 = HttpMethods.BASE_ROOT_URL + nodeInfo.getPic_four();
                            }
                        }

                        String pic5 = "";
                        if(nodeInfo.getPic_five() != null) {
                            if(!nodeInfo.getPic_five().equals("")) {
                                pic5 = HttpMethods.BASE_ROOT_URL + nodeInfo.getPic_five();
                            }
                        }

                        String pic1Thumb = "";
                        if(nodeInfo.getThumb_pic_one() != null) {
                            if(!nodeInfo.getThumb_pic_one().equals("")) {
                                pic1Thumb = HttpMethods.BASE_ROOT_URL + nodeInfo.getThumb_pic_one();
                            }
                        }
                        pageInfo.setPhoto_path1(pic1Thumb);

                        String pic2Thumb = "";
                        if(nodeInfo.getThumb_pic_two() != null) {
                            if(!nodeInfo.getThumb_pic_two().equals("")) {
                                pic2Thumb = HttpMethods.BASE_ROOT_URL + nodeInfo.getThumb_pic_two();
                            }
                        }
                        pageInfo.setPhoto_path2(pic2Thumb);

                        String pic3Thumb = "";
                        if(nodeInfo.getThumb_pic_three() != null) {
                            if(!nodeInfo.getThumb_pic_three().equals("")) {
                                pic3Thumb = HttpMethods.BASE_ROOT_URL + nodeInfo.getThumb_pic_three();
                            }
                        }
                        pageInfo.setPhoto_path3(pic3Thumb);

                        String pic4Thumb = "";
                        if(nodeInfo.getThumb_pic_four() != null) {
                            if(!nodeInfo.getThumb_pic_four().equals("")) {
                                pic4Thumb = HttpMethods.BASE_ROOT_URL + nodeInfo.getThumb_pic_four();
                            }
                        }
                        pageInfo.setPhoto_path4(pic4Thumb);

                        String pic5Thumb = "";
                        if(nodeInfo.getThumb_pic_five() != null) {
                            if(!nodeInfo.getThumb_pic_five().equals("")) {
                                pic5Thumb = HttpMethods.BASE_ROOT_URL + nodeInfo.getThumb_pic_five();
                            }
                        }
                        pageInfo.setPhoto_path5(pic5Thumb);

                        ArrayList<ImageInfo> imageInfoList = new ArrayList<>();
                        ImageInfo info1 = new ImageInfo();
                        info1.setThumbnailUrl(pic1Thumb);
                        info1.setBigImageUrl(pic1);
                        ImageInfo info2 = new ImageInfo();
                        info2.setThumbnailUrl(pic2Thumb);
                        info2.setBigImageUrl(pic2);
                        ImageInfo info3 = new ImageInfo();
                        info3.setThumbnailUrl(pic3Thumb);
                        info3.setBigImageUrl(pic3);
                        ImageInfo info4 = new ImageInfo();
                        info4.setThumbnailUrl(pic4Thumb);
                        info4.setBigImageUrl(pic4);
                        ImageInfo info5 = new ImageInfo();
                        info5.setThumbnailUrl(pic5Thumb);
                        info5.setBigImageUrl(pic5);

                        imageInfoList.add(info1);
                        imageInfoList.add(info2);
                        imageInfoList.add(info3);
                        imageInfoList.add(info4);
                        imageInfoList.add(info5);
                        pageInfo.setImageurls(imageInfoList);

                        list.add(pageInfo);
                    }

                    mAdapter.refreshDatas(list);

                    mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCompleted(){

            }
            @Override
            public void onError(){

            }
        };

        refreshData();
    }

    private void initDataTemp() {
        List<BuildPhoto_Info> list = new ArrayList<>();

        ArrayList<ImageInfo> imageInfoList = new ArrayList<>();
        ArrayList<ImageInfo> imageInfoList2 = new ArrayList<>();
        ArrayList<ImageInfo> imageInfoList3 = new ArrayList<>();

        ImageInfo infoNull = new ImageInfo();
        infoNull.setThumbnailUrl("");
        infoNull.setBigImageUrl("");

        ImageInfo infoLocal = new ImageInfo();
        infoNull.setThumbnailUrl(String.valueOf(R.drawable.photo_nopic));
        infoNull.setBigImageUrl(String.valueOf(R.drawable.ic_miaodj));

        ImageInfo info1 = new ImageInfo();
        info1.setThumbnailUrl("http://pic3.nipic.com/20090623/385351_095354025_2.jpg");
        info1.setBigImageUrl("http://pic3.nipic.com/20090623/385351_095354025_2.jpg");
        imageInfoList.add(info1);
        imageInfoList2.add(info1);
        imageInfoList3.add(info1);
        ImageInfo info2 = new ImageInfo();
        info2.setThumbnailUrl("http://images.zx123.cn/uploadfile/2015/0713/20150713162612_48283.jpg");
        info2.setBigImageUrl("http://images.zx123.cn/uploadfile/2015/0713/20150713162612_48283.jpg");
        imageInfoList.add(info2);
        imageInfoList2.add(info2);
        imageInfoList3.add(info2);
        ImageInfo info3 = new ImageInfo();
        info3.setThumbnailUrl("http://sylfzs.cn/userfiles/images/20140323083328-1380726360.jpg");
        info3.setBigImageUrl("http://sylfzs.cn/userfiles/images/20140323083328-1380726360.jpg");
        imageInfoList.add(info3);
        imageInfoList2.add(info3);
        imageInfoList3.add(info3);
        ImageInfo info4 = new ImageInfo();
        info4.setThumbnailUrl("http://pic11.nipic.com/20101204/6353277_135534097424_2.jpg");
        info4.setBigImageUrl("http://pic11.nipic.com/20101204/6353277_135534097424_2.jpg");
        imageInfoList.add(info4);
        imageInfoList2.add(infoNull);
        imageInfoList3.add(info4);
        ImageInfo info5 = new ImageInfo();
        info5.setThumbnailUrl("http://pic1.shejiben.com/case/2014/12/17/20141217154157-03a9fb55.jpg");
        info5.setBigImageUrl("http://pic1.shejiben.com/case/2014/12/17/20141217154157-03a9fb55.jpg");
        imageInfoList.add(info5);
        imageInfoList2.add(infoLocal);
        imageInfoList3.add(infoNull);


        for (int i = 0; i < 20; i++) {
            BuildPhoto_Info pageInfo = new BuildPhoto_Info();

            if (i % 4 == 0) {
                pageInfo.setImg_portraitPath("");
                pageInfo.setWorker_name("小波");
                pageInfo.setWorker_type("放线员");
                pageInfo.setDayNum(String.valueOf(20-i));
                pageInfo.setDate("2016-06-12");
                pageInfo.setPhoto_path1("http://pic3.nipic.com/20090623/385351_095354025_2.jpg");
                pageInfo.setPhoto_path2("http://images.zx123.cn/uploadfile/2015/0713/20150713162612_48283.jpg");
                pageInfo.setPhoto_path3("http://sylfzs.cn/userfiles/images/20140323083328-1380726360.jpg");
                pageInfo.setPhoto_path4("");
                //pageInfo.setPhoto_path5("http://pic1.shejiben.com/case/2014/12/17/20141217154157-03a9fb55.jpg");

                pageInfo.setImageurls(imageInfoList2);

            } else if (i % 4 == 1) {
                pageInfo.setImg_portraitPath("http://h.hiphotos.baidu.com/zhidao/pic/item/7c1ed21b0ef41bd5da8c805250da81cb38db3dbc.jpg");
                pageInfo.setWorker_name("张丽丽");
                pageInfo.setWorker_type("水电工");
                pageInfo.setDayNum(String.valueOf(20-i));
                pageInfo.setDate("2016-06-12");
                pageInfo.setPhoto_path1("http://pic3.nipic.com/20090623/385351_095354025_2.jpg");
                pageInfo.setPhoto_path2("http://images.zx123.cn/uploadfile/2015/0713/20150713162612_48283.jpg");
                pageInfo.setPhoto_path3("http://sylfzs.cn/userfiles/images/20140323083328-1380726360.jpg");
                pageInfo.setPhoto_path4("http://pic11.nipic.com/20101204/6353277_135534097424_2.jpg");
                pageInfo.setPhoto_path5("");

//                ImageInfo[] temImageInfo = new ImageInfo[imageInfoList.size()];
//                imageInfoList.toArray(temImageInfo);
//                temImageInfo[4].setThumbnailUrl("");
//                temImageInfo[4].setBigImageUrl("");
//                pageInfo.setImageurls(Arrays.asList(temImageInfo));
                pageInfo.setImageurls(imageInfoList3);

            } else if (i % 4 == 2) {
                pageInfo.setImg_portraitPath("http://img3.duitang.com/uploads/item/201501/28/20150128194217_mYSVJ.jpeg");
                pageInfo.setWorker_name("张丽丽");
                pageInfo.setWorker_type("水电工");
                pageInfo.setDayNum(String.valueOf(20-i));
                pageInfo.setDate("2016-06-12");
                pageInfo.setPhoto_path1("http://pic3.nipic.com/20090623/385351_095354025_2.jpg");
                pageInfo.setPhoto_path2("http://images.zx123.cn/uploadfile/2015/0713/20150713162612_48283.jpg");
                pageInfo.setPhoto_path3("http://sylfzs.cn/userfiles/images/20140323083328-1380726360.jpg");
                pageInfo.setPhoto_path4("http://pic11.nipic.com/20101204/6353277_135534097424_2.jpg");
                pageInfo.setPhoto_path5("http://pic1.shejiben.com/case/2014/12/17/20141217154157-03a9fb55.jpg");
                pageInfo.setImageurls(imageInfoList);

            } else if (i % 4 == 3) {
                pageInfo.setImg_portraitPath("http://img2.imgtn.bdimg.com/it/u=375192498,2173854692&fm=21&gp=0.jpg");
                pageInfo.setWorker_name("崇丽丽");
                pageInfo.setWorker_type("木工");
                pageInfo.setDayNum(String.valueOf(20-i));
                pageInfo.setDate("2016-06-12");
                pageInfo.setPhoto_path1("http://pic3.nipic.com/20090623/385351_095354025_2.jpg");
                pageInfo.setPhoto_path2("http://images.zx123.cn/uploadfile/2015/0713/20150713162612_48283.jpg");
                pageInfo.setPhoto_path3("http://sylfzs.cn/userfiles/images/20140323083328-1380726360.jpg");
                pageInfo.setPhoto_path4("http://pic11.nipic.com/20101204/6353277_135534097424_2.jpg");
                pageInfo.setPhoto_path5("http://pic1.shejiben.com/case/2014/12/17/20141217154157-03a9fb55.jpg");
                pageInfo.setImageurls(imageInfoList);

            }
            list.add(pageInfo);
        }
        mAdapter.refreshDatas(list);
    }

    private void refreshData(){
        String user_code = PreferencesUtils.getString(_mActivity,"user_code");
        String access_token =  PreferencesUtils.getString(_mActivity,"access_token");
        HttpMethods.getInstance().getBuildDiaryPicture(new ProgressSubscriber(mOnSuccessInit, _mActivity), user_code, access_token,mProjectCode);
    }

    protected void updateData() {

    }

    private void scrollToTop() {
        mRecycler.smoothScrollToPosition(0);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
