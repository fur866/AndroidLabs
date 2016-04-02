package com.example.fahad.androidlabs;

/**
 * Created by Fahad on 30/03/2016.
 */


import java.util.ArrayList;
import java.util.Random;

public class EightBallModel {

    static ArrayList<String> initialResponseArray = new ArrayList<>();
    static ArrayList<String> responseArray = new ArrayList<>();
    static ArrayList<String> responseBackgrounds = new ArrayList<>();

    EightBallModel() {
        this.initialiseResponse();
        this.initialiseBackgrounds();
    }


    EightBallModel(ArrayList <String> extraResponseArray) {
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

        this.initialResponseArray.add("Maybe if you try a little harder");
        this.initialResponseArray.add("Reply hazy, come back tomorrow");
        this.initialResponseArray.add("It is decidely so");
        this.initialResponseArray.add("My sources say no");
        this.initialResponseArray.add("Ask again later");
        this.initialResponseArray.add("Don't count on it");
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