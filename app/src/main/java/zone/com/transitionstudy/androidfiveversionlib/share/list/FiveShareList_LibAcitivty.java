package zone.com.transitionstudy.androidfiveversionlib.share.list;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import zone.com.transitionstudy.BaseActivity;
import zone.com.transition.ShareTransition;
import zone.com.transition.share.From;
import zone.com.transition.share.helper.Prepare;
import zone.com.transition.share.callback.MapSharedElementsCallback;
import zone.com.transition.share.helper.Parent;

/**
 * Created by fuzhipeng on 16/8/17.
 */
public class FiveShareList_LibAcitivty extends BaseActivity {
    private RecyclerView rv;
    private QuickRcvAdapter<String> adapter;
    private int returnIndex;
    private GridLayoutManager layout;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

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

                ShareTransition.from(FiveShareList_LibAcitivty.this)
                        .pairs(view.findViewById(R.id.ivv))
                        .onMapSharedElements(new MapSharedElementsCallback() {
                            @Override
                            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements, Parent parent) {
                                if (parent.isReturn()) {
                                    ViewGroup vg = (ViewGroup) rv.findViewWithTag(returnIndex);
                                    if (vg != null) {
                                        View view = vg.findViewById(R.id.ivv);
                                        sharedElements.put(parent.getShareNameByIndex(0), view);
                                    } else {
                                        names.clear();
                                        sharedElements.clear();
                                    }
                                }
                            }
                        })
                        .go(new Intent(FiveShareList_LibAcitivty.this, SharePic_LibAcitivity.class)
                                .putExtra("position", position).putExtra("havePic",
                                        ((ImageView) parent.findViewById(R.id.ivv)).getDrawable() == null ? false : true
                                ))
                        .back(new From.PrepareCallback() {
                            @Override
                            public void prepare(int resultCode, Intent data, final Prepare prepare) {
                                returnIndex = data.getIntExtra("position", 0);
                                //        final int[] info = RecyclerUtils.getShowInfo(rv, adapter);
                                //        if (returnIndex > info[1] || returnIndex < info[0]) {
                                layout.scrollToPositionWithOffset(returnIndex, 0);//这个方法略屌 比下面的精准  让整个item显示出来了
                                //            layout.scrollToPosition(returnIndex);//todo 是他的问题  没有让他全部显示出来;
                                //            System.out.println("滚动到 position:" + returnIndex);
                                rv.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                                    @Override
                                    public boolean onPreDraw() {
                                        rv.getViewTreeObserver().removeOnPreDrawListener(this);
                                        prepare.prepareOK();
                                        return true;
                                    }
                                });
                            }
                        });

            }
        });
    }

}
