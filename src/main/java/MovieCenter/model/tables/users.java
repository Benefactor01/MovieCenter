package MovieCenter.model.tables;

import javax.persistence.*;

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

    public users(String username, String password, String email) {
        this.user = username;
        this.pw = password;
        this.mail = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getMail() {
        return mail;
    }

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

    public users(){};
}
