/**************************************************************************************************
 *     App Name   : BookReservation                                                               *
 *     Class Name : ReturnAdapter                                                                 *
 *     Purpose    : In this Return adapter class, when user press return book button              *
 *                  after pressing if first delete record from specific user and                  *
 *                  increases its count to library and also shows a popup to enter the            *
 *                  rating for that particular book and also displays the random code             *
 *                  for that book                                                                 *
 *                                                                                                *
 **************************************************************************************************/

package edu.niu.z1807688.bookreservation.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import edu.niu.z1807688.bookreservation.R;
import edu.niu.z1807688.bookreservation.model.Book;
import edu.niu.z1807688.bookreservation.ui.admin.AddBookActivity;
import edu.niu.z1807688.bookreservation.ui.student.RequestBookActivity;
import edu.niu.z1807688.bookreservation.ui.student.StudentActivity;
import edu.niu.z1807688.bookreservation.util.DatabaseHelper;
import edu.niu.z1807688.bookreservation.util.Loading;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class ReturnAdapter extends RecyclerView.Adapter<ReturnAdapter.Views> {
    Context context;
    DatabaseHelper databaseHelper;
    public ReturnAdapter(List<Book> bookList) {
        this.bookList = bookList;

    }
    List<Book> bookList;
    FirebaseAuth auth;
    FirebaseFirestore db;
    Loading loading;
    @NonNull
    @Override
    public Views onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_return,parent,false);
        context=parent.getContext();
       // db=FirebaseFirestore.getInstance();
        databaseHelper=new DatabaseHelper(context);
     //   auth=FirebaseAuth.getInstance();
        loading=Loading.getInstance(context);
        return new Views(view);
    }
    @Override
    public void onBindViewHolder(@NonNull Views holder, int position) {
        Book book=bookList.get(position);
        holder.title.setText("Book Title: "+book.getTitle());
        holder.author.setText("Author: "+book.getAuthor());
        // this below line of code executong when we click on row to get code
        holder.btn_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Code is -> "+  databaseHelper.get_code(book.getId()),Toast.LENGTH_LONG).show();
            }
        });
        holder._return.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                long dd=dayDiff(book.getDate(),getDate()) ;
                if(dayDiff(book.getDate(),getDate())>2){
                   show_popup("Pay Fine","You keep the book more then a week so you have to pay fine!",book.getId());
                    //   showAlertDialog("Pay Fine","You keep the book more then a week so you have to pay fine!",book.getId());
                }else {
                    //      showAlertDialog("No Fine","You return the book with in a week so you don't have to pay fine!",book.getId());
                    show_popup("Return book","You have returned the book !",book.getId());
                }

            }
        });
    }
    //below method is for getting days difference
    @RequiresApi(api = Build.VERSION_CODES.O)
    private long dayDiff(String date1, String date2){
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        final LocalDate firstDate = LocalDate.parse(date1, formatter);
        final LocalDate secondDate = LocalDate.parse(date2, formatter);
        final long days = ChronoUnit.DAYS.between(firstDate, secondDate);
        return days;
    }
    // this  method is to get current date
    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
    // below method execute when user press return book button
    // after pressing if first delete record from specific user and increases its count to library
    private void returnBook(String id) {
        databaseHelper.delete_record("tbl_issued",new String[]{id});
        String temp_count=  databaseHelper.get_book_count(id);
        int new_count=Integer.parseInt(temp_count)+1;
        if(databaseHelper.update_book_remaining_count(id,String.valueOf(new_count))){
            Toast.makeText(context,"Successfully Returned !",Toast.LENGTH_LONG).show();
            ((Activity)context).finish();
        }
    }
    @Override
    public int getItemCount() {
        return bookList.size();
    }
    public class Views extends RecyclerView.ViewHolder {
        TextView title,author;
        Button _return;
        ImageView btn_getcode;
        public Views(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            author=itemView.findViewById(R.id.author);
            btn_getcode=itemView.findViewById(R.id.btn_getcode);
            _return=itemView.findViewById(R.id._return);

        }
    }
    // this popup method called when user press return button
    public void show_popup(String title, String msg, String id) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View mView = layoutInflaterAndroid.inflate(R.layout.custom_alertbox, null);
        RatingBar ratingBar = (RatingBar) mView.findViewById(R.id.ratingbar);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
        alertDialogBuilderUserInput.setTitle(title);
        alertDialogBuilderUserInput.setMessage(msg);
        alertDialogBuilderUserInput.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // after clicking on proceed button of alertdialog  it first add rating of specific book and then return book to library
                if(databaseHelper.addrating(id,String.valueOf(ratingBar.getRating())))
                {
                    returnBook(id);
                }
                else {
                    Toast.makeText(context,"Opps something went wrong ",Toast.LENGTH_LONG).show();
                }
            }
        });
        alertDialogBuilderUserInput.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialogBuilderUserInput.setView(mView);
        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.setCancelable(false);
        alertDialogAndroid.show();
    }
}
