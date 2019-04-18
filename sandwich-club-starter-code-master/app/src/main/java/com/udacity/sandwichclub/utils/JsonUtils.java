package com.udacity.sandwichclub.utils;

import java.util.*;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

public class JsonUtils {
    //private final int indexName;
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
        String mainName = parseString(jsonArray, json.indexOf("mainName"), "mainName");

        String placeOfOrigin = parseString(jsonArray, json.indexOf("placeOfOrigin"), "placeOfOrigin");
        List<String> alsoKnownAs = parseStringList(jsonArray, json, json.indexOf("alsoKnownAs"), "alsoKnownAs");
        String description = parseString(jsonArray, json.indexOf("description"), "description");
        String image = parseString(jsonArray, json.indexOf("image"), "image");
        List<String> ingredients = parseStringList(jsonArray, json, json.indexOf("ingredients"), "ingredients");
        for(String s : alsoKnownAs)
            Log.v("alsoKnownAs", s);
        Log.v("mainName", mainName);
        Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        return sandwich;
    }
    private static String parseString( char[] jsonArray ,int index, String target){
        // miss information
        if(index==-1)
            return "";
        StringBuilder content = new StringBuilder();
        int start = index + target.length() + 3;
        while(jsonArray[start]!='"' ){
            content.append(jsonArray[start]);
            start++;
        }
        return content.toString();
    }

    private static List<String> parseStringList( char[] jsonArray, String json, int index, String target){
        // miss information
        if(index==-1)
            return null;
        List<String> result = new ArrayList<>();
        int end = index + target.length() + 4;
        int start = end;
        while(jsonArray[end]!=']'){
            if(jsonArray[end]==','){
                result.add(json.substring(start, end-1));
                start = end+2;
            }
            end++;
        }
        if(end>start)
            result.add(json.substring(start, end-1));
        return result;
    }
}
