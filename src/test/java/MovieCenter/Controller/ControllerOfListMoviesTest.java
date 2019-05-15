package MovieCenter.Controller;

import MovieCenter.model.dbControl;
import MovieCenter.model.tables.moviesErtekeles;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


public class ControllerOfListMoviesTest {

    private moviesErtekeles elem, elem1, elem2 ,elem3 ,elem4 ,elem5 ,elem6 ,elem7 ,elem8 ,elem9 ,elem10 ,elem11 ,elem12 ,elem13 ,elem14;

    private List<moviesErtekeles> list1;

    private ControllerOfListMovies cflm;

    @BeforeEach
    void setUp() {
        dbControl.initDB();
        cflm = new ControllerOfListMovies();

        elem = new moviesErtekeles(3,0,5);
        elem1 = new moviesErtekeles(1,1,10);
        elem2 = new moviesErtekeles(3,1,8);
        elem3 = new moviesErtekeles(5,1,6);
        elem4 = new moviesErtekeles(6,4,5);
        elem5 = new moviesErtekeles(3,5,10);
        elem6 = new moviesErtekeles(1,6,8);
        elem7 = new moviesErtekeles(5,7,4);
        elem8 = new moviesErtekeles(3,8,8);
        elem9 = new moviesErtekeles(5,9,2);
        elem10 = new moviesErtekeles(6,10,6);
        elem11= new moviesErtekeles(1,11,5);
        elem12= new moviesErtekeles(1,18,10);
        elem13= new moviesErtekeles(3,2,8);
        elem14= new moviesErtekeles(8,0,8);

        list1 = Arrays.asList(elem, elem1, elem2 ,elem3 ,elem4 ,elem5 ,elem6 ,elem7 ,elem8 ,elem9 ,elem10 ,elem11 ,elem12 ,elem13 ,elem14);
    }

    @AfterEach
    void tearDown() {
        cflm = null;

        elem = null;
        elem1 = null;
        elem2 = null;
        elem3 = null;
        elem4 = null;
        elem5 = null;
        elem6 = null;
        elem7 = null;
        elem8 = null;
        elem9 = null;
        elem10 = null;
        elem11 = null;
        elem12 = null;
        elem13 = null;
        elem14 = null;

        list1 = null;
        dbControl.closeDB();
    }

    @Test
    void atlagteszt(){
        assertArrayEquals(new Double[]{6.5,8.0,8.0,0.0,5.0,10.0,8.0,4.0,8.0,2.0,6.0,5.0,0.0,0.0,0.0,0.0,0.0,0.0,10.0,0.0},cflm.atlag(list1, 20), "Rossz válasz!");
    }
}