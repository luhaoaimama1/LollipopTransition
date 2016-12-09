package zone.com.transition.share;

import android.annotation.TargetApi;
import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.SharedElementCallback;
import android.view.View;

import java.util.Map;

import zone.com.transition.share.callback.MapSharedElementsCallback;
import zone.com.transition.share.helper.Back;
import zone.com.transition.share.helper.Parent;
import zone.com.transition.share.helper.Prepare;

/**
 * Created by fuzhipeng on 2016/11/29.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class To extends Parent {
    private PrepareCallback mPrepareCallback;
    private From relateFrom;
    private int layoutResID = -1;
    private View layoutView;
    private BackCallBack mBackCallBack;

    To(Activity activity) {
        super(activity);
        ShareHelper.mToMap.put(activity.getClass().getName(), this);
    }

    To finish() {
        isReturn = true;
        if (mBackCallBack != null)
            mBackCallBack.setResult(mBack);
        return this;
    }

    public To back(BackCallBack mBackCallBack) {
        this.mBackCallBack = mBackCallBack;
        return this;
    }


    public To enterSharedElementCallback(SharedElementCallback exitCallback) {
        this.shareCallback = exitCallback;
        return this;
    }

    public To onMapSharedElements(MapSharedElementsCallback mMapSharedElementsCallback) {
        this.mMapSharedElementsCallback = mMapSharedElementsCallback;
        return this;
    }


    public To pairs(Map<String, View> sharedElements, View... views) {
        this.views = views;
        for (int i = 0; i < views.length; i++) {
            String transitionName = getShareNameByIndex(i);
            views[i].setTransitionName(transitionName);
            if (sharedElements != null)
                sharedElements.put(views[i].getTransitionName(), views[i]);
        }
        return this;
    }

    public To pairs(View... views) {
        this.views = views;
        return this;
    }

    public To pairs(int... ids) {
        this.ids = ids;
        return this;
    }

    public To setContentView(@LayoutRes int layoutResID) {
        this.layoutResID = layoutResID;
        return this;
    }

    public To setContentView(View layoutView) {
        this.layoutView = layoutView;
        return this;
    }

    /**
     * To中postponeEnterTransition 必须在setContent前面
     *
     * @param mPrepareCallback
     * @return
     */
    public To show(PrepareCallback mPrepareCallback) {
        if (activity == null)
            return this;
        this.mPrepareCallback = mPrepareCallback;
        isReturn = false;
        activity.postponeEnterTransition();//其start在fragment里呢
        if (layoutResID != -1)
            activity.setContentView(layoutResID);
        else if (layoutView != null)
            activity.setContentView(layoutView);
        else
            throw new IllegalStateException("please use method:setContentView");

        if (ids != null) {
            views = new View[ids.length];
            for (int i = 0; i < ids.length; i++) {
                views[i] = activity.findViewById(ids[i]);
            }
        }
        if (views != null)
            pairs(null, views);

        setEnterSharedElementCallback(activity);
        if (mPrepareCallback != null)
            mPrepareCallback.prepare(mPrepare);
        else
            activity.startPostponedEnterTransition();
        return this;
    }

    public To show() {
        return show(null);
    }

    private void setEnterSharedElementCallback(FragmentActivity activityTemp) {
        if (shareCallback != null || mMapSharedElementsCallback != null)
            activityTemp.setEnterSharedElementCallback(shareCallbackInner);

    }

    public From getRelateFrom() {
        return relateFrom;
    }

    public void setRelateFrom(From relateFrom) {
        this.relateFrom = relateFrom;
    }


    public interface PrepareCallback {
        void prepare(Prepare prepare);
    }

    public interface BackCallBack {
        void setResult(Back mBack);
    }
}
