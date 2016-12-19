package base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import baserx.RxManager;
import butterknife.ButterKnife;
import utils.TUtil;

/**
 * 基类
 */
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
public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends AppCompatActivity {
    public T mPresenter;
    public E mModel;
    public Context mContext;
    public RxManager mRxManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRxManager=new RxManager();
        doBeforeSetcontentView();
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mContext = this;
        //Contract里声明的Presenter : 也就是当前类的第一个泛型指定的控制器赋值给mPresenter
        //Contract里声明的model : 也就是当前类的第一个泛型指定的model赋值给mModel
        mPresenter = TUtil.getT(this, 0);
        mModel=TUtil.getT(this,1);
        if(mPresenter!=null){
            mPresenter.mContext=this;
        }
        this.initPresenter();
        this.initView();
    }

    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        //设置昼夜主题
        initTheme();
        // 把actvity放到application栈中管理
        // 去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 默认着色状态栏
        SetStatusBarColor();

    }
    /*********************子类实现*****************************/
    //获取布局文件
    public abstract int getLayoutId();
    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();
    //初始化view
    public abstract void initView();


    /**
     * 设置主题
     */
    private void initTheme() {
    }
    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(){
    }
    /**
     * 着色状态栏（4.4以上系统有效）
     */
    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar(){
    }



    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 开启浮动加载进度条
     */

    /**
     * 开启浮动加载进度条
     *
     * @param msg
     */
    public void startProgressDialog(String msg) {
    }

    /**
     * 停止浮动加载进度条
     */
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();
        mRxManager.clear();
        ButterKnife.unbind(this);
    }
}
