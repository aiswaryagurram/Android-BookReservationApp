/**************************************************************************************************
 *     App Name   : BookReservation                                                               *
 *     Class Name : StudentActivity class                                                         *
 *     Purpose    : In this class, we implemented functionality which are given to the student    *
 *                  like request book,search book, my books and logout,so when the student        *
 *                  clicks on button, it goes to that particular activity.                        *
 *                                                                                                *
 **************************************************************************************************/

package edu.niu.z1807688.bookreservation.ui.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import edu.niu.z1807688.bookreservation.R;
import edu.niu.z1807688.bookreservation.auth.LoignActivity;
import edu.niu.z1807688.bookreservation.databinding.ActivityStudentBinding;
import edu.niu.z1807688.bookreservation.ui.admin.AllBooksActivity;
import edu.niu.z1807688.bookreservation.ui.admin.SearchActivity;
import edu.niu.z1807688.bookreservation.util.Pref;
import com.google.firebase.auth.FirebaseAuth;
public class StudentActivity extends AppCompatActivity {
    public static boolean STUDENT_REQUEST=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStudentBinding binding= DataBindingUtil. setContentView(this,R.layout.activity_student);
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pref.isadmin=false;
                startActivity(new Intent(StudentActivity.this, LoignActivity.class));
                finishAffinity();
            }
        });
        binding.books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pref.isadmin=false;
                startActivity(new Intent(StudentActivity.this, AllBooksActivity.class));
            }
        });
        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pref.isadmin=false;
                startActivity(new Intent(StudentActivity.this, SearchActivity.class));
            }
        });
        binding.myBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentActivity.this, ReturnActivity.class));
            }
        });
    }
}