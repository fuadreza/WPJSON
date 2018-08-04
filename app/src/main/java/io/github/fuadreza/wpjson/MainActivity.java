package io.github.fuadreza.wpjson;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.github.fuadreza.wpjson.data.DataManager;
import io.github.fuadreza.wpjson.data.SharedPrefsHelper;

public class MainActivity extends Application {

    DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper(getApplicationContext());
        dataManager = new DataManager(sharedPrefsHelper);
    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
