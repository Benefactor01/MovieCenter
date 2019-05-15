package MovieCenter.model;

import MovieCenter.model.tables.movies;
import MovieCenter.model.tables.moviesErtekeles;
import MovieCenter.model.tables.users;
import org.tinylog.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

/**Controls the whole database.
 */
public class dbControl {
    private static EntityManagerFactory emf;
    private static EntityManager em;

    /**Initializes the database connection.*/
    public static void initDB(){
        emf = Persistence.createEntityManagerFactory("jpa-persistence-unit-1");
        em = emf.createEntityManager();
        Logger.info("Database connected!");
    }

    /**Closes the database connection.*/
    public static void closeDB(){
        em.close();
        emf.close();
        Logger.info("Database closed!");
    }

    /**Inserts a new user into the database's users table.
     * All {@link String} are given from the {@code Register} view's fields.
     * @param username is the username from the TextField
     * @param password is the password from the PasswordField
     * @param email is the email from the TextField
     */
    public static void createUser(String username, String password, String email){


        users user = new users(username, password, email);

        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**Checks for existing username and password combination.
     * If the given information are correct, it returns 1, and that means
     * you are logged in successfully. The {@code ControllerOfRegister} controls
     * the whole process and all the returned values.
     * @param username is the username from the TextField
     * @param password is the password from the PasswordField
     * @return gives back a number, and the controller handles this number
     * depending on the value.
     */
    public static int login(String username, String password){

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
        return 0;
    }

    /**Makes a list of the movies from the database, and all attributes of the them.
     * @return returns the list of the movies*/
    public static List<movies> getmovies(){
        TypedQuery<movies> query = em.createQuery("SELECT s FROM movies s", movies.class);
        List<movies> result = query.getResultList();

        return result;
    }

    /**Makes a list of the rates from the database.
     * @return returns all the rates*/
    public static List<moviesErtekeles> getallertekeles(){

        TypedQuery<moviesErtekeles> query = em.createQuery("SELECT s FROM moviesErtekeles s", moviesErtekeles.class);
        List<moviesErtekeles> result = query.getResultList();

        return result;
    }

    /**Returns the exact movie with the ID given.
     * @param ID is the movie ID from the database
     * @return returns one of the movies with all of its attributes*/
    public static movies getExactFilm(int ID){

        TypedQuery<movies> query = em.createQuery("SELECT s FROM movies s WHERE s.movieid = '"+ID+"'", movies.class);
        List<movies> result = query.getResultList();

        return result.get(0);
    }

    /**Returns the username of the given name.
     * @param name is the username from the database
     * @return gives back the user ID of the given username*/
    public static int getUserIDfromName(String name){

        TypedQuery<users> query = em.createQuery("SELECT s FROM users s WHERE s.user = '"+name+"'", users.class);
        List<users> result = query.getResultList();

        return result.get(0).getId();
    }

    /**Inserts a new row into the rates. This is handled by the {@code Film} view.
     * @param UserID is the user's ID
     * @param MovieID is the movie's ID
     * @param Rate is the rate you picked on the scroller*/
    public static void newRate(int UserID, int MovieID, int Rate){
        moviesErtekeles ujertekeles = new moviesErtekeles(UserID, MovieID, Rate);

        try {
            em.getTransaction().begin();
            em.persist(ujertekeles);
            em.getTransaction().commit();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**Returns a list of object arrays, containing all the rates.
     * With the help of this query, the program fills the lists on the {@code Film} view.
     * @param ID is the movie ID from the database
     * @return returns all rates from the database*/
    public static List<Object[]> getRateByUsername(int ID){

        List<Object[]> results = em.createQuery("SELECT u.user, m.ertekeles FROM users u, moviesErtekeles m WHERE u.id = m.userID AND m.movieID = '"+ID+"'").getResultList();

        return results;
    }

    /**It is necessary to prevent multiple rates from the same user.
     * Checks if there is already a rate from the logged in user.
     * @param UserID is the UserID
     * @param movieID is the movie's ID
     * @return depends on if the rate is exists or not*/
    public static int ertekelesExists(int UserID, int movieID){

        TypedQuery<moviesErtekeles> query = em.createQuery("SELECT s FROM moviesErtekeles s WHERE s.userID = '"+UserID+"' AND s.movieID ='"+movieID+"'", moviesErtekeles.class);
        List<moviesErtekeles> results = query.getResultList();

        if(results.isEmpty())
            return 0;
        else{
            return 1;
        }
    }
}
