package cn.chenhai.miaodj_monitor.view_custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cn.chenhai.miaodj_monitor.R;

/**
 * Created by ChenHai--霜华 on 2016/4/29
 * 邮箱：248866527@qq.com
 */

public class SearchViewCut extends LinearLayout implements View.OnClickListener {

    /*** 输入框*/
    private EditText etInput;

    /*** 删除键*/
    private ImageView ivDelete;

    private ImageView ivSearch;

    /*** 上下文对象*/
    private Context mContext;

    /*** 搜索回调接口*/
    private SearchViewListener mListener;

    /*** 设置搜索回调接口 @param listener 监听者*/
    public void setSearchViewListener(SearchViewListener listener) {
        mListener = listener;
    }

    public SearchViewCut(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.search_layout, this);
        initViews();
    }

    private void initViews() {
        etInput = (EditText) findViewById(R.id.search_et_input);
        ivDelete = (ImageView) findViewById(R.id.search_iv_delete);
        //btnBack = (Button) findViewById(R.id.search_btn_back);
        ivSearch = (ImageView) findViewById(R.id.search_iv_search) ;

        ivDelete.setOnClickListener(this);
        //btnBack.setOnClickListener(this);
        ivSearch.setOnClickListener(this);

        etInput.setOnClickListener(this);

    }

    public String getEtInputText(){
        return etInput.getText().toString();
    }
    public void setEtInputText(String text){
        etInput.setText(text);
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    /**
     * 通知监听者 进行搜索操作
     * @param text
     */
    private void notifyStartSearching(String text){
        if (mListener != null) {
            mListener.onSearch(etInput.getText().toString());
        }
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_et_input:
                break;
            case R.id.search_iv_delete:
                etInput.setText("");
                ivDelete.setVisibility(GONE);
                break;
            case R.id.search_iv_search:
                notifyStartSearching(etInput.getText().toString());
                //隐藏软键盘
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
//            case R.id.search_btn_back:
//                ((Activity) mContext).finish();
//                break;
        }
    }

    /**
     * search view回调方法
     */
    public interface SearchViewListener {

        /**
         * 开始搜索
         *
         * @param text 传入输入框的文本
         */
        void onSearch(String text);

    }

}


