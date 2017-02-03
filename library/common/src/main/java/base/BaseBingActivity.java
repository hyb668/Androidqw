package base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

/**
 * Created by liuyuzhe on 2017/2/2.
 */

public abstract class BaseBingActivity<SV extends ViewDataBinding> extends AppCompatActivity {


    protected SV bindingView;
    public Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        //super.setContentView(layoutResID);
        //getDelegate().setContentView(layoutResID);
        bindingView = DataBindingUtil.inflate(LayoutInflater.from(this), layoutResID, null, false);
        getWindow().setContentView(bindingView.getRoot());
        initData();
    }

    protected abstract void initData();
}
