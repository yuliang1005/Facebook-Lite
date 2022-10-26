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
public class friends implements Handler {

   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/friends.html";

   @Override
   public void handle(Context context) throws Exception {
      // Create a simple HTML webpage in a String
      String html = "<html>\n";

      // Add some Header information
      html = html + "<head>" + "<title>Friends</title>\n";

      // Add some CSS (external file)
      html = html + "<link rel='stylesheet' type='text/css' href='common.css' />\n";

      // Add the body
      html = html + "<body>\n";

      // Add the header
      html = html + "<div class='header' ><h1 class='slogan'>Facebook Lite</h1><p class='slogan'>Share your everyday.</div>";

      // Add HTML for the list of pages
      html = html + "<nav>" + "<ul>\n";

      // Link for each page
      html = html + "<li> <a href='/'>Home</a> </li>\n";
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

      // Finish the List HTML
      html = html + "</ul>"+"</nav>";

      // Add HTML for link back to the homepage
      html = html + "<div class='content'>";
      html = html + "<h1>Friends</h1>\n";
      // Look up some information from JDBC
      // First we need to use your JDBCConnection class
      JDBCConnection jdbc = JDBCConnection.getConnection();

      // Next we will ask this *class* for the movies
      var loginEmail = context.req.getSession().getAttribute("user");
      String LoginEmail = loginEmail.toString();
      ArrayList<String> approveRequest = jdbc.approveRequest(LoginEmail);
      ArrayList<Map> friends = jdbc.getFriends(LoginEmail);

      html = html + "<div id='request-list'>";
      html = html + "<h3>Friend Request</h3>\n";

      if(approveRequest == null){
         html = html + "<p>No request</p>";
      }else{
      for (String friend : approveRequest) {
         html = html +  "<div id='request-box'>";
         html = html + "<div id='request-label'>" + friend;
         html = html + "<input type='button' onclick=requestReject() value='x' class='request-process' id='request-x'>";
         html = html + "<input type='button' onclick=requestPass() value='+' class='request-process' id='request-plus'>";
         html = html + "<div></div>";
         context.req.getSession().setAttribute("friendRequest", friend);
         html = html + "<script>function requestPass(){window.location.href='requestPass.html'}</script>";
         html = html + "<script>function requestReject(){window.location.href='requestReject.html'}</script>";
         }
      }
     


      html = html + "</div>";

     

      // Add HTML for the movies list
      html = html + "<div>\n";
      
      // Finally we can print out all of the movies
      for (Map map : friends) {
         html = html + "<li class='friend-box'>";
         html = html + "<div class='friend-name'>"+ map.get("friendName") + "</div><div class='friend-status'>" + map.get("friendStatus")+ "</div>\n";
         html = html + "</li>\n";
      }

      // Finish the List HTML
      
     html = html + "</div>\n";
      html = html + "</div>";

      // Finish the HTML webpage
      html = html + "</body>" + "</html>\n";

      // DO NOT MODIFY THIS
      // Makes Javalin render the webpage
      context.html(html);
   }

}
