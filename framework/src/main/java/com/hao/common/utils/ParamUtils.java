package com.hao.common.utils;
import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/3/13 0013.
 */
public class ParamUtils {
    /**
     * 根据参数类得到拼接的参数
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String getParam(T t) {
        StringBuffer sb = new StringBuffer();
        Class<T> clz = (Class<T>) t.getClass();
        Field[] fields = clz.getFields();
        for (Field field : fields) {
            try {
                sb.append(field.getName()).append("=").append(field.get(t)).append("&");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sb.toString().substring(0, sb.length() - 1);
    }
}
