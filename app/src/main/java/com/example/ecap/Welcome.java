package com.example.ecap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity {

    Button customerlogin,driverlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        customerlogin = (Button) findViewById(R.id.btn_customer);
        driverlogin = (Button) findViewById(R.id.btn_driver);

        customerlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customerIntent = new Intent(Welcome.this,CustomerLoginRegisterActivity.class);
                startActivity(customerIntent);
            }
        });
        driverlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent driverIntent = new Intent(Welcome.this,DriverLoginRegisterActivity.class);
                startActivity(driverIntent);
            }
        });
    }
}