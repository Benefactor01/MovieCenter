package MovieCenter.model.tables;

import javax.persistence.*;
import java.sql.Date;

/**Class that represents the movies database.*/
@Entity
public class movies {
    @Id
    @Column(name = "movieID")
    private int movieid;

    @Column(name = "cim")
    private String title;

    @Column(name = "rendezo")
    private String rendezo;

	@Column(name = "megjelenes")
    private java.sql.Date date;
	
	@Column(name = "kategoria")
    private String category;

	@Column(name = "leiras")
    private String leiras;

	/**Getter of the movie ID.
     * @return the value of the movie ID.*/
    public int getMovieid() {
        return movieid;
    }

    /**Setter of the movie ID.
     * @param movieid should contain the new ID of the movie.*/
    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    /**Getter of the title.
     * @return the value of the title.*/
    public String getTitle() {
        return title;
    }

    /**Setter of the title.
     * @param title should contain the new title.*/
    public void setTitle(String title) {
        this.title = title;
    }

    /**Getter of the director.
     * @return the director.*/
    public String getRendezo() {
        return rendezo;
    }

    /**Setter of the director.
     * @param rendezo should contain the new director.*/
    public void setRendezo(String rendezo) {
        this.rendezo = rendezo;
    }

    /**Getter of the date.
     * @return the value of the date.*/
    public Date getDate() {
        return date;
    }

    /**Setter of the date.
     * @param date should contain the new date.*/
    public void setDate(Date date) {
        this.date = date;
    }

    /**Setter of the category.
     * @return the value of the category.*/
    public String getCategory() {
        return category;
    }

    /**Setter of the category.
     * @param category should contain the new category.*/
    public void setCategory(String category) {
        this.category = category;
    }

    /**Getter of the description.
     * @return the value of the description.*/
    public String getLeiras() {
        return leiras;
    }

    /**Setter of the description.
     * @param leiras should contain the new description.*/
    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    /**@param movieid is the name of the movie id in the class.
     * @param title is the name of the title in the class.
     * @param rendezo is the name of the director in the class.
     * @param date is the name of the date in the class.
     * @param category is the name of the category in the class.
     * @param leiras is the name of the description in the class.
     */
    public movies(int movieid, String title, String rendezo, Date date, String category, String leiras) {
        this.movieid = movieid;
        this.title = title;
        this.rendezo = rendezo;
        this.date = date;
        this.category = category;
        this.leiras = leiras;
    }

    @Override
    public String toString() {
        return "movies{" +
                "movieid=" + movieid +
                ", title='" + title + '\'' +
                ", rendezo='" + rendezo + '\'' +
                ", date=" + date +
                ", category='" + category + '\'' +
                ", leiras='" + leiras + '\'' +
                '}';
    }

    /**The empty constructor for the movies class.*/
    public movies(){};
}
