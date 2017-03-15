package com.campussecurity.app.utils;

import com.campussecurity.app.net.RestDataSoure;
import com.hao.common.manager.AppManager;
import com.hao.common.rx.RESTResultTransformData;
import com.hao.common.rx.RxUtil;

import java.io.File;

import rx.Observable;
import rx.functions.Func1;
import top.zibin.luban.Luban;

/**
 * @Package com.campussecurity.app.utils
 * @作 用:
 * @创 建 人: 林国定 邮箱：linggoudingg@gmail.com
 * @日 期: 2017/3/15 0015
 */

public class RxTransforemerUtisl {
    //上传图片
    public static<T>  Observable.Transformer<T, String> updatePicture(String accountGuid, String path) {
        return tObservable -> tObservable.flatMap(new Func1<T, Observable<String>>() {
            @Override
            public Observable<String> call(T t) {
                return RestDataSoure.newInstance().updateImage(accountGuid,path)
                        .compose(RxUtil.applySchedulersJobUI())
                        .compose(new RESTResultTransformData());
            }

        });
    }
    //压缩图片
    public static<T>  Observable.Transformer<T, File> compressPicture(String path) {
       return tObservable -> tObservable.flatMap(new Func1<T, Observable<File>>() {
           @Override
           public Observable<File> call(T t) {
               return Luban.get(AppManager.getApp())
                       .load(new File(path))
                       .putGear(Luban.THIRD_GEAR)
                       .asObservable()
                       .compose(RxUtil.applySchedulersJobUI())
                       .doOnError(Throwable::printStackTrace)
                       .onErrorResumeNext(throwable -> {
                           return Observable.empty();
                       });
           }
       });

    }
}
