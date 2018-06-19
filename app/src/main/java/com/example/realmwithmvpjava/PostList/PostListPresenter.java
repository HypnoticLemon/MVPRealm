package com.example.realmwithmvpjava.PostList;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.realmwithmvpjava.DB.RealmHelper;
import com.example.realmwithmvpjava.Main.MainActivity;
import com.example.realmwithmvpjava.Main.MyJobService;

import java.lang.ref.WeakReference;
import java.util.List;

import static com.example.realmwithmvpjava.Main.MyJobService.MESSENGER_INTENT_KEY;

public class PostListPresenter implements MVPListPresenter, LoadItemsInteractor.OnFinishedListener {

    private ListFragmentView listFragmentView;
    private LoadItemsInteractor loadItemsInteractor;
    private Activity context;
    private final static String TAG = PostListPresenter.class.getSimpleName();
    public static final int MSG_UNCOLOR_START = 0;
    public static final int MSG_UNCOLOR_STOP = 1;
    public static final int MSG_COLOR_START = 2;
    public static final int MSG_COLOR_STOP = 3;
    public static final int MY_BACKGROUND_JOB = 0;
    private Handler mHandler;

    public PostListPresenter(final Activity context, ListFragmentView listFragmentView, LoadItemsInteractor loadItemsInteractor) {
        this.listFragmentView = listFragmentView;
        this.loadItemsInteractor = loadItemsInteractor;
        this.context = context;
        setHandler();

    }

    private void setHandler() {
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Toast.makeText(context,
                        "JobService task running", Toast.LENGTH_SHORT)
                        .show();
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        if (listFragmentView != null) {
            listFragmentView.showProgress();
        }
        loadItemsInteractor.loadItems(this);
    }

    @Override
    public void onStartService() {
        /*Intent startServiceIntent = new Intent(context, MyJobService.class);
        Messenger messengerIncoming = new Messenger(mHandler);
        startServiceIntent.putExtra(MESSENGER_INTENT_KEY, messengerIncoming);
        context.startService(startServiceIntent);*/

        JobScheduler js = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo job = new JobInfo.Builder(
                MY_BACKGROUND_JOB,
                new ComponentName(context, MyJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setRequiresCharging(true)
                .build();
        js.schedule(job);

    }

    @Override
    public void onStopService() {
        context.stopService(new Intent(context, MyJobService.class));
    }

    @Override
    public void onItemClicked(int position) {
        if (listFragmentView != null) {
            listFragmentView.showMessage("Position:" + position + " Click");
        }
    }

    @Override
    public void onDestroy() {
        listFragmentView = null;
    }

    @Override
    public void onFinished(Boolean isInternetAvailable) {
        Log.d(TAG, "onFinished: Post List Presenter");
        if (listFragmentView != null) {
            listFragmentView.setItems(RealmHelper.with(context).getAllDatas());
            if (isInternetAvailable) {
                listFragmentView.showMessage("Data Load from API");
            } else {
                listFragmentView.showMessage("Data Load from DataBase");
            }
            listFragmentView.hideProgress();
        }
    }


    private static class IncomingMessageHandler extends Handler {

        // Prevent possible leaks with a weak reference.
        private WeakReference<Context> mActivity;

        IncomingMessageHandler(Context activity) {
            super(/* default looper */);
            this.mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            //MainActivity mainActivity = mActivity.get();
         /*   if (mainActivity == null) {
                // Activity is no longer available, exit.
                return;
            }*/

            Message m;
            switch (msg.what) {
                /*
                 * Receives callback from the service when a job has landed
                 * on the app. Turns on indicator and sends a message to turn it off after
                 * a second.
                 */
                case MSG_COLOR_START:
                    // Start received, turn on the indicator and show text.
                    //showStartView.setBackgroundColor(getColor(R.color.start_received));

                    updateParamsTextView(msg.obj, "started");

                    // Send message to turn it off after a second.
                    m = Message.obtain(this, MSG_UNCOLOR_START);
                    sendMessageDelayed(m, 1000L);
                    break;
                /*
                 * Receives callback from the service when a job that previously landed on the
                 * app must stop executing. Turns on indicator and sends a message to turn it
                 * off after two seconds.
                 */
                case MSG_COLOR_STOP:
                    // Stop received, turn on the indicator and show text.
                    //  showStopView.setBackgroundColor(getColor(R.color.stop_received));
                    updateParamsTextView(msg.obj, "stopped");

                    // Send message to turn it off after a second.
                    m = obtainMessage(MSG_UNCOLOR_STOP);
                    sendMessageDelayed(m, 2000L);
                    break;
                case MSG_UNCOLOR_START:
                    //showStartView.setBackgroundColor(getColor(R.color.none_received));
                    updateParamsTextView(null, "");
                    break;
                case MSG_UNCOLOR_STOP:
                    //showStopView.setBackgroundColor(getColor(R.color.none_received));
                    updateParamsTextView(null, "");
                    break;
            }
        }

        private void updateParamsTextView(@Nullable Object jobId, String action) {
            //TextView paramsTextView = (TextView) mActivity.get().findViewById(R.id.task_params);
            if (jobId == null) {
                Log.i(TAG, "updateParamsTextView: " + action);
                return;
            }
            String jobIdText = String.valueOf(jobId);
            Log.i(TAG, String.format("Job ID %s %s", jobIdText, action));
        }

        private int getColor(@ColorRes int color) {
            return mActivity.get().getResources().getColor(color);
        }
    }
}
