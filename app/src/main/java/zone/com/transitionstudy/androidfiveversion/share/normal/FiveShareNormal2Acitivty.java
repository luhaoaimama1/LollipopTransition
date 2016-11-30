package zone.com.transitionstudy.androidfiveversion.share.normal;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import zone.com.transitionstudy.R;

/**
 * Created by fuzhipeng on 16/8/17.
 */
public class FiveShareNormal2Acitivty extends AppCompatActivity {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //先等会在开始动画 没准备好
        postponeEnterTransition();
        setContentView(R.layout.activity_share_normal2);

        //共享元素 需要 transitionName 相同
        findViewById(R.id.bg).setTransitionName(FiveShareNormalAcitivty.BG);
        findViewById(R.id.iv).setTransitionName(FiveShareNormalAcitivty.PIC);

        //准备好了  开始吧
        startPostponedEnterTransition();
    }
}
