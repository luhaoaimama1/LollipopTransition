package zone.com.transitionstudy.androidfiveversionlib.share.normal;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import zone.com.transitionstudy.R;
import zone.com.transitionstudy.BaseActivity;
import zone.com.transition.ShareTransition;

/**
 * Created by fuzhipeng on 16/8/17.
 */
public class FiveShareNormal2_LibAcitivty extends BaseActivity {
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareTransition.to(this)
                .setContentView(R.layout.activity_share_normal2)
                .pairs(R.id.iv, R.id.bg)
                .show();

//        //先等会在开始动画 没准备好
//        postponeEnterTransition();
//
//        //共享元素 需要 transitionName 相同
//        findViewById(R.id.view).setTransitionName(FiveShareNormal_LibAcitivty.BG);
//        findViewById(R.id.imageView).setTransitionName(FiveShareNormal_LibAcitivty.PIC);
//
//        //准备好了  开始吧
//        startPostponedEnterTransition();
    }
}
