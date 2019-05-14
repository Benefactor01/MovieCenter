package MovieCenter.model.tables;

import javax.persistence.*;

/**Class that represents the user database.*/
@Entity
public class users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "username")
    private String user;

    @Column(name = "password")
    private String pw;

    @Column(name = "email")
    private String mail;

    /**Constructor of the users class.
     * @param username is the username.
     * @param password is the password.
     * @param email is the email.*/
    public users(String username, String password, String email) {
        this.user = username;
        this.pw = password;
        this.mail = email;
    }

    /**Getter of the ID.
     * @return the value of the user ID.*/
    public int getId() {
        return id;
    }

    /**Setter of the user ID.
     * @param id is the new value of the user ID.*/
    public void setId(int id) {
        this.id = id;
    }

    /**Getter of the username.
     * @return the value of the username.*/
    public String getUser() {
        return user;
    }

    /**Setter for the username.
     * @param user is the new value of the username.*/
    public void setUser(String user) {
        this.user = user;
    }

    /**Getter of the password.
     * @return is the value of the password.*/
    public String getPw() {
        return pw;
    }

    /**Setter of the password.
     * @param pw is the new value of password.*/
    public void setPw(String pw) {
        this.pw = pw;
    }

    /**Getter of the mail.
     * @return the value of the email.*/
    public String getMail() {
        return mail;
    }

    /**Setter for the mail.
     * @param mail is the value of the new email.*/
    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "users{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", pw='" + pw + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }

    /**Empty constructor of the user class.*/
    public users(){};
}
