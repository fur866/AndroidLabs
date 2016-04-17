package com.example.fahad.androidlabs;

import android.animation.Animator;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    LinearLayout background;

    EditText userInput;
    ImageView circleImage;
    TextView displayText;
    Button userButton;
    ArrayList<QuestionResponseModel> list;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    // animate the eightball model
    private void moveEightBall(EightBallModel fortune) {

//        android.view.animation.Animation myanimation;
//        myanimation = new android.view.animation.AlphaAnimation(0.0f, 1.0f);
//        myanimation.setDuration(400);
//        myanimation.setStartOffset(0);

        String magicSrc = "";
        final String randResp = fortune.magicResponse();
        magicSrc = fortune.magicBackground();
        list.add(new QuestionResponseModel(this.userInput.getText().toString(),randResp));
        int resID = getResources().getIdentifier(magicSrc, "drawable", getPackageName());
        //this.displayText.startAnimation(myanimation);
        this.displayText.animate().setDuration(2).alpha(0f).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                displayText.setText(randResp);
                displayText.animate().setDuration(2).alpha(1f);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        circleImage.setBackgroundResource(resID);

    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            FileOutputStream fos = new FileOutputStream(new File(getFilesDir() + "ObjectInformation"));
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(this.list);
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.load();
    }

    public void load()
    {
        try (
                InputStream file = new FileInputStream(getFilesDir() + "ObjectInformation");
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(buffer);
        ) {
            //deserialize the List
            this.list.clear();
            this.list = (ArrayList<QuestionResponseModel>) input.readObject();
        } catch (ClassNotFoundException ex) {

        } catch (IOException ex) {

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ArrayList<String> newResponses = new ArrayList<>();
        this.list = new ArrayList<QuestionResponseModel>();
        this.load();
        newResponses.add("Perfect and awesome!");
        newResponses.add("Yes");
        newResponses.add("Signs point to yes");

        final EightBallModel fortune;
        fortune = new EightBallModel(newResponses);
        final String randResp = fortune.magicResponse();

        this.background = (LinearLayout) findViewById(R.id.myLayout);

        this.userInput = (EditText) findViewById(R.id.editText);
        this.circleImage = (ImageView) findViewById(R.id.imageView);
        this.displayText = (TextView) findViewById(R.id.textView);
        this.userButton = (Button) findViewById(R.id.button);

        // set on click event for the button
        this.userButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//              list.add(new QuestionResponseModel(userInput.getText().toString(), randResp));
//              moveEightBall(fortune);
                Intent intent = new Intent(getApplicationContext(),HistoryActivity.class);
                intent.putExtra("QuestionResponseArray",list);
                startActivity(intent);
            }

        });


        // set the onchange event for the text field
        // this.userInput.onEditorAction

        this.userInput.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:

                            moveEightBall(fortune);

                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.load();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.fahad.androidlabs/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        this.load();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.fahad.androidlabs/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
