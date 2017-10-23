package com.yannis.share.thumbupimitate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/10/23.
 */

public class JiKeThumbUpView extends View implements View.OnClickListener {
    private Bitmap thumbup;
    private Bitmap thumbcancel;
    private Bitmap shining;
    private boolean isCheck = false;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);


    public JiKeThumbUpView(Context context) {
        super(context);
        initRes();
    }

    private void initRes() {
        thumbup = BitmapFactory.decodeResource(getResources(),R.drawable.ic_messages_like_selected);
        thumbcancel = BitmapFactory.decodeResource(getResources(),R.drawable.ic_messages_like_unselected);
        shining = BitmapFactory.decodeResource(getResources(),R.drawable.ic_messages_like_selected_shining);
        setOnClickListener(this);
    }

    public JiKeThumbUpView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initRes();
    }

    public JiKeThumbUpView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRes();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float left = getWidth()/2-thumbup.getWidth()/2;
        float top = getHeight()/2-thumbup.getHeight()/2;
        if(isCheck) {
            canvas.drawBitmap(thumbup,left,top,paint);
            canvas.drawBitmap(shining,left+5,top - shining.getHeight()/2,paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.RED);
            canvas.drawCircle(getWidth()/2,getHeight()/2,thumbup.getWidth()/2+5,paint);
        }else{
            canvas.drawBitmap(thumbcancel,left,top,paint);
        }
    }

    @Override
    public void onClick(View v) {
        isCheck = !isCheck;
        invalidate();
    }
}
