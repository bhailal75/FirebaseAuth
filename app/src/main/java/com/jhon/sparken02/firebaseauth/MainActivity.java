package com.jhon.sparken02.firebaseauth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jhon.sparken02.firebaseauth.Model.UserInformation;
import com.jhon.sparken02.firebaseauth.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityMainBinding mBinding;

    private ProgressDialog progressBarDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        databaseReference  = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        progressBarDialog = new ProgressDialog(this);
        mBinding.btnRegister.setOnClickListener(this);
        mBinding.txtSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnRegister){
                registerUSer();            
        }
        if (id == R.id.txtSignIn){
            startActivity(new Intent(this,LoginActivity.class));
        }
    }

    private void registerUSer() {
        final String email = mBinding.email.getText().toString().trim();
        final String password  = mBinding.password.getText().toString().trim();

        final String name,phone,address,gender;
        name = mBinding.name.getText().toString().trim();
        phone = mBinding.phoneNo.getText().toString().trim();
        address = mBinding.address.getText().toString().trim();
        gender = mBinding.gender.getText().toString().trim();


        if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Enter full name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Enter phone no", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(address)){
            Toast.makeText(this, "Enter address", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(gender)){
            Toast.makeText(this, "Enter gender", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBarDialog.setMessage("Registering User....");
        progressBarDialog.show();

        //store user authentication
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressBarDialog.dismiss();
                            if (firebaseAuth.getCurrentUser() != null){

                                //store user information in database
                                UserInformation userInformation = new UserInformation(name,email,phone,address,gender);
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                databaseReference.child(user.getUid()).setValue(userInformation);

                                Toast.makeText(MainActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this,UploadPhoto.class));
                            }
                        }
                        else{
                            progressBarDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Could not Register.Please try again", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
}
