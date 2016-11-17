package utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * ============================================================
 * <p/>
 * 版 权 ： 刘宇哲 版权所有 (c) 2016
 * <p/>
 * 作 者 : 刘宇哲
 * <p/>
 * 版 本 ： 1.0
 * <p/>
 * 创建日期 ：  on 2016/11/16.
 * <p/>
 * 描 述 ： 类型转化
 * <p/>
 * <p/>
 * 修订历史 ：
 * <p/>
 * ============================================================
 **/
public class TUtil {

    public static <T>T getT(Object o,int i)
    {
        try {
            //拿到他的超类
            ParameterizedType superclass = (ParameterizedType) (o.getClass().getGenericSuperclass());
            //返回他的数据类型
            Type type = superclass.getActualTypeArguments()[i];
            //转成class文件
            Class<T> aClass= (Class<T>)type;
            //返回他的实例
            return aClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

}
