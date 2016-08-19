package zone.com.transitionstudy;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import com.nineoldandroids.view.ViewHelper;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zone.com.transitionstudy.view.ChangeColor;

/**
 * Created by fuzhipeng on 16/8/16.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class TransitionActivity extends Activity implements Transition.TransitionListener {

    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.bt_fade)
    RadioButton btFade;
    private Scene nowScenesMethod;
    private Scene scene2;
    private Scene scene1;
    private ViewGroup root;
    private Transition nowTransition = new Fade();
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        ButterKnife.bind(this);
        btFade.performClick();
        scenesMethod();
        nowTransition.addListener(this);
    }

    //todo 总结  Scene的实例,记录当前的 的所有属性 ,如果你想改变某个view的属性,那么在 Scene的实例生成之前改变;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void scenesMethod() {
        root = (ViewGroup) findViewById(R.id.root);
        //这个无限次数都好使...   这种加载方式支持Gone 也支持invisiblity  但是不支持 生成场景之前 移动某个view的属性
//        sceneByXml_FirstType();

        //这个是已经加载的View  不支持 Gone的操作 仅仅支持invisibility的操作; 支持 生成场景之前支持移动某个View的属性;
//        sceneByNowLayout_SecondType();

        //这个支持Gone不过仅仅支持两次就失效了  但是支持 invisibility 支持 生成场景之前支持移动某个View的属性;
        sceneByMyselfInflateXml_ThreeType();


        //为啥这里用这个 因为我不加这个  第一次的场景他不是 scene1  导致第一次动画有点问题 加上就好了;
        //但是自定义那个xml Transition  则可不用加这个...!!!
        TransitionManager.go(scene1, new ChangeBounds());

        scene2 = Scene.getSceneForLayout(root, R.layout.item2, this);
        nowScenesMethod = scene1;
    }

    private void sceneByMyselfInflateXml_ThreeType() {
        ViewGroup view = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.item1, null, false);
        final View scene1Blue = view.findViewById(R.id.blue);
        ViewHelper.setTranslationY(scene1Blue, -100);//而且支持这种操作;
        scene1 = new Scene(root, view);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void sceneByNowLayout_SecondType() {
        ViewHelper.setTranslationY(findViewById(R.id.blue), -100);
        scene1 = new Scene(root, root.findViewById(R.id.secen1));
    }

    private void sceneByXml_FirstType() {
        scene1 = Scene.getSceneForLayout(root, R.layout.item1, this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.scenesMethod)
    public void onClick() {
        if (nowScenesMethod == scene1)
            nowScenesMethod = scene2;
        else if (nowScenesMethod == scene2)
            nowScenesMethod = scene1;
        //注意  invisbility 和Gone不一样 动画
        TransitionManager.go(nowScenesMethod, nowTransition);
//        TransitionManager.go(nowScenesMethod);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.bt_fade, R.id.bt_silde, R.id.bt_explode, R.id.bt_auto, R.id.bt_custom, R.id.bt_color, R.id.bt_delay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_fade:
                nowTransition = new Fade();
                textView.setText("Fade 是对可见性 既透明度,做动画,对位移则无效 所以Green位置直接显示最后的位置而没有动画");
                break;
            case R.id.bt_silde:
                nowTransition = new Slide();
                textView.setText("Slide同Fade 是对可见性做动画,不过动画不是渐变而是位移");
                break;
            case R.id.bt_explode:
                nowTransition = new Explode();
                textView.setText("Explode同Fade 类似Slide");
                break;
            case R.id.bt_auto:
                nowTransition = new AutoTransition();
                textView.setText("AutoTransition 默认,(ORDERING_SEQUENTIAL)Fade out,ChangeBounds(位置做动画), Fade in");
                break;
            case R.id.bt_custom:
                nowTransition = TransitionInflater.from(this).
                        inflateTransition(R.transition.custom);
                textView.setText("custom XMl Fade 排除红色 方块 既红色方块不做动画 直接隐藏或者显示");
                break;
            case R.id.bt_color:
                nowTransition = new ChangeColor();
                textView.setText("ChangeColor 自定义Transition,是对background 的变化做动画");
                break;
            case R.id.bt_delay:
                if (delay) {
                    TransitionManager.beginDelayedTransition(root, TransitionInflater.from(this).
                            inflateTransition(R.transition.custom));
                    ViewHelper.setScaleX(findViewById(R.id.yellow), 1.5F);
//                    findViewById(R.id.yellow).requestLayout();
                    ViewHelper.setScaleY(findViewById(R.id.yellow), 1.5F);
                } else {
                    ViewHelper.setScaleX(findViewById(R.id.yellow), 3F);
                    ViewHelper.setScaleY(findViewById(R.id.yellow), 3F);
                }
                delay = !delay;
                textView.setText("BeginDelay 不需要依赖Scence 而是在布局中 更改属性(可见,位置,大小,缩放等等) " +
                        "仅仅生效一次(一次可以改变多个属性 即使中间 requestLayout也能改变后边的~); ");
                break;
        }
    }

    boolean delay = true;

    //监听流程  动画正常完成    start->end
    //   动画 做到一半,然后做另一个:start->pause->onResume->end(到这是前面那个动画结束 后便开始另一个)->start->end
    //并且在start里 更改target不好使  只能在开始更改target
    @Override
    public void onTransitionStart(Transition transition) {
        System.out.println("onTransitionStart:" + transition.toString());
    }

    @Override
    public void onTransitionEnd(Transition transition) {
        System.out.println("onTransitionEnd:" + transition.toString());
    }

    @Override
    public void onTransitionCancel(Transition transition) {
        System.out.println("onTransitionCancel:" + transition.toString());
    }

    @Override
    public void onTransitionPause(Transition transition) {
        System.out.println("onTransitionPause:" + transition.toString());
    }

    @Override
    public void onTransitionResume(Transition transition) {
        System.out.println("onTransitionResume:" + transition.toString());
    }
}
