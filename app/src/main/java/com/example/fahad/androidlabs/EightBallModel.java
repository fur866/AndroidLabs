package com.example.fahad.androidlabs;

/**
 * Created by Fahad on 30/03/2016.
 */


import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

public class EightBallModel {

    static ArrayList<String> initialResponseArray = new ArrayList<>();
    static ArrayList<String> responseArray = new ArrayList<>();
    static ArrayList<String> responseBackgrounds = new ArrayList<>();
    Context context;

    EightBallModel() {
        this.initialiseResponse();
        this.initialiseBackgrounds();
    }


    EightBallModel(ArrayList <String> extraResponseArray,Context context) {
        this.context = context;
        this.initialiseResponse();

        for(String resp : initialResponseArray){
            responseArray.add(resp);
        }
        for (String value : extraResponseArray) {
            responseArray.add(value);
        }

        this.initialiseBackgrounds();

    }

    private void initialiseResponse() {

        this.initialResponseArray.add(this.context.getResources().getString(R.string.one));
        this.initialResponseArray.add(this.context.getResources().getString(R.string.two));
        this.initialResponseArray.add(this.context.getResources().getString(R.string.three));
        this.initialResponseArray.add(this.context.getResources().getString(R.string.four));
        this.initialResponseArray.add(this.context.getResources().getString(R.string.five));
        this.initialResponseArray.add(this.context.getResources().getString(R.string.six));
    }

    private void initialiseBackgrounds(){

        this.responseBackgrounds.add("circle1");
        this.responseBackgrounds.add("circle2");
        this.responseBackgrounds.add("circle3");
        this.responseBackgrounds.add("circle4");
        this.responseBackgrounds.add("circle5");
        this.responseBackgrounds.add("circle6");
    }

    public String magicResponse(){

        int size = responseArray.size();
        Random rand;
        int n = 0;
        String eightResponse;

        if(size> 0){
            rand = new Random();
            n = rand.nextInt(responseArray.size()-1) + 1;
            eightResponse = String.valueOf(responseArray.get(n));
            return eightResponse;
        }

        return "";

    }

    public String magicBackground(){

        int size = responseBackgrounds.size();
        Random rand;
        int n = 0;
        String backgroundName = "";

        if(size> 0){
            rand = new Random();
            n = rand.nextInt(responseBackgrounds.size()-1) + 1;
            backgroundName = String.valueOf(responseBackgrounds.get(n));
            return backgroundName;
        }

        return backgroundName;
    }

}