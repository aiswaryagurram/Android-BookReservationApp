package edu.niu.z1807688.bookreservation.model;
/**************************************************************************************************
 *     App Name   : BookReservation                                                               *
 *     Class Name : Book class                                                                    *
 *     Purpose    : In this class, we implemented the setter and getter methods                   *
 *                  for a book                                                                    *
 *                                                                                                *
 **************************************************************************************************/

public class Book {
    String title;
    String author,count,issuer;
    public String getIssuer() {
        return issuer;
    }
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCount() {
        return count;
    }
    public void setCount(String count) {
        this.count = count;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getId() {
        return id;
    }
    String id; 
    public String getDate() {
        return date;
    }
    String date;
    public Book(){
    }
    // this is contructor of Book Model class
    public Book(String id,String title, String author, String genre, String desc,String date) {
        this.id=id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.desc = desc;
        this.date=date;
    }
    String genre;
    //below methods are getter and setter
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    String desc;
}
