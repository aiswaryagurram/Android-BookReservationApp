/**************************************************************************************************
 *     App Name   : BookReservation                                                               *
 *     Class Name : BookAdapter                                                                   *
 *     Purpose    : In this book adapter class, we have implemented two features                  *
 *                  if it is user, it will send the data to addbook activity otherwise if         *
 *                  it is the student, it will pass all the data to request book activity.        *
 *                                                                                                *
 **************************************************************************************************/


package edu.niu.z1807688.bookreservation.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import  edu.niu.z1807688.bookreservation.R;
import edu.niu.z1807688.bookreservation.model.Book;
import edu.niu.z1807688.bookreservation.ui.admin.AddBookActivity;
import edu.niu.z1807688.bookreservation.ui.student.RequestBookActivity;
import edu.niu.z1807688.bookreservation.ui.student.StudentActivity;
import edu.niu.z1807688.bookreservation.util.Pref;
import java.util.List;
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.Views> {
    Context context;
    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }
    List<Book> bookList;
    @NonNull
    @Override
    public Views onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_book,parent,false);
        context=parent.getContext();
        return new Views(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Views holder, int position) {
        Book book=bookList.get(position);
        holder.title.setText("Book Title: "+book.getTitle());
        holder.author.setText("Author: "+book.getAuthor());
        holder.genre.setText("Genre: "+book.getGenre());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this is our books adapter class and below event, when we click on specific row . after clicking on specific row
                // it retrieves all records of clicked row and passing to add book activity if user is admin
                // else if user is student then it simply pass data to request book activity
                if(Pref.isadmin){
                    Intent intent = new Intent(context, AddBookActivity.class);
                    intent.putExtra("id",bookList.get(position).getId());
                    intent.putExtra("title",bookList.get(position).getTitle());
                    intent.putExtra("author",bookList.get(position).getAuthor());
                    intent.putExtra("genre",bookList.get(position).getGenre());
                    intent.putExtra("desc",bookList.get(position).getDesc());
                    intent.putExtra("qty",bookList.get(position).getCount());
                    context.startActivity(intent);
                }
                else {
                    // here we are checking if specific clicked row record have zero book available then it showing message that book not available
                    if(bookList.get(position).getCount().equals("0")){
                        Toast.makeText(context,"Currently book is unavailable ",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Intent intent = new Intent(context, RequestBookActivity.class);
                        intent.putExtra("id",bookList.get(position).getId());
                        intent.putExtra("title",bookList.get(position).getTitle());
                        intent.putExtra("author",bookList.get(position).getAuthor());
                        intent.putExtra("genre",bookList.get(position).getGenre());
                        intent.putExtra("desc",bookList.get(position).getDesc());
                        intent.putExtra("qty",bookList.get(position).getCount());
                        context.startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class Views extends RecyclerView.ViewHolder {
        TextView title,author,genre;
        public Views(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            author=itemView.findViewById(R.id.author);
            genre=itemView.findViewById(R.id.genre);

        }
    }
}
