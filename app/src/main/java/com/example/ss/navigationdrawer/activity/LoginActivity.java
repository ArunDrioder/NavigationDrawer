package com.example.ss.navigationdrawer.activity;

import android.app.Activity;

import android.content.Intent;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;

import android.widget.EditText;

import android.widget.Toast;

import com.example.ss.navigationdrawer.BeanClass.LoginDetails;


import com.example.ss.navigationdrawer.R;
import com.example.ss.navigationdrawer.Utils.Util;
import com.example.ss.navigationdrawer.Utils.Validation;
import com.example.ss.navigationdrawer.request.CommonRequest;
import com.example.ss.navigationdrawer.retrofit.RetrofitInterface;
import com.example.ss.navigationdrawer.retrofit.ListDetails;
import com.example.ss.navigationdrawer.session.Session;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity{



    Activity activity;
    RetrofitInterface apiInterface;
    Validation validation;

    EditText mobilenumber,password;
    Button button;
    Button register;
    String MobileNumber="";
    String Password="";
    boolean mobilenumber_bool=false, password_boolean=false;
    public LoginDetails details;
    Session session;
    public static final String TAG = LoginActivity.class.getSimpleName();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new Session(this);


        Intilization();
        validation = new Validation();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Validation();
                Intent inent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(inent);






            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });




    }

    public void Intilization(){
        activity = LoginActivity.this;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitInterface.serverUrl)
                // .baseUrl("https://192.168.1.2/hostel/public/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(RetrofitInterface.class);

        mobilenumber = (EditText) findViewById(R.id.mobilenumber);
        password = (EditText) findViewById(R.id.password);
        register = (Button)findViewById(R.id.register);



        button = (Button)findViewById(R.id.button);






    }
    public void Validation(){
        MobileNumber = mobilenumber.getText().toString();
        Password = password.getText().toString();

        if (!validation.isEmpty(MobileNumber)) {

            mobilenumber_bool = true;
            mobilenumber.setError(null);

        } else {
            mobilenumber_bool = false;
            mobilenumber.setError("Enter Valid  Mobile Number!");

        }

        if (validation.isValidPassword(Password)) {

            password_boolean = true;
            password.setError(null);

        } else {
            password_boolean = false;
           password.setError("Atleast  Minimum 6 Characters");
        }
        if (mobilenumber_bool && password_boolean) {
            //SignIn();

        } else

        {
            Toast.makeText(LoginActivity.this, "Check and Fill the Details", Toast.LENGTH_SHORT).show();
        }



    }
//    public void SignIn() {
//        Util.showProgress(activity);
//
//        CommonRequest commonRequest = new CommonRequest();
//
//        commonRequest.mobile = "9994877255";
//        commonRequest.password = "P@ssw0rd";
//        Call<ListDetails> Login = apiInterface.Login(commonRequest);
//        Login.enqueue(new Callback<ListDetails>() {
//
//            @Override
//            public void onResponse(Call<ListDetails> call, Response<ListDetails> response) {
//
//                if(response.body()!=null) {
//
//
//                    if (response.body().status.equalsIgnoreCase("success")) {
//                        Log.e(TAG, "response==>" + response.body().status);
//                        if (response.body().user != null) {
//                            details = response.body().user;
//                            Log.e(TAG, "id==>" + details.user_id);
//                            Log.e(TAG, "name==>" + details.name);
//                            session.setUser_id(details.user_id);
//                            session.setEmail(details.email_id);
//                            session.setMobile(details.mobile_no);
//
//
//                            Intent intnt = new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intnt);
//
//
//
//
//                        }
//                        Util.hideProgress();
//
//
//                    }
//
//
//                    else if(response.body().status.equalsIgnoreCase("error")){
//                        Toast.makeText(LoginActivity.this, response.body().msg, Toast.LENGTH_LONG).show();
//                        Util.hideProgress();
//                    }
//                }else {
//                    Toast.makeText(LoginActivity.this, "response null!", Toast.LENGTH_LONG).show();
//
//                }
//
//
//
//            }
//
//
//            @Override
//            public void onFailure(Call<ListDetails> call, Throwable t) {
//
//                Toast.makeText(LoginActivity.this, "Oops! something Went wrong. Please try again..!!!", Toast.LENGTH_LONG).show();
//
//            }
//
//
//        });
//
//
//    }


}