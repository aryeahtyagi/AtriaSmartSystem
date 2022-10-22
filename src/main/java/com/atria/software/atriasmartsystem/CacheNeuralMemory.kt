package com.atria.software.atriasmartsystem

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson


object CacheNeuralMemory {

    fun save(context: Context,brain : NeuralNetwork){

        val mPrefs: SharedPreferences = context.applicationContext.getSharedPreferences("Brain",MODE_PRIVATE)
        val prefsEditor = mPrefs.edit();
        val gson =  Gson();
        val json = gson.toJson(brain);
        prefsEditor.putString("brain", json);
        prefsEditor.apply();

    }

     fun fetch(context: Context) : NeuralNetwork?{
        val mPrefs: SharedPreferences = context.applicationContext.getSharedPreferences("Brain",MODE_PRIVATE)

        val gson = Gson()
        val json: String = mPrefs.getString("brain", "")?:""
        if(json== ""){
            return  null;
        }
        val obj: NeuralNetwork = gson.fromJson(json, NeuralNetwork::class.java)
        return obj
    }
}