package com.example.task41.Database;

import android.content.Context;
import android.content.SharedPreferences;

public class SP {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    public SP(Context context){
        this.context =  context;
        sharedPreferences = context.getSharedPreferences("User",Context.MODE_PRIVATE);
    }
        public void SaveUser(String name,String password,Boolean rem){
        editor = sharedPreferences.edit();
        editor.putString("name",name);
        editor.putString("password",password);
        editor.putBoolean("rem",rem);
        editor.commit();
    }
    public void setLogin(Boolean rem){
        editor = sharedPreferences.edit();
        editor.putBoolean("rem",rem);
        editor.commit();
    }

    public String getUserName(){
       return sharedPreferences.getString("name","");
    }
    public String getUserPassword(){
      return  sharedPreferences.getString("password","");
    }
    public Boolean getLogin(){
        return  sharedPreferences.getBoolean("rem",false);
    }

}
