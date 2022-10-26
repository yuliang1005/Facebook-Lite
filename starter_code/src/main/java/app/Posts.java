package app;

import java.util.ArrayList;
import java.util.Map;

import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin by writing the raw HTML into a Java
 * String object
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class Posts implements Handler {

   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/posts.html";


   @Override
   public void handle(Context context) throws Exception {
      // Create a simple HTML webpage in a String
      String html = "<html>\n";

      // Add some Header information
      html = html + "<head>" + "<title>Post</title>\n";

      // Add some CSS (external file)
      html = html + "<link rel='stylesheet' type='text/css' href='common.css' />\n";

      // Add the body
      html = html + "<body>\n";

      // Add the header
      html = html + "<div class='header' ><h1 class='slogan'>Facebook Lite</h1><p class='slogan'>Share your everyday.</div>";

      // Add HTML for the list of pages
      html = html + "<nav>" + "<ul>\n";

      // Link for each page
      html = html + "<li> <a href='/addposts.html'>Home</a> </li>\n";
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

      // Finish the HTML webpage
      html = html + "<div class='content'>";

      html = html + "<input type='button' id='add-post-button' class='user-button' value='Add a Post' onclick=showPostForm()>";
      html = html + "<script>function showPostForm(){ document.getElementById('post-form').style.display='block'}</script>";  

      html = html + "<form id='post-form' action='/addposts.html' method='post'>";
      html = html + "<label for='post-content'> Please input your post here: </label><br><br>";
      html = html + "<textarea id='post-content' name='post-content' required></textarea><br><br>";
      html = html + "<input type='submit' value='post' class='user-button'>";
      html = html + "</form>";
     
      JDBCConnection jdbc = JDBCConnection.getConnection();
      var loginEmail = context.req.getSession().getAttribute("user");
      String LoginEmail = loginEmail.toString();

      ArrayList<Map> post = jdbc.getPostEmail(LoginEmail);

      html = html + "<div>\n";

      // Finally we can print out all of the movies
      for (Map map : post) {
         html = html + "<div class='post-box'>\n";
         html = html + "<label class='post-label'>" + map.get("screenName") +"</label>";
         html = html + "<p class='post-timestamp'>"+ map.get("postTimeStamp")+"</p>";
         html = html + "<div class='post-details'>"+ map.get("body") + "</div>";
         // html = html + "<div class='friend-box'>" + email + "</div>\n";
         // html = html + "<div class='friend-box'>" + email + "</div>\n";
         // html = html + "<div class='friend-box'>" + email + "</div>\n";
         html = html + "</div>\n";
      }

      // Finish the List HTML
     html = html + "</div>\n";


      html = html + "</div>";
      html = html + "</body>" + "</html>\n";

      // DO NOT MODIFY THIS
      // Makes Javalin render the webpage
      context.html(html);
   }

}
