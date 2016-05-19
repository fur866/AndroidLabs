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

    public class DownloadImage extends AsyncTask<String,Void,Bitmap>
    {
        private int iPosition;
        private ImageHolder holder;

        public  DownloadImage(ImageHolder imageHolder,int position)
        {
            this.holder = imageHolder;
            this.iPosition = position;
        }


        @Override
        protected Bitmap doInBackground(String... urls) {
                return performRequest(urls[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(this.iPosition == holder.position) {
                holder.imageView.setImageBitmap(bitmap);
            }
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
}
