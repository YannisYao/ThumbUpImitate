package com.yannis.share.thumbupimitate;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
private JiKeThumbUpView thumbUpView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thumbUpView = (JiKeThumbUpView) findViewById(R.id.thumbup);

    }
}
