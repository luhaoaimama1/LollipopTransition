package zone.com.transition.share;

import android.app.Activity;
import android.content.Intent;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fuzhipeng on 2016/11/29.
 *
 *第一页 过去    ShareHelper.from(activity)
 *              .exitcallback(callback).onMapSharedElements(callback).pair(iv,iv2,iv3).go(intent).back(callback);
 *
 *第二页 显示    ShareHelper.to(activity).setContent()
 *              .enterCallbcak(callback).onMapSharedElements(callback).pair(iv,iv2,iv3).show(callback).back(callback);
 *
 *         ShareHelper.finishAfterTransition(activty)
 *         ShareHelper.destory(activty)
 *         ShareHelper.onActivityReenter(activty,resultCode,data)
 */

public class ShareHelper {

    static Map<String, From> mFromMap =
            Collections.synchronizedMap(new HashMap<String, From>());
    static Map<String, To> mToMap =
            Collections.synchronizedMap(new HashMap<String, To>());

    public static From from(Activity activity) {
        From from = getFrom(activity);
        if (from != null)
            return from;
        return new From(activity);
    }

    private static From getFrom(Activity activity) {
        return mFromMap.get(activity.getClass().getName());
    }

    private static To getTo(Activity activity) {
        return mToMap.get(activity.getClass().getName());
    }

    public static boolean onActivityReenter(Activity activity, int resultCode, Intent data) {
        boolean have = false;
        From from = getFrom(activity);
        if (from != null) {
            have = true;
            from.onActivityReenter(resultCode, data);
        }
        return have;
    }

    public static To to(Activity activity) {
        To to = new To(activity);
        From from = getFrom(activity);
        if (from != null)
            to.setRelateFrom(from);
        return to;
    }

    public static boolean finishAfterTransition(Activity activity) {
        boolean have = false;
        To to = getTo(activity);
        if (to != null) {
            have = true;
            to.finish();
        }
        return have;
    }

    public static void destory(Activity activity) {
        mFromMap.remove(activity.getClass().getName());
        mToMap.remove(activity.getClass().getName());
    }

}
