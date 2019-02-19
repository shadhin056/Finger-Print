package com.example.fingerprint;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.aflak.libraries.callback.FingerprintDialogSecureCallback;
import me.aflak.libraries.callback.PasswordCallback;
import me.aflak.libraries.dialog.FingerprintDialog;
import me.aflak.libraries.dialog.PasswordDialog;
import me.aflak.libraries.utils.FingerprintToken;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener, FingerprintDialogSecureCallback, PasswordCallback {
    private GlobalVariable globalVariable;
    TextView txtLogin;
    LinearLayout ll1;
    LinearLayout ll2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findViewById(R.id.button1).setOnClickListener(this);


        globalVariable = ((GlobalVariable) getApplicationContext());
        String lock=globalVariable.getLock();
        String showStatus=globalVariable.getFingerPrintEnable();
        findViewById(R.id.btnFingerPrintEnable);
        ll1=findViewById(R.id.ll1);
        ll2=findViewById(R.id.ll2);

        if (lock.equals("Yes")){
            android.support.v7.widget.SwitchCompat  btnFingerPrintEnable = (SwitchCompat) findViewById(R.id.btnFingerPrintEnable);
            btnFingerPrintEnable.setChecked(false);
            globalVariable.setFingerPrintEnable("No");
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.VISIBLE);
        }else if(showStatus.equals("Yes")){
            android.support.v7.widget.SwitchCompat  btnFingerPrintEnable = (SwitchCompat) findViewById(R.id.btnFingerPrintEnable);
            btnFingerPrintEnable.setChecked(true);
            ll1.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.GONE);
        }
        if(globalVariable.getFingerPrintEnable().equals("Yes")){
            android.support.v7.widget.SwitchCompat  btnFingerPrintEnable = (SwitchCompat) findViewById(R.id.btnFingerPrintEnable);
            btnFingerPrintEnable.setChecked(true);
            ll2.setVisibility(View.GONE);
            ll1.setVisibility(View.VISIBLE);
        }else {
            android.support.v7.widget.SwitchCompat  btnFingerPrintEnable = (SwitchCompat) findViewById(R.id.btnFingerPrintEnable);
            btnFingerPrintEnable.setChecked(false);
        }







        txtLogin=findViewById(R.id.txtLogin);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }
    public void itemClicked(View v) {


        /*FingerprintDialog.initialize(this)
                .title(R.string.fingerprint_title)
                .message(R.string.fingerprint_message)
                .callback(this, "KeyName1")
                .show();*/


        if(FingerprintDialog.isAvailable(this)) {

        }else {
            android.support.v7.widget.SwitchCompat  btnFingerPrintEnable = (SwitchCompat) findViewById(R.id.btnFingerPrintEnable);
            btnFingerPrintEnable.setChecked(false);
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Your System  Do not Have Any FingerPrint Registered")
                    .show();
            startActivityForResult(new Intent(Settings.ACTION_SECURITY_SETTINGS), 0);

        }


        if(globalVariable.getLock().equals("Yes")){

        }
        //code to check if this checkbox is checked!
        android.support.v7.widget.SwitchCompat checkBox = (SwitchCompat) v;
        if(checkBox.isChecked()){
            FingerprintDialog.initialize(this)
                    .title(R.string.fingerprint_title)
                    .message(R.string.fingerprint_message)
                    .callback(this, "KeyName1")
                    .show();
           // globalVariable.setFingerPrintEnable("Yes");
            Toast toast = Toast.makeText(getApplicationContext(), ""+globalVariable.getFingerPrintEnable(), Toast.LENGTH_SHORT);
            toast.show();
        }else {
            globalVariable.setFingerPrintEnable("No");
            Toast toast = Toast.makeText(getApplicationContext(), ""+globalVariable.getFingerPrintEnable(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }



    @Override
    public void onClick(View view) {
        if(FingerprintDialog.isAvailable(this)) {
            FingerprintDialog.initialize(this)
                    .title(R.string.fingerprint_title)
                    .message(R.string.fingerprint_message)
                    .callback(this, "KeyName1")
                    .show();
        }
    }
    @Override
    public void onAuthenticationSucceeded() {
        globalVariable.setFingerPrintEnable("Yes");
        android.support.v7.widget.SwitchCompat  btnFingerPrintEnable = (SwitchCompat) findViewById(R.id.btnFingerPrintEnable);
        btnFingerPrintEnable.setChecked(true);
    }

    @Override
    public void onAuthenticationCancel() {
        globalVariable.setFingerPrintEnable("No");
        android.support.v7.widget.SwitchCompat  btnFingerPrintEnable = (SwitchCompat) findViewById(R.id.btnFingerPrintEnable);
        btnFingerPrintEnable.setChecked(false);
    }

    @Override
    public void onNewFingerprintEnrolled(FingerprintToken token) {
        PasswordDialog.initialize(this, token)
                .title(R.string.password_title)
                .message(R.string.password_message)
                .callback(this)
                .passwordType(PasswordDialog.PASSWORD_TYPE_TEXT)
                .show();
    }

    @Override
    public void onPasswordSucceeded() {

    }

    @Override
    public boolean onPasswordCheck(String password) {
        if(password.equals("admin")){
            globalVariable.setFingerPrintEnable("Yes");
            globalVariable.setLock("No");
            ll1.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.GONE);
            android.support.v7.widget.SwitchCompat  btnFingerPrintEnable = (SwitchCompat) findViewById(R.id.btnFingerPrintEnable);
            btnFingerPrintEnable.setChecked(true);
        }

        return password.equals("admin");
    }

    @Override
    public void onPasswordCancel() {

    }
}
