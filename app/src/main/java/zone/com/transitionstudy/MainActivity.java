package zone.com.transitionstudy;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zone.com.transitionstudy.five.FiveContentAcitivity;
import zone.com.transitionstudy.five.share.list.FiveShareListAcitivty;
import zone.com.transitionstudy.five.share.normal.FiveShareNormalAcitivty;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.bt_fade)
    RadioButton btFade;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setExitTransition(new Slide(Gravity.TOP));
//        getWindow().setReenterTransition(new Slide(Gravity.BOTTOM));

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btFade.performClick();

    }

    private String flag;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.transition, R.id.bt_content, R.id.bt_fade, R.id.bt_slide,
            R.id.bt_explode, R.id.bt_custom,R.id.bt_share_normal, R.id.bt_share_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.transition:
                startActivity(new Intent(this, TransitionActivity.class));
                break;

            case R.id.bt_content:
                startActivity(new Intent(this, FiveContentAcitivity.class).putExtra("flag", flag),
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;
            case R.id.bt_fade:
                flag = "fade";
                break;
            case R.id.bt_slide:
                flag = "slide";
                break;
            case R.id.bt_explode:
                flag = "explode";
                break;
            case R.id.bt_custom:
                flag = "custom";
                break;

            case R.id.bt_share_normal:
                startActivity(new Intent(this,FiveShareNormalAcitivty.class));
                break;
            case R.id.bt_share_list:
                startActivity(new Intent(this,FiveShareListAcitivty.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(MainActivity.this).clearDiskCache();//清楚 磁盘缓存
//                Glide.get(MainActivity.this).clearMemory();
            }
        }).start();
//            Glide.get(MainActivity.this).clearDiskCache();//清楚 磁盘缓存
            Glide.get(MainActivity.this).clearMemory();
        super.onDestroy();
    }
}
