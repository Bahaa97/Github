package com.bahaa.github.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.bahaa.github.MyApplication;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

public class MyPreference {
    private static final String KEY_NAME = "userName";
    private static final String KEY_MAIL = "userEmail";
    private static final String KEY_PHONE = "phone";
    private static final String NEWS = "news";
    private static final String INTRO = "intro";
    private static final String KEY_FIRST_NAME = "firstName";
    private static final String KEY_LAST_NAME = "lastName";
    private final String KEY_TOKEN = "token";
    private final String LOGIN = "login";
    private final String SCREEN = "screen";
    private final String NOTIFICATIONS = "notifications";
    private final String BOOKS = "MY_BOOKS";
    private final String SECRET_KEY = "SECRET_KEY";
    private final String HAS_SHIPPING = "HAS_SHIPPING";
    private final String CART_COUNT = "CART_COUNT";
    private String SEARCH_HISTORY = "searchHistory";
    private Gson gson;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;

    public MyPreference(Gson gson) {
        preferences = MyApplication.getContext().getSharedPreferences("OpenCart", Context.MODE_PRIVATE);
        this.gson = gson;
        editor = preferences.edit();
        editor.apply();
    }



    public void login(){
        editor.putBoolean(LOGIN, true);
        editor.commit();
    }

    public void finishIntro(){
        editor.putBoolean(INTRO, true);
        editor.commit();
    }
    public boolean getIntro(){
        return preferences.getBoolean(INTRO,false);
    }

    public void setUserName(String name){
        editor.putString(KEY_NAME,name);
        editor.commit();
    }
    public void setFirstName(String name){
        editor.putString(KEY_FIRST_NAME,name);
        editor.commit();
    }
    public void setLastName(String name){
        editor.putString(KEY_LAST_NAME,name);
        editor.commit();
    }
    public String getUserName(){
        return preferences.getString(KEY_NAME,"");
    }
    public String getFirstName(){
        return preferences.getString(KEY_FIRST_NAME,"");
    }
    public String getLastName(){
        return preferences.getString(KEY_LAST_NAME,"");
    }
    public void setUserEmail(String name){
        editor.putString(KEY_MAIL,name);
        editor.commit();
    }
    public String getUserEmail(){
        return preferences.getString(KEY_MAIL,"");
    }
    public void setUserPhone(String name){
        editor.putString(KEY_PHONE,name);
        editor.commit();
    }
    public String getUserPhone(){
        return preferences.getString(KEY_PHONE,"");
    }

    public boolean isLogin(){
        return preferences.getBoolean(LOGIN, false);
    }

    public void setNotifications(boolean b){
        editor.putBoolean(NOTIFICATIONS, b);
        editor.commit();
    }
    public void setNews(boolean b){
        editor.putBoolean(NEWS, b);
        editor.commit();
    }
    public boolean getNews(){
        return preferences.getBoolean(NEWS,false);
    }

    public boolean isNotificationsEnabled(){
        return preferences.getBoolean(NOTIFICATIONS,true);
    }

    public void setScreen(boolean b){
        editor.putBoolean(SCREEN, b);
        editor.commit();
    }

    public void saveBooks(String books){
        editor.putString(BOOKS, books);
        editor.commit();
    }

    public String getSavedBooks(){
        return preferences.getString(BOOKS,"");
    }

    public boolean isStayAwake(){
        return preferences.getBoolean(SCREEN,false);
    }
    public void logOut(){
        editor.putBoolean(LOGIN, false);
        editor.remove(KEY_TOKEN);
        editor.remove(KEY_NAME);
        editor.remove(KEY_MAIL);
        editor.remove(KEY_PHONE);
        editor.commit();
    }

    public void setSECRET_KEY(String key){
        editor.putString(SECRET_KEY, key);
        editor.commit();
    }

    public String getSECRET_KEY(){
        return preferences.getString(SECRET_KEY,"");
    }


    public void setHasShipping(boolean hasShipping){
        editor.putBoolean(HAS_SHIPPING,hasShipping);
        editor.commit();
    }

    public boolean isHasShipping(){
        return preferences.getBoolean(HAS_SHIPPING,true);
    }

    public int getCartCount() {
        return preferences.getInt(CART_COUNT,0);
    }

    public void setCartCount(int count) {
        editor.putInt(CART_COUNT,count);
        editor.commit();
    }

    public String[] getHistory(){
        try {
            JSONArray jsonArray = new JSONArray(preferences.getString(SEARCH_HISTORY, "[]"));
            String[] strings = new String[jsonArray.length()];
            int x = 0;
            for (int i = jsonArray.length()-1; i >= 0 ; i--) {
                strings[x] = jsonArray.optString(i);
                x++;
            }
            return strings;
        } catch (JSONException e) {
            return new String[0];
        }
    }

    public void deleteSearchHistory(){
        editor.remove(SEARCH_HISTORY);
        editor.commit();
    }

    public String[] addNewKeyWord(String word){
        try {
            JSONArray jsonArray = new JSONArray(preferences.getString(SEARCH_HISTORY, "[]"));
            boolean isNewWord = true;
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.optString(i).equals(word)){
                    isNewWord = false;
                    i = jsonArray.length();
                }
            }
            if (isNewWord){
                jsonArray.put(word);
                editor.putString(SEARCH_HISTORY, jsonArray.toString());
                editor.commit();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getHistory();
    }

    public void isFirst() {
        editor.putBoolean("isFirst",true);
        editor.commit();
    }
    public boolean first(){
        return preferences.getBoolean("isFirst",false);
    }

    public void setLanguage(String lang){
        editor.putString("language",lang);
        editor.commit();
    }

    public String getLanguage(){
        return preferences.getString("language","ar");
    }
}
