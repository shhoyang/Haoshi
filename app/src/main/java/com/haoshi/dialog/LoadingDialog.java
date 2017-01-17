package com.haoshi.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoshi.R;

/**
 * @author: HaoShi
 */

public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context, String messsge) {
        super(context, R.style.LoadingDialog);
        View view = View.inflate(context, R.layout.loadingdialog, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText(messsge);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.loading_dialog);
        imageView.startAnimation(animation);
        setContentView(view);
    }
}
