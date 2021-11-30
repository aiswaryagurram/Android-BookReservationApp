package edu.niu.z1807688.bookreservation.ui.student;

/**************************************************************************************************
 *     App Name   : BookReservation                                                               *
 *     Class Name : RequestBookActivity class                                                     *
 *     Purpose    : In this class, when the student requests for the book, it first checks the    *
 *                  if the book is available or not. if it is available, then the book will be    *
 *                  issued to the student and also generate a random string for that book and also*
 *                  performs calculations to get the average rating for a book                    *
 *                                                                                                *
 **************************************************************************************************/
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;
import edu.niu.z1807688.bookreservation.R;
import edu.niu.z1807688.bookreservation.databinding.ActivityRequestBookBinding;
import edu.niu.z1807688.bookreservation.ui.admin.AddBookActivity;
import edu.niu.z1807688.bookreservation.ui.admin.AdminActivity;
import edu.niu.z1807688.bookreservation.util.DatabaseHelper;
import edu.niu.z1807688.bookreservation.util.Loading;
import edu.niu.z1807688.bookreservation.util.Pref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RequestBookActivity extends AppCompatActivity {
    ActivityRequestBookBinding binding;
    String id;
    DatabaseHelper databaseHelper;
    RatingBar ratingBar;
    ArrayList<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper=new DatabaseHelper(this);
        binding= DataBindingUtil. setContentView(this,R.layout.activity_request_book);
        // this below code is validating if we have extras from previous activity if yes then it get all extras
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = extras.getString("id");
            binding.title.setText(extras.getString("title"));
            binding.author.setText(extras.getString("author"));
            binding.genre.setText(extras.getString("genre"));
            binding.desc.setText(extras.getString("desc"));
            // this below line of code getting rating for each book
            // first it fetch all rating
            list= databaseHelper.getraings(id);
            // then here we are performing calculations of adding all list values and getting average and then setting that value to rating bar
            binding.ratingbar.setRating(getsum());
        }
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
        binding.request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    // below piece of code validating if user have requested 5 books then it not allow to request more books
                    if(databaseHelper.count(Pref.GetData(RequestBookActivity.this,"email"))==5){
                        Toast.makeText(RequestBookActivity.this,"Oops you can't request more books",Toast.LENGTH_LONG).show();
                    }
                    else{
                        //if books requested count less then 5 then below line of code execute
                        requestBook(binding.title.getText().toString(),binding.author.getText().toString(),getDate(),Pref.GetData(RequestBookActivity.this,"email"));
                    }
                }
                catch (Exception e){
                    Toast.makeText(RequestBookActivity.this,e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public float getsum()
    {
        float sum = (float) 0.0;
        for(int i = 0; i < list.size(); i++)
        {
            sum +=Float.parseFloat(list.get(i));
        }
        return sum/list.size();
    }
    private void requestBook(String title,String author,String date,String issuer) {
        String count=  databaseHelper.get_book_count(id);
        int new_count=Integer.parseInt(count)-1;
        if( databaseHelper.update_book_remaining_count(id,String.valueOf(new_count))){
            if(databaseHelper.issue_book(id,title,author,date,issuer,createRandomString(5))){
                Toast.makeText(RequestBookActivity.this,"Book Issued Successfully !",Toast.LENGTH_LONG).show();
                finish();
            }
            else{
            }
        }
    }
    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static String createRandomString(int length)
    {
        String randomString = "";
        String lookup[] = null;
        int upperBound = 0;
        Random random = new Random();
        lookup = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        upperBound = 36;
        for (int i = 0; i < length; i++)
        {
            randomString = randomString + lookup[random.nextInt(upperBound)];
        }
        return randomString;
    }
}