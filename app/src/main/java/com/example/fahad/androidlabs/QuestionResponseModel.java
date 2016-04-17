package com.example.fahad.androidlabs;

import java.io.Serializable;

/**
 * Created by Fahad on 16/04/2016.
 */
public class QuestionResponseModel implements Serializable{
    private String question;
    private String answer;

    public QuestionResponseModel(String q, String a)
    {
        this.question = q;
        this.answer = a;
    }

    public String getQuestion()
    {
        return this.question;
    }

    public String getAnswer()
    {
        return this.answer;
    }
}
