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

import java.util.function.LongConsumer;

public class CustomerLoginRegisterActivity extends AppCompatActivity {

    private Button customerLoginButton;
    private Button customerRegisterButton;
    private TextView customerRegistrationLink;
    private TextView customerStatus;
    private EditText customeremail;
    private EditText customerpassword;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_register);
        customerLoginButton = (Button) findViewById(R.id.btn_cuslogin);
        customerRegisterButton = (Button) findViewById(R.id.btn_cusregister);
        customerRegistrationLink = (TextView) findViewById(R.id.customer_register_link);
        customerStatus = (TextView) findViewById(R.id.customer_status);
        customeremail = (EditText) findViewById(R.id.email_customer);
        customerpassword = (EditText) findViewById(R.id.password_customer);

        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        customerRegisterButton.setVisibility(View.INVISIBLE);
        customerRegisterButton.setEnabled(false);

        customerRegistrationLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerLoginButton.setVisibility(v.INVISIBLE);
                customerRegistrationLink.setVisibility(v.INVISIBLE);
                customerRegisterButton.setVisibility(v.VISIBLE);
                customerRegisterButton.setEnabled(true);
                customerStatus.setText("Customer Register");
            }
        });

customerRegisterButton.setOnClickListener(new View.OnClickListener() {
    @Override
        public void onClick(View v) {
            String email = customeremail.getText().toString();
            String password = customerpassword.getText().toString();
            RegisterCustomer(email,password);
         }
      });

customerLoginButton.setOnClickListener(new View.OnClickListener() {
    @Override
            public void onClick(View v) {
                String email = customeremail.getText().toString();
                String password = customerpassword.getText().toString();
                loginCustomer(email,password);
            }
        });
    }

    private void loginCustomer(String email, String password) {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(CustomerLoginRegisterActivity.this,"Email Required...",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(CustomerLoginRegisterActivity.this,"Password Required...",Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Customer Login");
            loadingBar.setMessage("Please Wait.. While we login you..");
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(CustomerLoginRegisterActivity.this, "Customer Login Successful...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent cuustomerIntent = new Intent(CustomerLoginRegisterActivity.this,CustomerMapActivity.class);
                                startActivity(cuustomerIntent);
                            }else{
                                Toast.makeText(CustomerLoginRegisterActivity.this, "Customer Login Failed...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }

    private void RegisterCustomer(String email, String password) {

        if(TextUtils.isEmpty(email)){
            Toast.makeText(CustomerLoginRegisterActivity.this,"Email Required...",Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(CustomerLoginRegisterActivity.this,"Password Required...",Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Customer Registration");
            loadingBar.setMessage("Please Wait.. While we register you..");
            loadingBar.show();
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(CustomerLoginRegisterActivity.this, "Customer Registration Successful...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }else{
                                Toast.makeText(CustomerLoginRegisterActivity.this, "Customer Registration Failed...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }
}