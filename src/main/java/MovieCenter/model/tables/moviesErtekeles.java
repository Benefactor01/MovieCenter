package MovieCenter.model.tables;

import javax.persistence.*;

@Entity
public class moviesErtekeles {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ertekelesID")
    private int ertekelesID;

    @Column(name = "userID")
    private int userID;

    @Column(name = "movieID")
    private int movieID;

    @Column(name = "ertekeles")
    private int ertekeles;

    public int getErtekelesID() {
        return ertekelesID;
    }

    public void setErtekelesID(int ertekelesID) {
        this.ertekelesID = ertekelesID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public int getErtekeles() {
        return ertekeles;
    }

    public void setErtekeles(int ertekeles) {
        this.ertekeles = ertekeles;
    }

    public moviesErtekeles(int userID, int movieID, int ertekeles) {
        this.userID = userID;
        this.movieID = movieID;
        this.ertekeles = ertekeles;
    }

    @Override
    public String toString() {
        return "moviesErtekeles{" +
                "ertekelesID=" + ertekelesID +
                ", userID=" + userID +
                ", movieID=" + movieID +
                ", ertekeles=" + ertekeles +
                '}';
    }

    public moviesErtekeles(){}
}
