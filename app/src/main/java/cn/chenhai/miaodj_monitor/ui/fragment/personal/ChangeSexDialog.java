package cn.chenhai.miaodj_monitor.ui.fragment.personal;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cn.chenhai.miaodj_monitor.R;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * <警告Dialog>
 *
 * @author Allen
 * @version [版本号, 2016/6/17 14:52]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ChangeSexDialog extends Dialog {

    View customView;

    Context context;

    private SegmentedGroup mSegmented;
    private RadioButton mButton1;
    private RadioButton mButton2;
    private Button mButtonCancel;
    private Button mButtonDone;

    String strSex = "";

    SubmitDoing refreshTableRun;

    public ChangeSexDialog(Context context, SubmitDoing refreshTableRun) {
        super(context, R.style.customDialog);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.refreshTableRun = refreshTableRun;
    }


    /**
     * 展示
     *
     */
    public void show() {
        // TODO Auto-generated method stub
        super.show();
        LayoutInflater mInflater = LayoutInflater.from(getContext());
        customView = mInflater.inflate(R.layout.item_sex_choose, null);
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


        mSegmented = (SegmentedGroup) customView.findViewById(R.id.segmented);
        mButton1 = (RadioButton) customView.findViewById(R.id.button1);
        mButton2 = (RadioButton) customView.findViewById(R.id.button2);
        mButtonCancel = (Button) customView.findViewById(R.id.buttonCancel);
        mButtonDone = (Button) customView.findViewById(R.id.buttonDone);



        mSegmented.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.button1:
                        strSex = "男";
                        break;
                    case R.id.button2:
                        strSex = "女";
                        break;
                    default:
                        // Nothing to do
                }
            }
        });

//        mButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    strSex = "男";
//                }
//            }
//        });
//        mButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    strSex = "女";
//                }
//            }
//        });

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (refreshTableRun != null) {
                    refreshTableRun.submitDoing(strSex);
                    dismiss();
                }
            }
        });




//        mPhoneEt.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // TODO Auto-generated method stub
//                mBtn.setEnabled(!TextUtils.isEmpty(s.toString()) && !TextUtils.isEmpty(mNameEt.getText().toString()));
//
//            }
//        });


    }

    public View getRootView() {
        // TODO Auto-generated method stub
        return customView;
    }


    public interface SubmitDoing {
        void submitDoing(String strSex);
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
