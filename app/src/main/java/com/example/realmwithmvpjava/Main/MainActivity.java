package com.example.realmwithmvpjava.Main;

import android.Manifest;
import android.app.Application;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;


import com.example.realmwithmvpjava.BuildConfig;
import com.example.realmwithmvpjava.PostList.PostListFragment;
import com.example.realmwithmvpjava.R;
import com.example.realmwithmvpjava.Utilies.CustomDialog;

public class MainActivity extends AppCompatActivity implements MainView {

    private FrameLayout container;
    private final static int PERMISSION_CODE = 1111;
    private final static int REASK_PERMISIION_CODE = 2222;
    private MainPresenter mainPresenter;
    private Context context;
    private CustomDialog customDialog;
    private JobScheduler mJobScheduler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.container);
        mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        context = this;
        mainPresenter = new MainPresenter(context, this);
        mainPresenter.onResume();
        startScheduler();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Intent startServiceIntent = new Intent(this, JobSchedulerService.class);
        startService(startServiceIntent);
    }

    @Override
    protected void onStop() {
       // mJobScheduler.cancelAll();
     //   stopService(new Intent(this, JobSchedulerService.class));
        super.onStop();
    }

    public void startScheduler() {

        JobScheduler jobScheduler = (JobScheduler) getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);

        ComponentName componentName = new ComponentName(getApplicationContext(), JobSchedulerService.class);

        JobInfo jobInfo = new JobInfo.Builder(1, componentName)
                .setOverrideDeadline(10)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build();


        jobScheduler.schedule(jobInfo);

    }

    @Override
    public void loadFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        PostListFragment postListFragment = new PostListFragment();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, postListFragment, "postListFragment");
        fragmentTransaction.commit();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void permissionCheck() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_NETWORK_STATE) || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_WIFI_STATE) || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.INTERNET)) {

            customDialog = new CustomDialog(context, buttonClickListener);
            customDialog.initDialog();
            customDialog.setTitleText(getResources().getString(R.string.permission_title_text));
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE,
            }, PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean isAllPermissioAllow = true;
        switch (requestCode) {
            case REASK_PERMISIION_CODE: {
                for (int j = 0; j < grantResults.length; j++) {
                    if (grantResults[j] == -1) {
                        isAllPermissioAllow = false;
                    }
                }
                if (isAllPermissioAllow) {

                }
                break;
            }
            case PERMISSION_CODE: {
                isAllPermissioAllow = true;
                for (int i = 0; i < grantResults.length; i++) {
                    Log.d("PERMISSION_CODE", "grantResult" + grantResults[i]);
                    if (grantResults[i] == -1) {
                        isAllPermissioAllow = false;
                    }
                }
                break;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    CustomDialog.ButtonClickListener buttonClickListener = new CustomDialog.ButtonClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onButtonClick() {
            customDialog.dismiss();
            requestPermissions(new String[]{Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.INTERNET}, REASK_PERMISIION_CODE);
        }
    };


}
