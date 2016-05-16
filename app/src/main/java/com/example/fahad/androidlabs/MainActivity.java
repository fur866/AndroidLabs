package com.example.fahad.androidlabs;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class MainActivity extends Activity {

    LinearLayout background;

    EditText userInput;
    ImageView circleImage;
    TextView displayText;
    Button userButton;
    ArrayList<QuestionResponseModel> list;
    TextToSpeech tTS;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private void moveEightBall(EightBallModel fortune) {


        String magicSrc = "";
        final String randResp = fortune.magicResponse();
        magicSrc = fortune.magicBackground();
        list.add(new QuestionResponseModel(this.userInput.getText().toString(),randResp));
        int resID = getResources().getIdentifier(magicSrc, "drawable", getPackageName());
        this.tTS.speak(randResp,TextToSpeech.QUEUE_FLUSH,null);

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

       this.tTS = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
            }
        }
        );


        ArrayList<String> newResponses = new ArrayList<>();
        this.list = new ArrayList<QuestionResponseModel>();
        this.load();
        newResponses.add(getResources().getString(R.string.seven));
        newResponses.add(getResources().getString(R.string.eight));
        newResponses.add(getResources().getString(R.string.nine));

        final EightBallModel fortune;
        fortune = new EightBallModel(newResponses,this);
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
                //Intent intent = new Intent(getApplicationContext(),HistoryActivity.class);
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                DownloadTask downloadTask = new DownloadTask();
                //Firstly check the network connection:
                if (networkInfo != null && networkInfo.isConnected()) {
                    downloadTask.execute("http://li859-75.members.linode.com/retrieveAllEntries.php");
                } else {

                    Log.e("Error", "Networking not available!");
                }
//                intent.putExtra("QuestionResponseArray",downloadTask.getList());//downloadTask.getList());
//                startActivity(intent);
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

    private class DownloadTask extends AsyncTask<String, Void, String> {

        private ArrayList<QuestionResponseModel> listQuestions;
        @Override
        protected String doInBackground(String... urls) {

            try {
                return performRequest(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.v("tag", result);
            try {
                JSONArray array = new JSONArray(result);
              //  list.clear();
                this.listQuestions = new ArrayList<QuestionResponseModel>();
                for(int i = 0; i < array.length(); i++)
                {
                    JSONObject object = array.getJSONObject(i);
                    Log.d("Here p: ",object.getString("question"));
                    this.listQuestions.add(new QuestionResponseModel(object.getString("question"),object.getString("answer"),object.getString("imageURL")));
                }

                Intent intent = new Intent(getApplicationContext(),HistoryActivity.class);
                intent.putExtra("QuestionResponseArray",this.listQuestions);//downloadTask.getList());
                startActivity(intent);
            }
            catch(JSONException e)
            {

            }
        }

        private String performRequest(String myURL) throws IOException {

            InputStream is = null;
            int length = 100;

            try{
                URL url = new URL(myURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(10000); //10 seconds
                connection.setConnectTimeout(10000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect(); //Kick it off
                int response = connection.getResponseCode();

                Log.v("Response Cocde: ", new Integer(response).toString());

                is = connection.getInputStream();
                String stringResult = parseStream(is, length);
                return stringResult;

            } finally { //Close the input stream when we're done:
                if (is != null){
                    is.close();
                }
            }

        }

        public String parseStream(InputStream stream, int length) throws IOException, UnsupportedEncodingException {

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            StringBuilder result = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        }

        public ArrayList<QuestionResponseModel> getList()
        {
            //Log.d("Here pp",String.valueOf(this.list.size()));
            return this.listQuestions;
        }
    }
}
