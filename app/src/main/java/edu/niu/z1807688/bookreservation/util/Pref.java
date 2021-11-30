package edu.niu.z1807688.bookreservation.util;
import android.content.Context;
import android.content.SharedPreferences;
public class  Pref {
    public static boolean isadmin =false;
    public static Pref INSTANCE;
    static SharedPreferences sharedPreferences;
    public static Pref  getInstance(Context context){
        if(INSTANCE==null){
            sharedPreferences=context.getSharedPreferences("pref", Context.MODE_PRIVATE);
            INSTANCE= new Pref();
        }
        return INSTANCE;
    }
    public boolean isAdmin(){
        return sharedPreferences.getBoolean("isAdmin",false);
    }
    public void setStatus(boolean b){
        sharedPreferences.edit().putBoolean("isAdmin",b).apply();
    }
    public static void PutData(Context context, String key, String value)
    { try
        {
            SharedPreferences sharedpreferences = context.getSharedPreferences("ssshhhh!!!", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(key, value);
            editor.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static String GetData(Context context, String key)
    {
        try
        {
            SharedPreferences sharedpreferences = context.getSharedPreferences("ssshhhh!!!", Context.MODE_PRIVATE);
            return sharedpreferences.getString(key, null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
