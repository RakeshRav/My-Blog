package com.example.galaxy.myblog;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

/**
 * Created by galaxy on 07/10/15.
 */
public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "JuT1kiqPnJFVameL47Eb7U0izByB8KnAjZ7fs8qD", "e8PffZ22wQMD57h9jfyCiHiIkt10xPlbjjD4fCsy");

        ParseUser.enableAutomaticUser();
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParseACL defaultAcl = new ParseACL();

        ParseACL.setDefaultACL(defaultAcl,true);
    }
}
