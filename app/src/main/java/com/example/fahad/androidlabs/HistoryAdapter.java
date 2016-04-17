package com.example.fahad.androidlabs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
            first = (TextView) convertView.findViewById(android.R.id.text1);
            second = (TextView) convertView.findViewById(android.R.id.text2);
            convertView.setTag(new ViewHolder(first, second));
        } else {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            first = viewHolder.q;
            second = viewHolder.a;
        }

        QuestionResponseModel questionResponseModel = getItem(position);
        first.setText(questionResponseModel.getQuestion());
        second.setText(questionResponseModel.getAnswer());
        return convertView;
    }

    private static class ViewHolder
    {
        public final TextView q;
        public final TextView a;

        public ViewHolder(TextView q, TextView a)
        {
            this.q = q;
            this.a = a;
        }
    }
}
