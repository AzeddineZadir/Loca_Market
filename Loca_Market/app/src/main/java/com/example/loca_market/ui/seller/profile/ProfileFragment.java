package com.example.loca_market.ui.seller.profile;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.loca_market.R;
import com.example.loca_market.data.models.User;
import com.example.loca_market.databinding.FragmentProfilBinding;
import com.example.loca_market.ui.seller.ManageFragmentDirections;
import com.example.loca_market.ui.seller.profile.ProfileFragmentDirections;
import com.example.loca_market.ui.seller.profile.ProfileViewModel;
import com.example.loca_market.ui.userAuth.LoginActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    Button b_logout, b_update_user,b_update_pwd;
    FirebaseAuth mAuth;
    FirebaseFirestore fdb;
    FirebaseUser user;
    String username, email;
    public static final String USER_SHARED_PREFS = "userSharedPrefs";
    public static final String EMAIL = "email";
    public static final String PWD = "pwd";
    public static final String ROLE = "role";
    private ProfileViewModel profileViewModel;
    TextInputLayout tf_username, tf_email, tf_role, tf_phone_num;
    FragmentProfilBinding binding;
    User usertoUpdate;

    public ProfileFragment() {
        super(R.layout.fragment_profil);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*View view = inflater.inflate(R.layout.fragment_profil, container, false);*/
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profil, container, false);
        // get the bindede view
        View view = binding.getRoot();
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.init();

        // data binding
        binding.setProfileViewModel(profileViewModel);
        binding.setLifecycleOwner(this);
        b_logout = (Button) view.findViewById(R.id.b_logout);
        b_update_user = (Button) view.findViewById(R.id.b_update_user);
        b_update_pwd = (Button) view.findViewById(R.id.b_update_pwd);


        mAuth = FirebaseAuth.getInstance();
        usertoUpdate = new User();


        tf_username = (TextInputLayout) view.findViewById(R.id.tf_username);
        tf_email = (TextInputLayout) view.findViewById(R.id.tf_email);
        tf_role = (TextInputLayout) view.findViewById(R.id.tf_role);
        tf_phone_num = (TextInputLayout) view.findViewById(R.id.tf_phone_num);


        b_update_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileViewModel.updateUser(getUserToUpdate());
            }
        });


        b_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // se décconecter
                mAuth.signOut();
                wipeUserPref();
                Toast.makeText(getActivity(), "you just signed out ", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent1);


            }
        });

        b_update_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = ProfileFragmentDirections.actionProfilFragmentToUpdatePasswordFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        observUser();
        observeSbconfirmation();
        return view;
    }


    private void observUser() {
        profileViewModel.getUserDetails().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(User user) {
                usertoUpdate = user;
                tf_username.getEditText().setText(user.getUsername());
                tf_email.getEditText().setText(user.getEmail());
                tf_role.getEditText().setText(user.getRole());
                tf_phone_num.getEditText().setText(user.getPhone());
            }
        });

    }

    private void observeSbconfirmation() {
        profileViewModel.sbConfirmation.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                if (result == true) {
                    Snackbar snackbar = Snackbar.make(binding.getRoot(), "profile   mis a jour ", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });
    }

    public void wipeUserPref() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(USER_SHARED_PREFS, MODE_PRIVATE);
        sharedPreferences.edit().putString(EMAIL, "").apply();
        sharedPreferences.edit().putString(PWD, "").apply();
        sharedPreferences.edit().putString(ROLE, "").apply();
    }


    public User getUserToUpdate() {
        User u = new User();
        u = usertoUpdate;
        u.setEmail(tf_email.getEditText().getText().toString());
        u.setUsername(tf_username.getEditText().getText().toString());
        u.setPhone(tf_phone_num.getEditText().getText().toString());

        return u;
    }

}
