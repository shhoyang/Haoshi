package com.haoshi.lottie;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetWorkAnimActivity extends BaseActivity {

    private LottieAnimationView animationView;

    @Override
    public void initView() {
        animationView = (LottieAnimationView) findViewById(R.id.lottie_view);
    }

    @Override
    public void setData() {
        load();
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_net_work_anim;
    }

    @Override
    public String setTitle() {
        return TAG = NetWorkAnimActivity.class.getSimpleName();
    }

    @Override
    protected void onStop() {
        super.onStop();
        animationView.cancelAnimation();
    }

    private void load() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.chenailing.cn/EmptyState.json").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d(TAG, "请检查网络!");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        LottieComposition.Factory.fromJson(getResources(), jsonObject, composition -> {
                            animationView.setProgress(0);
                            animationView.loop(true);
                            animationView.setComposition(composition);
                            animationView.playAnimation();
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    LogUtils.d(TAG, response.message());
                }
            }
        });
    }
}
