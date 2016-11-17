package base;

import android.content.Context;

import baserx.RxManager;

/**
 * ============================================================
 * <p/>
 * 版 权 ： 刘宇哲 版权所有 (c) 2015
 * <p/>
 * 作 者 : 刘宇哲
 * <p/>
 * 版 本 ： 1.0
 * <p/>
 * 创建日期 ：  on 2016/11/16.
 * <p/>
 * 描 述 ：
 * <p/>
 * <p/>
 * 修订历史 ：
 * <p/>
 * ============================================================
 **/
public class BasePresenter<T, E> {


    public E mModel;
    public T mView;
    public Context mContext;
    public RxManager mRxManager = new RxManager();

    /**
     *
     * @param v 是View里
     * @param m 是mol
     */
    public void setVM(T v, E m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public void onStart() {

    }

    public void onDestroy()
    {
        mRxManager.clear();
    }

}
