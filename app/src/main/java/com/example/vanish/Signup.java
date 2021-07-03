package com.example.vanish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vanish.Model.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    ImageView leftCircle,rightCircle,backArrow;
    ConstraintLayout layout;
    Animation leftanimation,rightanimation,layoutfade;
    Button registerButton;
    EditText etUsername,etPassword,etEmail,etPhoneNo,etFullName;
    String Username,Password,Email,PhoneNo,FullName,userid;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;
    Model model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //findViewById
        leftCircle = findViewById(R.id.leftCircle);
        rightCircle = findViewById(R.id.rightCircle);
        layout = findViewById(R.id.innerlayout);
        registerButton = findViewById(R.id.registerButton);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etFullName = findViewById(R.id.etFullName);
        etPhoneNo = findViewById(R.id.etPhoneNo);
        backArrow = findViewById(R.id.backArrow);
        //Animation
        leftanimation = AnimationUtils.loadAnimation(this,R.anim.leftcircle);
        rightanimation = AnimationUtils.loadAnimation(this,R.anim.rightcircle);
        layoutfade = AnimationUtils.loadAnimation(this,R.anim.layoutalpha);
        rightCircle.setAnimation(rightanimation);
        leftCircle.setAnimation(leftanimation);
        layout.setAnimation(layoutfade);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this,Login.class));
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Username = etUsername.getText().toString().trim();
                Password = etPassword.getText().toString().trim();
                Email = etEmail.getText().toString().trim();
                FullName = etFullName.getText().toString().trim();
                PhoneNo = etPhoneNo.getText().toString().trim();
                firebaseAuth = FirebaseAuth.getInstance();
                if(TextUtils.isEmpty(Username)|| TextUtils.isEmpty(Email)|| TextUtils.isEmpty(Password)|| TextUtils.isEmpty(FullName)|| TextUtils.isEmpty(PhoneNo))
                {
                    Toast.makeText(Signup.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                }
                else if(Password.length()< 6)
                {
                    Toast.makeText(Signup.this, "Password must be more than 6 characters", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.createUserWithEmailAndPassword(Email,Password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                                        model = new Model(userid,Username,Password,Email,PhoneNo,FullName);
                                        reference.setValue(model);
                                        Toast.makeText(Signup.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Signup.this,Login.class));
                                        finish();
                                    }else{
                                        Toast.makeText(Signup.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Signup.this,Login.class));
        finish();
    }
}