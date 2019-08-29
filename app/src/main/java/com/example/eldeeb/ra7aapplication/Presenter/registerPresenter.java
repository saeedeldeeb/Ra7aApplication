package com.example.eldeeb.ra7aapplication.Presenter;


import com.example.eldeeb.ra7aapplication.Model.UserRegister;
import com.example.eldeeb.ra7aapplication.View.IRegiserView;

public class registerPresenter implements IRegisterPresenter {
    IRegiserView iregiserView;

    public registerPresenter(IRegiserView iregiserView) {
        this.iregiserView = iregiserView;
    }

    @Override
    public void onRegister(String fullName, String email, String password) {
        UserRegister userRegister = new UserRegister(fullName, email, password);
        boolean isRegisterSuccess = userRegister.isValidate();

        if (isRegisterSuccess)
            iregiserView.onRegiserResult("Register completed");
        else
            iregiserView.onRegiserResult("Error");

    }
}
