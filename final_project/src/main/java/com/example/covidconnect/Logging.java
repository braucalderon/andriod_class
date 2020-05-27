package com.example.covidconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class Logging extends AppCompatActivity {

    public static final String TAG ="LoggingActivity";
    private NotificationManager mNotifyManager;

    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final String ACTION_UPDATED_NOTIFICATION = "com.example.covidconnect";
    private static final int NOTIFICATION_ID = 0;

    private Button buttonNotif;
    private Button mNotificationButton;
    private Button mCancelNotificationButton;

    private CheckBox myChills,myFever, myTasteOrSmell, mySoreThroat, myHeadache, myMusclePain, myShaking, myDiarrhea,myCough, myBreath;
    private static final int ERROR_DIALOG_REQUEST = 9001;

    private CheckBox myBluish;
    private CheckBox myConfusion;
    private CheckBox myPersistentPain;
    private CheckBox myTroubleBreathing;
    private EditText myEditText;
    private NotificationReceiver mReceiver = new NotificationReceiver();





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logging);


        buttonNotif = findViewById(R.id.createNotification);

//        Notification buttons
        mNotificationButton = findViewById(R.id.createNotification);
        mCancelNotificationButton = findViewById(R.id.cancelNotification);

        myChills = findViewById(R.id.chills);
        myFever = findViewById(R.id.fever);
        myTasteOrSmell = findViewById(R.id.tasteOrSmell);
        mySoreThroat = findViewById(R.id.soreThroat);
        myHeadache =findViewById(R.id.headache);
        myMusclePain = findViewById(R.id.musclePain);
        myShaking = findViewById(R.id.repeatedShaking);
        myDiarrhea = findViewById(R.id.Diarrhea);
        myCough = findViewById(R.id.cough);
        myBreath = findViewById(R.id.breath);
        myTasteOrSmell = findViewById(R.id.tasteOrSmell);
        myBluish = findViewById(R.id.bluishLips);
        myConfusion = findViewById(R.id.confusion);
        myPersistentPain = findViewById(R.id.persistentPain);
        myTroubleBreathing = findViewById(R.id.troubleBreathing);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity();
            }
        });

//        register the broadcast receiver
        registerReceiver(mReceiver, new IntentFilter(ACTION_UPDATED_NOTIFICATION));

        isServiceOK();
        setButtonBotificationState(true,false);




    }// end onCreate
//--------------------------------------------------------------------
// Notification Buttons
    public void clickedButton(View view) {
        createNotificationChannel();
        sendNotification();
        setButtonBotificationState(false,true);


    }

    public void cancelButton(View view) {
        mNotifyManager.cancel(NOTIFICATION_ID);
        setButtonBotificationState(true,false);
    }

    public void setButtonBotificationState(Boolean isNotifyEnabled, Boolean isCancelEnabled){
        mCancelNotificationButton.setEnabled(isCancelEnabled);
        mNotificationButton.setEnabled(isNotifyEnabled);

    }

    private void mainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
//    ---------------------------------------------------------
//    Notifications

    protected void sendNotification() {
        Intent updateIntent = new Intent(ACTION_UPDATED_NOTIFICATION);
//        pending intent is sent and used only once
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(
                this, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notifyBuilder.addAction(R.drawable.ic_alert, "Update Notification", updatePendingIntent);
        getNotificationBuilder();


    }

    protected void updateNotification(){
        Bitmap androidImage = BitmapFactory
                .decodeResource(getResources(), R.drawable.ic_alert);

//        Notification builder object
        NotificationCompat.Builder notityBuilder = getNotificationBuilder();
        notityBuilder.setStyle(new NotificationCompat.BigPictureStyle()
            .bigPicture(androidImage)
            .setBigContentTitle("Notification Updated"));

        mNotifyManager.notify(NOTIFICATION_ID, notityBuilder.build());

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private NotificationCompat.Builder getNotificationBuilder(){
        Intent notificationIntent = new Intent(this, Logging.class);
//      communicate with another app to execute some predefined code at some point in the future
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID,
                notificationIntent, FLAG_UPDATE_CURRENT);



        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("You have been notified")
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_alert);

        return notifyBuilder;
    }

    public void createNotificationChannel(){
        mNotifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID, notificationBuilder.build());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    public class NotificationReceiver extends BroadcastReceiver{

        public NotificationReceiver(){

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            sendNotification();
        }
    }

