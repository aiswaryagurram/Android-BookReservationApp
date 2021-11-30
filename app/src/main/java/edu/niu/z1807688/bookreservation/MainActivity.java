/*************************************************************************************                                    *
 *               App Name: Book Reservation                                          *
 *                Purpose: This app has two login portals one for admin and other    *
 *                         for student. Admin has some features like search book,    *
 *                         add book and delete book. Students are given with features*
 *                         like request book, search book, return book. Student can  *
 *                         request the book and reserve the book in online and code  *
 *                         is generated so that he can collect the book from the     *
 *                         library.                                                   *
 ************************************************************************************/

package edu.niu.z1807688.bookreservation;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}