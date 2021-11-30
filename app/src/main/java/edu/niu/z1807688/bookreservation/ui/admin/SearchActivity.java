/**************************************************************************************************
 *     App Name   : BookReservation                                                               *
 *     Class Name : SearchActivity class                                                          *
 *     Purpose    : In this class,when a search button is making query to database to             *
 *                  search book by title or genre or by author name or keywords in the            *
 *                  database                                                                      *
 *                                                                                                *
 **************************************************************************************************/

package edu.niu.z1807688.bookreservation.ui.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import edu.niu.z1807688.bookreservation.R;
import edu.niu.z1807688.bookreservation.adapter.BookAdapter;
import edu.niu.z1807688.bookreservation.databinding.ActivitySearchBinding;
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
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {
    ActivitySearchBinding searchBinding;
    List<Book> list=new ArrayList<>();
    BookAdapter adapter;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchBinding= DataBindingUtil.setContentView(this,R.layout.activity_search);
        setSupportActionBar(searchBinding.toolbar);
        setTitle("Search Book");
        databaseHelper=new DatabaseHelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        searchBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // below search button is making query to database to search book by title or genre or by author name or keywords
        searchBinding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!searchBinding.text.getText().toString().isEmpty()){

                    list=databaseHelper.search_book_info_by_title_genre_author(searchBinding.text.getText().toString());
                    if(list.size()>0){
                        setAdapter(list);
                    }else {

                        Toast.makeText(SearchActivity.this, "Try another book", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(SearchActivity.this, "Enter Book Title", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void  remove_by_pos(int pos){
        //    list.remove(pos);
    }
    @Override
    public void onStart()
    {
        super.onStart();
        if(!searchBinding.text.getText().toString().isEmpty()) {
            list = databaseHelper.search_book_info_by_title_genre_author(searchBinding.text.getText().toString());
            if (list.size() > 0) {
                setAdapter(list);
                adapter.notifyDataSetChanged();

            } else {
                searchBinding.recycler.setAdapter(null);
            }
        }
    }
    private void setAdapter(List<Book> list) {
        adapter=new BookAdapter(list);
        searchBinding.recycler.setAdapter(adapter);
    }
}