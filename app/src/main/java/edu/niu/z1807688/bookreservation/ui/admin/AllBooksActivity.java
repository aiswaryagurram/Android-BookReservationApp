package edu.niu.z1807688.bookreservation.ui.admin;

/**************************************************************************************************
 *     App Name   : BookReservation                                                               *
 *     Class Name : AllBooksActivity class                                                        *
 *     Purpose    : In this class,It setup adapter and show all books in recyclerview             *
 *                                                                                                *
 **************************************************************************************************/

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import edu.niu.z1807688.bookreservation.R;
import edu.niu.z1807688.bookreservation.adapter.BookAdapter;
import edu.niu.z1807688.bookreservation.databinding.ActivityAllBooksBinding;
import edu.niu.z1807688.bookreservation.model.Book;
import edu.niu.z1807688.bookreservation.util.DatabaseHelper;
import edu.niu.z1807688.bookreservation.util.Loading;
import edu.niu.z1807688.bookreservation.util.Pref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class AllBooksActivity extends AppCompatActivity {
    List<Book> list=new ArrayList<>();
    BookAdapter adapter;
    ActivityAllBooksBinding binding;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper=new DatabaseHelper(this);
        binding= DataBindingUtil .setContentView(this,R.layout.activity_all_books);
        setSupportActionBar(binding.toolbar);
        setTitle("All Book");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onStart()
    {
        super.onStart();
        getAllBooks();
    }
    // below method is to get all books record
    private void getAllBooks() {
        list=databaseHelper.get_books();
        if(list.size()>0){
            setAdapter(list);
        }else {
            binding.recycler.setAdapter(null);
            Toast.makeText(AllBooksActivity.this, "No Book Added Yet!", Toast.LENGTH_SHORT).show();
        }
    }
    // this method is to setup adapter and show all books in recyclerview
    private void setAdapter(List<Book> list) {
        adapter=new BookAdapter(list);
        binding.recycler.setAdapter(adapter);
    }
}