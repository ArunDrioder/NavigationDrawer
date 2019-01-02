package com.example.ss.navigationdrawer.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ss.navigationdrawer.R;
import com.example.ss.navigationdrawer.Utils.Util;
import com.example.ss.navigationdrawer.Utils.Validation;
import com.example.ss.navigationdrawer.request.CommonRequest;
import com.example.ss.navigationdrawer.retrofit.ListDetails;
import com.example.ss.navigationdrawer.retrofit.RetrofitInterface;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;



public class RegisterActivity extends AppCompatActivity {

    RetrofitInterface apiInterface;


    EditText regName, regLastName, regEmail, regMobile, regPassword, regConfirmPassword;
    TextView tvLogin;
    String TAG = RegisterActivity.class.getSimpleName();

    String Name, LastName, Mobile, Email, Password, ConfirmPassword;
    boolean name, lastname, mobile, email, password, confirmpassword;

    Validation validation;
    Activity activity;
    CommonRequest commonRequest;


    //Declaration Button
    Button regBtn;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        validation = new Validation();

        initTextViewLogin();
        initViews();
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Validation();
            }
        });
    }

    //this method used to set Login TextView click event
    private void initTextViewLogin() {
        TextView tvLogin = (TextView) findViewById(R.id.tvlogin);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //this method is used to connect XML views to its Objects
    private void initViews() {

        activity = RegisterActivity.this;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitInterface.url)
                // .baseUrl("https://192.168.1.2/hostel/public/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(RetrofitInterface.class);

        regName = (EditText) findViewById(R.id.regName);
        regLastName = (EditText) findViewById(R.id.regLastName);
        regEmail = (EditText) findViewById(R.id.regEmail);
        regMobile = (EditText) findViewById(R.id.regMobile);
        regPassword = (EditText) findViewById(R.id.regPassword);
        regConfirmPassword = (EditText) findViewById(R.id.regConfirmPassword);
        tvLogin = (TextView) findViewById(R.id.tvlogin);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        regBtn = (Button) findViewById(R.id.regBtn);



    }

    public void Validation(){

        Name = regName.getText().toString();
        LastName = regLastName.getText().toString();
        Email = regEmail.getText().toString();
        Mobile = regMobile.getText().toString();
        Password = regPassword.getText().toString();
        ConfirmPassword = regConfirmPassword.getText().toString();


        if (Validation.isValidName(Name)) {
            name = true;
            regName.setError(null);

        } else {
            name = false;
            regName.setError("Enter Valid Name!");

        }
        if (!validation.isEmpty(Name)) {
            name = true;
            regName.setError(null);

        } else {
            name = false;
            regName.setError("Enter Your Name!");

        }

        if (Validation.isValidName(LastName)){
            lastname = true;
            regLastName.setError(null);
        }
        else{
            lastname = false;
            regLastName.setError("Enter your Last Name");
        }
        if (!validation.isEmpty(LastName)) {
            lastname = true;
            regLastName.setError(null);

        } else {
            name = false;
            regName.setError("Enter Your Last Name!");

        }

        if (validation.isValidEmail(Email)) {
            email = true;
            regEmail.setError(null);
        } else {
            email = false;
            regEmail.setError("Enter Valid Email!");

        }
        if (!validation.isEmpty(Email)) {
            email = true;
            regEmail.setError(null);
        } else {
            email = false;
            regEmail.setError("Enter Email");

        }
        if (validation.isValidPhone(Mobile)) {

            mobile = true;
            regMobile.setError(null);

        } else {
            mobile = false;
            regMobile.setError("Enter Valid  Mobile Number!");

        }
        if (!validation.isEmpty(Mobile)) {

            mobile = true;
            regMobile.setError(null);

        } else {
            mobile = false;
            regMobile.setError("Enter Mobile Number!");

        }

        if (validation.isValidPassword(Password)) {

            password = true;
            regPassword.setError(null);

        } else {
            password = false;
            regPassword.setError("Atleast  Minimum 6 Characters");

        }

        if (!validation.isEmpty(Password)) {

            password = true;
            regPassword.setError(null);

        } else {
            password = false;
            regPassword.setError("Enter Password");

        }

        if (validation.isValidPassword(ConfirmPassword)){
            confirmpassword = true;
            regConfirmPassword.setError(null);
        }
        else{
            confirmpassword = false;
            regConfirmPassword.setError("Atleast  Minimum 6 Characters");
        }
        if (!validation.isEmpty(ConfirmPassword)) {

            confirmpassword = true;
            regConfirmPassword.setError(null);

        } else {
            confirmpassword = false;
            regConfirmPassword.setError("Enter Confirm Password");

        }


if(name && lastname && email && mobile && password && confirmpassword){
    Signup();

}
else{
    Toast.makeText(RegisterActivity.this, "Check and fill all the details", Toast.LENGTH_LONG).show();
}





}

