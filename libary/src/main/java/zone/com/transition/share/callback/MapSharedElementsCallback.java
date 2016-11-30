package zone.com.transition.share.callback;

import android.view.View;

import java.util.List;
import java.util.Map;

import zone.com.transition.share.helper.Parent;

/**
 * Created by fuzhipeng on 2016/11/29.
 */

public interface MapSharedElementsCallback {

    void onMapSharedElements(List<String> names, Map<String, View> sharedElements, Parent parent);
}
