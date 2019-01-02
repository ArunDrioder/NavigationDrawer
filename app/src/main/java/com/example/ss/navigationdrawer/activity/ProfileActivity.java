package com.example.ss.navigationdrawer.activity;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ss.navigationdrawer.R;
import com.example.ss.navigationdrawer.Utility;
import com.example.ss.navigationdrawer.Utils.Validation;
import com.example.ss.navigationdrawer.other.CircleTransform;
import com.example.ss.navigationdrawer.request.CommonRequest;
import com.example.ss.navigationdrawer.retrofit.ListDetails;
import com.example.ss.navigationdrawer.retrofit.ProfileDetails;
import com.example.ss.navigationdrawer.retrofit.RetrofitInterface;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    public static final int REQUEST_PERMISSION = 200;
    private  int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;





    Validation validation;
    CommonRequest commonRequest;

    EditText editEmail,editGender,editDateofBirth,editPan,editNomName,editNomRelation;
    ImageView arrow_back,edit_icon, profpic_edit;

    CircleImageView profile_image;



    LinearLayout profilelinear;


    String Email,Gender,DateofBirth,PanNo,NomiineeName,NomRelation;
    boolean email_bool,gender_bool,dateofBirth_bool,panNo_bool,nomineeName_bool,nomRel_bool;




    public static final String TAG = ProfileActivity.class.getSimpleName();

    List<ProfileDetails> profileDetailsList;

    RetrofitInterface apiInterface;

    Activity activity;



    TextView username,email,mobile,refer,gender,dob,pan,adhaar,voterID;

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //checkLocationPermission();
            getPermissionsDetails();
        }











        profpic_edit = (ImageView) findViewById(R.id.profpic_edit);

        profile_image = (CircleImageView) findViewById(R.id.profile_image);

        profpic_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();




            }
        });




        validation = new Validation();


        context = ProfileActivity.this;

        Initialization();

        GetProfiles();


    }


    private void getPermissionsDetails() {
        List<String> permissionsNeeded = new ArrayList<String>();
        final List<String> permissionsList = new ArrayList<String>();
        if (Build.VERSION.SDK_INT >= 23) {
            if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                permissionsNeeded.add("WRITE_EXTERNAL_STORAGE");
            if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
                permissionsNeeded.add("READ_EXTERNAL_STORAGE");

            if (!addPermission(permissionsList, Manifest.permission.RECORD_AUDIO))
                permissionsNeeded.add("RECORD_AUDIO");
            if (!addPermission(permissionsList, Manifest.permission.CAMERA))
                permissionsNeeded.add("CAMERA");
            if (!addPermission(permissionsList, Manifest.permission.CALL_PHONE))
                permissionsNeeded.add("CALL_PHONE");
            if (permissionsList.size() > 0) {
                if (permissionsNeeded.size() > 0) {
                    // Need Rationale
                    String message = "You need to grant access to " + permissionsNeeded.get(0);
                    for (int i = 1; i < permissionsNeeded.size(); i++)
                        message = message + ", " + permissionsNeeded.get(i);
                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                    return;
                }
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                return;
            } else {

             }
        } else { //permission is automatically granted on sdk<23 upon installation

        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }



    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ProfileActivity.this.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }



    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(ProfileActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }



    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }


    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        profile_image.setImageBitmap(thumbnail);
    }


    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        profile_image.setImageBitmap(bm);
    }








    public void Initialization() {
        activity = ProfileActivity.this;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitInterface.serverUrl)
                // .baseUrl("https://192.168.1.2/hostel/public/api/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(RetrofitInterface.class);
        profilelinear = (LinearLayout)findViewById(R.id.profilelinear);

        arrow_back = (ImageView)findViewById(R.id.backarrow);


        username = (TextView) findViewById(R.id.username);
        email = (TextView) findViewById(R.id.email);
        mobile = (TextView) findViewById(R.id.mobile);
        refer = (TextView) findViewById(R.id.refer);
        gender = (TextView) findViewById(R.id.gender);
        dob = (TextView) findViewById(R.id.dob);
        pan = (TextView) findViewById(R.id.pan);
        adhaar = (TextView) findViewById(R.id.adhaar);
        voterID = (TextView) findViewById(R.id.voterID);
        edit_icon = (ImageView)findViewById(R.id.edit_icon);



        edit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(ProfileActivity.this);
            }
        });




        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(goBack);
            }
        });

    }

















    public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.edit_profile_layout);



        editEmail = dialog.findViewById(R.id.editEmail);
        editGender = dialog.findViewById(R.id.editGender);
        editDateofBirth = dialog.findViewById(R.id.editDateofBirth);
        editPan = dialog.findViewById(R.id.editPan);
        editNomName = dialog.findViewById(R.id.editNomName);
        editNomRelation = dialog.findViewById(R.id.editNomRelation);
        editDateofBirth.addTextChangedListener(new TextWatcher() {

            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                    String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    editDateofBirth.setText(current);
                    editDateofBirth.setSelection(sel < current.length() ? sel : current.length());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });












        Button btnok = (Button) dialog.findViewById(R.id.btnok);


        Button btncn = (Button) dialog.findViewById(R.id.btncn);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Validation();

                dialog.dismiss();

            }
        });
        btncn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();



    }

    private void Validation() {

        Email = editEmail.getText().toString().trim();
        Gender = editGender.getText().toString().trim();
        DateofBirth = editDateofBirth.getText().toString().trim();
        PanNo = editPan.getText().toString().trim();
        NomiineeName = editNomName.getText().toString().trim();
        NomRelation = editNomRelation.getText().toString().trim();


        if (!Validation.isEmpty(Email)) {
            email_bool = true;

        } else {
            email_bool = false;
        }

        if (!Validation.isEmpty(Gender)) {
            gender_bool = true;
        } else {
            gender_bool = false;
        }

        if (!Validation.isEmpty(DateofBirth)) {
            dateofBirth_bool = true;
        } else {
            dateofBirth_bool = false;
        }

        if (!Validation.isEmpty(PanNo)) {
            panNo_bool = true;
        } else {
            panNo_bool = false;
        }

        if (!Validation.isEmpty(NomiineeName)) {
            nomineeName_bool = true;
        } else {
            nomineeName_bool = false;
        }

        if (!Validation.isEmpty(NomRelation)) {
            nomRel_bool = true;
        } else {
            nomRel_bool = false;
        }

        if(email_bool && gender_bool && dateofBirth_bool && panNo_bool && nomineeName_bool && nomRel_bool ) {
            updateProfile();
        }
        else{
            Toast.makeText(getApplicationContext(),"Check and Fill all the details",Toast.LENGTH_LONG).show();
        }

    }

    public void updateProfile(){

        commonRequest= new CommonRequest();
        commonRequest.user_id = "1";
        commonRequest.email_id = editEmail.getText().toString();
        commonRequest.gender = editGender.getText().toString();
        commonRequest.dob = editDateofBirth.getText().toString();
        commonRequest.pan_no = editPan.getText().toString();
        commonRequest.nominee_name = editNomName.getText().toString();
        commonRequest.nominee_relation = editNomRelation.getText().toString();

        Log.e(TAG,"post_values==>:"+"email_id:==>"+commonRequest.email_id + "\n gender:==>"+commonRequest.gender + "\n dob==>"+commonRequest.dob + "\n pan_no"+commonRequest.pan_no + "\n nominee_name==>"+ commonRequest.nominee_name +"\n nominee_relation" +commonRequest.nominee_relation);

        Call<ListDetails> updateprofile = apiInterface.profile_update(commonRequest);
        updateprofile.enqueue(new Callback<ListDetails>() {
            @Override
            public void onResponse(Call<ListDetails> call, Response<ListDetails> response) {


                Log.e(TAG,"post_values==>:"+"email_id:==>"+commonRequest.email_id + "\n gender:==>"+commonRequest.gender + "\n dob==>"+commonRequest.dob + "\n pan_no"+commonRequest.pan_no + "\n nominee_name==>"+ commonRequest.nominee_name +"\n nominee_relation" +commonRequest.nominee_relation);

                try {
                    if (response.isSuccessful()){
                        if (response.body().status.equalsIgnoreCase("success")) {
                            Log.e(TAG, "response==>" + response.body().status);
                            Toast.makeText(ProfileActivity.this, response.body().msg, Toast.LENGTH_LONG).show();


                        }else if (response.body().status.equalsIgnoreCase("error")){
                            Log.e(TAG, "response==>" + response.body().status);
                            Toast.makeText(ProfileActivity.this, response.body().msg, Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(ProfileActivity.this, "Response null!", Toast.LENGTH_LONG).show();
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ListDetails> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Oops! something Went wrong. Please try again..!!!", Toast.LENGTH_LONG).show();

            }
        });









    }






    public void GetProfiles() {

        Log.e(TAG, "GetProfiles==>");
        CommonRequest commonRequest = new CommonRequest();
        commonRequest.user_id = "1";
        commonRequest.mobile = "8124082944";
        final Call<ListDetails> profiledetails = apiInterface.user_details(commonRequest);
        profiledetails.enqueue(new Callback<ListDetails>() {
            @Override
            public void onResponse(Call<ListDetails> call, Response<ListDetails> response) {
                if (response.body() != null) {
                    if (response.body().status.equalsIgnoreCase("success")) {
                        if (!response.body().user_details.isEmpty()) {
                            if (response.body().user_details != null) {
                                profileDetailsList = response.body().user_details;

                                username.setText(profileDetailsList.get(0).name);
                                email.setText(profileDetailsList.get(0).email_id);
                                mobile.setText(profileDetailsList.get(0).mobile);
                                refer.setText(profileDetailsList.get(0).refered_by);
                                gender.setText(profileDetailsList.get(0).gender);
                                dob.setText(profileDetailsList.get(0).dob);
                                pan.setText(profileDetailsList.get(0).pan_no);
                                adhaar.setText(profileDetailsList.get(0).aadhar_no);
                                voterID.setText(profileDetailsList.get(0).voter_id);

                                String image = profileDetailsList.get(0).image;

                                if(!image.equals("")) {

                                    Log.e(TAG, "profile_image==>" + image);


                                    Picasso.with(activity).load(image).into(profile_image);
                                    //profile_image.setScaleType(CircleImageView.ScaleType.FIT_XY);

                                }else {
                                    Toast.makeText(ProfileActivity.this,"profile_image is Empty",Toast.LENGTH_SHORT).show();
                                }

                            }else{
                                Toast.makeText(ProfileActivity.this,"No Response",Toast.LENGTH_SHORT).show();

                            }

                        }else{
                            Toast.makeText(ProfileActivity.this,"Server taking long time",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(ProfileActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ProfileActivity.this,"Fetching failed",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ListDetails> call, Throwable t) {
                Toast.makeText(ProfileActivity.this,"Fetching failed",Toast.LENGTH_SHORT).show();


            }
        });
    }
}