//    --------------------------------------------------------------------
//    Symptoms selection

    public void selectSympton(View view)
    {
        int checkBox  = view.getId();

        switch (checkBox)
        {
            case R.id.fever:
                if(myFever.isChecked() && myChills.isChecked() || myFever.isChecked() && myMusclePain.isChecked() ||
                        myFever.isChecked() && myCough.isChecked() || myFever.isChecked() && mySoreThroat.isChecked() ||
                        myFever.isChecked() && myBreath.isChecked() || myFever.isChecked() && myTasteOrSmell.isChecked() ||
                        myFever.isChecked() && myHeadache.isChecked() || myFever.isChecked() && myDiarrhea.isChecked() ||
                        myFever.isChecked() && myShaking.isChecked()){
                    toastMessage("Please consult your medical provider for these symptoms");
                }
                break;

            case R.id.chills:
                if(myChills.isChecked() && myFever.isChecked() || myChills.isChecked() && myMusclePain.isChecked() ||
                        myChills.isChecked() && myCough.isChecked() || myChills.isChecked() && mySoreThroat.isChecked() ||
                        myChills.isChecked() && myBreath.isChecked() || myChills.isChecked() && myTasteOrSmell.isChecked() ||
                        myChills.isChecked() && myHeadache.isChecked() || myChills.isChecked() && myDiarrhea.isChecked() ||
                        myChills.isChecked() && myShaking.isChecked()) {
                    toastMessage("Please consult your medical provider for these symptoms");
                }
                break;

            case R.id.repeatedShaking:
                if(myShaking.isChecked() && myFever.isChecked() || myShaking.isChecked() && myMusclePain.isChecked() ||
                        myShaking.isChecked() && myCough.isChecked() || myShaking.isChecked() && mySoreThroat.isChecked() ||
                        myShaking.isChecked() && myBreath.isChecked() || myShaking.isChecked() && myTasteOrSmell.isChecked() ||
                        myShaking.isChecked() && myHeadache.isChecked() || myShaking.isChecked() && myDiarrhea.isChecked() ||
                        myChills.isChecked() && myShaking.isChecked()) {
                    toastMessage("Please consult your medical provider for these symptoms");
                }
                break;

            case R.id.musclePain:
                if(myMusclePain.isChecked() && myFever.isChecked() || myMusclePain.isChecked() && myChills.isChecked() ||
                        myMusclePain.isChecked() && myCough.isChecked() || myMusclePain.isChecked() && mySoreThroat.isChecked() ||
                        myMusclePain.isChecked() && myBreath.isChecked() || myMusclePain.isChecked() && myTasteOrSmell.isChecked() ||
                        myMusclePain.isChecked() && myHeadache.isChecked() || myMusclePain.isChecked() && myDiarrhea.isChecked() ||
                        myMusclePain.isChecked() && myShaking.isChecked()){
                    toastMessage("Please consult your medical provider for these symptoms");
                }
                break;

            case R.id.headache:
                if(myHeadache.isChecked() && myFever.isChecked() || myHeadache.isChecked() && myChills.isChecked() ||
                        myHeadache.isChecked() && myCough.isChecked() || myHeadache.isChecked() && mySoreThroat.isChecked() ||
                        myHeadache.isChecked() && myBreath.isChecked() || myHeadache.isChecked() && myTasteOrSmell.isChecked() ||
                        myHeadache.isChecked() && myMusclePain.isChecked() || myHeadache.isChecked() && myDiarrhea.isChecked() ||
                        myHeadache.isChecked() && myShaking.isChecked()){
                    toastMessage("Please consult your medical provider for these symptoms");
                }

                break;

            case R.id.soreThroat:
                if(mySoreThroat.isChecked() && myFever.isChecked() || mySoreThroat.isChecked() && myChills.isChecked() ||
                        mySoreThroat.isChecked() && myCough.isChecked() || mySoreThroat.isChecked() && myHeadache.isChecked() ||
                        mySoreThroat.isChecked() && myBreath.isChecked() || mySoreThroat.isChecked() && myTasteOrSmell.isChecked() ||
                        mySoreThroat.isChecked() && myMusclePain.isChecked() || mySoreThroat.isChecked() && myDiarrhea.isChecked() ||
                        mySoreThroat.isChecked() && myShaking.isChecked()){
                    toastMessage("Please consult your medical provider for these symptoms");
                }
                break;

            case R.id.tasteOrSmell:
                if(myTasteOrSmell.isChecked() && myFever.isChecked() || myTasteOrSmell.isChecked() && myChills.isChecked() ||
                        myTasteOrSmell.isChecked() && myCough.isChecked() || myTasteOrSmell.isChecked() && myHeadache.isChecked() ||
                        myTasteOrSmell.isChecked() && myBreath.isChecked() || myTasteOrSmell.isChecked() && mySoreThroat.isChecked() ||
                        myTasteOrSmell.isChecked() && myMusclePain.isChecked() || myTasteOrSmell.isChecked() && myDiarrhea.isChecked() ||
                        myTasteOrSmell.isChecked() && myShaking.isChecked()){
                    toastMessage("Please consult your medical provider for these symptoms");
                }
                break;

            case R.id.Diarrhea:
                if(myDiarrhea.isChecked() && myFever.isChecked() || myDiarrhea.isChecked() && myChills.isChecked() ||
                        myDiarrhea.isChecked() && myCough.isChecked() || myDiarrhea.isChecked() && myHeadache.isChecked() ||
                        myDiarrhea.isChecked() && myBreath.isChecked() || myDiarrhea.isChecked() && mySoreThroat.isChecked() ||
                        myDiarrhea.isChecked() && myMusclePain.isChecked() || myDiarrhea.isChecked() && myTasteOrSmell.isChecked() ||
                        myDiarrhea.isChecked() && myShaking.isChecked()){
                    toastMessage("Please consult your medical provider for these symptoms");
                }
                break;

            case R.id.cough:
                if(myCough.isChecked() && myFever.isChecked() || myCough.isChecked() && myChills.isChecked() ||
                        myCough.isChecked() && myDiarrhea.isChecked() || myCough.isChecked() && myHeadache.isChecked() ||
                        myCough.isChecked() && myBreath.isChecked() || myCough.isChecked() && mySoreThroat.isChecked() ||
                        myCough.isChecked() && myMusclePain.isChecked() || myCough.isChecked() && myTasteOrSmell.isChecked() ||
                        myCough.isChecked() && myShaking.isChecked()){
                    toastMessage("Please consult your medical provider for these symptoms");
                }

                break;

            case R.id.breath:
                if(myBreath.isChecked() && myFever.isChecked() || myBreath.isChecked() && myChills.isChecked() ||
                        myBreath.isChecked() && myDiarrhea.isChecked() || myBreath.isChecked() && myHeadache.isChecked() ||
                        myBreath.isChecked() && myCough.isChecked() || myBreath.isChecked() && mySoreThroat.isChecked() ||
                        myBreath.isChecked() && myMusclePain.isChecked() || myBreath.isChecked() && myTasteOrSmell.isChecked() ||
                        myBreath.isChecked() && myShaking.isChecked()){
                    toastMessage("Please consult your medical provider for these symptoms");
                }
                break;
            case R.id.bluishLips:
            case R.id.troubleBreathing:
            case R.id.confusion:
            case R.id.persistentPain:
                toastMessage("This symptom require medical attention immediately");
                break;

        }

    }

    private void toastMessage(String message){
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }


//----------------------------------------------------------------
// search map & phone dial with message
    public void searchMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);


    }
    public void helpCenterButton(View view) {
        confirmActivity();
    }

    public void phoneDial(){
        String number  = "tel:8448639314";
        Intent intent = new Intent(Intent.ACTION_DIAL);  // set permission
        intent.setData(Uri.parse(number));
        startActivity(intent);

    }

    public void confirmActivity(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Confirm");
        alertDialog.setMessage("Connecting to the hotline help center");
        alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                phoneDial();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }
//    ----------------------------------------------------------------

    //    making the mapService error message
    public boolean isServiceOK(){
        Log.d(TAG, "isServiceOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Logging.this);

        if(available == ConnectionResult.SUCCESS){
//            everything is fine and can make a map request
            Log.d(TAG, "isServiceOK: Google Play Services is working");
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
//            an error occurred but we can resolve
            Log.d(TAG,"isServiceOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Logging.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();

        }else{
            Toast.makeText(this, "You can't make a map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }




} // end class
