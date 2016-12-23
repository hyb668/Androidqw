package base;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import unvieradapter.DataIO;
import unvieradapter.anim.AlphaInAnimation;
import unvieradapter.anim.BaseAnimation;
import utils.ImageLoaderUtils;

/**
 * des:基础ReclyerView适配器
 * Created by xsf
 * on 2016.04.17:03
 */
public class BaseReclyerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements DataIO<T> {

    protected Context mContext;
    protected List<T> data;
    //动画
    private int mLastPosition = -1;
    private boolean mOpenAnimationEnable = true;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mDuration = 300;
    private BaseAnimation mSelectAnimation = new AlphaInAnimation();

    protected PageBean pageBean;

    public BaseReclyerViewAdapter(Context context) {
        mContext = context;
        data = new ArrayList<>();
        pageBean = new PageBean();
    }

    public BaseReclyerViewAdapter(Context context, List<T> data) {
        mContext = context;
        this.data = data;
    }

    public void setData(List<T> d) {
        this.data = d;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //添加动画
        addAnimation(holder);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }



    public void setImageUrl(String url, ImageView view) {
        ImageLoaderUtils.display(mContext, view, url);
    }

    public void setVisible(View v, boolean visible) {
        if (visible) {
            v.setVisibility(View.VISIBLE);
        } else {
            v.setVisibility(View.GONE);
        }

    }

    /**
     * 添加动画
     *
     * @param holder
     */
    public void addAnimation(RecyclerView.ViewHolder holder) {
        if (mOpenAnimationEnable) {
            if (holder.getLayoutPosition() > mLastPosition) {
                BaseAnimation animation = null;
                if (mSelectAnimation != null) {
                    animation = mSelectAnimation;
                }
                for (Animator anim : animation.getAnimators(holder.itemView)) {
                    startAnim(anim, holder.getLayoutPosition());
                }
                mLastPosition = holder.getLayoutPosition();
            }
        }
    }

    /**
     * 开始动画
     *
     * @param anim
     * @param index
     */
    protected void startAnim(Animator anim, int index) {
        anim.setDuration(mDuration).start();
        anim.setInterpolator(mInterpolator);
    }

    /**
     * 设置动画
     *
     * @param animation ObjectAnimator
     */
    public void openLoadAnimation(BaseAnimation animation) {
        this.mOpenAnimationEnable = true;
        this.mSelectAnimation = animation;
    }

    /**
     * 关闭动画
     */
    public void closeLoadAnimation() {
        this.mOpenAnimationEnable = false;
    }

    //--------- 封装基础数据操作 Begin -----------------------------------------------------


    public List<T> getData() {
        return data;
    }




    /**
     * 添加
     *
     * @param location
     * @param elem
     */
    public void add(int location, T elem) {
        if (elem == null) return;
        data.add(location, elem);
        notifyDataSetChanged();

    }

    /**
     * 重置数据源
     *
     * @param elems
     */
    public void reset(List<T> elems) {
        if (elems == null) return;
        data.clear();
        data.addAll(elems);
        notifyDataSetChanged();
    }

    /**
     * 插入列表
     *
     * @param location
     * @param elems
     */
    public void addAll(int location, List<T> elems) {
        if (elems == null) return;
        data.addAll(location, elems);
        notifyItemRangeChanged(location, elems.size());
    }




    /**
     * 移除数据
     *
     * @param position
     */
    public void remove(int position) {
        if (position >= 0 && position < data.size()) {
            data.remove(position);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    @Override
    public void add(T elem) {
        data.add(elem);
        notifyDataSetChanged();
    }

    @Override
    public void addAt(int location, T elem) {
        data.add(location, elem);
        notifyDataSetChanged();
    }

    @Override
    public void addAll(List<T> elements) {
        data.addAll(elements);
        notifyDataSetChanged();
    }

    @Override
    public void addAllAt(int location, List<T> elements) {
        data.addAll(location, elements);
        notifyDataSetChanged();
    }

    @Override
    public void remove(T elem) {
        data.remove(elem);
        notifyDataSetChanged();
    }

    @Override
    public void removeAt(int index) {
        data.remove(index);
        notifyDataSetChanged();
    }

    @Override
    public void removeAll(List<T> elements) {
        data.removeAll(elements);
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        if (data != null && data.size() > 0) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public void replace(T oldElem, T newElem) {
        replaceAt(data.indexOf(oldElem), newElem);
    }

    @Override
    public void replaceAt(int index, T elem) {
        data.set(index, elem);
        notifyDataSetChanged();
    }

    @Override
    public void replaceAll(List<T> elements) {
        if (data.size() > 0) {
            data.clear();
        }
        data.addAll(elements);
        notifyDataSetChanged();
    }

    @Override
    public T get(int position) {
        if (position >= data.size())
            return null;
        return data.get(position);
    }

    @Override
    public List<T> getAll() {
        return data;
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public boolean contains(T elem) {
        return data.contains(elem);
    }



    /**
     * 数据大小
     *
     * @return
     */
    public int size() {
        return data == null ? 0 : data.size();
    }

    /**
     * 数据为空
     *
     * @return
     */
    public boolean isEmpty() {
        return data == null || data.isEmpty();
    }

    /**
     * 分页
     *
     * @return
     */
    public PageBean getPageBean() {
        return pageBean;
    }
}
