package com.example.vanish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    ImageView leftCircle,rightCircle;
    ConstraintLayout layout;
    Animation leftanimation,rightanimation,layoutfade;
    Button logIn;
    TextView signUp;
    EditText Username,Password;
    CheckBox checkBox;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        leftCircle = findViewById(R.id.leftCircle);
        rightCircle = findViewById(R.id.rightCircle);
        layout = findViewById(R.id.innerlayout);
        logIn = findViewById(R.id.logInButton);
        Username = findViewById(R.id.etUsername);
        Password = findViewById(R.id.etPassword);
        signUp = findViewById(R.id.signUp);
        checkBox = findViewById(R.id.checkBox);

        leftanimation = AnimationUtils.loadAnimation(this,R.anim.leftcircle);
        rightanimation = AnimationUtils.loadAnimation(this,R.anim.rightcircle);
        layoutfade = AnimationUtils.loadAnimation(this,R.anim.layoutalpha);
        rightCircle.setAnimation(rightanimation);
        leftCircle.setAnimation(leftanimation);
        layout.setAnimation(layoutfade);

        sharedPreferences = getSharedPreferences("DATA",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        isLogin = sharedPreferences.getBoolean("IsLoggedIn",false);
        if(isLogin) {
            startActivity(new Intent(Login.this, MainScreen.class));
            finish();
        }

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Signup.class));
                finish();
            }
        });


        FirebaseAuth auth = FirebaseAuth.getInstance();
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = Username.getText().toString().trim();
                String password  =  Password.getText().toString().trim();
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
                {
                    Toast.makeText(Login.this, "Both Email and password field must be filled", Toast.LENGTH_SHORT).show();
                }else
                {
                    auth.signInWithEmailAndPassword(username,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        if (checkBox.isChecked()){
                                            editor.putBoolean("IsLoggedIn",true);
                                            editor.apply();
                                            Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Login.this,MainScreen.class));
                                            finish();
                                        }else{
                                            Toast.makeText(Login.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Login.this,MainScreen.class));
                                            finish();
                                        }
                                    }else
                                    {
                                        Toast.makeText(Login.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
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
        finish();
    }
}