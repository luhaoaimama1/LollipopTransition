package zone.com.transitionstudy.androidfiveversion.share.list;


import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Transition;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import zone.com.transitionstudy.Images;
import zone.com.transitionstudy.R;
import zone.com.transitionstudy.androidfiveversion.share.normal.FiveShareNormalAcitivty;

/**
 * Created by fuzhipeng on 16/8/17.
 */
public class ShareFragment extends Fragment {
    private ImageView imageView;
    public int index;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_share_normal2, null);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = (ImageView) view.findViewById(R.id.iv);
        imageView.setTransitionName(FiveShareNormalAcitivty.PIC);
        if (!getArguments().getBoolean("havePic")) {
            imageView.setImageBitmap(null);
            ((SharePicAcitivity) getActivity()).go();
            final Transition transition = getActivity().getWindow().getSharedElementEnterTransition();

            if (transition != null) {
                // There is an entering shared element transition so add a listener to it
                transition.addListener(new TransitionAdapter() {

                    @Override
                    public void onTransitionEnd(Transition transition) {
                        Glide.with(getActivity())
                                .load(Images.imageThumbUrls[index = getArguments().getInt("position", 0)]).into(imageView);
                    }
                });
            }
        } else {
            Glide.with(getActivity())
                    .load(Images.imageThumbUrls[index = getArguments().getInt("position", 0)]).asBitmap().into(target);
        }
        imageView.setTag(index);
    }

    private SimpleTarget target = new SimpleTarget<Bitmap>() {

        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
            imageView.setImageBitmap(bitmap);
            System.out.println("Fragment :" + index + "_onResourceReady");
            ((SharePicAcitivity) getActivity()).go();
        }
    };

    public ImageView getAlbumImage() {
        return imageView;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    class TransitionAdapter implements Transition.TransitionListener {

        @Override
        public void onTransitionStart(Transition transition) {

        }

        @Override
        public void onTransitionEnd(Transition transition) {

        }

        @Override
        public void onTransitionCancel(Transition transition) {

        }

        @Override
        public void onTransitionPause(Transition transition) {

        }

        @Override
        public void onTransitionResume(Transition transition) {

        }
    }

}
