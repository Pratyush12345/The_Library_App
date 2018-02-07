package com.example.eng_hardik.svnit_library;

/**
 * Created by Eng_Hardik on 27-01-2018.
 */

import android.app.Application;


import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;


public class FireBase extends Application{

    @Override
    public void onCreate(){
        super.onCreate();

        if(!FirebaseApp.getApps(this).isEmpty()){

            FirebaseDatabase.getInstance().setPersistenceEnabled( true );
        }
    }
}
