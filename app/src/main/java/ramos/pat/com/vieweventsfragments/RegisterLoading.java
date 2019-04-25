package ramos.pat.com.vieweventsfragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ConnectionSpec;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterLoading extends AppCompatActivity {
    private TextView tvone;
    private TextView tvtwo;
    private LottieAnimationView LottieLoad;
    public String registerUrl = "https://0bdea93e.ngrok.io/thomasianjourney/register/registerUser";

    OkHttpClient client;

    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_loading);
        stepView();
        lottie();

        counter = 0;

        client = new OkHttpClient.Builder()
                .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS))
                .build();

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String mobile = intent.getStringExtra("mobileNumber");
//        int studentsId = intent.getIntExtra("studentsId", -1);

        OkHttpHandler okHttpHandler = new OkHttpHandler();

        okHttpHandler.execute(registerUrl, email, mobile);

    }

    public class OkHttpHandler extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            return doInBackgroundTask(params[1], params[2], params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            openVerifyCode(s);
        }
    }

    public String doInBackgroundTask(String emailAddress, String mobileNumber, String url) {
        try {
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("emailAddress", emailAddress)
                    .addFormDataPart("mobileNumber", mobileNumber)
                    .build();

            Request.Builder builder = new Request.Builder();
            builder.url(url)
                    .post(requestBody);
            Request request = builder.build();

            Response response = client.newCall(request).execute();

            System.out.print("Response: " + response.code());

            if (response.isSuccessful()) {

                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
//            if (counter < 5) {
//                System.out.println("Counter is: " + counter);
//                counter++;
//                doInBackgroundTask(emailAddress, mobileNumber, url);
//            } else {
//                // five failed attempts
//            }
        }

        return "";
    }

    public void openVerifyCode(String s) {

        if (!TextUtils.isEmpty(s)) {

            Gson gson = new Gson();

            JsonObject jsonObject = new JsonObject();
            try {
                jsonObject = gson.fromJson(s, JsonObject.class);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();

                Toast.makeText(this, "Email not found", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterLoading.this, Main2Activity.class);
                startActivity(intent);
                finish();
            }

            String email;
            String mobileNumber;
            int studentsId;

            if (jsonObject.has("data")) {

                JsonObject dataObject = jsonObject.get("data").getAsJsonObject();

                email = dataObject.get("studEmail").getAsString();
                mobileNumber = dataObject.get("studMobileNumber").getAsString();
                studentsId = dataObject.get("studentsId").getAsInt();

                Intent intent = new Intent(RegisterLoading.this, VerifyCode.class);
                intent.putExtra("email", email);
                intent.putExtra("mobile", mobileNumber);
                intent.putExtra("studentsId", studentsId);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterLoading.this, Main2Activity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    public void lottie() {
        //ANIMATION LOTTIE
        LottieLoad = findViewById(R.id.mainlottieLoad);
        LottieLoad.setScale(7f);
        LottieLoad.setVisibility(View.VISIBLE);
        LottieLoad.setAnimation(R.raw.load);
        LottieLoad.playAnimation();

        tvone = (TextView) findViewById(R.id.tvone);
        tvtwo = (TextView) findViewById(R.id.tvtwo);
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        tvone.startAnimation(myanim);
        tvtwo.startAnimation(myanim);
    }
    public void stepView() {
        //STEPVIEW

        HorizontalStepView stepview = (HorizontalStepView) findViewById(R.id.step_view);

        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("", 0);
        StepBean stepBean1 = new StepBean("", -1);
        StepBean stepBean2 = new StepBean("", -1);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);

        stepview
                .setStepViewTexts(stepsBeanList)//总步骤
                .setTextSize(12)//set textSize
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(RegisterLoading.this, android.R.color.black))
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(RegisterLoading.this, R.color.uncompleted_text_color))
                .setStepViewComplectedTextColor(ContextCompat.getColor(RegisterLoading.this, android.R.color.black))
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(RegisterLoading.this, R.color.uncompleted_text_color))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(RegisterLoading.this, R.drawable.ic_check_black))
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(RegisterLoading.this, R.drawable.ic_radio))
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(RegisterLoading.this, R.drawable.tiger_rar));
    }
}







