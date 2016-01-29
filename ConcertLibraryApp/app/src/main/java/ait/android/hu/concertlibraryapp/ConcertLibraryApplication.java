package ait.android.hu.concertlibraryapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;

import ait.android.hu.concertlibraryapp.data.ConcertEntry;

/**
 * Created by peter on 2015. 11. 09..
 */
public class ConcertLibraryApplication extends Application {

    public static final String APP_ID =
            "xBD0AXCQaDiMAZdytzqulUgT9Unt9GXRdxlJsbhI";
    public static final String CLIENT_ID =
            "XEyNr5wc0E7hWk3zelbPZVjMEp0HOwIvrZD7YIda";

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(ConcertEntry.class);

        Parse.initialize(this, APP_ID,
                CLIENT_ID);

        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParsePush.subscribeInBackground("ConcertLibraryApp");
    }
}
