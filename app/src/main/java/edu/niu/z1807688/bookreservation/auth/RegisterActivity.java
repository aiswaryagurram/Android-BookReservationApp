/**************************************************************************************************
 *     App Name   : BookReservation                                                               *
 *     Class Name : RegistrationActivity                                                          *
 *     Purpose    : In this class, it first validate all fields are filled if yes then it         *
 *                  check for valid email then check stronger password constraint then it         *
 *                  check for specific email is already registered or not then check for          *
 *                  registration number that already exist or not after passing all constraint    *
 *                  then it simply add record to database                                         *
 *                                                                                                *
 **************************************************************************************************/
package edu.niu.z1807688.bookreservation.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import edu.niu.z1807688.bookreservation.R;
import edu.niu.z1807688.bookreservation.databinding.ActivityRegisterBinding;
import edu.niu.z1807688.bookreservation.ui.student.StudentActivity;
import edu.niu.z1807688.bookreservation.util.DatabaseHelper;
import edu.niu.z1807688.bookreservation.util.Loading;
import edu.niu.z1807688.bookreservation.util.Pref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    Loading loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DatabaseHelper databaseHelper=new DatabaseHelper(RegisterActivity.this);
        loading= Loading.getInstance(this);
        binding= DataBindingUtil. setContentView(this,R.layout.activity_register);
        // this below register button click event first validate all fields are filled if yes then it check for valid email then check
        //stronger password constraint then it check for specific email is already registered or not
        //then check for registration number that already exist or not after passing all constraint then it simply add record to database
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.email.getText().toString().equals("") || binding.pass.getText().toString().equals("") || binding.reg.getText().toString().equals("")|| binding.username.getText().toString().equals("")){

                    Toast.makeText(RegisterActivity.this,"Please input all fields",Toast.LENGTH_LONG).show();
                }
                else if (!isValidEmail(binding.email.getText().toString().trim())){
                    Toast.makeText(RegisterActivity.this,"Please input valid email",Toast.LENGTH_LONG).show();

                }
                else    if(binding.pass.getText().toString().length()<8 &&!isValidPassword(binding.pass.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"Your password is not valid try stronger password instead",Toast.LENGTH_LONG).show();

                }
                else{

                    if(databaseHelper.check_email(binding.email.getText().toString())){
                        Toast.makeText(RegisterActivity.this,"Email is already registered try again different one !",Toast.LENGTH_LONG).show();
                    }
                    else  if(databaseHelper.check_regno(binding.reg.getText().toString())){
                        Toast.makeText(RegisterActivity.this,"Registration number is already taken try different one",Toast.LENGTH_LONG).show();
                    }
                    else{
                        if(databaseHelper.addUsers(binding.username.getText().toString(),binding.email.getText().toString(),binding.pass.getText().toString(),binding.reg.getText().toString())) {
                            Toast.makeText(RegisterActivity.this,"Registration successfull !",Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else{
                            Toast.makeText(RegisterActivity.this,"Error while registration!",Toast.LENGTH_LONG).show();

                        }
                    }
                }
            }
        });
    }
    // this method is to check valid of email
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    // this method is to validate password
    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }
}