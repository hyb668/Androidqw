package com.google.androidqw;

import com.google.androidqw.utils.BaseModel;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * ============================================================
 * <p>
 * 版 权 ： 刘宇哲 版权所有 (c) 2016
 * <p>
 * 作 者 : 刘宇哲
 * <p>
 * 版 本 ： 1.0
 * <p>
 * 创建日期 ：  on 2016/12/14.
 * <p>
 * 描 述 ：
 * <p>
 * <p>
 * 修订历史 ：
 * <p>
 * ============================================================
 **/
public interface APiBean {

    @GET()
    Observable<BaseModel> getRequestURL(@Url String url);

    //@GET()
    //Observable<T> getRequestURL(@Url String url);


   /* *//**
     * 通用模板：
     * <p>
     * 包含getResult(T)，返回泛型解析模板
     * </p>
     *//*
    public class BaseResponse {
        public String code = "-1";
        public String msg = "";
        public Object result;

        public String getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public Object getResult() {
            return result;
        }

        @SuppressWarnings("unchecked")
        public <T> T getResult(T t) {
            try {
                String resultString = gson.toJson(result);
                return gson.fromJson(resultString, (Class<T>) t);
            } catch (Exception e) {
                return null;
            }
        }

	*//*	public <T> T getResult(Class<T> t) {
            String resultString = gson.toJson(result);
			return (T)gson.fromJson(resultString, t);
		}
	*//*

    }*/
}
