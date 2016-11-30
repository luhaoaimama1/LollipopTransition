package zone.com.transitionstudy;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import zone.com.transition.ShareTransition;

/**
 * Created by fuzhipeng on 2016/11/30.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        ShareTransition.onActivityReenter(this,resultCode,data);
    }

    @Override
    protected void onDestroy() {
        ShareTransition.destory(this);
        super.onDestroy();
    }

    @Override
    public void finishAfterTransition() {
        ShareTransition.finishAfterTransition(this);
        super.finishAfterTransition();
    }
}
