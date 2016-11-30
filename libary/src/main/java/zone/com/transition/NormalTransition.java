package zone.com.transition;

import android.annotation.TargetApi;
import android.support.annotation.TransitionRes;
import android.app.Activity;
import android.os.Build;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.widget.FrameLayout;

/**
 * Created by fuzhipeng on 2016/11/30.
 * tip:transition.addListener(this);
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class NormalTransition {

    Activity activity;

    NormalTransition(Activity activity) {
        this.activity = activity;
    }

    public static NormalTransition activityAnimation(Activity activity) {
        return new NormalTransition(activity);
    }

    private void checkSetContentView() {
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        if (content.getChildCount() > 0)
            throw new IllegalStateException("please use this method before setContentView");
    }


    public NormalTransition enterTransition(Transition transition) {
        checkSetContentView();
        activity.getWindow().setEnterTransition(transition);
        return this;
    }

    public NormalTransition enterTransition(@TransitionRes int resource) {
        return enterTransition(TransitionInflater.from(activity).
                inflateTransition(resource));
    }

    public NormalTransition returnTransition(Transition transition) {
        checkSetContentView();
        activity.getWindow().setReturnTransition(transition);
        return this;
    }

    public NormalTransition returnTransition(@TransitionRes int resource) {
        return returnTransition(TransitionInflater.from(activity).
                inflateTransition(resource));
    }

    public NormalTransition exitTransition(Transition transition) {
        checkSetContentView();
        activity.getWindow().setExitTransition(transition);
        return this;
    }

    public NormalTransition exitTransition(@TransitionRes int resource) {
        return exitTransition(TransitionInflater.from(activity).
                inflateTransition(resource));
    }

    public NormalTransition reenterTransition(Transition transition) {
        checkSetContentView();
        activity.getWindow().setReenterTransition(transition);
        return this;
    }

    public NormalTransition reenterTransition(@TransitionRes int resource) {
        return reenterTransition(TransitionInflater.from(activity).
                inflateTransition(resource));
    }

}
