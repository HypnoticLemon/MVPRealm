package com.example.realmwithmvpjava.DB;

import android.util.Log;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class DbMigration implements RealmMigration {

    private final static String TAG = DbMigration.class.getSimpleName();

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        final RealmSchema schema = realm.getSchema();
        Log.d(TAG, "oldVersion:" + oldVersion + " newVersion: " + newVersion);
        if (oldVersion == 0) {
            final RealmObjectSchema postDataSchema = schema.get("PostDataModel");
            postDataSchema.addField("name", String.class);
        }


        if(oldVersion == 1){
            final RealmObjectSchema postDataSchema = schema.get("PostDataModel");
            postDataSchema.addField("rollNo", int.class);
            postDataSchema.removeField("name");
        }


    }
}
