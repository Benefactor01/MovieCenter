package MovieCenter.model.tables;

import javax.persistence.*;

/**Class that represent the rates in the database.*/
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

    /**Getter of the rate ID.
     * @return returns the ID of the rate.*/
    public int getErtekelesID() { return ertekelesID; }

    /**Setter of the rate ID.
     * @param ertekelesID is the new value of the rate.*/
    public void setErtekelesID(int ertekelesID) { this.ertekelesID = ertekelesID; }

    /**Getter of the user ID.
     * @return the value of user ID.*/
    public int getUserID() { return userID; }

    /**Setter of the user ID.
     * @param userID is the new value of the user ID.*/
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**Getter of the movie ID.
     * @return the ID of the movie.*/
    public int getMovieID() { return movieID; }

    /**Setter of the movie ID.
     * @param movieID is the ID of the movie.*/
    public void setMovieID(int movieID) { this.movieID = movieID; }

    /**Getter of the rate.
     * @return the value of rate.*/
    public int getErtekeles() {
        return ertekeles;
    }

    /**Setter of the rate.
     * @param ertekeles is the new value of the rate.*/
    public void setErtekeles(int ertekeles) {
        this.ertekeles = ertekeles;
    }

    /**Constructor for the rates.
     * @param userID is the user ID.
     * @param movieID is the movie ID.
     * @param ertekeles is the rate on the movie.*/
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

    /**Empty constructor for the rates.*/
    public moviesErtekeles(){}
}
