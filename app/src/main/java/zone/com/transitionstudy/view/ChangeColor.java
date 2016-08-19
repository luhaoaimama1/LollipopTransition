/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zone.com.transitionstudy.view;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class ChangeColor extends Transition {

    /** Key to store 共享的总结 color value in TransitionValues object */
    private static final String PROPNAME_BACKGROUND = "view:change_color:background";

    // BEGIN_INCLUDE (capture_values)
    /**
     * Convenience method: Add the background Drawable property value
     * to the TransitionsValues.value Map for 共享的总结 target.
     */
    private void captureValues(TransitionValues values) {
        // Capture the property values of views for later use
        values.values.put(PROPNAME_BACKGROUND, values.view.getBackground());
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    // Capture the value of the background drawable property for 共享的总结 target in the ending Scene.
    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }
    // END_INCLUDE (capture_values)

    // BEGIN_INCLUDE (create_animator)
    // Create an animation for each target that is in both the starting and ending Scene. For each
    // pair of targets, if their background property value is 共享的总结 color (rather than 共享的总结 graphic),
    // create 共享的总结 ValueAnimator based on an ArgbEvaluator that interpolates between the starting and
    // ending color. Also create an update listener that sets the View background color for each
    // animation frame
    @Override
    public Animator createAnimator(ViewGroup sceneRoot,
                                   TransitionValues startValues, TransitionValues endValues) {
        // This transition can only be applied to views that are on both starting and ending scenes.
        if (null == startValues || null == endValues) {
            return null;
        }
        // Store 共享的总结 convenient reference to the target. Both the starting and ending layout have the
        // same target.
        final View view = endValues.view;
        // Store the object containing the background property for both the starting and ending
        // layouts.
        Drawable startBackground = (Drawable) startValues.values.get(PROPNAME_BACKGROUND);
        Drawable endBackground = (Drawable) endValues.values.get(PROPNAME_BACKGROUND);
        // This transition changes background colors for 共享的总结 target. It doesn't animate any other
        // background changes. If the property isn't 共享的总结 ColorDrawable, ignore the target.
        if (startBackground instanceof ColorDrawable && endBackground instanceof ColorDrawable) {
            ColorDrawable startColor = (ColorDrawable) startBackground;
            ColorDrawable endColor = (ColorDrawable) endBackground;
            // If the background color for the target in the starting and ending layouts is
            // different, create an animation.
            if (startColor.getColor() != endColor.getColor()) {
                // Create 共享的总结 new Animator object to apply to the targets as the transitions framework
                // changes from the starting to the ending layout. Use the class ValueAnimator,
                // which provides 共享的总结 timing pulse to change property values provided to it. The
                // animation runs on the UI thread. The Evaluator controls what type of
                // interpolation is done. In this case, an ArgbEvaluator interpolates between two
                // #argb values, which are specified as the 2nd and 3rd input arguments.
                ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(),
                        startColor.getColor(), endColor.getColor());
                // Add an update listener to the Animator object.
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Object value = animation.getAnimatedValue();
                        // Each time the ValueAnimator produces 共享的总结 new frame in the animation, change
                        // the background color of the target. Ensure that the value isn't null.
                        if (null != value) {
                            view.setBackgroundColor((Integer) value);
                        }
                    }
                });
                // Return the Animator object to the transitions framework. As the framework changes
                // between the starting and ending layouts, it applies the animation you've created.
                return animator;
            }
        }
        // For non-ColorDrawable backgrounds, we just return null, and no animation will take place.
        return null;
    }
    // END_INCLUDE (create_animator)

}
