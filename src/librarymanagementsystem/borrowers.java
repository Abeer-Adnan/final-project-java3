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
public class borrowers {
    private Integer Id;
    private String FirstName;
    private String LastName;
    private String Mobile;
    private String Email;
    private String Address;
    private String Gender;

    
    
    public borrowers(){
        
    }
    
    public borrowers(Integer Id, String FirstName, String LastName, String Mobile, String Email, String Address, String Gender) {
        this.Id = Id;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Mobile = Mobile;
        this.Email = Email;
        this.Address = Address;
        this.Gender = Gender;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    @Override
    public String toString() {
        return "borrowers{" + "Id=" + Id + ", FirstName=" + FirstName + ", LastName=" + LastName + ", Mobile=" + Mobile + ", Email=" + Email + ", Address=" + Address + ", Gender=" + Gender + '}';
    }
    
    
}
