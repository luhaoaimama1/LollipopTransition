package zone.com.transitionstudy.androidfiveversion;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Window;

import zone.com.transition.NormalTransition;
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
                NormalTransition.activityAnimation(this)
                        .enterTransition(new Fade())
                        .exitTransition(new Slide(Gravity.LEFT));
//                getWindow().setEnterTransition(new Fade());
//                getWindow().setExitTransition(new Slide(Gravity.LEFT));
                break;
            case "slide":
                NormalTransition.activityAnimation(this)
                        .enterTransition(new Slide(Gravity.LEFT))
                        .returnTransition(new Slide(Gravity.RIGHT));
//                getWindow().setEnterTransition(new Slide(Gravity.LEFT));
//                getWindow().setReturnTransition(new Slide(Gravity.RIGHT));
                break;
            case "explode":
                NormalTransition.activityAnimation(this)
                        .enterTransition(new Explode());
//                getWindow().setEnterTransition(new Explode());
                break;
            case "custom":
                //吧状态栏 动画去掉;
                NormalTransition.activityAnimation(this)
                        .enterTransition(R.transition.content_explore);
//                //吧状态栏 动画去掉;
//                getWindow().setEnterTransition(TransitionInflater.from(this).
//                        inflateTransition(R.transition.content_explore));
                break;
            default:
                break;
        }
        setContentView(R.layout.item1);
        if ("error".equals(flag))
            NormalTransition.activityAnimation(this)
                    .enterTransition(new Slide(Gravity.LEFT));
    }
}
