package net.nettape.dal.object;
// Generated 11-jan-2012 20:37:05 by Hibernate Tools 3.4.0.CR1



/**
 * Contact generated by hbm2java
 */
public class Contact  implements java.io.Serializable {


     private Integer contactid;
     private User user;
     private String name;
     private String firstname;
     private String email;

    public Contact() {
    }

    public Contact(User user, String name, String firstname, String email) {
       this.user = user;
       this.name = name;
       this.firstname = firstname;
       this.email = email;
    }
   
    public Integer getContactid() {
        return this.contactid;
    }
    
    public void setContactid(Integer contactid) {
        this.contactid = contactid;
    }
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public String getFirstname() {
        return this.firstname;
    }
    
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }




}


