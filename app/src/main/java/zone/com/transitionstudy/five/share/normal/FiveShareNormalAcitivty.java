package zone.com.transitionstudy.five.share.normal;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;

import butterknife.ButterKnife;
import butterknife.OnClick;
import zone.com.transitionstudy.R;

/**
 * Created by fuzhipeng on 16/8/17.
 */
public class FiveShareNormalAcitivty extends AppCompatActivity {
    public final static String PIC="pic";
    public final static String BG="bg";
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
        startActivity(new Intent(this,FiveShareNormal2Acitivty.class),
                ActivityOptions.makeSceneTransitionAnimation(this,
                        Pair.create(findViewById(R.id.iv),PIC)  ,
                        Pair.create(findViewById(R.id.bg),BG)).toBundle());
    }
}
