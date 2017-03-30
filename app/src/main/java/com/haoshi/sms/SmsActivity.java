package com.haoshi.sms;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.haoshi.R;
import com.haoshi.hao.BaseActivity;
import com.haoshi.hao.IndexActivity;
import com.haoshi.utils.ToastUtils;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class SmsActivity extends BaseActivity {

    private static final int COUNTDOWN = 0;
    private static final int INTENT = 1;
    private static final int SMS = 2;


    private TextInputLayout inputNum, inputCode;
    private EditText editNum, editCode;
    private Button buttonCode, buttonLogin;
    private String num, code;
    private int i = 60;

    @Override
    public void initView() {
        inputNum = (TextInputLayout) findViewById(R.id.input_num);
        inputCode = (TextInputLayout) findViewById(R.id.input_code);
        editNum = inputNum.getEditText();
        editCode = inputCode.getEditText();
        buttonCode = (Button) findViewById(R.id.button_code);
        buttonLogin = (Button) findViewById(R.id.button_login);
        buttonCode.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
    }

    @Override
    public void setData() {
        SMSSDK.initSDK(this, "1c8515545ff4a", "450cbd2573de2c2edd2882a4d7187edd");
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    public int setContentViewID() {
        return R.layout.activity_sms;
    }

    @Override
    public String setTitle() {
        return TAG = SmsActivity.class.getSimpleName();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.button_code:
                getCode();
                break;
            case R.id.button_login:
                login();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    private void getCode() {
        num = editNum.getText().toString();
        if (num.length() == 11) {
            inputNum.setErrorEnabled(false);
            buttonCode.setText(i + "s后再次获取");
            buttonCode.setEnabled(false);
            handler.sendEmptyMessage(COUNTDOWN);
            SMSSDK.getVerificationCode("86", num);
        } else {
            inputNum.setError("手机号错误");
        }
    }

    private void login() {
        code = editCode.getText().toString();
        if (code.length() != 4) {
            inputCode.setError("验证码长度错误");
            return;
        }
        SMSSDK.submitVerificationCode("86", num, code);
    }

    private void countdown() {
        i--;
        if (i == 0) {
            i = 60;
            buttonCode.setEnabled(true);
            buttonCode.setText("获取验证码");
        } else {
            buttonCode.setText(i + "s后再次获取");
            handler.sendEmptyMessageDelayed(COUNTDOWN, 1000);
        }
    }

    private void sms(Message msg) {
        int event = msg.arg1;
        int result = msg.arg2;
        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                ToastUtils.showShort(SmsActivity.this, "验证码已发送");
            } else {
                i = 0;
                ToastUtils.showShort(SmsActivity.this, "获取验证码失败");
            }
        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                editNum.setText("");
                editCode.setText("");
                inputCode.setErrorEnabled(false);
                dialog.setMessage("正在登录...");
                dialog.show();
                handler.sendEmptyMessageDelayed(INTENT, 2000);
            } else {
                editCode.setText("");
                ToastUtils.showShort(SmsActivity.this, "验证失败");
            }
        }
    }

    private void loginSuccess() {
        dialog.dismiss();
        ToastUtils.showShort(SmsActivity.this, "登录成功");
        startActivity(new Intent(SmsActivity.this, IndexActivity.class));
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case COUNTDOWN:
                    countdown();
                    break;
                case INTENT:
                    loginSuccess();
                    break;
                case SMS:
                    sms(msg);
                    break;
            }
        }
    };

    private EventHandler eventHandler = new EventHandler() {
        @Override
        public void afterEvent(int i, int i1, Object o) {
            super.afterEvent(i, i1, o);
            Message msg = Message.obtain();
            msg.what = SMS;
            msg.arg1 = i;
            msg.arg2 = i1;
            msg.obj = o;
            handler.sendMessage(msg);
        }
    };
}
