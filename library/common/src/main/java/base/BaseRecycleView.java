package base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import utils.ImageLoaderUtils;
import view.DataIO;

/**
 * ============================================================
 * <p/>
 * 版 权 ： 刘宇哲 版权所有 (c) 2016
 * <p/>
 * 作 者 : 刘宇哲
 * <p/>
 * 版 本 ： 1.0
 * <p/>
 * 创建日期 ：  on 2016/11/21.
 * <p/>
 * 描 述 ：
 * <p/>
 * <p/>
 * 修订历史 ：
 * <p/>
 * ============================================================
 **/
public class BaseRecycleView<T> extends RecyclerView.Adapter implements DataIO<T>{
    private Context mContext;
    protected  List<T> datas;

    public BaseRecycleView(Context context,List<T> datas) {
        mContext = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public void  setVisible(View v, boolean visible){
        if (visible) {
            v.setVisibility(View.VISIBLE);
        }else{
            v.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    @Override
    public void add(T elem) {

    }

    @Override
    public void addAt(int location, T elem) {

    }

    @Override
    public void addAll(List<T> elements) {

    }

    @Override
    public void addAllAt(int location, List<T> elements) {

    }

    @Override
    public void remove(T elem) {

    }

    @Override
    public void removeAll(List<T> elements) {

    }

    @Override
    public void removeAt(int index) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void replace(T oldElem, T newElem) {

    }

    @Override
    public void replaceAt(int index, T elem) {

    }

    @Override
    public void replaceAll(List<T> elements) {

    }

    @Override
    public List<T> getAll() {
        return null;
    }

    @Override
    public T get(int position) {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    public void  setImageUrl(String url, ImageView view) {
        ImageLoaderUtils.display(mContext,view,url);
    }

    @Override
    public boolean contains(T elem) {


        return false;
    }
}
