/****************************************************************************************************
 *     App Name   : BookReservation                                                                 *
 *     Class Name : DatabaseHelper class                                                            *
 *     Purpose    : In this class, we have created tables and columns to store the details of user  *
 *                  and the book. This class has some methods like to add the user to the database, *
 *                  to add books, to add the issued books, update book to database, to add ratings  *
 *                  for specific book into database, to check email, register number that already   *
 *                  registered or not, to get the ratings, get the code, get the count of remaining *
 *                  books, to delete th books from database.                                        *
 *                                                                                                  *
 ***************************************************************************************************/

package edu.niu.z1807688.bookreservation.util;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.Display;
import edu.niu.z1807688.bookreservation.model.Book;
import java.util.ArrayList;
import java.util.List;
public class DatabaseHelper extends SQLiteOpenHelper {
    // database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "library.db";
    // user table and columns
    private static final String TABLE_USERS = "tbl_users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_REGNO = "reg_no";
    // books table and columns
    public static final String TABLE_BOOKS = "tbl_books";
    private static final String COLUMN_BOOK_ID = "id";
    private static final String COLUMN_BOOK_TITLE = "book_title";
    private static final String COLUMN_BOOK_AUTHOR = "book_author";
    private static final String COLUMN_BOOK_GENRE = "book_genre";
    private static final String COLUMN_BOOK_DESCRIPTION = "book_description";
    private static final String COLUMN_BOOK_COUNT = "book_count";
    // issued book table and columns
    public static final String TABLE_ISSUED = "tbl_issued";
    private static final String COLUMN_ISSUED_ID = "issue_id";
    private static final String COLUMN_ISSUE_DATE = "issue_date";
    private static final String COLUMN_ISSUER = "username";
    private static final String COLUMN_CODE = "code";
    // rating table and columns
    public static final String TABLE_RATINGS = "tbl_ratings";
    private static final String COLUMN_RATING_ID = "rating_id";
    private static final String COLUMN_RATING = "rating";
    //queries to create tables
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS + "(" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"+ COLUMN_USER_EMAIL +" TEXT,"+ COLUMN_USER_PASSWORD +" TEXT,"+ COLUMN_USER_REGNO + " TEXT" + ")";
    private String CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_BOOKS + "(" + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_BOOK_TITLE + " TEXT,"+ COLUMN_BOOK_AUTHOR + " TEXT,"+ COLUMN_BOOK_GENRE + " TEXT,"+ COLUMN_BOOK_DESCRIPTION + " TEXT,"+ COLUMN_BOOK_COUNT + " TEXT" +")";
    private String CREATE_ISSUED_TABLE = "CREATE TABLE " + TABLE_ISSUED + "(" + COLUMN_ISSUED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_BOOK_ID + " TEXT," + COLUMN_BOOK_TITLE + " TEXT,"+ COLUMN_BOOK_AUTHOR + " TEXT,"+ COLUMN_ISSUE_DATE + " TEXT,"+ COLUMN_ISSUER + " TEXT,"+ COLUMN_CODE + " TEXT" +")";
    private String CREATE_RATING_TABLE = "CREATE TABLE " + TABLE_RATINGS + "(" + COLUMN_RATING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_BOOK_ID + " TEXT," + COLUMN_RATING + " TEXT" +")";
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USERS;
    private String DROP_BOOKS_TABLE = "DROP TABLE IF EXISTS " + TABLE_BOOKS;
    private String DROP_ISSUED_TABLE = "DROP TABLE IF EXISTS " + TABLE_ISSUED;
    private String DROP_RATING_TABLE = "DROP TABLE IF EXISTS " + TABLE_RATINGS;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_BOOKS_TABLE);
        db.execSQL(CREATE_ISSUED_TABLE);
        db.execSQL(CREATE_RATING_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_BOOKS_TABLE);
        db.execSQL(DROP_ISSUED_TABLE);
        db.execSQL(DROP_RATING_TABLE);
        onCreate(db);
    }
    //this method is to add users
    public Boolean addUsers(String name,String email,String password,String regno) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);
        values.put(COLUMN_USER_REGNO, regno);
        // Inserting Row
        long result=  db.insert(TABLE_USERS, null, values);
        if (result == -1)
            return false;
        else
            return true;
    }
    //this method is to add books into database
    public Boolean addBooks(String title,String author,String genre,String description,String count){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_TITLE, title);
        values.put(COLUMN_BOOK_AUTHOR, author);
        values.put(COLUMN_BOOK_GENRE,genre);
        values.put(COLUMN_BOOK_DESCRIPTION,description);
        values.put(COLUMN_BOOK_COUNT,count);
        long result=  db.insert(TABLE_BOOKS, null, values);

        if (result == -1)

            return false;
        else
            return true;
    }
    //this method is to add issue books record into database
    public Boolean issue_book(String id,String title,String author,String date,String issuer,String code){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_ID, id);
        values.put(COLUMN_BOOK_TITLE, title);
        values.put(COLUMN_BOOK_AUTHOR, author);
        values.put(COLUMN_ISSUE_DATE,date);
        values.put(COLUMN_ISSUER,issuer);
        values.put(COLUMN_CODE,code);
        long result=  db.insert(TABLE_ISSUED, null, values);

        if (result == -1)

            return false;
        else
            return true;
    }
    //this method is to add ratings for specific book into database
    public Boolean addrating(String id,String rating){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_ID, id);
        values.put(COLUMN_RATING, rating);
        long result=  db.insert(TABLE_RATINGS, null, values);

        if (result == -1)

            return false;
        else
            return true;
    }
    //this method is to update book data into database
    public boolean update_book(String id,String  title,String author,String genre,String description,String count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_TITLE,title);
        values.put(COLUMN_BOOK_AUTHOR,author);
        values.put(COLUMN_BOOK_GENRE,genre);
        values.put(COLUMN_BOOK_DESCRIPTION,description);
        values.put(COLUMN_BOOK_COUNT,count);
        int cursor= db.update(TABLE_BOOKS, values, "id=?", new String[] {String.valueOf(id)});
        db.close();
        if (cursor > 0)
            return true;
        else
            return false;
    }
    //this method is to check email that already registered or not
    public boolean check_email(String email) {
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
    //this method check registration number already registered or not
    public boolean check_regno(String regno) {
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_REGNO + " = ?";
        String[] selectionArgs = {regno};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
    //this method is validate login
    public boolean checkUser(String email, String password) {
        String[] columns = {COLUMN_USER_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
    // this method is to get ratings
    public ArrayList<String> getraings(String id) {
        String[] columns = {COLUMN_RATING};
        ArrayList<String> ratingList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_BOOK_ID + " = ?";
        String[] selectionArgs = {id};
        Cursor cursor = db.query(TABLE_RATINGS, //Table to query
                columns,    //columns to return
                selection ,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order
        if (cursor.moveToFirst()) {
            do {
                ratingList.add(cursor.getString(cursor.getColumnIndex(COLUMN_RATING)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ratingList;
    }
    // this method is to get books record
    public ArrayList<Book> get_books() {
        String[] columns = {COLUMN_BOOK_ID,COLUMN_BOOK_TITLE,COLUMN_BOOK_AUTHOR, COLUMN_BOOK_GENRE, COLUMN_BOOK_DESCRIPTION,COLUMN_BOOK_COUNT};
        ArrayList<Book> bookList = new ArrayList<Book>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_BOOKS, columns, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Book books = new Book();
                books.setId(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_ID)));
                books.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_TITLE)));
                books.setAuthor(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_AUTHOR)));
                books.setGenre(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_GENRE)));
                books.setDesc(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_DESCRIPTION)));
                books.setCount(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_COUNT)));
                bookList.add(books);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user list
        return bookList;
    }
    // search method for books
    public ArrayList<Book> search_book_info_by_title_genre_author(String title) {
        ArrayList<Book> userList = new ArrayList<Book>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query( TABLE_BOOKS, null,"book_title LIKE '%"+title+"%' or book_genre LIKE '%"+title+"%' or book_author LIKE '%"+title+"%' or book_description LIKE '%"+title+"%'",null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Book _model = new Book();
                _model.setId(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_ID)));
                _model.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_TITLE)));
                _model.setAuthor(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_AUTHOR)));
                _model.setGenre(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_GENRE)));
                _model.setDesc(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_DESCRIPTION)));
                _model.setCount(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_COUNT)));
                userList.add(_model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }
    public ArrayList<Book> get_books_by_email(String email) {
        String[] columns = {COLUMN_BOOK_ID,COLUMN_BOOK_TITLE,COLUMN_BOOK_AUTHOR, COLUMN_ISSUE_DATE, COLUMN_ISSUER};
        ArrayList<Book> userList = new ArrayList<Book>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_ISSUER + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_ISSUED, columns, selection , selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Book _model = new Book();
                _model.setId(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_ID)));
                _model.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_TITLE)));
                _model.setAuthor(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_AUTHOR)));
                _model.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_ISSUE_DATE)));
                _model.setIssuer(cursor.getString(cursor.getColumnIndex(COLUMN_ISSUER)));
                userList.add(_model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }
    public int count(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c =db.rawQuery("Select * from tbl_issued Where username='"+email+"'",null);
        return c.getCount();
    }
    // this method is to get books count how many books remaining
    public String get_book_count(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + "tbl_books" + " where id = " +id + "" , null);
        String str = null;
        if (c.moveToFirst()) {
            do {
                str = c.getString(c.getColumnIndex("book_count"));
            } while (c.moveToNext());
        }
        return str;
    }
    // this method is to get code for each issued book
    public String get_code(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + "tbl_issued" + " where id = " +id + "" , null);
        String str = null;
        if (c.moveToFirst()) {
            do {
                str = c.getString(c.getColumnIndex("code"));
            } while (c.moveToNext());
        }
        return str;
    }
    // this method is to update books count after issue
    public boolean update_book_remaining_count(String id,String count) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_COUNT,count);
        int cursor= db.update(TABLE_BOOKS, values, "id=?", new String[] {String.valueOf(id)});
        db.close();
        if (cursor > 0)
            return true;
        else
            return false;
    }
    // this method is to delete record based on specific book id
    public void delete_record(String table ,String[] id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, "id" + " = ?",id);
        db.close();
    }
}