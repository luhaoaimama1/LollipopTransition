package zone.com.transitionstudy.uitls;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

/**
 * Created by fuzhipeng on 16/8/19.
 */
public class ViewPagerHelper {
    private ViewPager vp;
    private ViewPager.OnPageChangeListener listener;
    private float currentPostion;

    public ViewPagerHelper(ViewPager vp, ViewPager.OnPageChangeListener listener) {
        this.vp = vp;
        this.listener = listener;
        init();
    }

    public ViewPagerHelper(ViewPager pager) {
        this(pager, null);
    }

    private void init() {
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (listener != null)
                    listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                currentPostion = position + positionOffset;
            }

            @Override
            public void onPageSelected(int position) {
                if (listener != null)
                    listener.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (listener != null)
                    listener.onPageScrollStateChanged(state);
            }
        });
    }

    /**
     * @param context
     * @param current  这个是为了找到tag:
     * @param position
     * @return
     */
    public static <T extends Fragment> T getPositionFrament(FragmentActivity context, T current, int position) {
        //tag 类似这个  反正最后一个是位置 ——android:switcher:2131558500:8
        String[] splits = current.getTag().split(":");
//        String tag = current.getTag().substring(0, current.getTag().length() - 1) + position;
        StringBuilder tag = new StringBuilder();
        for (int i = 0; i < splits.length; i++) {
            if (i == splits.length - 1)
                tag.append("" + position);
            else {
                tag.append(splits[i]);
                tag.append(":");
            }
        }
        System.out.println("getPositionFrament_tag:" + tag.toString());
        return (T) context.getSupportFragmentManager().findFragmentByTag(tag.toString());
    }

    public ViewPager getVp() {
        return vp;
    }

    public int getCurrentPostion() {
        System.out.println("现在的位置呢:" + currentPostion);
        return Math.round(currentPostion);
    }


}
