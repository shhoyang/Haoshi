package com.haoshi.hao;

import android.os.Environment;

/**
 * @author HaoShi
 */
public class Constant {

    public static final String ROOT_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/haoshi";

    public static final String BASE_URL = "http://v.juhe.cn/toutiao/";
    public static final String NEWS_URL = "index";
    public static final String API_KEY = "1fcd54967cb53b647e67d4b5411d2b9a";
    public static final String[] CHANNELS = {"头条", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚"};
    public static final String[] CHANNELS_KEY = {"top", "shehui", "guonei", "guoji", "yule", "tiyu", "junshi", "keji", "caijing", "shishang"};
}
