package cn.chenhai.miaodj_monitor.views.fragment.detail;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import cn.chenhai.miaodj_monitor.R;
import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * Created by ChenHai--霜华 on 2016/8/10. 13:09
 * 邮箱：248866527@qq.com
 */
public class DialogCheckNotOk extends Dialog {

    View customView;

    Context context;

    private FrameLayout mPopupAuxiNotOk;
    private FrameLayout mPopImgClose;
    private EditText mPopEtContent;
    private Button mPopBtnDone;


    SubmitDoing mSubmit;

    public DialogCheckNotOk(Context context, SubmitDoing SubmitRun) {
        super(context, R.style.customDialog);

        this.context = context;
        this.mSubmit = SubmitRun;
    }


    /**
     * 展示
     *
     */
    public void show() {
        super.show();
        LayoutInflater mInflater = LayoutInflater.from(getContext());
        customView = mInflater.inflate(R.layout.popup_auxilary_not_ok, null);
        setContentView(customView);

        // 设置他的宽度

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);

        int screenWidth = dm.widthPixels;

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = screenWidth * 4 / 5;
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);

        setCancelable(false);
        setCanceledOnTouchOutside(false);

//        ButterKnife.bind(this);


        mPopImgClose = (FrameLayout) customView.findViewById(R.id.pop_img_close);
        mPopEtContent = (EditText) customView.findViewById(R.id.pop_et_content);
        mPopBtnDone = (Button) customView.findViewById(R.id.pop_btn_done);


        mPopImgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mPopBtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSubmit != null) {
                    if(TextUtils.isEmpty(mPopEtContent.getText().toString().trim())) {
                        Toast.makeText(context, "请输入不确定原因!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mSubmit.submitDoing(mPopEtContent.getText().toString());
                    dismiss();
                }
            }
        });


//        mPhoneEt.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                mBtn.setEnabled(!TextUtils.isEmpty(s.toString()) && !TextUtils.isEmpty(mNameEt.getText().toString()));
//
//            }
//        });


    }

    public View getRootView() {
        return customView;
    }


    public interface SubmitDoing {
        void submitDoing(String strReason);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dismiss();
            return false;
        }
        return super.onKeyDown(keyCode, event);

    }
}
