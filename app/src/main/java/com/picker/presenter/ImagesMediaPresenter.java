package com.picker.presenter;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.campussecurity.app.R;
import com.hao.common.nucleus.presenter.LoadPresenter;
import com.picker.model.ImageFolderModel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;


/**
 * @Package com.daoda.aijiacommunity.module.picker.presenter
 * @作用：
 * @作者：linguoding
 * @日期:2016/3/30 11:13
 */
public class ImagesMediaPresenter extends LoadPresenter {

    public void loadFolderModelList(Context context,boolean takePhotoEnabled){
        loadModel(Observable.create(new Observable.OnSubscribe<List<ImageFolderModel>>() {
            @Override
            public void call(Subscriber<? super List<ImageFolderModel>> subscriber) {
                List<ImageFolderModel> list = new ArrayList<>();
                Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media.DATA}, null, null, MediaStore.Images.Media.DATE_ADDED + " DESC");
                ImageFolderModel allImageFolderModel = new ImageFolderModel(takePhotoEnabled);
                HashMap<String, ImageFolderModel> imageFolderModelMap = new HashMap<>();
                ImageFolderModel otherImageFolderModel = null;
                if (cursor != null && cursor.getCount() > 0) {
                    boolean firstInto = true;
                    while (cursor.moveToNext()) {
                        String imagePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

                        if (firstInto) {
                            allImageFolderModel.name = context.getString(R.string.all_image);
                            allImageFolderModel.coverPath = imagePath;
                            firstInto = false;
                        }
                        // 所有图片目录每次都添加
                        allImageFolderModel.addLastImage(imagePath);

                        // 其他图片目录
                        String folderName = new File(imagePath).getParentFile().getName();
                        if (imageFolderModelMap.containsKey(folderName)) {
                            otherImageFolderModel = imageFolderModelMap.get(folderName);
                        } else {
                            otherImageFolderModel = new ImageFolderModel(folderName, imagePath);
                            imageFolderModelMap.put(folderName, otherImageFolderModel);
                        }
                        otherImageFolderModel.addLastImage(imagePath);
                    }
                    cursor.close();
                }


                // 添加所有图片目录
                list.add(allImageFolderModel);

                // 添加其他图片目录
                Iterator<Map.Entry<String, ImageFolderModel>> iterator = imageFolderModelMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    list.add(iterator.next().getValue());
                }
                subscriber.onNext(list);
                subscriber.onCompleted();
            }
        }));
    }


}
