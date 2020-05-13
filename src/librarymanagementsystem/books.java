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
public class books {
    private Integer Id;
    private String Name;
    private String Description;
    
    
    public books(){
        
    }

    public books(Integer Id, String Name, String Description) {
        this.Id = Id;
        this.Name = Name;
        this.Description = Description;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    @Override
    public String toString() {
return String.format("%-5s %-10s %-30s ", Id, Name, Description);    }

    
    
}
