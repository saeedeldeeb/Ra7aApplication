package com.example.eldeeb.ra7aapplication.Presenter;

import com.example.eldeeb.ra7aapplication.Model.UserLogin;
import com.example.eldeeb.ra7aapplication.View.ILoginView;

public class loginPresenter implements ILoginPresenter {

    ILoginView iLoginView;

    public loginPresenter(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
    }

    @Override
    public void OnLogin(String email, String password) {
        UserLogin userLogin = new UserLogin(email, password);
        boolean isRegisterSuccess = userLogin.isValidate();
        if (isRegisterSuccess)
            iLoginView.OnLoginResult("Register completed");
        else
            iLoginView.OnLoginResult("Error");

    }
}
