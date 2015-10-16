package cn.kenneth.rotate3danimation;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FontManager.markAsIconContainer(findViewById(R.id.view), FontManager.getTypeface(this, FontManager.FONT_AWESOME));

        findViewById(R.id.twitter).setOnClickListener(this);
        findViewById(R.id.facebook).setOnClickListener(this);
        findViewById(R.id.wechat).setOnClickListener(this);
        findViewById(R.id.weibo).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        TextView textView = (TextView) v;
        String icon = textView.getText().toString();
        ColorStateList textColors = textView.getTextColors();

        int[] location = new int[2];
        textView.getLocationInWindow(location);
        location[0] = location[0] + textView.getWidth() / 2;
        location[1] = location[1] + textView.getHeight() / 2;

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("icon", icon);
        intent.putExtra("textColors", textColors);
        intent.putExtra("x", location[0]);
        intent.putExtra("y", location[1]);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
}
