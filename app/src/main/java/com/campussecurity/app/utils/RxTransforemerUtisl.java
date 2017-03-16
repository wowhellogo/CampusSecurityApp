package com.campussecurity.app.utils;

import com.campussecurity.app.net.RestDataSoure;
import com.campussecurity.app.securitycheck.model.UpdateParam;
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
    public static Observable.Transformer<String, String> updatePicture() {
        return tObservable -> tObservable.flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String t) {
                return RestDataSoure.newInstance().updateImage(t)
                        .compose(RxUtil.applySchedulersJobUI())
                        .compose(new RESTResultTransformData());
            }

        });
    }


    /**
     * 构建上传参数
     *
     * @return
     */
    public static Observable.Transformer<String, UpdateParam> buildUpdataParam() {
        return stringObservable -> stringObservable.flatMap(new Func1<String, Observable<UpdateParam>>() {
            @Override
            public Observable<UpdateParam> call(String s) {
                String str[] = s.split(",");
                return Observable.just(new UpdateParam(str[0], str[1]));
            }
        });
    }

    //压缩图片
    public static Observable.Transformer<String, String> compressPicture() {
        return tObservable -> tObservable.flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String t) {
                return Luban.get(AppManager.getApp())
                        .load(new File(t))
                        .putGear(Luban.THIRD_GEAR)
                        .asObservable()
                        .compose(RxUtil.applySchedulersJobUI())
                        .doOnError(Throwable::printStackTrace)
                        .onErrorResumeNext(throwable -> {
                            return Observable.empty();
                        }).map(file -> file.getAbsolutePath());
            }
        });

    }
}
