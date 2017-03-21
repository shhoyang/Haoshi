package com.haoshi.sqlite.ormlite;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.utils.LogUtils;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author HaoShi
 */
public class OrmliteActivity extends BaseActivity {

    private EditText editName, editNum;
    private DbManager dbManager;
    private Personnel personnel;
    private String name;
    private String num;

    @Override
    public void initView() {
        editName = (EditText) findViewById(R.id.edit_name);
        editNum = (EditText) findViewById(R.id.edit_num);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button11).setOnClickListener(this);
        findViewById(R.id.button12).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button21).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button31).setOnClickListener(this);
    }

    @Override
    public void setData() {

    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_ormlite;
    }

    @Override
    public String setTitle() {
        return TAG = OrmliteActivity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        try {
            name = editName.getText().toString();
            num = editNum.getText().toString();
            dbManager = new DbManager(this);
            switch (v.getId()) {
                case R.id.button:
                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(num)) {
                        personnel = new Personnel(name, num);
                        dbManager.insert(personnel);
                    }
                    break;
                case R.id.button1:
                    if (personnel != null) {
                        dbManager.delete(personnel);
                    }
                    break;
                case R.id.button11:
                    if (!TextUtils.isEmpty(num)) {
                        dbManager.deleteByNum(num);
                    }
                    break;
                case R.id.button12:
                    dbManager.deleteAll();
                    break;
                case R.id.button2:
                    if (personnel != null && !TextUtils.isEmpty(name)) {
                        personnel.setName(name);
                        dbManager.update(personnel);
                    }
                    break;
                case R.id.button21:
                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(num)) {
                        dbManager.updateByNum(new Personnel(name, num));
                    }
                    break;
                case R.id.button3:
                    if (!TextUtils.isEmpty(num)) {
                        List<Personnel> list = dbManager.queryByNum(num);
                        if (list != null && list.size() != 0) {
                            personnel = list.get(0);
                            for (Personnel personnel : list) {
                                LogUtils.d(TAG, personnel.toString());
                            }
                        }
                    }
                    break;
                case R.id.button31:
                    List<Personnel> list = dbManager.query();
                    for (Personnel personnel : list) {
                        LogUtils.d(TAG, personnel.toString());
                    }
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
