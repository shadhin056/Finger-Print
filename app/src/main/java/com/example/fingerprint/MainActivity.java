package com.example.fingerprint;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(FingerprintDialog.isAvailable(this)) {
            FingerprintDialog.initialize(this)
                    .title(R.string.fingerprint_title)
                    .message(R.string.fingerprint_message)
                    .callback(this, "KeyName1")
                    .show();
        }else {
           // startActivity(new Intent(Settings.ACTION_FINGERPRINT_ENROLL));
            startActivityForResult(new Intent(Settings.ACTION_SECURITY_SETTINGS), 0);
        }
    }

    @Override
    public void onAuthenticationSucceeded() {
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
   /*     FingerprintDialog.initialize(this)
                .title(R.string.fingerprint_title)
                .message(R.string.fingerprint_message)
                .enterAnimation(DialogAnimation.Enter.RIGHT)
                .exitAnimation(DialogAnimation.Exit.RIGHT)
                .circleScanningColor(R.color.colorAccent)
                .callback(this)
                .show();*/
        PasswordDialog.initialize(this, token)
                .title(R.string.password_title)
                .message(R.string.password_message)
                .callback(this)
                .passwordType(PasswordDialog.PASSWORD_TYPE_TEXT)
                .show();
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