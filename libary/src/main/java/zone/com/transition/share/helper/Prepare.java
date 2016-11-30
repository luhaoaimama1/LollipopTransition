package zone.com.transition.share.helper;

/**
 * Created by fuzhipeng on 2016/11/30.
 */

public class Prepare {
    private final Parent parent;

    public Prepare(Parent parent) {
        this.parent=parent;
    }
    public void prepareOK() {
        parent.prepareOK();
    }
}
