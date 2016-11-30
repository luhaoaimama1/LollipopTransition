package zone.com.transitionstudy;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zone.com.transition.ShareTransition;
import zone.com.transitionstudy.androidfiveversion.FiveContentAcitivity;
import zone.com.transitionstudy.androidfiveversion.share.list.FiveShareListAcitivty;
import zone.com.transitionstudy.androidfiveversion.share.normal.FiveShareNormalAcitivty;
import zone.com.transitionstudy.androidfiveversionlib.share.list.FiveShareList_LibAcitivty;
import zone.com.transitionstudy.androidfiveversionlib.share.normal.FiveShareNormal_LibAcitivty;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.bt_fade)
    RadioButton btFade;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setExitTransition(new Slide(Gravity.TOP));
//        getWindow().setReenterTransition(new Slide(Gravity.BOTTOM));

        FrameLayout content = (FrameLayout) findViewById(android.R.id.content);
        System.out.println("before Child count:" + content.getChildCount());
        setContentView(R.layout.activity_main);
        System.out.println("after Child count:" + content.getChildCount());

        ButterKnife.bind(this);
        btFade.performClick();

    }

    private String flag;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.transition, R.id.bt_content, R.id.bt_fade, R.id.bt_slide, R.id.bt_error,
            R.id.bt_explode, R.id.bt_custom, R.id.bt_share_normal, R.id.bt_share_list
            , R.id.bt_share_list_lib, R.id.bt_share_normal_lib})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.transition:
                startActivity(new Intent(this, TransitionActivity.class));
                break;

            case R.id.bt_content:
                ShareTransition.from(this)
                        .go(new Intent(this, FiveContentAcitivity.class).putExtra("flag", flag));
//                startActivity(new Intent(this, FiveContentAcitivity.class).putExtra("flag", flag),
//                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
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
            case R.id.bt_error:
                flag = "error";
                break;

            case R.id.bt_share_normal:
                startActivity(new Intent(this, FiveShareNormalAcitivty.class));
                break;
            case R.id.bt_share_list:
                startActivity(new Intent(this, FiveShareListAcitivty.class));
                break;

            case R.id.bt_share_normal_lib:
                startActivity(new Intent(this, FiveShareNormal_LibAcitivty.class));
                break;
            case R.id.bt_share_list_lib:
                startActivity(new Intent(this, FiveShareList_LibAcitivty.class));
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
