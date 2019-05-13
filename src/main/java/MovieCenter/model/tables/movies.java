package MovieCenter.model.tables;

import javax.persistence.*;
import java.sql.Date;

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

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRendezo() {
        return rendezo;
    }

    public void setRendezo(String rendezo) {
        this.rendezo = rendezo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLeiras() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

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

    public movies(){};
}
