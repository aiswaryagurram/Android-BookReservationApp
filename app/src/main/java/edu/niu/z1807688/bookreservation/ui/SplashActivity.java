package edu.niu.z1807688.bookreservation.ui;
/**************************************************************************************************
 *     App Name   : BookReservation                                                               *
 *     Class Name : SplashActivity class                                                          *
 *     Purpose    : This will display a splash screen for a while and  directs to the login page  *
 *                  of the application                                                            *
 *                                                                                                *
 **************************************************************************************************/
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import edu.niu.z1807688.bookreservation.auth.LoignActivity;
import edu.niu.z1807688.bookreservation.R;
import edu.niu.z1807688.bookreservation.ui.student.StudentActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, LoignActivity.class));
                finish();
            }
        }, 5000);
    }
}