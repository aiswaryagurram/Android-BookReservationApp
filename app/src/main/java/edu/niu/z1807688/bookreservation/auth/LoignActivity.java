/**************************************************************************************************
 *     App Name   : BookReservation                                                               *
 *     Class Name : LoginActivity                                                                 *
 *     Purpose    : In this class, it verifies the credentials of the user and student properly   *
 *                  if the details are correct, it allows the person to access the further        *
 *                  features of the app otherwise it will display a message regarding the wrong   *
 *                  information provided                                                          *
 *                                                                                                *
 **************************************************************************************************/
package edu.niu.z1807688.bookreservation.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import edu.niu.z1807688.bookreservation.R;
import edu.niu.z1807688.bookreservation.databinding.ActivityLoignBinding;
import edu.niu.z1807688.bookreservation.ui.admin.AdminActivity;
import edu.niu.z1807688.bookreservation.ui.student.StudentActivity;
import edu.niu.z1807688.bookreservation.util.DatabaseHelper;
import edu.niu.z1807688.bookreservation.util.Loading;
import edu.niu.z1807688.bookreservation.util.Pref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoignActivity extends AppCompatActivity {
    ActivityLoignBinding binding;
    boolean isAdmin=false;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper=new DatabaseHelper(this);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_loign);
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=findViewById(checkedId);
                if(radioButton.getText().equals("Admin")){
                    binding.email.setHint("Username");
                    isAdmin=true;
                }else {
                    binding.email.setHint("Email");
                    isAdmin=false;
                }
            }
        });
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoignActivity.this,RegisterActivity.class));
            }
        });
        binding.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.email.getText().toString().isEmpty() && !binding.password.getText().toString().isEmpty() ){
                    if(isAdmin){
                        Pref.getInstance(LoignActivity.this).setStatus(true);
                        //Move to Admin
                        if(binding.email.getText().toString().trim().equals("admin") && binding.password.getText().toString().trim().equals("1234")){
                            startActivity(new Intent(LoignActivity.this, AdminActivity.class));
                            finishAffinity();
                        }else {
                            Toast.makeText(LoignActivity.this, "Wrong Information", Toast.LENGTH_SHORT).show();
                        }
                    }else {

                        Pref.getInstance(LoignActivity.this).setStatus(false);
                        // this below lines of code execute when user presses on login button this make query to database to check user by email and password
                        if(databaseHelper.checkUser(binding.email.getText().toString(),binding.password.getText().toString())){
                            startActivity(new Intent(LoignActivity.this, StudentActivity.class));
                            Pref.PutData(LoignActivity.this,"email",binding.email.getText().toString());
                            finishAffinity();
                        }
                        else{
                            Toast.makeText(LoignActivity.this, "Oops user not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(LoignActivity.this, "Enter all details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onStart()
    {
        super.onStart();
        binding.email.setText("");
        binding.password.setText("");
    }
}