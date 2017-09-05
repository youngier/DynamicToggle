package com.young.dynamictoggle;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @author: young
 * date:17/9/4  15:55
 */

public class DynamicToggleButton extends RelativeLayout{

    private RelativeLayout mToggleRl;
    private View circle;

    private boolean isOn = true;

    public DynamicToggleButton(Context context) {
        super(context);
    }

    public DynamicToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mToggleRl = (RelativeLayout) findViewById(R.id.rl_toggle);
        mToggleRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });
        circle = findViewById(R.id.circle);
    }

    private void startAnimation() {

        int valueIn = 30;

        if( !isOn )
            valueIn = -30;

        ValueAnimator va1 = ValueAnimator.ofInt(0, valueIn);
        final ValueAnimator va2 = ValueAnimator.ofInt(valueIn, 0);
        va1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //rotate
                int value = (Integer) valueAnimator.getAnimatedValue();
                mToggleRl.setPivotX(mToggleRl.getWidth()/2);
                mToggleRl.setPivotY(mToggleRl.getHeight()/2);//支点在图片中心
                mToggleRl.setRotation(value);

                if( !isOn )
                    circle.setTranslationX(value/3*2);
                else {
                    circle.setTranslationX(40 + value / 3 * 2);
                }

            }
        });
        va2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //rotate
                int value = (Integer) valueAnimator.getAnimatedValue();
                mToggleRl.setPivotX(mToggleRl.getWidth()/2);
                mToggleRl.setPivotY(mToggleRl.getHeight()/2);//支点在图片中心
                mToggleRl.setRotation(value);

                if( !isOn ) {
                    circle.setTranslationX(20 + (20 - value / 3 * 2));
                }
                else
                    circle.setTranslationX(20 - (20+value/3*2));
            }
        });
        va1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                va2.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        va1.setDuration(500);
        va2.setDuration(500);
        va1.start();

        int colorA = Color.parseColor("#303F9F");
        int colorB = Color.parseColor("#FF4081");
        GradientDrawable drawable = (GradientDrawable) circle.getBackground();
        ObjectAnimator objectAnimator;

        if( isOn ){
            isOn = false;
            objectAnimator = ObjectAnimator.ofInt(drawable,"color",colorA,colorB);
        }else{
            isOn = true;
            objectAnimator = ObjectAnimator.ofInt(drawable,"color",colorB,colorA);
        }

        objectAnimator.setDuration(1000);
        objectAnimator.setEvaluator(new ArgbEvaluator());
        objectAnimator.start();

    }
}
