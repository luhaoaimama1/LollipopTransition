package zone.com.transitionstudy.androidfiveversionlib.share.normal;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import zone.com.transitionstudy.R;
import zone.com.transitionstudy.BaseActivity;
import zone.com.transition.ShareTransition;

/**
 * Created by fuzhipeng on 16/8/17.
 */
public class FiveShareNormal_LibAcitivty extends BaseActivity {
    public final static String PIC="pic";
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_normal);
        ButterKnife.bind(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.go)
    public void onClick() {
        ShareTransition.from(this).pairs(findViewById(R.id.iv),findViewById(R.id.bg))
                .go(new Intent(this,FiveShareNormal2_LibAcitivty.class));
//        startActivity(new Intent(this,FiveShareNormal2_LibAcitivty.class),
//                ActivityOptions.makeSceneTransitionAnimation(this,
//                        Pair.create(findViewById(R.id.iv),PIC)  ,
//                        Pair.create(findViewById(R.id.bg),BG)).toBundle());
    }
}
