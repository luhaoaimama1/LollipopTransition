package zone.com.transitionstudy.androidfiveversion.share.list;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;

import com.zone.adapter.QuickRcvAdapter;
import com.zone.adapter.callback.Helper;
import com.zone.adapter.callback.IAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import zone.com.transitionstudy.Images;
import zone.com.transitionstudy.R;
import zone.com.transitionstudy.androidfiveversion.share.normal.FiveShareNormalAcitivty;

/**
 * Created by fuzhipeng on 16/8/17.
 */
public class FiveShareListAcitivty extends AppCompatActivity {
    private RecyclerView rv;
    private QuickRcvAdapter<String> adapter;

    SharedElementCallback exitCallback = new SharedElementCallback() {

        @Override
        public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
            super.onMapSharedElements(names, sharedElements);
            if (isReturn) {
                ViewGroup vg = (ViewGroup) rv.findViewWithTag(returnIndex);
                if (vg != null) {
                    View view = vg.findViewById(R.id.ivv);
                    sharedElements.put(FiveShareNormalAcitivty.PIC, view);
                } else {
                    names.clear();
                    sharedElements.clear();
                }
            }
        }
    };
    private boolean isReturn;
    private int returnIndex;
    private GridLayoutManager layout;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setExitSharedElementCallback(exitCallback);

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(layout = new GridLayoutManager(this, 2));
        rv.setAdapter(adapter = new QuickRcvAdapter<String>(this, Arrays.asList(Images.imageThumbUrls)) {
            @Override
            public void fillData(final Helper<String> helper, String item, boolean itemChanged, int layoutId) {
                helper.setImageUrl(R.id.ivv, item);
                helper.setText(R.id.tv, item);
                helper.getView(R.id.parent).setTag(helper.getPosition());
            }


            @Override
            public int getItemLayoutId(String s, int position) {
                return R.layout.rv_item;
            }
        });
        adapter.setOnItemClickListener(new IAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position, long id) {
                isReturn = false;
                startActivity(new Intent(FiveShareListAcitivty.this, SharePicAcitivity.class)
                                .putExtra("position", position).putExtra("havePic",
                                ((ImageView) parent.findViewById(R.id.ivv)).getDrawable() == null ? false : true
                                ),
                        ActivityOptions.makeSceneTransitionAnimation(FiveShareListAcitivty.this,
                                Pair.create(view.findViewById(R.id.ivv), FiveShareNormalAcitivty.PIC)).toBundle());
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        isReturn = true;
        returnIndex = data.getIntExtra("position", 0);
//        final int[] info = RecyclerUtils.getShowInfo(rv, adapter);
//        if (returnIndex > info[1] || returnIndex < info[0]) {
        layout.scrollToPositionWithOffset(returnIndex, 0);//这个方法略屌 比下面的精准  让整个item显示出来了
//            layout.scrollToPosition(returnIndex);//todo 是他的问题  没有让他全部显示出来;
//            System.out.println("滚动到 position:" + returnIndex);
//        }

        postponeEnterTransition();
        rv.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                rv.getViewTreeObserver().removeOnPreDrawListener(this);
                startPostponedEnterTransition();
                return true;
            }
        });
    }

}
