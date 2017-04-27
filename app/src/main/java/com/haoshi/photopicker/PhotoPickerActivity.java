package com.haoshi.photopicker;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.foamtrace.photopicker.ImageCaptureManager;
import com.foamtrace.photopicker.ImageConfig;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;
import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.utils.ToastUtils;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;

public class PhotoPickerActivity extends BaseActivity {


    private static final int REQUEST_CAMERA_CODE = 101;
    private static final int REQUEST_PREVIEW_CODE = 102;

    private GridView gridView;
    private ArrayList<String> imagePaths = new ArrayList<>();
    private ImageCaptureManager captureManager; // 相机拍照处理类
    private PhotoAdapter adapter;
    private int columnWidth;

    @Override
    public void initView() {
        gridView = (GridView) findViewById(R.id.grid);
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int columns = screenWidth / getResources().getDisplayMetrics().densityDpi;
        columns = columns < 3 ? 3 : columns;
        gridView.setNumColumns(columns);

        int columnSpace = getResources().getDimensionPixelOffset(R.dimen.margin_s);
        columnWidth = (screenWidth - columnSpace * (columns - 1)) / columns;

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            PhotoPreviewIntent intent = new PhotoPreviewIntent(this);
            intent.setCurrentItem(position);
            intent.setPhotoPaths(imagePaths);
            startActivityForResult(intent, REQUEST_PREVIEW_CODE);
        });
        findViewById(R.id.button_multi_select).setOnClickListener(this);
        findViewById(R.id.button_single_select).setOnClickListener(this);
        findViewById(R.id.button_take_picture).setOnClickListener(this);
    }

    @Override
    public void setData() {
        adapter = new PhotoAdapter(columnWidth);
        gridView.setAdapter(adapter);
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_photo_picker;
    }

    @Override
    public String setTitle() {
        return null;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button_multi_select:
                multiSelect();
                break;
            case R.id.button_single_select:
                singleSelect();
                break;
            case R.id.button_take_picture:
                tackPicture();
                break;
        }
    }

    private void multiSelect() {
        PhotoPickerIntent intent = new PhotoPickerIntent(this);
        intent.setSelectModel(SelectModel.MULTI);
        // 是否显示拍照
        intent.setShowCarema(true);
        // 最多选择照片数量，默认为9
        intent.setMaxTotal(9);
        // 已选中的照片地址， 用于回显选中状态
        intent.setSelectedPaths(imagePaths);
        //配置过滤
        ImageConfig config = new ImageConfig();
        config.minHeight = 400;
        config.minWidth = 400;
        config.mimeType = new String[]{"image/jpeg", "image/png"};
        config.minSize = 1 * 1024 * 1024; // 1Mb
        intent.setImageConfig(config);
        startActivityForResult(intent, REQUEST_CAMERA_CODE);
    }

    private void singleSelect() {
        PhotoPickerIntent intent = new PhotoPickerIntent(this);
        intent.setSelectModel(SelectModel.SINGLE);
        intent.setShowCarema(true);
        startActivityForResult(intent, REQUEST_CAMERA_CODE);
    }

    private void tackPicture() {
        try {
            if (captureManager == null) {
                captureManager = new ImageCaptureManager(this);
            }
            Intent intent = captureManager.dispatchTakePictureIntent();
            startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
        } catch (IOException e) {
            ToastUtils.showShort(this, "未检测到相机");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    loadAdapter(data.getStringArrayListExtra(com.foamtrace.photopicker.PhotoPickerActivity.EXTRA_RESULT));
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    loadAdapter(data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT));
                    break;
                // 调用相机拍照
                case ImageCaptureManager.REQUEST_TAKE_PHOTO:
                    if(captureManager.getCurrentPhotoPath() != null) {
                        captureManager.galleryAddPic();

                        ArrayList<String> paths = new ArrayList<>();
                        paths.add(captureManager.getCurrentPhotoPath());
                        loadAdapter(paths);
                    }
                    break;

            }
        }
    }

    private void loadAdapter(ArrayList<String> paths){
        if(paths== null || paths.size() == 0){
            return;
        }
        imagePaths.clear();
        imagePaths.addAll(paths);
        adapter.setList(imagePaths);
    }
}
