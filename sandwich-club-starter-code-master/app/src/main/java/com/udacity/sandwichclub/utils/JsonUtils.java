package com.udacity.sandwichclub.utils;

import java.util.*;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

public class JsonUtils {

    private final static String unknown = "Unknown information";

    public static Sandwich parseSandwichJson(String json) {
        /**
         * this.mainName = mainName;
         *         this.alsoKnownAs = alsoKnownAs;
         *         this.placeOfOrigin = placeOfOrigin;
         *         this.description = description;
         *         this.image = image;
         *         this.ingredients = ingredients;
         */
        char[] jsonArray = json.toCharArray();
        Log.v("json from sandwitch", json);
        String mainName = parseString(jsonArray, findIndex(json, "mainName"), "mainName");
        //Log.v("mainName", mainName);
        String placeOfOrigin = parseString(jsonArray, findIndex(json, "placeOfOrigin"), "placeOfOrigin");
        //Log.v("placeOfOrigin", placeOfOrigin);
        List<String> alsoKnownAs = parseStringList(jsonArray, json, findIndex(json, "alsoKnownAs"), "alsoKnownAs");
        //for(String s : alsoKnownAs)
        //    Log.v("alsoKnownAs", s);
        String description = parseString(jsonArray, findIndex(json, "description"), "description");
        //Log.v("description", description);
        String image = parseString(jsonArray, findIndex(json, "image"), "image");
        //Log.v("image", image);
        List<String> ingredients = parseStringList(jsonArray, json, findIndex(json, "ingredients"), "ingredients");
        /**
        for(String s : ingredients)
            Log.v("ingredients", s);
        **/
        Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        return sandwich;
    }
    private static String parseString( char[] jsonArray ,int index, String target){
        // miss information
        if(index==-1)
            return unknown;
        StringBuilder content = new StringBuilder();
        int ptr = index + target.length() + 3;
        while((jsonArray[ptr] != '"') || ((jsonArray[ptr] == '"' && jsonArray[ptr-1] == '\\'))){
            if(jsonArray[ptr]!='\\')
                content.append(jsonArray[ptr]);
            ptr++;
        }
        if(content.length()==0)
            return unknown;
        else
            return content.toString();
    }

    private static List<String> parseStringList( char[] jsonArray, String json, int index, String target){

        List<String> result = new ArrayList<>();
        int ptr = index + target.length()+4;
        // miss information
        if(index==-1 || jsonArray[ptr-1]==']'){
            result.add(unknown);
            return result;
        }
        int start = ptr;
        while( ptr<jsonArray.length && jsonArray[ptr]!=']'){
            while( jsonArray[ptr]!='"')
                ptr++;
            String cur = json.substring(start, ptr);
            result.add(cur);
            while(jsonArray[ptr]=='"' || jsonArray[ptr]==',')
                ptr++;
            start = ptr;
        }

        return result;
    }

    private static int findIndex(String json, String target){
        int index = 0;
        while(json.indexOf(target ,index)!=-1){
            int possibleIndex = json.indexOf(target, index);
            if((possibleIndex>=1 && json.charAt(possibleIndex-1)=='"') && (possibleIndex+target.length()<json.length() && json.charAt(possibleIndex+target.length())=='"'))
                return possibleIndex;
            index = possibleIndex+1;
        }
        return -1;
    }
}
