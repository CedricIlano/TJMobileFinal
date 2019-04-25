package ramos.pat.com.vieweventsfragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ConnectionSpec;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConfirmCode extends AppCompatActivity {
    private TextView tvone;
    private TextView tvtwo;
    private LottieAnimationView LottieLoad;
    public String registerUrl = "https://0bdea93e.ngrok.io/thomasianjourney/register/checkCode";

    String numberCode;
    String email;
    String mobile;
    int studentsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_code);
        stepView();
        lottie();

        Intent intent = getIntent();
//        String number = intent.getStringExtra("numbercode");
//        String email = intent.getStringExtra("email");
//        String mobile = intent.getStringExtra("mobileNumber");
//        int studentsId = intent.getIntExtra("studentsId", -1);

        if (intent != null) {
            numberCode = intent.getStringExtra("numbercode");
            email = intent.getStringExtra("email");
            mobile = intent.getStringExtra("mobileNumber");
            studentsId = intent.getIntExtra("studentsId", -1);
        }


        OkHttpHandler okHttpHandler = new OkHttpHandler();

        okHttpHandler.execute(registerUrl, numberCode, studentsId +"");

//        final Intent i = new Intent(this,VerifySuccess.class);
    }

    public class OkHttpHandler extends AsyncTask<String, Void, String> {

        OkHttpClient client = new OkHttpClient.Builder()
                .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS))
                .build();

        @Override
        protected String doInBackground(String... params) {

            try {
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("numbercode", params[1])
                        .addFormDataPart("studentsId", params[2])
                        .build();

                Request.Builder builder = new Request.Builder();
                builder.url(params[0])
                        .post(requestBody);
                Request request = builder.build();

                Response response = client.newCall(request).execute();

                System.out.print("Response: " + response.code());

                if (response.isSuccessful()) {

                    return response.body().string();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            openVerifySuccess(s);
        }
    }
    public void openVerifySuccess (String s) {

        if (!TextUtils.isEmpty(s)) {

            Gson gson = new Gson();

            JsonObject jsonObject = new JsonObject();
            try {
                jsonObject = gson.fromJson(s, JsonObject.class);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();

                Toast.makeText(this, "Code is incorrect", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ConfirmCode.this, VerifyCode.class);
                startActivity(intent);
                finish();
            }

            String email = null;
            String mobileNumber = null;
            int studentsId = 0;
            String number = null;

            if  (jsonObject.has("data")) {

                JsonObject dataObject = jsonObject.get("data").getAsJsonObject();

                if (dataObject != null) {

                    if(dataObject.get("studEmail") != null ) {
                        email = dataObject.get("studEmail").getAsString();
                    }

                    if(dataObject.get("studMobileNumber") != null) {
                        mobileNumber = dataObject.get("studMobileNumber").getAsString();
                    }

                    if(dataObject.get("studentsId") != null) {
                        studentsId = dataObject.get("studentsId").getAsInt();
                    }

                    if(dataObject.get("numbercode") != null){
                        number = dataObject.get("numbercode").getAsString();
                    }

                    Intent intent = new Intent(ConfirmCode.this, VerifySuccess.class);
                    intent.putExtra("email",email);
                    intent.putExtra("mobile", mobileNumber);
                    intent.putExtra("studentsId", studentsId);
                    intent.putExtra("numbercode", number);
                    startActivity(intent);
                    finish();
                }

            } else {
                Toast.makeText(this, "Code is incorrect", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ConfirmCode.this, VerifyCode.class);
                intent.putExtra("email", this.email);
                intent.putExtra("mobileNumber", this.mobile);
                startActivity(intent);
                finish();
            }
        }
    }
    public void stepView() {
        //STEPVIEW

        HorizontalStepView stepview = (HorizontalStepView) findViewById(R.id.step_view);

        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("",1);
        StepBean stepBean1 = new StepBean("",1);
        StepBean stepBean2 = new StepBean("",0);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);

        stepview
                .setStepViewTexts(stepsBeanList)//总步骤
                .setTextSize(12)//set textSize
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(ConfirmCode.this, android.R.color.black))
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(ConfirmCode.this, R.color.uncompleted_text_color))
                .setStepViewComplectedTextColor(ContextCompat.getColor(ConfirmCode.this, android.R.color.black))
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(ConfirmCode.this, R.color.uncompleted_text_color))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(ConfirmCode.this, R.drawable.ic_check_black))
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(ConfirmCode.this, R.drawable.ic_radio))
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(ConfirmCode.this, R.drawable.tiger_rar));
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
        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        tvone.startAnimation(myanim);
        tvtwo.startAnimation(myanim);
    }

}
