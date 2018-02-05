package com.wyf.daily;

import android.animation.Animator;
import android.transition.ChangeBounds;
import android.transition.TransitionValues;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

/**
 * 自定义的Activity切换动画
 *
 * @author wifi9984
 * @date 2017/12/22
 */

public class CustomChangeBounds extends ChangeBounds {
    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        Animator changeBounds = super.createAnimator(sceneRoot, startValues, endValues);
        if (sceneRoot == null || startValues == null || endValues == null) {
            return null;
        }
        changeBounds.setDuration(300);
        changeBounds.setInterpolator(AnimationUtils.loadInterpolator(sceneRoot.getContext(),
                android.R.interpolator.fast_out_slow_in));
        return changeBounds;
    }
}
