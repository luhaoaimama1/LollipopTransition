package zone.com.transitionstudy.uitls;

import android.support.v7.internal.app.WindowDecorActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zone.adapter.QuickRcvAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by fuzhipeng on 16/8/18.
 */
public class RecyclerUtils {
    //firstVisibility :0   lastVisibility:1,  visibilityCount :2
    public static int[] getShowInfo(RecyclerView rv, QuickRcvAdapter adapter) {

        int[] result = new int[3];
        ArrayList<ViewSort> viewList = new ArrayList<>();

        for (int i = 0; i < adapter.getItemCount(); i++) {
            RecyclerView.ViewHolder hold = rv.findViewHolderForAdapterPosition(i);
            if (hold != null) {
                View item = hold.itemView;
//                System.out.println("index:" + i + "_left:" + item.getLeft() + "_x:"
//                        + item.getX() + "_top:" + item.getTop() + "_y:" + item.getY());
                viewList.add(new ViewSort(item, i));
            }
        }
        if (viewList.size() == 0)
            return result;
        Collections.sort(viewList);
        result[0] = viewList.get(0).index;
        result[1] = viewList.get(viewList.size() - 1).index;
        result[2] = result[1] - result[0];
//        System.out.println("first:"+result[0]+"_last:"+result[1]);
        return result;
    }

    static class ViewSort implements Comparable {

        public View view;
        public int index;

        public ViewSort(View view, int index) {
            this.view = view;
            this.index = index;
        }

        @Override
        public int compareTo(Object another) {
            View v2 = ((ViewSort) another).view;
            int top = view.getTop() - v2.getTop();
            if (top != 0)
                return top;
            else
                return view.getLeft() - v2.getLeft();//那个大就返回这样 就是从小到大
        }
    }
}