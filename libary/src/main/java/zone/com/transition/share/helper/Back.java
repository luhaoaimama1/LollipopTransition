package zone.com.transition.share.helper;

import android.content.Intent;

/**
 * Created by fuzhipeng on 2016/11/30.
 */

public class Back {

    private final Parent parent;

    public Back(Parent parent) {
        this.parent = parent;
    }

    public void setResult(int resultCode, Intent data) {
        parent.setResult(resultCode, data);
    }
}
