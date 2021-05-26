package com.example.loca_market.ui.seller.profile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.loca_market.data.models.User;
import com.example.loca_market.data.repositores.UserRepository;

public class ProfileViewModel extends ViewModel {
    private final UserRepository userRepository;
    public MutableLiveData<User> userMutableLiveData= new MutableLiveData<>();
    public MutableLiveData<Boolean>  sbConfirmation = new  MutableLiveData<>();

    public ProfileViewModel() {
        userRepository = UserRepository.getInstance();
        userMutableLiveData.setValue(new User());
        sbConfirmation.setValue(false);

    }

    public void init() {
        getUserDetails();
        if (userMutableLiveData != null) {
            return;

        }

    }

    public MutableLiveData<User> getUserDetails() {

        userMutableLiveData = userRepository.getUserByUid();

        return userMutableLiveData;
    }

    public boolean updateUser(User user){

        boolean  result =  userRepository.updateUser(user) ;
        if (result){
            showConfirmationSb();
        }
        return result ;
    }

    public void showConfirmationSb(){
        sbConfirmation.setValue(true);
    }
}
