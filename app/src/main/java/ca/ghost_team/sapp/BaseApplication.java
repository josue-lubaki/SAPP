package ca.ghost_team.sapp;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import androidx.room.Room;

import ca.ghost_team.sapp.database.sappDatabase;


public class BaseApplication extends MultiDexApplication {

    public static String NAME_DB = "sappDatabase";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}