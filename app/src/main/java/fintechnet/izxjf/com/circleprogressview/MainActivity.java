package fintechnet.izxjf.com.circleprogressview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CircleProgressView mCircleProgress = (CircleProgressView) findViewById(R.id.myCircleProgress);
        mCircleProgress.setMaxProgress(100);
        mCircleProgress.setAnimatorProgress(60);

    }
}
