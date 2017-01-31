package com.google.androidqw.ui.news.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.google.androidqw.R;
import com.google.androidqw.bean.NewsChannelTable;
import com.google.androidqw.ui.news.adapter.ChannelAdapter;
import com.google.androidqw.ui.news.contract.NewsChannelContract;
import com.google.androidqw.ui.news.model.ChannelItemMoveChanage;
import com.google.androidqw.ui.news.model.NewsChannelModel;
import com.google.androidqw.ui.news.prensenter.NewsChannelPrensent;
import com.google.androidqw.view.ItemDragHelpterCallBack;

import java.util.List;

import app.AppConstant;
import base.BaseActivity;
import butterknife.Bind;
import rx.functions.Action1;

/**
 * 新闻频道
 * Created by liuyuzhe on 2017/1/30.
 */
public class NewsChannelActivity extends BaseActivity<NewsChannelPrensent, NewsChannelModel> implements NewsChannelContract.View {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    private RecyclerView mRecycleNewMineChannel;
    private RecyclerView mRecycleNewMoreChannel;
    private ChannelAdapter mMoreAdapter;
    private ChannelAdapter mMineAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activit_new_channel;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        mRecycleNewMineChannel = (RecyclerView) findViewById(R.id.recycle_new_mine_channel);
        mRecycleNewMoreChannel = (RecyclerView) findViewById(R.id.recycle_new_more_channel);
        mRecycleNewMineChannel.setLayoutManager(new GridLayoutManager(mContext, 4, LinearLayoutManager.VERTICAL, false));
        mRecycleNewMineChannel.setItemAnimator(new DefaultItemAnimator());
        mRecycleNewMoreChannel.setLayoutManager(new GridLayoutManager(mContext, 4, LinearLayoutManager.VERTICAL, false));

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolBarFinish(NewsChannelActivity.this);
            }
        });
        mRxManager.on(AppConstant.CHANNEL_SWAP, new Action1<ChannelItemMoveChanage>() {
            @Override
            public void call(ChannelItemMoveChanage channelItemMoveChanage) {
                //我，觉得，这里应该保存下来吧，然后最后结束再告诉告诉服务器
                if (channelItemMoveChanage != null) {
                    mPresenter.onItemSwap(mMineAdapter.getAll(),
                            channelItemMoveChanage.getFromPosition(),channelItemMoveChanage.getToPosition());
                }
            }
        });

        mPresenter.requestChannelDatas();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, NewsChannelActivity.class));
    }


    @Override
    public void returnMineChannelDatas(List<NewsChannelTable> datas) {
        mMineAdapter = new ChannelAdapter(mContext, R.layout.item_news_channel);
        mRecycleNewMineChannel.setAdapter(mMineAdapter);
        mMineAdapter.replaceAll(datas);

        mMineAdapter.setChannelListener(new ChannelAdapter.OnItemChannelListener<NewsChannelTable>() {
            @Override
            public void onChannelChange(View view, int position, NewsChannelTable table) {
                //界面数据
                mMineAdapter.remove(table);
                mMoreAdapter.add(table);
                //同步服务器
                mPresenter.onAddorRemove(mMineAdapter.getAll(),mMoreAdapter.getAll());
            }
        });
        //因为我的里面可以拖拽所有设置下面这些
        ItemDragHelpterCallBack helpterCallBack = new ItemDragHelpterCallBack(mMineAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(helpterCallBack);
        itemTouchHelper.attachToRecyclerView(mRecycleNewMineChannel);
        //这句是为了调用扩展方法
        mMineAdapter.setDragHelpterCallBack(helpterCallBack);

    }

    @Override
    public void returnMoreChannelDatas(List<NewsChannelTable> datas) {

        mMoreAdapter = new ChannelAdapter(mContext, R.layout.item_news_channel);
        mRecycleNewMoreChannel.setAdapter(mMoreAdapter);
        mMoreAdapter.replaceAll(datas);
        mMoreAdapter.setChannelListener(new ChannelAdapter.OnItemChannelListener<NewsChannelTable>() {
            @Override
            public void onChannelChange(View view, int position, NewsChannelTable table) {
                    //界面数据
                    mMoreAdapter.remove(table);
                    mMineAdapter.add(table);
                    //同步服务器
                    mPresenter.onAddorRemove(mMineAdapter.getAll(),mMoreAdapter.getAll());

            }
        });

    }

    @Override
    public void showLoading(String title) {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showErrorTip(String msg) {

    }
}
