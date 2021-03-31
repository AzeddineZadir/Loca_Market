package com.example.loca_market.ui.userAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loca_market.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    public final String TAG ="ForgotPasswordActivity";
    EditText et_email_reset ;
    FirebaseAuth auth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        auth = FirebaseAuth.getInstance();
        // To apply the default app language instead of explicitly setting it.
        auth.useAppLanguage();
        et_email_reset=(EditText)findViewById(R.id.et_email_reset);

    }

    public void click_b_reset_password(View view) {
        String email = et_email_reset.getText().toString().trim();
        if (email.isEmpty()) {
            et_email_reset.setError("Email is required");
            et_email_reset.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email_reset.setError("Please enter a valid email");
            et_email_reset.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this, "Email sent", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Email sent.");
                            Intent i = new Intent(getApplicationContext(), SellerLoginActivity.class);
                            startActivity(i);
                        }
                    }
                });
    }
}