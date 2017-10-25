package com.yannis.share.thumbupimitate;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by Administrator on 2017/10/23.
 */

public class JiKeThumbUpView extends View implements View.OnClickListener {
    private static final int ANIMATOR_TIME = 200;
    private Bitmap thumbup;
    private Bitmap thumbcancel;
    private Bitmap shining;
    private boolean isCheck = false;
    private float radius = 0f;//点击外部圆圈半径
    private int alphaCircle = 0;
    private float scaleThumb = 1;
    private float fraction = 0;//当前动画完成度
    private boolean isFirst = true;
    private int count = 109;
    private float sliding = 0;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getScaleThumb() {
        return scaleThumb;
    }

    public void setScaleThumb(float scaleThumb) {
        this.scaleThumb = scaleThumb;
    }

    public int getAlphaCircle() {
        return alphaCircle;
    }

    public void setAlphaCircle(int alphaCircle) {
        this.alphaCircle = alphaCircle;
        invalidate();
    }

    //private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);


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

    public float getSliding() {
        return sliding;
    }

    public void setSliding(float sliding) {
        this.sliding = sliding;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        float centerX = getWidth()/2;
        float centerY = getHeight()/2;
        Bitmap bitmap = ressetBitmap(thumbup, scaleThumb);
        float left = getWidth()/2-bitmap.getWidth()/2;
        float top = getHeight()/2-bitmap.getHeight()/2;

        if(isFirst){
            canvas.drawBitmap(thumbcancel,left,top,paint);
            paint.setTextSize(40);
            paint.setColor(Color.GRAY);
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            canvas.drawText(String.valueOf(count),centerX+50,centerY-(fontMetrics.ascent + fontMetrics.descent)/2,paint);
              /* char[] chars = String.valueOf(count).toCharArray();
                for(int i =0;i< chars.length;i++){
                    String text = String.valueOf(chars[i]);
                    float textWidth  = 0;
                    if(i >0 ){
                        for(int j = 0 ; j < i ;j ++){
                            textWidth = textWidth + paint.measureText(String.valueOf(chars[j]));
                        }
                    }
                    canvas.drawText(text,centerX+50+textWidth,centerY-(fontMetrics.ascent + fontMetrics.descent)/2+80,paint);
                }*/
            isFirst = false;
            return;
        }

        if(isCheck) {
            if(fraction < 0.5f){
                 canvas.drawBitmap(ressetBitmap(thumbcancel,scaleThumb),left,top,paint);
            }else {
                canvas.drawBitmap(bitmap, left, top, paint);
            }
            paint.setTextSize(40);
            paint.setColor(Color.GRAY);
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            String noChangeArea = splitText(count-1,count)[0][0];
            String oldChangeArea = splitText(count-1,count)[0][1];
            String newChangeArea = splitText(count-1,count)[1][1];

            float xOffset = 0;
            if(!TextUtils.isEmpty(noChangeArea)){
                canvas.drawText(noChangeArea,centerX+50,centerY-(fontMetrics.ascent + fontMetrics.descent)/2,paint);
                xOffset = paint.measureText(noChangeArea);
            }
            if(!TextUtils.isEmpty(newChangeArea)){
                canvas.drawText(newChangeArea,centerX+50+xOffset,centerY-(fontMetrics.ascent + fontMetrics.descent)/2+ 50- sliding,paint);
            }
            paint.setAlpha(alphaCircle);
            if(!TextUtils.isEmpty(oldChangeArea)){
                canvas.drawText(oldChangeArea,centerX+50+xOffset,centerY-(fontMetrics.ascent + fontMetrics.descent)/2- sliding,paint);
            }
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(3);
            paint.setAlpha(alphaCircle);
            canvas.drawCircle(centerX,centerY,bitmap.getWidth()/2+radius,paint);
            paint.setAlpha(255-alphaCircle);
            canvas.drawBitmap(shining,left+5,top - shining.getHeight()/2,paint);
        }else{
            paint.setTextSize(40);
            paint.setColor(Color.GRAY);
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            if(fraction < 0.5f){
                canvas.drawBitmap(bitmap, left, top, paint);
            }else{
                canvas.drawBitmap(ressetBitmap(thumbcancel,scaleThumb),left,top,paint);
                //canvas.drawText(String.valueOf(count),centerX+50,centerY-(fontMetrics.ascent + fontMetrics.descent)/2,paint);
            }
            String noChangeArea = splitText(count+1,count)[0][0];
            String oldChangeArea = splitText(count+1,count)[0][1];
            String newChangeArea = splitText(count+1,count)[1][1];
            float xOffset = 0;
            if(!TextUtils.isEmpty(noChangeArea)){
                canvas.drawText(noChangeArea,centerX+50,centerY-(fontMetrics.ascent + fontMetrics.descent)/2,paint);
                xOffset = paint.measureText(noChangeArea);
            }
            if(!TextUtils.isEmpty(newChangeArea)){
                canvas.drawText(newChangeArea,centerX+50+xOffset,centerY-(fontMetrics.ascent + fontMetrics.descent)/2-50+ sliding,paint);
            }
            paint.setAlpha(alphaCircle);
            if(!TextUtils.isEmpty(oldChangeArea)){
                canvas.drawText(oldChangeArea,centerX+50+xOffset,centerY-(fontMetrics.ascent + fontMetrics.descent)/2+ sliding,paint);
            }
            canvas.drawBitmap(shining,left+5,top - shining.getHeight()/2,paint);
        }
    }

