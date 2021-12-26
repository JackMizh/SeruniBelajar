
package com.serunibelajar.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "101";
    private ActionBar actionBar;
    private NestedScrollView nested_scroll_view;

    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;
    private HorizontalScrollView horizontalScrollView;

    private Fragment fragment;
    private Fragment fragmenthome;
    private SessionManager sessionManager;
    RelativeLayout toolbar;
    LinearLayout content;
    NestedScrollView scrolllayout;
    ImageView home_icon, report_icon, notification_icon, account_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.setSystemBarColor(this, R.color.colortop);
        setContentView(R.layout.activity_main);

        content = findViewById(R.id.list_category_item_dashboard);
        scrolllayout = findViewById(R.id.nested_scroll_view);
        //Set Theme
        Tools.setSystemBarColor(this, R.color.colortop);

//        PushNotifications.start(getApplicationContext(), "f8d62a3f-a3c0-4661-8ede-83f1bbec836e");
//        PushNotifications.addDeviceInterest("hello");

        FirebaseMessaging.getInstance().subscribeToTopic("all");

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin(this);


        home_icon = findViewById(R.id.home_icon);
        report_icon = findViewById(R.id.report_icon);
        notification_icon = findViewById(R.id.notification_icon);
        account_icon = findViewById(R.id.account_icon);

        initComponent();
        createNotificationChannel();
        getToken();
        subscribeToTopic();

        LinearLayout lhome = findViewById(R.id.layout_home);
        lhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toHome();
            }
        });

        home_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toHome();
            }
        });

        LinearLayout lreport = findViewById(R.id.layout_report);
        lreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toReport();
            }
        });

        report_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toReport();
            }
        });

        LinearLayout lnotification = findViewById(R.id.layout_notification);
        lnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toNotification();
            }
        });

        notification_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toNotification();
            }
        });

        LinearLayout laccount = findViewById(R.id.layout_account);
        laccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toAccount();
            }
        });

        account_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toAccount();
            }
        });
    }

    private void getToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        Log.e("Token", instanceIdResult.getToken());
                    }
                });
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "firebaseNotifChannel";
            String description = "This is the channel to recieve firebase notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void subscribeToTopic(){
        FirebaseMessaging.getInstance().subscribeToTopic("notification")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }

    private void initComponent() {
        nested_scroll_view = (NestedScrollView) findViewById(R.id.nested_scroll_view);
        if (sessionManager.getUserDetail().get("PREVILLAGE").equals("Guru")){
            fragment = new HomeguruFragment();
        }
        else if(sessionManager.getUserDetail().get("PREVILLAGE").equals("Orang Tua")){
            fragment = new HomeorangtuaFragment();
        }
        else if(sessionManager.getUserDetail().get("PREVILLAGE").equals("Siswa")){
            fragment = new HomesiswaFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment,
                fragment.getClass().getSimpleName()).commit();

        home_icon.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void toHome() {
        ViewAnimation.fadeOutIn(nested_scroll_view);
        if (sessionManager.getUserDetail().get("PREVILLAGE").equals("Guru")){
            fragment = new HomeguruFragment();
        }
        else if(sessionManager.getUserDetail().get("PREVILLAGE").equals("Orang Tua")){
            fragment = new HomeorangtuaFragment();
        }
        else if(sessionManager.getUserDetail().get("PREVILLAGE").equals("Siswa")){
            fragment = new HomesiswaFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment,
                fragment.getClass().getSimpleName()).commit();

        home_icon.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));
        report_icon.setBackgroundTintList(this.getResources().getColorStateList(R.color.text));
        notification_icon.setBackgroundTintList(this.getResources().getColorStateList(R.color.text));
        account_icon.setBackgroundTintList(this.getResources().getColorStateList(R.color.text));
    }

    public void toReport() {
        ViewAnimation.fadeOutIn(nested_scroll_view);
        fragment = new PengumumanFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment,
                fragment.getClass().getSimpleName()).commit();

        home_icon.setBackgroundTintList(this.getResources().getColorStateList(R.color.text));
        report_icon.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));
        notification_icon.setBackgroundTintList(this.getResources().getColorStateList(R.color.text));
        account_icon.setBackgroundTintList(this.getResources().getColorStateList(R.color.text));
    }

    public void toNotification(){
        ViewAnimation.fadeOutIn(nested_scroll_view);
        fragment = new NotificationFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment,
                fragment.getClass().getSimpleName()).commit();

        home_icon.setBackgroundTintList(this.getResources().getColorStateList(R.color.text));
        report_icon.setBackgroundTintList(this.getResources().getColorStateList(R.color.text));
        notification_icon.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));
        account_icon.setBackgroundTintList(this.getResources().getColorStateList(R.color.text));
    }

    public void toAccount(){
        ViewAnimation.fadeOutIn(nested_scroll_view);
        fragment = new AccountFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment,
                fragment.getClass().getSimpleName()).commit();

        home_icon.setBackgroundTintList(this.getResources().getColorStateList(R.color.text));
        report_icon.setBackgroundTintList(this.getResources().getColorStateList(R.color.text));
        notification_icon.setBackgroundTintList(this.getResources().getColorStateList(R.color.text));
        account_icon.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorPrimary));
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}