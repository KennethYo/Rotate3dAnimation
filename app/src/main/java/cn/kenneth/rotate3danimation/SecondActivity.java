package cn.kenneth.rotate3danimation;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView mTextView;
    private TextView mView;
    private int mXdelta;
    private int mYdelta;
    private boolean isRunningCloseAnimation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        String icon = intent.getStringExtra("icon");
        ColorStateList textColors = intent.getParcelableExtra("textColors");

        int x = intent.getIntExtra("x", 0);
        int y = intent.getIntExtra("y", 0);
        int windowWidth = getWindow().getWindowManager().getDefaultDisplay().getWidth();
        int windowHeight = getWindow().getWindowManager().getDefaultDisplay().getHeight();

        mXdelta = x - windowWidth / 2;
        mYdelta = y - windowHeight / 2;

        mTextView = (TextView) findViewById(R.id.view1);
        mTextView.setTypeface(FontManager.getTypeface(this, FontManager.FONT_AWESOME));
        mTextView.setText(icon);
        mTextView.setTextColor(textColors);

        mView = (TextView) findViewById(R.id.view2);
        mView.setTypeface(FontManager.getTypeface(this, FontManager.FONT_AWESOME));

        AnimationSet animationSet = new AnimationSet(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f);
        animationSet.addAnimation(scaleAnimation);

        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, mXdelta, Animation.RELATIVE_TO_SELF, 0f, Animation.ABSOLUTE, mYdelta, Animation.RELATIVE_TO_SELF, 0f);
        animationSet.addAnimation(translateAnimation);

        Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(0, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, 0, true);
        animationSet.addAnimation(rotate3dAnimation);

        animationSet.setAnimationListener(new MyOpenAnimation());

        setAnimationDefault(animationSet);

        mTextView.startAnimation(animationSet);
    }

    private class MyOpenAnimation implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mTextView.setVisibility(View.GONE);
            mView.setVisibility(View.VISIBLE);

            AnimationSet animationSet = new AnimationSet(true);

            Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(-90, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, 0, true);
            setAnimationDefault(rotate3dAnimation);
            mView.startAnimation(rotate3dAnimation);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    @Override
    public void onBackPressed() {
        finishAnimation();
    }

    private void finishAnimation() {
        if (isRunningCloseAnimation) return;

        Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(0, -90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, 0, true);
        setAnimationDefault(rotate3dAnimation);
        rotate3dAnimation.setAnimationListener(new MyCloseAnimation());
        mView.startAnimation(rotate3dAnimation);
    }

    private class MyCloseAnimation implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            isRunningCloseAnimation = true;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mView.setVisibility(View.GONE);
            mTextView.setVisibility(View.VISIBLE);

            AnimationSet animationSet = new AnimationSet(true);

            ScaleAnimation scaleAnimation = new ScaleAnimation(2.0f, 1.0f, 2.0f, 1.0f);
            animationSet.addAnimation(scaleAnimation);

            TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.ABSOLUTE, mXdelta, Animation.RELATIVE_TO_SELF, 0f, Animation.ABSOLUTE, mYdelta);
            animationSet.addAnimation(translateAnimation);

            Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(90, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, 0, true);
            animationSet.addAnimation(rotate3dAnimation);

            animationSet.setAnimationListener(new MyFinishAnimation());

            setAnimationDefault(animationSet);

            mTextView.startAnimation(animationSet);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    private class MyFinishAnimation implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            finish();
            overridePendingTransition(0, 0);
            isRunningCloseAnimation = false;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }


    private void setAnimationDefault(Animation animation) {
        int durationMillis = 500;
        animation.setDuration(durationMillis);
        animation.setFillAfter(true);
        animation.setInterpolator(new AccelerateInterpolator());
    }
}
