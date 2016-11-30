package zone.com.transition;

import android.app.Activity;
import android.content.Intent;

import zone.com.transition.share.From;
import zone.com.transition.share.ShareHelper;
import zone.com.transition.share.To;

/**
 * Created by fuzhipeng on 2016/11/29.
 * <p>
 */

public class ShareTransition {

    public static From from(Activity activity) {
        return ShareHelper.from(activity);
    }


    public static boolean onActivityReenter(Activity activity, int resultCode, Intent data) {
        return ShareHelper.onActivityReenter(activity,resultCode,data);
    }

    public static To to(Activity activity) {
        return ShareHelper.to(activity);
    }

    public static boolean finishAfterTransition(Activity activity) {
        return ShareHelper.finishAfterTransition(activity);
    }

    public static void destory(Activity activity) {
        ShareHelper.destory(activity);
    }

}
