package ramos.pat.com.vieweventsfragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity {
    private TextView tv ;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        tv.startAnimation(myanim);
        iv.startAnimation(myanim);

        Intent intent;

        if (isSharedPreferencesPresent()) {
            intent = new Intent(Splash.this, SecondActivity.class);
        } else {
            intent = new Intent(Splash.this, Main2Activity.class);
        }

        startActivity(intent);
    }

    public boolean isSharedPreferencesPresent() {

        SharedPreferences sharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE);

        return sharedPreferences.contains("email") && sharedPreferences.contains("mobile") && sharedPreferences.contains("studentsId");

    }
}
