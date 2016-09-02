package cn.chenhai.miaodj_monitor.ui.view_custom;

import android.app.DatePickerDialog;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhy.autolayout.AutoFrameLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.utils.TimeUtil;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * <时间选择Pop>
 * <功能详细描述>
 *
 * @author Allen
 * @version [版本号, 2016/8/30 11:48]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TimeSelectPop extends PopupWindow {

    private PopupWindow mPopupWindow;

    //private AutoFrameLayout mPopupApply;
//    private ImageButton mApplyImgClose;
//    private TextView mPublishTvDate;
//    private Button mPublishBtn;

    private SupportActivity mContext;
    private ImageView mPopupTimePickIcon;
    private TextView mPopupTimePickTitleTv;
    private TextView mPopupTimePickDataLableTv;
    private ImageButton mPopupTimePickClose;
    private TextView mPopupTimePickDateTv;
    private Button mPopupTimePickSubmitBtn;
    private TextView mPopupTimePickHintOne;
    private TextView mPopupTimePickHintTwe;
    private AutoFrameLayout mPopupTimePickRootView;

    SubmitOnClickListener mSubmit;

    private int mType = 0;

    //获取日期格式器对象
    private DateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private DateFormat fmtTime = new SimpleDateFormat("HH:mm", Locale.getDefault());

    //获取一个日历对象
    private Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);

    public TimeSelectPop(SupportActivity context, int type, SubmitOnClickListener mSubmitOnClickListener) {
        this.mContext = context;
        this.mSubmit = mSubmitOnClickListener;
        this.mType = type;

        // 一个自定义的布局，作为显示的内容
        View popView = LayoutInflater.from(mContext).inflate(R.layout.popup_time_pick, null);
        mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //初始化
        initPopWindow(popView, mPopupWindow);

        switch (mType) {
            case 0://申请施工进场
                mPopupTimePickIcon.setBackground(mContext.getResources().getDrawable(R.drawable.ic_apply_work));
                mPopupTimePickTitleTv.setText("申请施工进场");
                mPopupTimePickTitleTv.setTextColor(mContext.getResources().getColor(R.color.btn_yellow));
                mPopupTimePickDataLableTv.setText("请确定预计进场日期");
                mPopupTimePickSubmitBtn.setBackground(mContext.getResources().getDrawable(R.drawable.btn_effect_bg_yellow));
                mPopupTimePickHintOne.setText("请先和业主沟通确认施工进场日期");
                mPopupTimePickHintTwe.setText("施工进场申请待业主确认后，施工节点将按日期自动生成");
                break;
            case 1://主材发起配送
                mPopupTimePickIcon.setBackground(mContext.getResources().getDrawable(R.drawable.ic_pop_delivery_blue));
                mPopupTimePickTitleTv.setText("发起配送");
                mPopupTimePickTitleTv.setTextColor(mContext.getResources().getColor(R.color.text_color_blue));
                mPopupTimePickDataLableTv.setText("期望送达日期");
                mPopupTimePickSubmitBtn.setBackground(mContext.getResources().getDrawable(R.drawable.btn_effect_bg_blue));
                mPopupTimePickHintOne.setText("发起配送说明：");
                mPopupTimePickHintTwe.setText("主材发起配送到工地，在此请编辑期望送达日期！");
                break;
            case 2://辅材发起配送
                mPopupTimePickIcon.setBackground(mContext.getResources().getDrawable(R.drawable.ic_pop_delivery_orange));
                mPopupTimePickTitleTv.setText("发起配送");
                mPopupTimePickTitleTv.setTextColor(mContext.getResources().getColor(R.color.text_color_orange));
                mPopupTimePickDataLableTv.setText("期望送达日期");
                mPopupTimePickSubmitBtn.setBackground(mContext.getResources().getDrawable(R.drawable.btn_effect_bg_orange));
                mPopupTimePickHintOne.setText("发起配送说明：");
                mPopupTimePickHintTwe.setText("辅材辅料发起配送到工地，在此请编辑期望送达日期！");
                break;
        }

        //当点击DatePickerDialog控件的设置按钮时，调用该方法
        DatePickerDialog.OnDateSetListener dataSetCallBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                //修改日历控件的年，月，日
                //这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
                dateAndTime.set(Calendar.YEAR, year);
                dateAndTime.set(Calendar.MONTH, monthOfYear);
                dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //将页面TextView的显示更新为最新时间
                upDateDate();

            }
        };

        // 设置按钮的点击事件
        mPopupTimePickClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();

            }
        });

        mPopupTimePickDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dateDlg = new DatePickerDialog(mContext,
                        dataSetCallBack,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH));

                dateDlg.getDatePicker().setMinDate(System.currentTimeMillis());
                dateDlg.show();

            }
        });
        upDateDate();


        mPopupTimePickSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(_mActivity, "输入的货物重量超标！请重输", Toast.LENGTH_SHORT).show();
                long timeMill = dateAndTime.getTimeInMillis();

                mPopupWindow.dismiss();

                if (mSubmit != null) {
                    mSubmit.SubmitOnClickListener(TimeUtil.getFormat(String.valueOf(timeMill)));
                }
            }
        });


        /** 禁止点击外部区域取消popup windows*/
        mPopupTimePickRootView.setFocusable(true);
        mPopupTimePickRootView.setFocusableInTouchMode(true);
        mPopupTimePickRootView.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // 手机键盘上的返回键
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:
                        mPopupWindow.dismiss();
                        break;
                }
                return false;
            }
        });
        /** ----------------------------------------------*/

        mPopupWindow.setTouchable(true);

        //获取焦点
        mPopupWindow.setFocusable(true);

        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());

        mPopupWindow.update();
