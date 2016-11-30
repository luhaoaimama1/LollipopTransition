package zone.com.transitionstudy.androidfiveversionlib.share.list;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import zone.com.transitionstudy.Images;
import zone.com.transitionstudy.R;
import zone.com.transitionstudy.BaseActivity;
import zone.com.transition.ShareTransition;
import zone.com.transition.share.helper.Back;
import zone.com.transition.share.helper.Prepare;
import zone.com.transition.share.callback.MapSharedElementsCallback;
import zone.com.transition.share.helper.Parent;
import zone.com.transition.share.To;
import zone.com.transitionstudy.uitls.ViewPagerHelper;

/**
 * Created by fuzhipeng on 16/8/17.
 */
public class SharePic_LibAcitivity extends BaseActivity {
    @Bind(R.id.pager)
    ViewPager pager;
    private int mCurrentPosition;
    public Share_LibFragment mCurrentFragment;
    public boolean startOk;
    private boolean havePic;
    private ViewPagerHelper vpHelper;
    private Prepare prepare;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentPosition = getIntent().getIntExtra("position", 0);
        havePic = getIntent().getBooleanExtra("havePic", false);

        ShareTransition.to(this)
                .setContentView(R.layout.activity_sharepic)
                .onMapSharedElements(new MapSharedElementsCallback() {
                    @Override
                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements, Parent parent) {
                        if (parent.isReturn()) {
                            ImageView sharedElement = ViewPagerHelper.getPositionFrament(SharePic_LibAcitivity.this,
                                    mCurrentFragment, vpHelper.getCurrentPostion()).getAlbumImage();
                            if (sharedElement != null) {
                                sharedElement.setTransitionName(parent.getShareNameByIndex(0));
                                sharedElements.put(sharedElement.getTransitionName(), sharedElement);
                            }
                        } else {
                            ImageView sharedElement = mCurrentFragment.getAlbumImage();
                            if (sharedElement != null) {
                                sharedElement.setTransitionName(parent.getShareNameByIndex(0));
                                sharedElements.put(sharedElement.getTransitionName(), sharedElement);

                            }
                        }
                    }
                })
                .show(new To.PrepareCallback() {

                    @Override
                    public void prepare(Prepare prepare) {
                        SharePic_LibAcitivity.this.prepare = prepare;
                    }
                })
                .back(new To.BackCallBack() {
                    @Override
                    public void setResult(Back mBack) {
                        mBack.setResult(233, new Intent().putExtra("position", vpHelper.getCurrentPostion()));
                    }
                });


        ButterKnife.bind(this);
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                Share_LibFragment fragment = new Share_LibFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putBoolean("havePic", havePic);
                fragment.setArguments(bundle);
                return fragment;
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                super.setPrimaryItem(container, position, object);
                mCurrentFragment = (Share_LibFragment) object;
            }

            @Override
            public int getCount() {
                return Images.imageThumbUrls.length;
            }
        });
        pager.setCurrentItem(mCurrentPosition);
        vpHelper = new ViewPagerHelper(pager);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void go() {
        if (!startOk && prepare != null) {
            prepare.prepareOK();
            startOk = true;
        }
    }


}
