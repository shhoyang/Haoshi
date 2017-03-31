package com.haoshi.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoshi.R;

/**
 * @author HaoShi
 */
public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context, String message) {
        super(context, R.style.LoadingDialog);
        View view = View.inflate(context, R.layout.dialog_loading, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText(message);
        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(1000);
        imageView.startAnimation(animation);
        setContentView(view);
    }
}
