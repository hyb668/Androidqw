package base;

/**
 * 基类
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import baserx.RxManager;
import butterknife.ButterKnife;
import utils.TUtil;
import view.StatusBarCompat;

/***************使用例子*********************/
//1.mvp模式
//public class SampleActivity extends BaseActivity<NewsChanelPresenter, NewsChannelModel>implements NewsChannelContract.View {
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_news_channel;
//    }
//
//    @Override
//    public void initPresenter() {
//        mPresenter.setVM(this, mModel);
//    }
//
//    @Override
//    public void initView() {
//    }
//}
//2.普通模式
//public class SampleActivity extends BaseActivity {
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_news_channel;
//    }
//
//    @Override
//    public void initPresenter() {
//    }
//
//    @Override
//    public void initView() {
//    }
//}
public abstract class BaseActivity<T extends BasePresenter,E extends BaseModel> extends AppCompatActivity {
    public T mPresenter;
    public RxManager mRxManager;
    public Context mContext;
    public E mModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxManager = new RxManager();
        // TODO: 2016/11/16  昼夜切换
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mContext=this;
        //当前类 和 索引0 转化成 p
        mPresenter=TUtil.getT(this,0);
        mModel=TUtil.getT(this,1);
        if (mPresenter != null) {
            mPresenter.mContext=this;
        }
        this.initPresenter();
        this.initView();
    }

    /*********************子类实现*****************************/
    //获取布局文件
    public abstract int getLayoutId();
    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();
    //初始化view
    public abstract void initView();


    /**
     *  状态栏 4.4以上系统有效
     */
    public void setTranslanterBar()
    {
        StatusBarCompat.translucentStatusBar(this);
    }
}