//        if (!mPopupWindow.isShowing()) {
//            //设置显示位置
//            mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//        }

    }

    public void show(View parentView) {
        // TODO Auto-generated method stub
//        showAtLocation(parentView, Gravity.CENTER, 0, 0);
//        backgroundAlpha(0.5f);

        backgroundAlpha(0.3f, 1f);//透明度
        if (!mPopupWindow.isShowing()) {
            //设置显示位置
            mPopupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);
        }
    }


    /**
     * 初始化popWindow
     */
    private void initPopWindow(View popView, PopupWindow popupWindow) {

        //设置popwindow出现和消失动画
        popupWindow.setAnimationStyle(R.style.PopupAnimation);

        mPopupTimePickTitleTv = (TextView) popView.findViewById(R.id.popup_time_pick_titleTv);
        mPopupTimePickIcon = (ImageView) popView.findViewById(R.id.popup_time_pick_icon);
        mPopupTimePickDataLableTv = (TextView) popView.findViewById(R.id.popup_time_pick_dataLableTv);
        mPopupTimePickDateTv = (TextView) popView.findViewById(R.id.popup_time_pick_dateTv);
        mPopupTimePickClose = (ImageButton) popView.findViewById(R.id.popup_time_pick_close);
        mPopupTimePickSubmitBtn = (Button) popView.findViewById(R.id.popup_time_pick_submitBtn);
        mPopupTimePickHintOne = (TextView) popView.findViewById(R.id.popup_time_pick_hintOne);
        mPopupTimePickHintTwe = (TextView) popView.findViewById(R.id.popup_time_pick_hintTwe);
        mPopupTimePickRootView = (AutoFrameLayout) popView.findViewById(R.id.popup_time_pick_rootView);

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha, float bgDim) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.dimAmount = bgDim;
        lp.alpha = bgAlpha; //0.0-1.0
        mContext.getWindow().setAttributes(lp);

        mContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements OnDismissListener {
        @Override
        public void onDismiss() {
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f, 0.1f);
        }
    }

    private void upDateDate() {
        mPopupTimePickDateTv.setText(fmtDate.format(dateAndTime.getTime()));
    }


    public interface SubmitOnClickListener {
        void SubmitOnClickListener(String mData);

    }
}
