package com.haoshi.hao;

import android.os.Environment;

/**
 * @author: HaoShi
 */

public class Constant {

    public static final String ROOT_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/haoshi";

    public static final String BASE_URL = "http://news-at.zhihu.com/api/3/";

    public static final String NEWS_HOT = "news/hot";
}
