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
public class profileEdit implements Handler {

   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/profileEdit.html";

   @Override
   public void handle(Context context) throws Exception {
      // Create a simple HTML webpage in a String
      String html = "<html>\n";

      // Add some Header information
      html = html + "<head>" + "<title>Edit Profile</title>\n";

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
         html = html + "<form action='/profileEdit.html' method='post' class='user-form' id='register-form'>";
      html = html + "<h1>Edit Profile</h1>\n";

      html = html + "<div class='login-box'>";
      html = html + "<label for='fullname' class='control-label'>Fullname:</label><br>\n";
      html = html + "<input type='text' class='user-input' id='fullname' name='fullname' value='" + map.get("fullname") +"' required><br><br>\n";
      html = html + "</div>";

      html = html + "<div class='login-box'>";
      html = html + "<label for='screenname' class='control-label'>Screen name:</label><br>\n";
      html = html + "<input type='text' class='user-input' id='screenname' name='screenname'value='" + map.get("screenname") +"' required><br><br>\n";
      html = html + "</div>";

      html = html + "<div class='login-box'>";
      html = html + "<label for='dob' class='control-label'>Date of birth:</label><br>\n";
      html = html + "<input type='date' class='user-input' id='dob' name='dob' value='" + map.get("dob") +"' required><br><br>\n";
      html = html + "</div>";

      html = html + "<div class='login-box'>";
      html = html + "<label for='gender' class='control-label'>Gender:</label><br>\n";
      html = html + "<select id='gender' class='user-input' name='gender' value='" + map.get("gender") +"'>\n";
      html = html + "<option value='Male'>Male</option>";
      html = html + "<option value='Female'>Female</option>";
      html = html + "</select><br><br>";
      html = html + "</div>";

      html = html + "<div class='login-box'>";
      html = html + "<label for='location' class='control-label'>Location:</label><br>\n";
      html = html + "<input type='text' class='user-input' id='location' name='location' value='" + map.get("location") +"' required><br><br>\n";
      html = html + "</div>";

      html = html + "<div class='login-box'>";
      html = html + "<label for='visibility' class='control-label'>Visibility:</label><br>\n";
      html = html + "<select id='visibility' class='user-input' name='visibility' value='" + map.get("visibility") +"'>\n";
      html = html + "<option value='public'>public</option>";
      html = html + "<option value='friends-only'>friends-only</option>";
      html = html + "<option value='private'>private</option>";
      html = html + "</select><br><br>";
      html = html + "</div>";

      html = html + "<div class='login-box'>";
      html = html + "<label for='password' class='control-label'>Password:</label><br>\n";
      html = html + "<input type='password' class='user-input' id='password' name='password' value='" + map.get("password") +"' required><br><br>\n";
      html = html + "</div><br><br>";

      html = html + "<input type='submit' class='user-button' id='register-submit' value='Save changes'>\n";

      }
      String fullname = context.formParam("fullname");
      String screenname = context.formParam("screenname"); 
      String gender = context.formParam("gender");
      String dob = context.formParam("dob");
      String location = context.formParam("location");
      String visibility = context.formParam("visibility");
      String password = context.formParam("password");
      jdbc.updateUser(LoginEmail, fullname, screenname, gender, dob, location, visibility, password);
      
      if(screenname != null){
         var update = jdbc.updateUser(LoginEmail, fullname, screenname, gender, dob, location, visibility, password);
         if(update == null){
               html = html + "<div></div>";
         }else{
            html = html + updateSuccessful(LoginEmail);
               }
   }


      html = html + "</div>";
      // Finish the HTML webpage
      html = html + "</body>" + "</html>\n";

      // DO NOT MODIFY THIS
      // Makes Javalin render the webpage
      context.html(html);
   }

   public String updateSuccessful(String email){
      String html = "";
      html = html + "<script>alert('Update Successful!')</script>";
      html = html + "<script>location.href='profile.html'</script>";
      return html;
   }

}
