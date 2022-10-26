package app;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.sql.Timestamp; 

/**
 * Class for Managing the JDBC Connection to a SQLLite Database. Allows SQL
 * queries to be used with the SQLLite Databse in Java.
 * 
 * This is an example JDBC Connection that has a single query for the Movies
 * Database This is similar to the project workshop JDBC examples.
 *
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Halil Ali, 2021. email halil.ali@rmit.edu.au
 */
public class JDBCConnection {

   // the default oracle account uses the the read only MOVIES database
   // once you create a set of tables in your own account, update this to your RMIT
   // Oracle account details
   private static final String DATABASE_USERNAME = "s3704446";
   private static final String DATABASE_PASSWORD = "Lmjlyl123@";

   private static final String DATABASE_URL = "jdbc:oracle:thin:@//localhost:9922/CSAMPR1.its.rmit.edu.au";
   private static JDBCConnection jdbc = null;
   private static Connection connection;
   SimpleDateFormat sDateFormat=new SimpleDateFormat("dd/MM/yyyy"); 
   String date=sDateFormat.format(new Date());  
   
   Timestamp timestamp = new Timestamp(System.currentTimeMillis()); 

   /**
   * Singleton function to return single copy of this class to other classes
   **/
   public static JDBCConnection getConnection(){

      //check that ssh session is still open (if not reopen)
      SSHTunnel.getSession();

      //check that JDBCconnection is available (if not establish)
      if(jdbc==null){
         jdbc = new JDBCConnection();
      }
      return jdbc;
   }

   /**
   * Hidden constructor to establish Database connection (once)
   **/
   private JDBCConnection() {
      System.out.println("Created JDBC Connection Object");
      
      try {
         // Connect to JDBC data base
         connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
      } catch (SQLException e) {
         // If there is an error, lets just print the error
         System.err.println(e.getMessage());
      }
   }

   /**
   * Closes the database connection - called only when server shutdown
   **/
   public static void closeConnection(){
      try {
         if (connection != null) {
            connection.close();
            System.out.println("Database Connection closed");
         }
      } catch (SQLException e) {
         // connection close failed.
         System.err.println(e.getMessage());
      }
   }

   /**
    * Get all of the Movies in the database
    */
   public ArrayList<String> getMovies() {
      ArrayList<String> movies = new ArrayList<String>();

      try {
         // Prepare a new SQL Query & Set a timeout
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);

         // The Query
         String query = "SELECT *"   + "\n" +
                        "FROM movie" ;

         // Get Result
         ResultSet results = statement.executeQuery(query);

         // Process all of the results
         // The "results" variable is similar to an array
         // We can iterate through all of the database query results
         while (results.next()) {
            // We can lookup a column of the a single record in the
            // result using the column name
            // BUT, we must be careful of the column type!
            // int id = results.getInt("mvnumb");
            String movieName = results.getString("mvtitle");
            // int year = results.getInt("yrmde");
            // String type = results.getString("mvtype");

            // For now we will just store the movieName and ignore the id
            movies.add(movieName);
         }

         // Close the statement because we are done with it
         statement.close();
      } catch (SQLException e) {
         // If there is an error, lets just print the error
         System.err.println(e.getMessage());
      }

