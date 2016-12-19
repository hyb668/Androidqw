package utils;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * 集合操作工具类
 *
 */
public class CollectionUtils {

    /**
     * 判断集合是否为null或者0个元素
     *
     * @param c s
     * @return
     */
    public static boolean isNullOrEmpty(Collection c) {
        return null == c || c.isEmpty();
    }

    public static Type getT(Object o) {
        try {
            Type[] superTypes = o.getClass().getGenericInterfaces();
            Type type = ((ParameterizedType) superTypes[0]).getActualTypeArguments()[0];
            return type;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T getTResult(Object o) {
        try {
            Type[] superTypes = o.getClass().getGenericInterfaces();
            Type type = ((ParameterizedType) superTypes[0]).getActualTypeArguments()[0];
            String 	resultStr=new Gson().toJson(o);
            return  new Gson().fromJson(resultStr,(Class<T>)type);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public <T> T getResult(){
//        Class<T> t = getT();
//        String 	resultStr=new Gson().toJson(result);
//        return new Gson().fromJson(resultStr,t);
//
//    }

}
