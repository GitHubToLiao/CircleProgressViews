package fintechnet.izxjf.com.circleprogressview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by as on 2017/5/30.
 */

public class CircleProgressView extends View {
    private int outerCircleColor = Color.RED;
    private int innerCircleColor =Color.BLUE;
    private int mTextColor =Color.WHITE;
    private int mTextSize =15;
    private int mRadius =5;

    private int maxProgress =100;
    private float progress=0;

    private Paint outerPaint,innerPaint,textPaint;
    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        outerCircleColor = typedArray.getColor(R.styleable.CircleProgressView_outerCircleColor,outerCircleColor);
        innerCircleColor =typedArray.getColor(R.styleable.CircleProgressView_innerCircleColor,innerCircleColor);
        mTextSize=typedArray.getDimensionPixelOffset(R.styleable.CircleProgressView_mTextSize,sp2px(mTextSize));
        mTextColor =typedArray.getColor(R.styleable.CircleProgressView_mTextColor,mTextColor);
        mRadius = (int) typedArray.getDimension(R.styleable.CircleProgressView_mRadius,dp2px(mRadius));

        outerPaint = new Paint();
        outerPaint.setAntiAlias(true);
        outerPaint.setColor(outerCircleColor);
        outerPaint.setStrokeWidth(mRadius);
        outerPaint.setStyle(Paint.Style.STROKE);
        outerPaint.setStrokeCap(Paint.Cap.ROUND);



        innerPaint = new Paint();
        innerPaint.setAntiAlias(true);
        innerPaint.setStyle(Paint.Style.FILL);
        innerPaint.setColor(innerCircleColor);

        textPaint = new Paint();
        textPaint.setColor(mTextColor);
        textPaint.setTextSize(mTextSize);
        textPaint.setAntiAlias(true);
    }

    public int sp2px(int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }
    public int dp2px(int dp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制内圆
        canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2-mRadius,innerPaint);

        //绘制外圆弧
        RectF rectF = new RectF(mRadius/2,mRadius/2,getWidth()-mRadius/2,getHeight()-mRadius/2);
        if(progress ==0 ||maxProgress ==0)
            return;
        canvas.drawArc(rectF,0,(progress/maxProgress)*360,false,outerPaint);

        //绘制文字
        String text =progress+"%";
        Rect rect = new Rect();
        textPaint.getTextBounds(text,0,text.length(),rect);
        //获取width
        int textWidth = getWidth()/2 -rect.width()/2;
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom -fontMetricsInt.top)/2 -fontMetricsInt.bottom;
        int bastLine =getWidth()/2 +dy;
        canvas.drawText(text,textWidth,bastLine,textPaint);
    }

    //设置宽和高，保证绘画出来的是一个正方形
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height =MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width,height),Math.min(width,height));
    }

    public void setProgress(float progress){
        this.progress =progress;
        invalidate();
    }
    //设置最大进度
    public void setMaxProgress(int maxProgress){
        this.maxProgress =maxProgress;
    }

    //设置进度带动画
    public void setAnimatorProgress(int mProgress){
        ValueAnimator valueAnimator = ObjectAnimator.ofInt(0,mProgress);
        valueAnimator.setDuration(1500);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                setProgress((float) progress);
            }
        });

        valueAnimator.start();
    }

}
