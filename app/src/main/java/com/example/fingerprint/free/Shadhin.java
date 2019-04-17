package com.example.fingerprint.free;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.fingerprint.MainActivity;
import com.example.fingerprint.R;

public class Shadhin extends AppCompatActivity {
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadhin);
        btnSubmit=findViewById(R.id.btnSubmit);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    Intent intent=new Intent(Shadhin.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Shadhin.this, "Lower Version of android", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