      // Finally we return all of the movies
      return movies;
   }

   public ArrayList<String> setUsers(String fullname,String email,String screenname, String gender,String dob, String location, String password) {
      ArrayList<String> users = new ArrayList<String>();

      // Setup the variable for the JDBC connection
      //Connection connection = null;

      try {
         // Prepare a new SQL Query & Set a timeout
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);

         // The Query
         
         String query = "insert into MEMBER (FULLNAME, EMAIL, SCREENNAME, GENDER, DATEOFBIRTH, LOCATION, PASSWORD, STATUS, VISIBILITY) VALUES ("   + "\n" +
                           "'" + fullname + "','" + email + "','" + screenname + "','" + gender + "','" + dob + "','" + location + "','" + password + "','" + "offline" + "','" + "public" + "')";

         // Get Result
         statement.executeQuery(query);


         // Close the statement because we are done with it
         statement.close();
      } catch (SQLException e) {
         // If there is an error, lets just pring the error
         System.err.println(e.getMessage());
      }

      // Finally we return all of the movies
      return users;
   }

   public ArrayList<String> getUsers(String email, String password) {
      ArrayList<String> users = new ArrayList<String>();

      try {
         // Prepare a new SQL Query & Set a timeout
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);

         // The Query
         String query = "SELECT *"   + "\n" +
                        "FROM MEMBER" + "\n" +
                        "WHERE EMAIL='" + email + "' AND PASSWORD='" + password + "'";

         // Get Result
         statement.executeQuery(query);
         ResultSet results = statement.executeQuery(query);
         if (results.next()) {
            String loginEmail = results.getString("email");
            users.add(loginEmail);

            String loginPassword = results.getString("password");
            users.add(loginPassword);

            String loginFullname = results.getString("fullname");
            users.add(loginFullname);

            String loginScreenname = results.getString("screenname");
            users.add(loginScreenname);

            String loginDob = results.getString("dateofbirth");
            users.add(loginDob);

            String loginGender = results.getString("gender");
            users.add(loginGender);

            String loginStatus = results.getString("status");
            users.add(loginStatus);

            String loginLocation = results.getString("location");
            users.add(loginLocation);

            String loginVisibility = results.getString("visibility");
            users.add(loginVisibility);

            return users;
         }

         statement.close();
      } catch (SQLException e) {
         // If there is an error, lets just print the error
         System.err.println(e.getMessage());
      }

      // Finally we return all of the movies
      return users;
   }

   public ArrayList<Map> getProfile(String email) {
      ArrayList<Map> user = new ArrayList<>();

      // Setup the variable for the JDBC connection
      //Connection connection = null;

      try {
         // Prepare a new SQL Query & Set a timeout
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);

         // The Query
         String query = "select * from MEMBER WHERE email='" + email + "'";
         System.out.println(query);

         // Get Result
         ResultSet results = statement.executeQuery(query);

         // Process all of the results
         while (results.next()) {
            Map map = new HashMap();
            String fullname= results.getString("fullname");
            String screenname = results.getString("screenname");
            String dob = results.getString("dateOfBirth");
            String gender = results.getString("gender");
            String location = results.getString("location");
            String visibility = results.getString("visibility");
            String password = results.getString("password");

            map.put("fullname", fullname);
            map.put("screenname", screenname);
            map.put("dob", dob);
            map.put("gender", gender);
            map.put("location", location);
            map.put("visibility", visibility);
            map.put("password", password);
            user.add(map);
         }

         // Close the statement because we are done with it
         statement.close();
      } catch (SQLException e) {
         // If there is an error, lets just pring the error
         System.err.println(e.getMessage());
      }

      // Finally we return all of the movies
      return user;
   }

   public ArrayList<String> updateUser(String email, String fullname, String screenname, String gender, String dob, String location, String visibility, String password) {
      ArrayList<String> users = new ArrayList<String>();

      // Setup the variable for the JDBC connection
      //Connection connection = null;

      try {
         // Prepare a new SQL Query & Set a timeout
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);

         // The Query
         
         String query = "UPDATE MEMBER SET FULLNAME='" + fullname + "', SCREENNAME ='" + screenname +"', DATEOFBIRTH ='" + "\n" +
         dob + "', GENDER ='" + gender + "', LOCATION='" + location +"', VISIBILITY ='" + visibility + "', PASSWORD ='" + password +"' \n" +
         "WHERE EMAIL='"+ email + "'";

         // Get Result
         statement.executeQuery(query);


         // Close the statement because we are done with it
         statement.close();
      } catch (SQLException e) {
         // If there is an error, lets just pring the error
         System.err.println(e.getMessage());
      }

      // Finally we return all of the movies
      return users;
   } 

   public ArrayList<String> UserLogin(String email) {
      ArrayList<String> users = new ArrayList<String>();

      // Setup the variable for the JDBC connection
      //Connection connection = null;

      try {
         // Prepare a new SQL Query & Set a timeout
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);

         // The Query
         
         String query = "UPDATE MEMBER SET STATUS='online' WHERE EMAIL='"+ email + "'";

         // Get Result
         statement.executeQuery(query);


         // Close the statement because we are done with it
         statement.close();
      } catch (SQLException e) {
         // If there is an error, lets just pring the error
         System.err.println(e.getMessage());
      }

      // Finally we return all of the movies
      return users;
   } 
   public ArrayList<String> UserLogout(String email) {
      ArrayList<String> users = new ArrayList<String>();

      // Setup the variable for the JDBC connection
      //Connection connection = null;

      try {
         // Prepare a new SQL Query & Set a timeout
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);

         // The Query
         
         String query = "UPDATE MEMBER SET STATUS='offline' WHERE EMAIL='"+ email + "'";

         // Get Result
         statement.executeQuery(query);


         // Close the statement because we are done with it
         statement.close();
      } catch (SQLException e) {
         // If there is an error, lets just pring the error
         System.err.println(e.getMessage());
      }

      // Finally we return all of the movies
      return users;
   } 

   public ArrayList<String> searchUsers(String email) {
      ArrayList<String> users = new ArrayList<String>();

      // Setup the variable for the JDBC connection
      //Connection connection = null;

      try {
         // Prepare a new SQL Query & Set a timeout
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);

         // The Query
         String query = "SELECT *"   + "\n" +
                        "FROM MEMBER"  + "\n" +
                        "WHERE email = '" + email + "' AND VISIBILITY = 'public' ";
         System.out.println(query);

         // Get Result
         ResultSet results = statement.executeQuery(query);

         // Process all of the results
         while (results.next()) {
            String resultScreenname = results.getString("screenname");
            users.add(resultScreenname);
         }

         // Close the statement because we are done with it
         statement.close();
      } catch (SQLException e) {
         // If there is an error, lets just pring the error
         System.err.println(e.getMessage());
      }

      // Finally we return all of the movies
      return users;
   }

   public ArrayList<String> friendRequest(String email, String loginEmail) {
      ArrayList<String> users = new ArrayList<String>();

      // Setup the variable for the JDBC connection
      //Connection connection = null;

      try {
         // Prepare a new SQL Query & Set a timeout
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);

         // The Query
         
         String query = "insert into FRIENDSREQUEST (MEMBERREQUESTEREMAIL, MEMBERRECEPIENTEMAIL, REQUESTDATE, REQUESTSTATUS) VALUES ("   + "\n" +
                           "'" + loginEmail+ "','" + email + "','" + date + "','" + "N" +  "')";

         // Get Result
         statement.executeQuery(query);


         // Close the statement because we are done with it
         statement.close();
      } catch (SQLException e) {
         // If there is an error, lets just pring the error
         System.err.println(e.getMessage());
      }

      // Finally we return all of the movies
      return users;
   }

   public ArrayList<String> approveRequest(String email) {
      ArrayList<String> user = new ArrayList<String>();

      // Setup the variable for the JDBC connection
      //Connection connection = null;

      try {
         // Prepare a new SQL Query & Set a timeout
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);

         // The Query
         String query = "SELECT *"                                        + "\n" +
                        "FROM FRIENDSREQUEST"                                      + "\n" +
                        "RIGHT JOIN MEMBER ON FRIENDSREQUEST.MEMBERREQUESTEREMAIL = MEMBER.EMAIL\n" +
                        "WHERE MEMBERRECEPIENTEMAIL='" + email + "' AND REQUESTSTATUS ='N'";
         System.out.println(query);

         // Get Result
         ResultSet results = statement.executeQuery(query);

         // Process all of the results
         while (results.next()) {
            String requestEmail = results.getString("email");
            user.add(requestEmail);
         }

         // Close the statement because we are done with it
         statement.close();
      } catch (SQLException e) {
         // If there is an error, lets just pring the error
         System.err.println(e.getMessage());
      }

      // Finally we return all of the movies
      return user;
   }

   public ArrayList<String> saveFriends(String email, String loginEmail) {
      ArrayList<String> users = new ArrayList<String>();

      // Setup the variable for the JDBC connection
      //Connection connection = null;

      try {
         // Prepare a new SQL Query & Set a timeout
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);

         // The Query
         
         String query = "insert into FRIENDSWITH(MEMBERONEEMAIL, MEMBERTWOEMAIL, STARTDATE) VALUES ("   + "\n" +
         "'" + loginEmail+ "','" + email + "','" + date +  "')";


         // Get Result
         statement.executeQuery(query);

         // Close the statement because we are done with it
         statement.close();
      } catch (SQLException e) {
         // If there is an error, lets just pring the error
         System.err.println(e.getMessage());
      }

      // Finally we return all of the movies
      return users;
   }
   public ArrayList<String> updateRequestStatus(String email, String loginEmail) {
      ArrayList<String> users = new ArrayList<String>();

      // Setup the variable for the JDBC connection
      //Connection connection = null;

      try {
         // Prepare a new SQL Query & Set a timeout
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);

         // The Query
         
         String query = "UPDATE FRIENDSREQUEST SET REQUESTSTATUS='P' WHERE MEMBERREQUESTEREMAIL='" + email + "' AND MEMBERRECEPIENTEMAIL=\n" +
         "'" + loginEmail + "'";

         // Get Result
         statement.executeQuery(query);


         // Close the statement because we are done with it
         statement.close();
      } catch (SQLException e) {
         // If there is an error, lets just pring the error
         System.err.println(e.getMessage());
      }

      // Finally we return all of the movies
      return users;
   }

   public ArrayList<String> deleteRequest(String email, String loginEmail) {
      ArrayList<String> users = new ArrayList<String>();

      // Setup the variable for the JDBC connection
      //Connection connection = null;

      try {
         // Prepare a new SQL Query & Set a timeout
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);

         // The Query
         
         String query = "DELETE FROM FRIENDSREQUEST WHERE MEMBERREQUESTEREMAIL='" + email + "' AND MEMBERRECEPIENTEMAIL=\n" +
         "'" + loginEmail + "'";

         // Get Result
         statement.executeQuery(query);


         // Close the statement because we are done with it
         statement.close();
      } catch (SQLException e) {
         // If there is an error, lets just pring the error
         System.err.println(e.getMessage());
      }

      // Finally we return all of the movies
      return users;
   }


   public ArrayList<Map> getFriends(String email) {
      ArrayList<Map> friends = new ArrayList<>();

      // Setup the variable for the JDBC connection
      //Connection connection = null;

      try {
         // Prepare a new SQL Query & Set a timeout
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);

         // The Query
         String query = "select email, screenname, visibility, status FROM FRIENDSWITH, MEMBER \n" +
         "WHERE (friendswith.memberoneemail = '" + email + "' and friendswith.membertwoemail=MEMBER.email) \n" +
         "OR (friendswith.membertwoemail = '" + email +"' and friendswith.memberoneemail=MEMBER.email) \n" +
         "AND VISIBILITY = 'public' AND VISIBILITY = 'friends-only' ORDER BY status DESC";
         System.out.println(query);

         // Get Result
         ResultSet results = statement.executeQuery(query);

         // Process all of the results
         while (results.next()) {
            Map map = new HashMap();
            String friendName= results.getString("screenname");
            String friendStatus = results.getString("status");

            map.put("friendName", friendName);
            map.put("friendStatus", friendStatus);
            friends.add(map);
         }

         // Close the statement because we are done with it
         statement.close();
      } catch (SQLException e) {
         // If there is an error, lets just pring the error
         System.err.println(e.getMessage());
      }

      // Finally we return all of the movies
      return friends;
   }

   public ArrayList<String> addPost(String email, String postContent) {
      ArrayList<String> post = new ArrayList<String>();

      // Setup the variable for the JDBC connection
      //Connection connection = null;

      try {
         // Prepare a new SQL Query & Set a timeout
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);

         // The Query
         
         String query = "insert into POST(POSTTIMESTAMP, BODY, EMAIL) VALUES ("   + "\n" +
         "'" + new Timestamp(System.currentTimeMillis()) + "','" + postContent + "','" + email +  "')";


         // Get Result
         statement.executeQuery(query);

         // Close the statement because we are done with it
         statement.close();
      } catch (SQLException e) {
         // If there is an error, lets just pring the error
         System.err.println(e.getMessage());
      }

      // Finally we return all of the movies
      return post;
   }

   public ArrayList<Map> getPostEmail(String email) {
      ArrayList<Map> posts = new ArrayList<>();

      // Setup the variable for the JDBC connection
      //Connection connection = null;

      try {
         // Prepare a new SQL Query & Set a timeout
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);

         // The Query
         // String query = "select member.email, screenname, status, POSTTIMESTAMP, BODY FROM FRIENDSWITH, MEMBER, POST\n" + 
         // "WHERE (friendswith.memberoneemail = '" + email + "' and friendswith.membertwoemail=MEMBER.email)\n" + 
         // "OR (friendswith.membertwoemail = '" + email + "' and friendswith.memberoneemail=MEMBER.email)\n" +
         // "OR MEMBER.email ='" + email + "'\n" +
         // "AND MEMBER.email = post.email";

         String query = "select member.email as EMAIL, SCREENNAME, POSTTIMESTAMP, BODY FROM POST"+
         " left join MEMBER ON POST.EMAIL=MEMBER.EMAIL"+
         " where member.email='"+email+"' or member.email IN"+ 
         " (select MEMBERTWOEMAIL from FRIENDSWITH where MEMBERONEEMAIL='"+email+"')" +
         "  or member.email IN"+ 
         " (select MEMBERONEEMAIL from FRIENDSWITH where MEMBERTWOEMAIL='"+email+"') order by POSTTIMESTAMP DESC";
         System.out.println(query);

         // Get Result
         ResultSet results = statement.executeQuery(query);

         // Process all of the results
         while (results.next()) {
            Map map = new HashMap();
            String screenname= results.getString("SCREENNAME");
            String postTimeStamp= results.getString("POSTTIMESTAMP");
            String body= results.getString("BODY");

            map.put("screenName", screenname);
            map.put("postTimeStamp", postTimeStamp);
            map.put("body", body);

            posts.add(map);
         }

         // Close the statement because we are done with it
         statement.close();
      } catch (SQLException e) {
         // If there is an error, lets just pring the error
         System.err.println(e.getMessage());
      }

      // Finally we return all of the movies
      return posts;
   }

   
   /**
    * Get all the movies in the database by a given type. Note this takes a string
    * of the type as an argument! This has been implemented for you as an example.
    * HINT: you can use this to find all of the horror movies!
    */
   public ArrayList<String> getMoviesByType(String movieType) {
      ArrayList<String> movies = new ArrayList<String>();

      // Setup the variable for the JDBC connection
      //Connection connection = null;

      try {
         // Prepare a new SQL Query & Set a timeout
         Statement statement = connection.createStatement();
         statement.setQueryTimeout(30);

         // The Query
         String query = "SELECT *"                                        + "\n" +
                        "FROM movie"                                      + "\n" +
                        "WHERE LOWER(mvtype) = LOWER('" + movieType + "')";
         System.out.println(query);

         // Get Result
         ResultSet results = statement.executeQuery(query);

         // Process all of the results
         while (results.next()) {
            String movieName = results.getString("mvtitle");
            movies.add(movieName);
         }

         // Close the statement because we are done with it
         statement.close();
      } catch (SQLException e) {
         // If there is an error, lets just pring the error
         System.err.println(e.getMessage());
      }

      // Finally we return all of the movies
      return movies;
   }
}
