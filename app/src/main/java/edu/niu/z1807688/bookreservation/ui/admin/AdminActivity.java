
/**************************************************************************************************
 *     App Name   : BookReservation                                                               *
 *     Class Name : AdminActivity class                                                           *
 *     Purpose    : In this class, we implemented functionality which are given to the admin like *
 *                  add book,search book, all books and logout,so when the admin click button, it *
 *                  goes to that particular activity.                                             *
 *                                                                                                *
 **************************************************************************************************/

package edu.niu.z1807688.bookreservation.ui.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import edu.niu.z1807688.bookreservation.R;
import edu.niu.z1807688.bookreservation.auth.LoignActivity;
import edu.niu.z1807688.bookreservation.databinding.ActivityAdminBinding;
import edu.niu.z1807688.bookreservation.util.Pref;
public class AdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAdminBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_admin);
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pref.isadmin=true;
                startActivity(new Intent(AdminActivity.this,AddBookActivity.class));
            }
        });
        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pref.isadmin=true;
                startActivity(new Intent(AdminActivity.this,SearchActivity.class));
            }
        });
        binding.books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pref.isadmin=true;
                startActivity(new Intent(AdminActivity.this,AllBooksActivity.class));
            }
        });
        binding.checkUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pref.isadmin=true;
                startActivity(new Intent(AdminActivity.this,CheckUserActivity.class));
            }
        });
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, LoignActivity.class));
                finishAffinity();
            }
        });
    }
}