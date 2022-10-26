package app;

import java.util.ArrayList;
import java.util.Map;

import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Temporary HTML as an example page.
 * 
 * Based on the Project Workshop code examples.
 * This page currently:
 *  - Provides a link back to the index page
 *  - Displays the list of movies from the Movies Database using the JDBCConnection
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * @author Halil Ali, 2021. email: halil.ali@rmit.edu.au
 */
public class profile implements Handler {

   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/profile.html";

   @Override
   public void handle(Context context) throws Exception {
      // Create a simple HTML webpage in a String
      String html = "<html>\n";

      // Add some Header information
      html = html + "<head>" + "<title>Profile</title>\n";

      // Add some CSS (external file)
      html = html + "<link rel='stylesheet' type='text/css' href='common.css' />\n";

      // Add the body
      html = html + "<body>\n";

      html = html + "<div class='header' ><h1 class='slogan'>Facebook Lite</h1><p class='slogan'>Share your everyday.</div>";

      // Add HTML for the list of pages
      html = html + "<nav>" + "<ul>\n";

      // Link for each page
      html = html + "<li> <a href='/posts.html'>Home</a> </li>\n";
      html = html + "<li> <a href='profile.html'>Profile</a> </li>\n";
      html = html + "<li> <a href='posts.html'>Post</a> </li>\n";
      html = html + "<li> <a href='members.html'>Members</a> </li>\n";
      html = html + "<li> <a href='friends.html'>Friends</a> </li>\n";
      html = html + "<li> <a href='register.html'>Register</a> </li>\n";
      if(context.req.getSession().getAttribute("user") == null) {
         html = html + "<li class='login-nav'> <a href='login.html'>Login</a> </li>\n";
         html = html + "<script>location.href='login.html'</script>";
         } else{
            html = html + "<li class='logout-nav'> <a href='logout.html'>Logout</a> </li>\n";
         }
      html = html + "</ul>"+"</nav>";
      html = html + "<div class='content'>";

      JDBCConnection jdbc = JDBCConnection.getConnection();
      var loginEmail = context.req.getSession().getAttribute("user");
      String LoginEmail = loginEmail.toString();
      ArrayList<Map> user = jdbc.getProfile(LoginEmail);

      for(Map map: user){
        html = html + "<table id='profile-table'>";
        html = html + "<h1>Profile</h1>";

        html = html + "<tr><th>Email:</th>";
        html = html +  "<th>" + LoginEmail + "</th>";

        html = html + "</tr><tr><td>Fullname:</td>";
        html = html +  "<td>" + map.get("fullname") + "</td>";

        html = html + "</tr><tr><td>Screenname: </td>";
        html = html +  "<td>" + map.get("screenname") + "</td>";

        html = html + "</tr><tr><td>Gender: </td>";
        html = html +  "<td>" + map.get("gender") + "</td>";

        html = html + "</tr><tr><td>Date Of Birth: </td>";
        html = html +  "<td>" + map.get("dob") + "</td>";

        html = html + "</tr><tr><td>Location: </td>";
        html = html +  "<td>" + map.get("location") + "</td>";

        html = html + "</tr><tr><td>Visibility: </td>";
        html = html +  "<td>" + map.get("visibility") + "</td>";

        html = html + "</tr>";
        html = html + "</table>";
        html = html + "<a href='/profileEdit.html'><input type='button' class='profile-button' id='profile-update' value='Edit'></a>";



      }
  

      html = html + "</div>";
      // Finish the HTML webpage
      html = html + "</body>" + "</html>\n";

      // DO NOT MODIFY THIS
      // Makes Javalin render the webpage
      context.html(html);
   }



}
