package com.example.fingerprint;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.aflak.libraries.callback.FingerprintCallback;
import me.aflak.libraries.callback.FingerprintDialogCallback;
import me.aflak.libraries.callback.FingerprintDialogSecureCallback;
import me.aflak.libraries.callback.FingerprintSecureCallback;
import me.aflak.libraries.callback.PasswordCallback;
import me.aflak.libraries.dialog.DialogAnimation;
import me.aflak.libraries.dialog.FingerprintDialog;
import me.aflak.libraries.dialog.PasswordDialog;
import me.aflak.libraries.utils.FingerprintToken;
import me.aflak.libraries.view.Fingerprint;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, FingerprintDialogSecureCallback, PasswordCallback {
    private GlobalVariable globalVariable;
    Button btnLoginSubmit;
    EditText txtUserPass;
    EditText txtUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        globalVariable = ((GlobalVariable) getApplicationContext());
        btnLoginSubmit=findViewById(R.id.btnLoginSubmit);
        txtUserName=findViewById(R.id.txtUserName);
        txtUserPass=findViewById(R.id.txtUserPass);

        findViewById(R.id.button).setOnClickListener(this);

        btnLoginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String user= txtUserName.getText().toString();
               String pass= txtUserPass.getText().toString();
              if (user.equals("admin") && pass.equals("admin")){
                  Intent intent=new Intent(MainActivity.this,SettingActivity.class);
                  startActivity(intent);
              }else {
                  new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                          .setTitleText("Oops...")
                          .setContentText("UserName and Password Not match")
                          .show();
              }

            }
        });

    }
    @Override
    public void onClick(View view) {
        Toast toast = Toast.makeText(getApplicationContext(), ""+globalVariable.getFingerPrintEnable(), Toast.LENGTH_SHORT);
        toast.show();
        String fingerPrintCheck=globalVariable.getFingerPrintEnable();
        if ("No".equals(fingerPrintCheck)){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Set Login with FingerPrint Enable After login With Username Password")
                    .show();
        }else {
            if(FingerprintDialog.isAvailable(this)) {
                FingerprintDialog.initialize(this)
                        .title(R.string.fingerprint_title)
                        .message(R.string.fingerprint_message)
                        .callback(this, "KeyName1")
                        .show();
            }else {
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Your System  Do not Have Any FingerPrint Registered")
                        .show();
                startActivityForResult(new Intent(Settings.ACTION_SECURITY_SETTINGS), 0);

            }
        }

    }

    @Override
    public void onAuthenticationSucceeded() {
        Intent intent=new Intent(MainActivity.this,SettingActivity.class);
        startActivity(intent);
        globalVariable.setFingerPrintEnable("Yes");
        // Logic when fingerprint is recognized
        Toast toast = Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onAuthenticationCancel() {
        // Logic when user canceled operation
    }

    @Override
    public void onNewFingerprintEnrolled(FingerprintToken token) {
        // /!\ A new fingerprint was added /!\
        //
        // Prompt a password to verify identity, then :
        // if (password.correct()) {
        //      token.continueAuthentication();
        // }
        //
        // OR
        //
        // Use PasswordDialog to simplify the process

        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText("New FingerPrint Change has been Detected So FingerPrint login has been Disabled")
                .show();
        globalVariable.setLock("Yes");

        /*PasswordDialog.initialize(this, token)
                .title(R.string.password_title)
                .message(R.string.password_message)
                .callback(this)
                .passwordType(PasswordDialog.PASSWORD_TYPE_TEXT)
                .show();*/
    }

    @Override
    public boolean onPasswordCheck(String password) {
        return password.equals("password");
    }

    @Override
    public void onPasswordCancel() {
        // Logic when user canceled operation
    }

    @Override
    public void onPasswordSucceeded() {
        // Logic when password is correct (new keys have been generated)
    }
}