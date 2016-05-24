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
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Fahad on 16/04/2016.
 */
public class HistoryAdapter extends ArrayAdapter<QuestionResponseModel>{

    private Context context;
    private ArrayList<QuestionResponseModel> list;
    private DownloadImage downloadImage;

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
        ImageHolder imageHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.question_response_model_row, parent, false);
            first = (TextView) convertView.findViewById(R.id.text1);
            second = (TextView) convertView.findViewById(R.id.text2);
            view = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(R.id.viewHolder,new ViewHolder(first, second,view));
            imageHolder = new ImageHolder(view);
            convertView.setTag(R.id.imageHolder,imageHolder);
        } else {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag(R.id.viewHolder);
            imageHolder = (ImageHolder) convertView.getTag(R.id.imageHolder);
            first = viewHolder.q;
            second = viewHolder.a;
            view = viewHolder.imageView;
        }

        QuestionResponseModel questionResponseModel = getItem(position);
        first.setText(questionResponseModel.getQuestion());
        second.setText(questionResponseModel.getAnswer());

        imageHolder.position = position;
        this.downloadImage = new DownloadImage(imageHolder,position);
        this.downloadImage.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,questionResponseModel.getURL());

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

    public static class ImageHolder
    {
        public int position;
        public ImageView imageView;

        public ImageHolder(ImageView imageView)
        {
            this.imageView = imageView;
        }
    }

    public class DownloadImage extends AsyncTask<String,Void,RequestCreator>
    {
        private int iPosition;
        private ImageHolder holder;

        public  DownloadImage(ImageHolder imageHolder,int position) {
            this.holder = imageHolder;
            this.iPosition = position;
        }

        @Override
        protected RequestCreator doInBackground(String... urls) {
            return Picasso.with(context)
                    .load(urls[0])
                    .resize(110, 110)
                    .centerCrop();
        }

        @Override
        protected void onPostExecute(RequestCreator creator) {
            if(this.iPosition == holder.position) {
                creator.into(holder.imageView);
            }
        }
    }
}
