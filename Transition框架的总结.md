###三种加载场景的方式;
（Delay则在另一个文章里）生成场景的三种方式的对比：TransitionActivity 可以打开注释进行尝试;

| 生成方式 | 优点  | 缺点 | 案例 |
| :------------: |:---------------:| :-----:|:-----:|
|  Scene.getSceneForLayout(root, R.layout.item1, this)   | 支持Gone,invisibilty的变化 | 不支持生成场景之前  移动某个属性； | sceneByXml_FirstType |
| new Scene(root, root.findViewById(R.id.secen1))     | 支持生成场景之前  移动某个属性，支持invisibilty的变化        |  不支持Gone； | sceneByNowLayout_SecondType |
|  new Scene(root, view); | 支持生成场景之前  移动某个属性，支持invisibilty的变化     |   不支持Gone（仅仅好使两次）； | sceneByMyselfInflateXml_ThreeType |



###执行动画
 
    TransitionManager.go(nowScenesMethod, nowTransition);

###加载xml Transition

     nowTransition = TransitionInflater.from(this).
                        inflateTransition(R.transition.custom);

TransitionInflater.from(this).inflateTransition(R.transition.custom);// <!--默认是together 因为  new TransitionSet 默认就是together-->

### 注意:AutoTransition 则  不是together而是顺序执行；

        setOrdering(ORDERING_SEQUENTIAL);
        addTransition(new Fade(Fade.OUT)).
                addTransition(new ChangeBounds()).
                addTransition(new Fade(Fade.IN));




##动画监听 监听流程  

动画正常完成  :  start->end

动画 做到一半,然后做另一个:start->pause->onResume->end(到这是前面那个动画结束 后便开始另一个)->start->end

注意：并且在start里 更改target不好使  只能在开始更改target

 nowTransition.addListener(this);
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
    

### beginDelayedTranstion

BeginDelay 不需要依赖Scence 而是在布局中 更改属性(可见,位置,大小,缩放等等) " +
        "仅仅生效一次(一次可以改变多个属性 即使中间 requestLayout也能改变后边的~)
        
跟踪源码就知道  他添加了ViewTree 监听里的 onPreDraw   还有别的;

###自定义Transition可以参考 ChangeColor(Google的源码)




