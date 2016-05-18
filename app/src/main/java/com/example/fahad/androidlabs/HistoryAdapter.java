package com.example.fahad.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Fahad on 16/04/2016.
 */
public class HistoryAdapter extends ArrayAdapter<QuestionResponseModel>{

    private Context context;
    private ArrayList<QuestionResponseModel> list;
    public HistoryAdapter(Context context, ArrayList<QuestionResponseModel> list)
    {
        super(context,-1,list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.acitvity_history,parent,false);
        TextView first;
        TextView second;
        ImageView view;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.question_response_model_row, parent, false);
            first = (TextView) convertView.findViewById(R.id.text1);
            second = (TextView) convertView.findViewById(R.id.text2);
            view = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(new ViewHolder(first, second,view));
        } else {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            first = viewHolder.q;
            second = viewHolder.a;
            view = viewHolder.imageView;
        }

        QuestionResponseModel questionResponseModel = getItem(position);
        first.setText(questionResponseModel.getQuestion());
        second.setText(questionResponseModel.getAnswer());

        DownloadImage downloadImage = new DownloadImage(view);
        downloadImage.execute(questionResponseModel.getURL());

        return convertView;
    }

    private static class ViewHolder
    {
        public final TextView q;
        public final TextView a;
        public final ImageView imageView;

        public ViewHolder(TextView q, TextView a, ImageView imageView)
        {
            this.q = q;
            this.a = a;
            this.imageView = imageView;
        }
    }

    public class DownloadImage extends AsyncTask<String,Void,Bitmap>
    {
        private ImageView imageView;

        public  DownloadImage(ImageView view)
        {
            this.imageView = view;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
                return performRequest(urls[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            this.imageView.setImageBitmap(bitmap);
        }

        public Bitmap performRequest(String url)
        {
            try {
                URL urlObject = new URL(url);
                return BitmapFactory.decodeStream(urlObject.openConnection().getInputStream());
            }
            catch (MalformedURLException e)
            {

            }
            catch (IOException e)
            {

            }
            return null;
        }

    }

//    public static void LoadImageFromWebOperations(final ImageView view, String url) {
//
//        try {
//            final URL urlObject = new URL(url);
//            Thread thread = new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//                    try{
//                        view.setImageBitmap(BitmapFactory.decodeStream(urlObject.openConnection().getInputStream()));
//                    }
//                    catch (Exception e)
//                    {
//                    }
//
//                }
//            });
//
//            thread.start();
//            thread.join();
//        }
//        catch(MalformedURLException e)
//        {
//        }
//        catch (InterruptedException e)
//        {
//
//        }
//    }
}
