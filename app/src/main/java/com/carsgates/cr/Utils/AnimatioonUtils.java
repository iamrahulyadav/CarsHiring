package com.carsgates.cr.Utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class AnimatioonUtils {
    public static void animate(RecyclerView.ViewHolder holder,Boolean goesdown)
    {
        YoYo.with(Techniques.RubberBand).duration(1000).playOn(holder.itemView);
       /* AnimatorSet animatorSet=new AnimatorSet();
        ObjectAnimator scalex=ObjectAnimator.ofFloat(holder.itemView,"scaleX",0.5F,0.8F,1.0F);
        ObjectAnimator sxaley=ObjectAnimator.ofFloat(holder.itemView,"scaleY",0.5F,0.8F,1.0F);
        ObjectAnimator obanim=ObjectAnimator.ofFloat(holder.itemView,"translationY",goesdown==true ? -400: 400,0);
        ObjectAnimator obanim1=ObjectAnimator.ofFloat(holder.itemView,"translationX",-50,50,-30,30,-10,10,-5,5);
        animatorSet.playTogether(obanim,obanim1,scalex,sxaley);
        animatorSet.setInterpolator(new AnticipateInterpolator());
        animatorSet.setDuration(1000);
        animatorSet.start();*/
    }
    public static void animateToolbarDroppingDown(View containerToolbar)
    {
        containerToolbar.setRotationX(-90);
        containerToolbar.setAlpha(0.2F);
        containerToolbar.setPivotX(0.0F);
        containerToolbar.setPivotY(0.0F);
        Animator alpha = ObjectAnimator.ofFloat(containerToolbar, "alpha", 0.2F, 0.4F, 0.6F, 0.8F, 1.0F).setDuration(4000);
        Animator rotationX = ObjectAnimator.ofFloat(containerToolbar, "rotationX", -90, 60, -45, 45, -10, 30, 0, 20, 0, 5, 0).setDuration(8000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.playTogether(alpha, rotationX);
        animatorSet.start();
    }
}
