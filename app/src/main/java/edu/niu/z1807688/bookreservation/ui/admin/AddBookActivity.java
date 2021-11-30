package edu.niu.z1807688.bookreservation.ui.admin;
/**************************************************************************************************
 *     App Name   : BookReservation                                                               *
 *     Class Name : AddBookActivity                                                               *
 *     Purpose    : In this class, when the admin clicks on button, it either add or update the   *
 *                  book into database. After adding/updating the book, it will reset the whole   *
 *                  page.It also performs the book deletion action. It removes the book from the  *
 *                  database when admin clicks on the delete button.                              *
 *                                                                                                *
 **************************************************************************************************/

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import edu.niu.z1807688.bookreservation.R;
import edu.niu.z1807688.bookreservation.databinding.ActivityAddBookBinding;
import edu.niu.z1807688.bookreservation.util.DatabaseHelper;
import edu.niu.z1807688.bookreservation.util.Loading;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import java.util.HashMap;
import java.util.Map;
public class AddBookActivity extends AppCompatActivity {
    ActivityAddBookBinding bookBinding;
    DatabaseHelper databaseHelper;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper=new DatabaseHelper(this);
        bookBinding= DataBindingUtil. setContentView(this,R.layout.activity_add_book);
        setSupportActionBar(bookBinding.toolbar);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            setTitle("Update Book");
            bookBinding.btnDelete.setVisibility(View.VISIBLE);
            id = extras.getString("id");
            bookBinding.title.setText(extras.getString("title"));
            bookBinding.author.setText(extras.getString("author"));
            bookBinding.genre.setText(extras.getString("genre"));
            bookBinding.desc.setText(extras.getString("desc"));
            bookBinding.qty.setText(extras.getString("qty"));
        }
        else {
            setTitle("Add Book");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        bookBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        bookBinding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper.delete_record("tbl_books",new String[]{id});
                Toast.makeText(AddBookActivity.this,"Book is deleted",Toast.LENGTH_LONG).show();
                finish();
            }
        });
        // this button click event is either updating or adding new record into database
        bookBinding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bookBinding.author.getText().toString().isEmpty() && !bookBinding.title.getText().toString().isEmpty() &&
                        !bookBinding.genre.getText().toString().isEmpty()  ){
                    //here checking if title of activity is update book that mean we have to perform update else insertion
                    if(getTitle().equals("Update Book")){
                        if( databaseHelper.update_book(id,bookBinding.title.getText().toString() , bookBinding.author.getText().toString(), bookBinding.genre.getText().toString(), bookBinding.desc.getText().toString(), bookBinding.qty.getText().toString())){

                            Toast.makeText(AddBookActivity.this, "Book Info updated successfully !", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        // below piece of code is to add books after successful insertion it showing message and resetting whole text boxes
                        if( databaseHelper.addBooks(bookBinding.title.getText().toString(),bookBinding.author.getText().toString(),bookBinding.genre.getText().toString(),bookBinding.desc.getText().toString(),bookBinding.qty.getText().toString())){

                            Toast.makeText(AddBookActivity.this, "Book Added successfully !", Toast.LENGTH_SHORT).show();
                            bookBinding.author.setText("");
                            bookBinding.genre.setText("");
                            bookBinding.desc.setText("");
                            bookBinding.title.setText("");
                            bookBinding.qty.setText("");
                            bookBinding.title.requestFocus();
                        }
                    }
                }else {
                    Toast.makeText(AddBookActivity.this, "Enter All Details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}