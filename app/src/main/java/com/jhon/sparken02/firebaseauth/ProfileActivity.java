package com.jhon.sparken02.firebaseauth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jhon.sparken02.firebaseauth.Model.UserInformation;
import com.jhon.sparken02.firebaseauth.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityProfileBinding mBinding;
    private ProgressDialog progressBarDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_profile);
        databaseReference  = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        mBinding.txtprofileEmail.setText("Welcome" + user.getEmail());
        progressBarDialog = new ProgressDialog(this);
        mBinding.btnLogout.setOnClickListener(this);
        mBinding.btnuplodprofile.setOnClickListener(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.btnLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }else if (id == R.id.btnuplodprofile){
            finish();
            startActivity(new Intent(ProfileActivity.this,UploadPhoto.class));
        }
    }
}
