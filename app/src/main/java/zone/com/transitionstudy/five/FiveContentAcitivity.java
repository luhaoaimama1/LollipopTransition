package zone.com.transitionstudy.five;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.Window;

import butterknife.ButterKnife;
import zone.com.transitionstudy.R;

/**
 * Created by fuzhipeng on 16/8/17.
 */
public class FiveContentAcitivity extends AppCompatActivity {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);

        String flag = getIntent().getExtras().getString("flag");
        // 设置不同的动画效果
        switch (flag) {
            case "fade":
                getWindow().setEnterTransition(new Fade());
                getWindow().setExitTransition(new Slide(Gravity.LEFT));
                break;
            case "slide":
                getWindow().setEnterTransition(new Slide(Gravity.LEFT));
                getWindow().setReturnTransition(new Slide(Gravity.RIGHT));
                break;
            case "explode":
                getWindow().setEnterTransition(new Explode());
                break;
            case "custom":
                //吧状态栏 动画去掉;
                getWindow().setEnterTransition(TransitionInflater.from(this).
                        inflateTransition(R.transition.content_explore));
                break;
            default:
                break;
        }
        setContentView(R.layout.item1);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        finishAfterTransition();
//        super.onBackPressed();
    }
}
