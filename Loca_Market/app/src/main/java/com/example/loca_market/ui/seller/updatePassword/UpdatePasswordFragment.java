package com.example.loca_market.ui.seller.updatePassword;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.loca_market.R;
import com.example.loca_market.data.repositores.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePasswordFragment extends Fragment {

    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String USERS = "users";
    UserRepository userRepository;
    TextInputLayout tf_old_password, tf_new_password, tf_confirme_password;
    Button b_fupdate_pwd;
    String old_pwd, new_pwd, confirm_pwd;
    private static FirebaseUser currentUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_password, container, false);

        userRepository = UserRepository.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        tf_old_password = view.findViewById(R.id.tf_old_password);
        tf_new_password = view.findViewById(R.id.tf_new_password);
        tf_confirme_password = view.findViewById(R.id.tf_confirme_password);
        b_fupdate_pwd = view.findViewById(R.id.b_fupdate_pwd);

        tf_old_password.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                tf_old_password.setError(null);
                return false;
            }
        });

        tf_new_password.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                tf_new_password.setError(null);
                return false;
            }
        });

        tf_confirme_password.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                tf_confirme_password.setError(null);
                return false;
            }
        });


        b_fupdate_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old_pwd = tf_old_password.getEditText().getText().toString();
                new_pwd = tf_new_password.getEditText().getText().toString();
                confirm_pwd = tf_confirme_password.getEditText().getText().toString();

                mAuth.signInWithEmailAndPassword(currentUser.getEmail(), old_pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            if (new_pwd.equals(confirm_pwd)) {

                                currentUser.updatePassword(new_pwd)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Snackbar snackbar = Snackbar.make(view, "mot de passe mis a jour ", Snackbar.LENGTH_SHORT);
                                                    snackbar.show();

                                                    getFragmentManager().popBackStack();

                                                }
                                            }
                                        });

                            } else {
                                tf_new_password.setError("mot de passe non correspondant");
                                tf_new_password.requestFocus();
                                tf_confirme_password.setError("mot de passe non correspondant");
                                tf_confirme_password.requestFocus();
                            }

                        }else{

                            tf_old_password.setError("Mot de passe incorrcte");
                            tf_old_password.requestFocus();
                            tf_old_password.setEndIconActivated(true);
                        }
                    }


                });

            }
        });


        return view;
    }

}
