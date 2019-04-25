package ramos.pat.com.vieweventsfragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RecoverySystem;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ConnectionSpec;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VerifyCode extends AppCompatActivity {

    private Button cancelbtn;
    private Button verifybtn;
    TextView emailView;
    TextView resend;
    Dialog dialog_resend;
    Button okbtn;
    TextView titleResend, exResend;
    ImageView closeDialogResend, imageResend;
    PinEntryEditText numbercode;
    private LottieAnimationView LottieMail;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);
        stepView();
        lottie();

        Intent intent = getIntent();

        final String email = intent.getStringExtra("email");
        final String mobile = intent.getStringExtra("mobileNumber");

        cancelbtn = findViewById(R.id.cancelbtn);
        resend =    findViewById(R.id.resend);
        verifybtn = findViewById(R.id.verifybtn);
        numbercode =  findViewById(R.id.numbercode);
        emailView = findViewById(R.id.emailView);

        emailView.setText(email);

        final int studentsId = intent.getIntExtra("studentsId", 0);

        dialog_resend = new Dialog(this);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogResend();
            }
        });
        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openMain2Activity();
            }
        });

        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = numbercode.getText().toString();

                if (number != null && !number.isEmpty()) {

                    Intent intent = new Intent(VerifyCode.this, ConfirmCode.class);
                    intent.putExtra("numbercode", number);
                    intent.putExtra("email", email);
                    intent.putExtra("mobileNumber", mobile);
                    intent.putExtra("studentsId", studentsId);
                    startActivity(intent);
                    finish();

                } else {

                    Toast.makeText(VerifyCode.this, "Code is empty", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void lottie(){
        LottieMail = findViewById(R.id.mainlottieMail);
        LottieMail.setScale(1.5f);
        LottieMail.setVisibility(View.VISIBLE);
        LottieMail.setAnimation(R.raw.mail);
        LottieMail.playAnimation();
    }
    public void stepView() {
        //STEPVIEW

        HorizontalStepView stepview = (HorizontalStepView) findViewById(R.id.step_view);

        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("",1);
        StepBean stepBean1 = new StepBean("",0);
        StepBean stepBean2 = new StepBean("",-1);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);

        stepview
                .setStepViewTexts(stepsBeanList)//总步骤
                .setTextSize(12)//set textSize
                .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(VerifyCode.this, android.R.color.black))
                .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(VerifyCode.this, R.color.uncompleted_text_color))
                .setStepViewComplectedTextColor(ContextCompat.getColor(VerifyCode.this, android.R.color.black))
                .setStepViewUnComplectedTextColor(ContextCompat.getColor(VerifyCode.this, R.color.uncompleted_text_color))
                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(VerifyCode.this, R.drawable.ic_check_black))
                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(VerifyCode.this, R.drawable.ic_radio))
                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(VerifyCode.this, R.drawable.tiger_rar));

    }
    public void openMain2Activity() {
        Intent intent = new Intent(this,Main2Activity.class);
        startActivity(intent);
    }
    public void ShowDialogResend() {
        dialog_resend.setContentView(R.layout.dialog_resend);
        closeDialogResend = (ImageView) dialog_resend.findViewById(R.id.closeDialogResend);
        imageResend = (ImageView) dialog_resend.findViewById(R.id.imageResend);
        okbtn = (Button) dialog_resend.findViewById(R.id.okbtn);
        titleResend = (TextView) dialog_resend.findViewById(R.id.titleResend);
        exResend = (TextView) dialog_resend.findViewById(R.id.exResend);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_resend.dismiss();
            }
        });

        closeDialogResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_resend.dismiss();
            }
        });

        dialog_resend.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog_resend.show();
    }

    public void CancelAnim(View view) {
        if (view == findViewById(R.id.cancelbtn)) {
            //open verifycode
            startActivity(new Intent(this, Main2Activity.class));
            //add animation
            Animatoo.animateSlideRight(this);
        }
    }

    public void VerifyAnim(View view) {
        if (view == findViewById(R.id.verifybtn)) {
            //open verifycode
            startActivity(new Intent(this, ConfirmCode.class));
            //add animation
            Animatoo.animateSlideLeft(this);
            finish();
        }
    }
//    public void openConfirmCode(String s) {
//
//        JsonObject jsonObject = new Gson().fromJson(s, JsonObject.class);
//
//        if (jsonObject != null) {
//
////            if (jsonObject.get("message").equals("User login successful.")) {
//
//                if (jsonObject.has("data")) {
//
//                    JsonObject dataObject = jsonObject.get("data").getAsJsonObject();
//
//                    int studentsId = dataObject.get("studentsId").getAsInt();
//
//                    System.out.println("STUDENTS ID:" + studentsId);
//
//                    Intent intent = new Intent(this, ConfirmCode.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//
//                    Toast.makeText(VerifyCode.this, "Code is incorrect", Toast.LENGTH_LONG).show();
//                }
//            } else {
//
//                Toast.makeText(VerifyCode.this, "null", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
}