    private static String[][]  splitText(int oldVale,int newValue){
        String[][] temp  = new String[2][];
        char[] charOld = String.valueOf(oldVale).toCharArray();
        char[] charNew = String.valueOf(newValue).toCharArray();
        if(charOld.length != charNew.length){
            temp[0] = new String[]{null,new String(charOld)};
            temp[1] = new String[]{null,new String(charNew)};
        }else{
            int index = 0;
            int lenth = charOld.length;
            for(int i = 0;i < lenth;i++){
                if(charOld[i] != charNew[i]){
                    index = i;
                    break;
                }
            }
            String[] oldstr  = getsplitText(charOld,index);
            String[] newstr  = getsplitText(charNew,index);
            temp[0] = oldstr;
            temp[1] = newstr;
        }
        return temp;
    }

    public static String[] getsplitText(char[] chars,int index){
        if(index < 0 || index > chars.length-1) return null;
        String[] twoStrs = new String[]{"",""};
        for(int i =0;i<index;i++){
            twoStrs[0] = twoStrs[0] + String.valueOf(chars[i]);
        }
        for(int i =index;i<chars.length;i++){
            twoStrs[1] = twoStrs[1] + String.valueOf(chars[i]);
        }
        return twoStrs;
    }

    public static Bitmap ressetBitmap(Bitmap bitmap,float scale){
        Matrix matrix = new Matrix();
        matrix.postScale(scale,scale);
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,false);
    }

    public float getRadius() {
        return radius;
    }
    public void setRadius(float radius) {
        this.radius = radius;
        invalidate();//千万不要忘记重绘这句代码
    }


    @Override
    public void onClick(View v) {
        isCheck = !isCheck;
        if(isCheck) count++;
        else count--;
        fraction = 0;
            PropertyValuesHolder holder1 =PropertyValuesHolder.ofFloat("radius",0f,10f);
            PropertyValuesHolder holder2 =PropertyValuesHolder.ofInt("alphaCircle",255,0);
            Keyframe keyframe1 = Keyframe.ofFloat(0f,1);
            Keyframe keyframe2 = Keyframe.ofFloat(0.3f,0.8f);
            Keyframe keyframe3 = Keyframe.ofFloat(1f,1);
            PropertyValuesHolder holder3 = PropertyValuesHolder.ofKeyframe("scaleThumb",keyframe1,keyframe2,keyframe3);
            PropertyValuesHolder holder4 = PropertyValuesHolder.ofFloat("sliding",0,50);
            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this,holder1,holder2,holder3,holder4);
            animator.setInterpolator(new AccelerateInterpolator());
            animator.setDuration(ANIMATOR_TIME);
            animator.start();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    fraction =  animation.getAnimatedFraction();
                }
            });
    }
}
