package com.jhon.sparken02.firebaseauth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.jhon.sparken02.firebaseauth.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private ActivityLoginBinding mBinding;

    private ProgressDialog progressBarDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();

//        if (firebaseAuth.getCurrentUser() != null){
//            finish();
//            startActivity(new Intent(this,ProfileActivity.class));
//        }

        progressBarDialog = new ProgressDialog(this);
        mBinding.btnLogin.setOnClickListener(this);
        mBinding.txtSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.btnLogin){
            loginUSer();
        }
        if (id == R.id.txtSignUp){
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    private void loginUSer() {

        String email = mBinding.email.getText().toString().trim();
        String password  = mBinding.password.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBarDialog.setMessage("Login please wait....");
        progressBarDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressBarDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            if (firebaseAuth.getCurrentUser() != null){
                                finish();
                                startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
                            }
                        }
                        else{
                            progressBarDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Could not Login.Please try again", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


}
