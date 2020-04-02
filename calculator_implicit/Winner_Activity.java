package com.example.scorecounter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Winner_Activity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    //    private static final int REQUEST_PHONE_CALL = 1;
    private TextView text_result;
    private Button button_newGame, button_sms, button_call, button_openLocation;
    private EditText editText_phoneNumber, editText_openLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_);
        text_result = findViewById(R.id.result);
        editText_phoneNumber = findViewById(R.id.editText_number);
        editText_openLocation = findViewById(R.id.editText_location);

        button_newGame = findViewById(R.id.button_new_game);
        button_newGame.setOnClickListener(this);

        button_call = findViewById(R.id.button_phoneNumber);
        button_call.setOnClickListener(this);

        button_sms = findViewById(R.id.button_sms);
        button_sms.setOnClickListener(this);

        button_openLocation = findViewById(R.id.button_location);
        button_openLocation.setOnClickListener(this);



        result();

    }

    public void result(){
        Intent intent = getIntent();
        String r = intent.getStringExtra("result_key");
        text_result.setText(r);
    }

    public void Main_Acticity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void confirmActivity(int num){
        final int final_number = num;
        Log.d(TAG, "inside confirmActivity");
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("confirm");
        alertDialog.setMessage("Is the Phone Number Correct");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(final_number == 1){
                    phone_Call();
                }
                if(final_number == 0){
                    send_Message();
                }

            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        alertDialog.show();
    }

    public void send_Message(){
        String message = text_result.getText().toString();
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Send the message to: ")
                .setText("Running track game outcome: "+message)
                .startChooser();


    }

    public void phone_Call(){
        String number = editText_phoneNumber.getText().toString();
        Intent intent = new Intent(Intent.ACTION_CALL); // set permission
        intent.setData(Uri.parse("tel:"+number));
        if(intent.resolveActivity(getPackageManager()) != null){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(intent);
            }
//               ActivityCompat.requestPermissions(this, new String[] {
//                       Manifest.permission.CALL_PHONE
//               }, REQUEST_PHONE_CALL);
//            }else{
//                startActivity(intent);
//            }

        }

    }

    public void near_Location(){
        String loc = editText_openLocation.getText().toString();
        Uri adressUri = Uri.parse("geo:41.4671,-71.3112?q="+loc);
        Intent intent = new Intent(Intent.ACTION_VIEW, adressUri);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }

    }



    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.button_new_game:
                Main_Acticity();
                break;

            case R.id.button_phoneNumber:
//                phone_Call();
                confirmActivity(1);

                break;
            case R.id.button_sms:
                confirmActivity(0);
                break;

            case R.id.button_location:
                near_Location();
                break;

        }
    }

}
