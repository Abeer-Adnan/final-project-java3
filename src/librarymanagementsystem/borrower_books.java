/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

/**
 *
 * @author rant
 */
public class borrower_books {
    private Integer Book_id ;
    private Integer Borrower_id ;
    private String borrowers_date;
    private String Return_date;
    
    public borrower_books(){
        
    }

    public borrower_books(Integer Book_id, Integer Borrower_id, String borrowers_date, String Return_date) {
        this.Book_id = Book_id;
        this.Borrower_id = Borrower_id;
        this.borrowers_date = borrowers_date;
        this.Return_date = Return_date;
    }

    public Integer getBook_id() {
        return Book_id;
    }

    public void setBook_id(Integer Book_id) {
        this.Book_id = Book_id;
    }

    public Integer getBorrower_id() {
        return Borrower_id;
    }

    public void setBorrower_id(Integer Borrower_id) {
        this.Borrower_id = Borrower_id;
    }

    public String getBorrowers_date() {
        return borrowers_date;
    }

    public void setBorrowers_date(String borrowers_date) {
        this.borrowers_date = borrowers_date;
    }

    public String getReturn_date() {
        return Return_date;
    }

    public void setReturn_date(String Return_date) {
        this.Return_date = Return_date;
    }

    @Override
    public String toString() {
        //return "borrower_books{" + "Book_id=" + Book_id + ", Borrower_id=" + Borrower_id + ", borrowers_date=" + borrowers_date + ", Return_date=" + Return_date + '}';
   return String.format("%-5s %-5s %-20s %-20s", Book_id, Borrower_id, borrowers_date,Return_date);
    }
     public int compareTo(borrower_books brb) {
       return (int)-( this.Book_id-brb.Book_id);
    }
}
