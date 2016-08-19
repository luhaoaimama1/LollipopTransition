package zone.com.transitionstudy.five.share.list;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.List;
import java.util.Map;
import butterknife.Bind;
import butterknife.ButterKnife;
import zone.com.transitionstudy.Images;
import zone.com.transitionstudy.R;
import zone.com.transitionstudy.uitls.ViewPagerHelper;
import zone.com.transitionstudy.five.share.normal.FiveShareNormalAcitivty;

/**
 * Created by fuzhipeng on 16/8/17.
 */
public class SharePicAcitivity extends AppCompatActivity {
    @Bind(R.id.pager)
    ViewPager pager;
    private int mCurrentPosition;
    public ShareFragment mCurrentFragment;
    public boolean startOk;
    private boolean mIsReturning;
    private boolean havePic;

    private SharedElementCallback mCallback = new SharedElementCallback() {


        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
            super.onMapSharedElements(names, sharedElements);
            if (mIsReturning) {
                ImageView sharedElement = ViewPagerHelper.getPositionFrament(SharePicAcitivity.this,
                        mCurrentFragment, vpHelper.getCurrentPostion()).getAlbumImage();
                if (sharedElement != null) {
                    sharedElement.setTransitionName(FiveShareNormalAcitivty.PIC);
                    sharedElements.put(sharedElement.getTransitionName(), sharedElement);
                }
            } else {
                ImageView sharedElement = mCurrentFragment.getAlbumImage();
                if (sharedElement != null) {
                    sharedElement.setTransitionName(FiveShareNormalAcitivty.PIC);
                    sharedElements.put(sharedElement.getTransitionName(), sharedElement);

                }
            }

        }
    };
    private ViewPagerHelper vpHelper;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentPosition = getIntent().getIntExtra("position", 0);
        havePic = getIntent().getBooleanExtra("havePic", false);
        postponeEnterTransition();//其start在fragment里呢

        setEnterSharedElementCallback(mCallback);

        setContentView(R.layout.activity_sharepic);
        ButterKnife.bind(this);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                ShareFragment fragment = new ShareFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putBoolean("havePic", havePic);
                fragment.setArguments(bundle);
                return fragment;
            }
            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                super.setPrimaryItem(container, position, object);
                mCurrentFragment = (ShareFragment) object;
            }

            @Override
            public int getCount() {
                return Images.imageThumbUrls.length;
            }
        });
        pager.setCurrentItem(mCurrentPosition);
        vpHelper=new ViewPagerHelper(pager);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void go() {
        if (!startOk) {
            startPostponedEnterTransition();
            startOk = true;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        super.onBackPressed();//默认会走finishAfterTransition
    }

    @Override
    public void finishAfterTransition() {
        mIsReturning = true;
        setResult(233, new Intent().putExtra("position", vpHelper.getCurrentPostion()));
        System.out.println("finishAfterTransition_mCurrentPosition:" + vpHelper.getCurrentPostion());
        super.finishAfterTransition();
    }
}
