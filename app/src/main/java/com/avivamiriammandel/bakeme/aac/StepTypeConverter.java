package com.avivamiriammandel.bakeme.aac;

import android.arch.persistence.room.TypeConverter;

import com.avivamiriammandel.bakeme.model.Step;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class StepTypeConverter {

    static Gson gson = new Gson();

    @TypeConverter
    public static Step stringToStep(String step) {
        if (step == null) {
            return null;
        }
        Type listType = new TypeToken<Step>() {}.getType();
        return gson.fromJson(step, listType);
    }

    @TypeConverter
    public static String stepToString(Step step){
        return gson.toJson(step);
    }
}
