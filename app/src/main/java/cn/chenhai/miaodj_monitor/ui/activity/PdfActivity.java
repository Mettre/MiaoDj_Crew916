package cn.chenhai.miaodj_monitor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.GetBargainDetailEntity;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.service.helper.UIHelper;
import cn.chenhai.miaodj_monitor.ui.base.BaseActivity;
import cn.chenhai.miaodj_monitor.utils.DownServer;
import cn.chenhai.miaodj_monitor.utils.SharedPrefsUtil;
import cn.chenhai.miaodj_monitor.utils.Utils;

public class PdfActivity extends BaseActivity implements DownServer.DownComplete {


    private PDFView pdfView;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private DownServer downServer;
    private SubscriberOnSuccessListener mOnSuccessPopup;
    private String name;


    public static void startActivity(Context context, String bargain_code) {
        Intent intent = new Intent(context, PdfActivity.class);
        intent.putExtra("bargain_code", bargain_code);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdfview_main);
        pdfView = (PDFView) findViewById(R.id.pdfview);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mToolbarTitle.setText("合同详情");

        initToolbarNav(mToolbar);

        refreshData();

    }

    protected void initToolbarNav(Toolbar toolbar) {
        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_white32);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void refreshData() {
        String user_code = PreferencesUtils.getString(PdfActivity.this, "user_code");
        String access_token = PreferencesUtils.getString(PdfActivity.this, "access_token");
        String bargain_code = getIntent().getStringExtra("bargain_code");

        mOnSuccessPopup = new SubscriberOnSuccessListener<HttpResult<GetBargainDetailEntity>>() {
            @Override
            public void onSuccess(HttpResult<GetBargainDetailEntity> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(PdfActivity.this, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(PdfActivity.this);
                } else {
                    if (result.getCode() == 200) {
                        String pdfUrl = HttpMethods.BASE_ROOT_URL + result.getInfo().getBargain().getBargain_pdf();
                        init(pdfUrl);
                    }
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError() {

            }
        };

        HttpMethods.getInstance().getBargainDetail(new ProgressSubscriber(mOnSuccessPopup, PdfActivity.this), user_code, access_token, bargain_code);


    }


    private void init(String url) {

        name = Utils.subString(url);
        String savePathString = Environment.getExternalStorageDirectory() + "/DownFile/" + name + ".pdf";
        File file = new File(savePathString);
        if (url != null) {
            Log.d("2112112", "url" + url + "name" + name + "::::::::::::::" + (SharedPrefsUtil.getValue(this, url, false) && file.exists()));
            if (SharedPrefsUtil.getValue(this, url, false) && file.exists()) {
                initView();
            } else {

                downServer = new DownServer(this, url, name);
                downServer.setmListener(this);
                downServer.downStar();
            }
        }

    }

    @Override
    public void DownComplete(boolean flog) {

        initView();
    }


    private void initView() {

        String savePathString = Environment.getExternalStorageDirectory() + "/DownFile/" + name + ".pdf";
        File file = new File(savePathString);
        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(false)
                .password(null)
                .scrollHandle(null)
                .load();


    }


}
