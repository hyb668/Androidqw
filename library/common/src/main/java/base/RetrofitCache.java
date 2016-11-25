package base;

import android.content.Context;

import java.io.Serializable;

import baserx.RxSchedulers;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import utils.ACache;

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
 * 修订历史 ： 缓存网络数据
 * <p/>
 * ============================================================
 **/
public class RetrofitCache {

    /**
     *   fromNetCache = Api.getDefault(HostType.NETEASE_NEWS_VIDEO).getNewsList(Api.getCacheControl(), type, id, startPage)
     * @param context
     * @param cacheKey
     * @param fromNetCache 网络数据
     * @param forceRefresh 是否强制刷新
     * @param <T>
     * @return
     */
    public <T> Observable<T> load(final Context context, final String cacheKey, Observable<T> fromNetCache, boolean forceRefresh) {

        Observable<T> fromCache = Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                T cache = (T) ACache.get(context).getAsObject(cacheKey);
                if (cache != null) {
                    subscriber.onNext(cache);
                } else {
                    subscriber.onCompleted();
                }
            }
        }).compose(RxSchedulers.<T>io_main());


        //把数据给存起来
        fromNetCache = fromNetCache.map(new Func1<T, T>() {
            @Override
            public T call(T result) {
                ACache.get(context).put(cacheKey, (Serializable) result);
                return result;
            }
        });

        if (forceRefresh) {
            return fromNetCache;
        } else {
            return Observable.concat(fromCache,fromNetCache).first();
        }

    }
}
