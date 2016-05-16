package com.example.fahad.androidlabs;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by Fahad on 16/04/2016.
 */
public class QuestionResponseModel implements Serializable{
    private String question;
    private String answer;
    private String URL;

    public QuestionResponseModel(String q, String a)
    {
        this.question = q;
        this.answer = a;
    }

    public QuestionResponseModel(String q, String a, String url)
    {
        this.question = q;
        this.answer = a;
        this.URL = url;
    }

    public String getQuestion()
    {
        return this.question;
    }

    public String getAnswer()
    {
        return this.answer;
    }

    public String getURL()
    {
        return this.URL;
    }
}
