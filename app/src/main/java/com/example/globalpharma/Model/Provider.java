package com.example.globalpharma.Model;

import com.firebase.ui.auth.AuthUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Provider {
    public List<AuthUI.IdpConfig> providers;

    public Provider (){
        providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
             new AuthUI.IdpConfig.PhoneBuilder().build()
        );
    }
}
