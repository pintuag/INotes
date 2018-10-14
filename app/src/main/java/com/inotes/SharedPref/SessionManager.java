package com.inotes.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vidit on 17/6/17.
 */

public class SessionManager {


    SharedPreferences.Editor editor;
    Context _context;
    private static final String prefs = "inotes";
    SharedPreferences preferences;
    public void setPrefs(Context _context, String key, String value)
    {


        editor = _context.getSharedPreferences(prefs, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();


    }
    public void setPrefs(Context _context, String key, int value)
    {


        editor = _context.getSharedPreferences(prefs, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.commit();


    }
    public void setPrefs(Context _context, String key, Float value)
    {


        editor = _context.getSharedPreferences(prefs, Context.MODE_PRIVATE).edit();
        editor.putFloat(key, value);
        editor.commit();


    }
    public void setPrefs(Context _context, String key, Double value)
    {
        String val=""+value;
        editor = _context.getSharedPreferences(prefs, Context.MODE_PRIVATE).edit();
        editor.putString(key, val);
        editor.commit();


    }
    public void setPrefs(Context _context, String key, boolean value)
    {
        editor = _context.getSharedPreferences(prefs, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.commit();

    }


    public String getPrefs(Context _context, String key) {


        preferences = _context.getSharedPreferences(prefs, Context.MODE_PRIVATE);//why ?
        return preferences.getString(key,"");

    }
    public int getPrefs(Context _context, String key, int def) {


        preferences = _context.getSharedPreferences(prefs, Context.MODE_PRIVATE);//why ?
        return preferences.getInt(key,def);

    }
    public boolean getPrefs(Context _context, String key, boolean def) {


        preferences = _context.getSharedPreferences(prefs, Context.MODE_PRIVATE);//why ?
        return preferences.getBoolean(key,def);

    }
    public float getPrefs(Context _context, String key, float def) {


        preferences = _context.getSharedPreferences(prefs, Context.MODE_PRIVATE);//why ?
        return preferences.getFloat(key,def);

    }
    public Double getPrefs(Context _context, String key, Double def) {


        preferences = _context.getSharedPreferences(prefs, Context.MODE_PRIVATE);//why ?
        return Double.parseDouble(preferences.getString(key,""+def));

    }
    public boolean logout(Context context){
        preferences = context.getSharedPreferences(prefs, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.clear();
        editor.commit();
        return true;
    }



}














