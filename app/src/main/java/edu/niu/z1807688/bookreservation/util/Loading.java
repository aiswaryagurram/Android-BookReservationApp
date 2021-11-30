package edu.niu.z1807688.bookreservation.util;

import android.app.ProgressDialog;
import android.content.Context;
public class Loading {
    public static Loading INSTANCE;
    static ProgressDialog progressDialog;
    public static Loading  getInstance(Context context){
        progressDialog=new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait...");
        INSTANCE= new Loading();
        return INSTANCE;
    }
}
