package com.example.realmwithmvpjava.Utilies;

import android.app.Application;

import com.example.realmwithmvpjava.DB.DbMigration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmMvpAPP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("javaMvpDemo.realm")
                .schemaVersion(2)
                .migration(new DbMigration())
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
