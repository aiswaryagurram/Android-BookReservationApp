package edu.niu.z1807688.bookreservation.ui.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import edu.niu.z1807688.bookreservation.R;
import edu.niu.z1807688.bookreservation.adapter.BookAdapter;
import edu.niu.z1807688.bookreservation.adapter.ReturnAdapter;
import edu.niu.z1807688.bookreservation.databinding.ActivityReturnBinding;
import edu.niu.z1807688.bookreservation.model.Book;
import edu.niu.z1807688.bookreservation.ui.admin.AllBooksActivity;
import edu.niu.z1807688.bookreservation.util.DatabaseHelper;
import edu.niu.z1807688.bookreservation.util.Loading;
import edu.niu.z1807688.bookreservation.util.Pref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class ReturnActivity extends AppCompatActivity {
    List<Book> list=new ArrayList<>();
    ReturnAdapter adapter;
    ActivityReturnBinding binding;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_return);
        databaseHelper=new DatabaseHelper(this);
        setSupportActionBar(binding.toolbar);
        setTitle("Return Book");
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
    // below method is to get all books issued by specific user
    private void getAllBooks() {
        list=databaseHelper.get_books_by_email(Pref.GetData(ReturnActivity.this,"email"));
        Toast.makeText(ReturnActivity.this,String.valueOf(list.size()), Toast.LENGTH_SHORT).show();
        if(list.size()>0){
            setAdapter(list);
        }else {
            Toast.makeText(ReturnActivity.this, "No Book Owned Yet!", Toast.LENGTH_SHORT).show();
        }
    }
    private void setAdapter(List<Book> list) {
        adapter=new ReturnAdapter(list);
        binding.recycler.setAdapter(adapter);
    }
}