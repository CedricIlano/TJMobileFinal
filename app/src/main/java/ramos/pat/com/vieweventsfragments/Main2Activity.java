package ramos.pat.com.vieweventsfragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ConnectionSpec;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity {
    private Button contbutton;
    EditText emailAddress, mobileNumber;

    private OkHttpClient client;
    private TextView textView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        stepView();

        contbutton = findViewById(R.id.contbutton);
        emailAddress = findViewById(R.id.emailAddress);
        mobileNumber = findViewById(R.id.mobileNumber);

        contbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String email = emailAddress.getText().toString();
                String mobile = mobileNumber.getText().toString();

                if (email != null && !email.isEmpty() && mobile != null && !mobile.isEmpty()) {

                    Intent intent = new Intent(Main2Activity.this, RegisterLoading.class);
                    intent.putExtra("email", email);
                    intent.putExtra("mobileNumber", mobile);
                    startActivity(intent);
                    finish();

                } else {

                    Toast.makeText(Main2Activity.this, "Email and Mobile number cannot be empty ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void contAnim(View view) {
        if (view == findViewById(R.id.contbutton)) {
            //open verifycode
            startActivity(new Intent(this, VerifyCode.class));
            //add animation
            Animatoo.animateSlideLeft(this);
        }
    }
    public void stepView() {

        //STEPVIEW

        HorizontalStepView stepview = (HorizontalStepView) findViewById(R.id.step_view);

        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("",0);
        StepBean stepBean1 = new StepBean("",-1);
        StepBean stepBean2 = new StepBean("",-1);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);

        stepview
                .setStepViewTexts(stepsBeanList)//总步骤
                .setTextSize(12)//set textSize
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(Main2Activity.this, android.R.color.black))
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(Main2Activity.this, R.color.uncompleted_text_color))
                .setStepViewComplectedTextColor(ContextCompat.getColor(Main2Activity.this, android.R.color.black))
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(Main2Activity.this, R.color.uncompleted_text_color))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(Main2Activity.this, R.drawable.ic_check_black))
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(Main2Activity.this, R.drawable.ic_radio))
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(Main2Activity.this, R.drawable.tiger_rar));

    }
}