public void Signup(){


    Util.showProgress(activity);
    commonRequest= new CommonRequest();
    commonRequest.firstname = regName.getText().toString();
    commonRequest.lastname = regLastName.getText().toString();
    commonRequest.email = regEmail.getText().toString();
    commonRequest.mob = regMobile.getText().toString();
    commonRequest.pass = regPassword.getText().toString();
    commonRequest.cpass = regConfirmPassword.getText().toString();
    commonRequest.device_id = "788f369d0bb35967";

    Log.e(TAG,"post_values==>:"+"firstname:==>"+commonRequest.firstname+"\nlastname:==>"+commonRequest.lastname+"\nmobile==>"+commonRequest.mob+"\nemail==>"+commonRequest.email+"\npassword==>"+commonRequest.pass+"\nconfirmpassword==>"+commonRequest.cpass+
            "\ndevice_id==>"+commonRequest.device_id);

    Call <ListDetails> register = apiInterface.Register_post(commonRequest);
    register.enqueue(new Callback<ListDetails>() {
        @Override
        public void onResponse(Call<ListDetails> call, Response<ListDetails> response) {

            Log.e(TAG,"post_values==>:"+"firstname:==>"+commonRequest.firstname+"\nlastname:==>"+commonRequest.lastname+"\nmobile==>"+commonRequest.mob+"\nemail==>"+commonRequest.email+"\npassword==>"+commonRequest.pass+"\nconfirmpassword==>"+commonRequest.cpass+
                    "\ndevice_id==>"+commonRequest.device_id);


            Util.hideProgress();

            try {
                if(response.isSuccessful()) {

                    if (response.body().status.equalsIgnoreCase("success")) {

                        Log.e(TAG, "response==>" + response.body().status);
                        Toast.makeText(RegisterActivity.this, response.body().msg, Toast.LENGTH_LONG).show();


                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);


                    } else if (response.body().status.equalsIgnoreCase("error")) {

                        Log.e(TAG, "response==>" + response.body().status);
                        Toast.makeText(RegisterActivity.this, response.body().msg, Toast.LENGTH_LONG).show();

                    }
                }else {
                    Toast.makeText(RegisterActivity.this, "Response null!", Toast.LENGTH_LONG).show();
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }


        }

        @Override
        public void onFailure(Call<ListDetails> call, Throwable t) {

            Toast.makeText(RegisterActivity.this, "Oops! something Went wrong. Please try again..!!!", Toast.LENGTH_LONG).show();
            Util.hideProgress();
        }
    });



}

    public void AlertDialog(final String status, String message) {
        activity = RegisterActivity.this;

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // builder.setTitle(status);
        builder.setMessage(message);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                if (status.equalsIgnoreCase("success")) {
                    dialog.dismiss();

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);

                    finish();
                } else if (status.equalsIgnoreCase("error")) {
                    dialog.dismiss();
                }


            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }




    }

    //This method is used to validate input given by user


