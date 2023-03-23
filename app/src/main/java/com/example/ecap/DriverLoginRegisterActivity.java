package com.example.ecap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DriverLoginRegisterActivity extends AppCompatActivity {

    private Button driverLoginButton;
    private Button driverRegisterButton;
    private TextView driverRegistrationLink;
    private TextView driverStatus;
    private EditText driveremail;
    private EditText driverpassword;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login_register);
        driverLoginButton = (Button) findViewById(R.id.btn_driver_login);
        driverRegisterButton = (Button) findViewById(R.id.btn_driver_register);
        driverRegistrationLink = (TextView)findViewById(R.id.driver_register_link);
        driverStatus = (TextView) findViewById(R.id.driver_status);
        driveremail = (EditText) findViewById(R.id.email_driver);
        driverpassword = (EditText) findViewById(R.id.password_driver);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        driverRegisterButton.setVisibility(View.INVISIBLE);
        driverRegisterButton.setEnabled(false);

        driverRegistrationLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverLoginButton.setVisibility(v.INVISIBLE);
                driverRegistrationLink.setVisibility(v.INVISIBLE);
                driverRegisterButton.setVisibility(v.VISIBLE);
                driverRegisterButton.setEnabled(true);
                driverStatus.setText("Driver Registration");
            }
        });

        driverRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = driveremail.getText().toString();
                String password = driverpassword.getText().toString();
                registerDriver(email,password);
            }
        });

        driverLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = driveremail.getText().toString();
                String password = driverpassword.getText().toString();
                driverLogin(email,password);
            }
        });
    }

    private void driverLogin(String email, String password) {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(DriverLoginRegisterActivity.this,"Email Required...",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(DriverLoginRegisterActivity.this,"Password Required...",Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Driver Loging In");
            loadingBar.setMessage("Please Wait.. While we login you..");
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(DriverLoginRegisterActivity.this,"Login Successful...",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent driverIntent = new Intent(DriverLoginRegisterActivity.this,DriversMapActivity.class);
                                startActivity(driverIntent);
                            }else{
                                Toast.makeText(DriverLoginRegisterActivity.this,"Login Failed...",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }

    private void registerDriver(String email, String password) {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(DriverLoginRegisterActivity.this,"Email Required...",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(DriverLoginRegisterActivity.this,"Password Required...",Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Driver Registration");
            loadingBar.setMessage("Please Wait.. While we register you..");
            loadingBar.show();
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(DriverLoginRegisterActivity.this,"Driver Registration Successful...",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }else{
                                Toast.makeText(DriverLoginRegisterActivity.this,"Driver Registration Failed...",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }
}