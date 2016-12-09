package zone.com.transition.share.helper;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.SharedElementCallback;
import android.view.View;
import java.util.List;
import java.util.Map;
import zone.com.transition.share.callback.MapSharedElementsCallback;

/**
 * Created by fuzhipeng on 2016/11/29.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class Parent {


    public static final String ShareString = "ShareView_";
    protected boolean isReturn;
    protected Prepare mPrepare = new Prepare(this);
    protected Back mBack = new Back(this);

    protected View[] views;
    protected int[] ids;

    protected FragmentActivity activity;

    protected Parent(Activity activity) {
        if (activity instanceof FragmentActivity)
            this.activity = (FragmentActivity)activity;
        else
            throw new IllegalStateException("actvitiy must be FragmentActivity");
    }

    public String getShareNameByIndex(int i) {
        return ShareString + i;
    }

    void prepareOK() {
        if (activity!= null)
            activity.startPostponedEnterTransition();
    }

    void setResult(int resultCode, Intent data) {
        if (activity != null)
            activity.setResult(resultCode, data);
    }

    public boolean isReturn() {
        return isReturn;
    }


    protected SharedElementCallback shareCallback;
    protected MapSharedElementsCallback mMapSharedElementsCallback;

    protected SharedElementCallback shareCallbackInner = new SharedElementCallback() {
        @Override
        public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
            super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
            if (shareCallback != null)
                shareCallback.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
        }

        @Override
        public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
            super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
            if (shareCallback != null)
                shareCallback.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
        }

        @Override
        public void onRejectSharedElements(List<View> rejectedSharedElements) {
            super.onRejectSharedElements(rejectedSharedElements);
            if (shareCallback != null)
                shareCallback.onRejectSharedElements(rejectedSharedElements);
        }

        @Override
        public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
            super.onMapSharedElements(names, sharedElements);
            if (mMapSharedElementsCallback != null)
                mMapSharedElementsCallback.onMapSharedElements(names, sharedElements, Parent.this);
            else if (shareCallback != null)
                shareCallback.onMapSharedElements(names, sharedElements);
        }

        @Override
        public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix
                viewToGlobalMatrix, RectF screenBounds) {
            Parcelable result = super.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
            if (shareCallback != null)
                return shareCallback.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
            return result;
        }

        @Override
        public View onCreateSnapshotView(Context context, Parcelable snapshot) {
            View result = super.onCreateSnapshotView(context, snapshot);
            if (shareCallback != null)
                return shareCallback.onCreateSnapshotView(context, snapshot);
            else
                return result;
        }

    };

}
