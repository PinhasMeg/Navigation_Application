package il.co.expertize.navigationapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import il.co.expertize.navigationapp.MainActivity;
import il.co.expertize.navigationapp.R;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener {

    private static final String TAG = "FirebaseEmailPassword";
    public static SharedPreferences sharedPreferences ;

    private EditText edtEmail;
    private EditText edtPassword;
    public boolean flag = false;
    private static FirebaseAuth mAuth;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_password);

        sharedPreferences = getSharedPreferences("USER",MODE_PRIVATE);

        if(sharedPreferences.contains("Email"))
        {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPassword = (EditText) findViewById(R.id.edt_password);

        findViewById(R.id.btn_email_sign_in).setOnClickListener(this);
        findViewById(R.id.btn_email_create_account).setOnClickListener(this);
        findViewById(R.id.btn_sign_out).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ok", "start");
        findViewById(R.id.email_password_buttons).setVisibility(View.VISIBLE);
        findViewById(R.id.email_password_fields).setVisibility(View.VISIBLE);
        findViewById(R.id.layout_signed_in_buttons).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_email_create_account) {
            createAccount(edtEmail.getText().toString(), edtPassword.getText().toString());
        } else if (i == R.id.btn_email_sign_in) {
            signIn(edtEmail.getText().toString(), edtPassword.getText().toString());
        } else if (i == R.id.btn_sign_out) {
            signOut();
        }
    }

    private void createAccount(String email, String password) {
        Log.e(TAG, "createAccount:" + email);
        if (!validateForm(email, password)) {
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.e(TAG, "createAccount: Success!");
                        sharedPreferences.edit().putString("Email",email).putString("Password",password).apply();
                        FirebaseUser user = mAuth.getCurrentUser();
                        sendEmailVerification(user);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Log.e(TAG, "createAccount: Fail!", task.getException());
                        Toast.makeText(getApplicationContext(), "Create account failed!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void signIn(String email, String password) {
        Log.e(TAG, "signIn:" + email);
        if (!validateForm(email, password)) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e(TAG, "signIn: Success!");
                            sharedPreferences.edit().putString("Email",email).putString("Password",password).apply();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Log.e(TAG, "signIn: Fail!", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public static void signOut() {
        mAuth.signOut();
        sharedPreferences.edit().clear().apply();
    }

    private void sendEmailVerification(@NotNull FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(this, task -> {

                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Verification email sent to " + user.getEmail() + ", please verify it.", Toast.LENGTH_LONG).show();
//                        while (!user.isEmailVerified()){
//                            mAuth.getCurrentUser().reload();
//                            try {
//                                Thread.sleep(1000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        Toast.makeText(LoginActivity.this, "Successfully logged in", Toast.LENGTH_LONG).show();
                    } else {
                        Log.e(TAG, "sendEmailVerification failed!", task.getException());
                        Toast.makeText(getApplicationContext(), "Failed to send verification email.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private boolean validateForm(String email, String password) {

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_LONG).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
