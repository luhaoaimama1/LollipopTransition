package zone.com.transition.share;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.SharedElementCallback;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import zone.com.transition.share.callback.MapSharedElementsCallback;
import zone.com.transition.share.helper.Parent;
import zone.com.transition.share.helper.Prepare;

/**
 * Created by fuzhipeng on 2016/11/29.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class From extends Parent {

    private PrepareCallback mPrepareCallback;
    private Intent intent;

    From(Activity activity) {
        super(activity);
        ShareHelper.mFromMap.put(activity.getClass().getName(), this);
    }

    public From back(PrepareCallback mPrepareCallback) {
        this.mPrepareCallback = mPrepareCallback;
        return this;
    }

    public From back() {
        return back(null);
    }


    From onActivityReenter(int resultCode, Intent data) {
        if (activity == null)
            return this;

        isReturn = true;
        if (mPrepareCallback != null) {
            activity.postponeEnterTransition();
            mPrepareCallback.prepare(resultCode, data, mPrepare);
        }
        return this;
    }

    public From exitSharedElementCallback(SharedElementCallback exitCallback) {
        this.shareCallback = exitCallback;
        return this;
    }

    public From onMapSharedElements(MapSharedElementsCallback mMapSharedElementsCallback) {
        this.mMapSharedElementsCallback = mMapSharedElementsCallback;
        return this;
    }

    /**
     * transitionName的命名规则:ShareHelper.ShareString+i
     */
    public From pairs(View... views) {
        this.views = views;
        return this;
    }

    /**
     * transitionName的命名规则:ShareHelper.ShareString+i
     */
    public From pairs(int... ids) {
        this.ids = ids;
        return this;
    }

    public From go(Intent intent) {
        if (activity == null)
            return this;

        this.intent = intent;
        isReturn = false;
        setExitSharedElementCallback(activity);
        if (ids != null) {
            views = new View[ids.length];
            for (int i = 0; i < ids.length; i++) {
                views[i] = activity.findViewById(ids[i]);
            }
        }

        if (views != null) {
            Pair<View, String>[] sharedElements = new Pair[views.length];
            for (int i = 0; i < views.length; i++)
                sharedElements[i] = Pair.create(views[i], getShareNameByIndex(i));
            activity.startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(activity, sharedElements).toBundle());
        } else {
            Log.i("Tag", "没有共享元素");
            activity.startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
        }


        return this;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setExitSharedElementCallback(FragmentActivity activityTemp) {
        if (shareCallback != null || mMapSharedElementsCallback != null)
            activityTemp.setExitSharedElementCallback(shareCallbackInner);
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }


    public interface PrepareCallback {
        void prepare(int resultCode, Intent data, Prepare prepare);
    }

}
