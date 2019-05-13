package MovieCenter.model;

import MovieCenter.model.tables.movies;
import MovieCenter.model.tables.moviesErtekeles;
import MovieCenter.model.tables.users;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;


public class UserController {

    private static EntityManagerFactory emf;
    private static EntityManager em;


    public static void createUser(String username, String password, String email){
        emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
        em = emf.createEntityManager();

        users user = new users(username, password, email);

        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw ex;
        }

        em.close();
        emf.close();

    }

    public static int login(String username, String password){

        emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
        em = emf.createEntityManager();
        em.getTransaction().begin();

        try{
            TypedQuery<users> query = em.createQuery("SELECT s FROM users s WHERE s.user = '"+username+"'", users.class);
            List<users> result = query.getResultList();
            if(result.get(0) != null) {
                try {
                    TypedQuery<users> query2 = em.createQuery("SELECT s FROM users s WHERE s.user = '" + username + "' AND s.pw = '" + password + "'", users.class);
                    List<users> result2 = query2.getResultList();
                    if(result2.get(0) != null){//Sikeres belépés
                        return 1;
                    }
                } catch (IndexOutOfBoundsException c) {//Nem jó jelszó
                    return -1;
                }
            }
        }
        catch (IndexOutOfBoundsException e){//Nincs ilyen felhasználó!
            return -2;
        }

        em.getTransaction().commit();
        em.close();
        emf.close();

        return 0;
    }


    //Filmek listázása
    public static List<movies> getmovies(){
        emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
        em = emf.createEntityManager();
        em.getTransaction().begin();

        TypedQuery<movies> query = em.createQuery("SELECT s FROM movies s", movies.class);
        List<movies> result = query.getResultList();

        em.getTransaction().commit();
        em.close();
        emf.close();

        return result;
    }

    public static List<moviesErtekeles> getallertekeles(){

        emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
        em = emf.createEntityManager();
        em.getTransaction().begin();

        TypedQuery<moviesErtekeles> query = em.createQuery("SELECT s FROM moviesErtekeles s", moviesErtekeles.class);
        List<moviesErtekeles> result = query.getResultList();

        em.getTransaction().commit();
        em.close();
        emf.close();

        return result;
    }

    public static movies getExactFilm(int ID){
        emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
        em = emf.createEntityManager();
        em.getTransaction().begin();

        TypedQuery<movies> query = em.createQuery("SELECT s FROM movies s WHERE s.movieid = '"+ID+"'", movies.class);
        List<movies> result = query.getResultList();

        em.getTransaction().commit();
        em.close();
        emf.close();

        return result.get(0);
    }

    public static int getUserIDfromName(String name){
        emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
        em = emf.createEntityManager();
        em.getTransaction().begin();

        TypedQuery<users> query = em.createQuery("SELECT s FROM users s WHERE s.user = '"+name+"'", users.class);
        List<users> result = query.getResultList();

        em.close();
        emf.close();

        return result.get(0).getId();
    }

    public static void newRate(int UserID, int MovieID, int Rate){
        emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
        em = emf.createEntityManager();

        moviesErtekeles ujertekeles = new moviesErtekeles(UserID, MovieID, Rate);

        try {
            em.getTransaction().begin();
            em.persist(ujertekeles);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw ex;
        }

        em.close();
        emf.close();
    }

    public static List<Object[]> getRateByUsername(int ID){
        emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
        em = emf.createEntityManager();
        em.getTransaction().begin();

        List<Object[]> results = em.createQuery("SELECT u.user, m.ertekeles FROM users u, moviesErtekeles m WHERE u.id = m.userID AND m.movieID = '"+ID+"'").getResultList();


        //TypedQuery<users> query = em.createQuery("SELECT s FROM users s JOIN moviesErtekeles ON users.id = moviesErtekeles.userID WHERE s.id = '"+ID+"'", users.class);
        //List<users> result = query.getResultList();

        em.close();
        emf.close();
        return results;
    }

    public static int ertekelesExists(int UserID, int movieID){
        emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
        em = emf.createEntityManager();

        TypedQuery<moviesErtekeles> query = em.createQuery("SELECT s FROM moviesErtekeles s WHERE s.userID = '"+UserID+"' AND s.movieID ='"+movieID+"'", moviesErtekeles.class);
        List<moviesErtekeles> results = query.getResultList();

        em.close();
        emf.close();
        if(results.isEmpty())
            return 0;
        else{
            return 1;
        }
    }
}
