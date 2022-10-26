package app;

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
public class register implements Handler {

   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/register.html";

   @Override
   public void handle(Context context) throws Exception {
      // Create a simple HTML webpage in a String
      String html = "<html>\n";

      // Add some Header information
      html = html + "<head>" + "<title>Register</title>\n";

      // Add some CSS (external file)
      html = html + "<link rel='stylesheet' type='text/css' href='common.css' />\n";

      // Add the body
      html = html + "<body>\n";    

      // Add HTML for link back to the homepage
      html = html + "<div class='content'>";
      

      // Look up some information from JDBC
      // First we need to use your JDBCConnection class
      JDBCConnection jdbc = JDBCConnection.getConnection();

      html = html + "<form action='/register' method='post' class='user-form' id='register-form'>";
      html = html + "<h1>Sign up</h1>\n";

      html = html + "<div class='login-box'>";
      html = html + "<label for='fullname' class='control-label'>Fullname:</label><br>\n";
      html = html + "<input type='text' class='user-input' id='fullname' name='fullname' required><br><br>\n";
      html = html + "</div>";

      html = html + "<div class='login-box'>";
      html = html + "<label for='email' class='control-label'>Email:</label><br>\n";
      html = html + "<input type='email' class='user-input' id='email' name='email' required><br><br>\n";
      html = html + "</div>";

      html = html + "<div class='login-box'>";
      html = html + "<label for='screenname' class='control-label'>Screen name:</label><br>\n";
      html = html + "<input type='text' class='user-input' id='screenname' name='screenname' required><br><br>\n";
      html = html + "</div>";

      html = html + "<div class='login-box'>";
      html = html + "<label for='dob' class='control-label'>Date of birth:</label><br>\n";
      html = html + "<input type='date' class='user-input' id='dob' name='dob' required><br><br>\n";
      html = html + "</div>";

      html = html + "<div class='login-box'>";
      html = html + "<label for='gender' class='control-label'>Gender:</label><br>\n";
      html = html + "<select id='gender' class='user-input' name='gender'>\n";
      html = html + "<option value='Male'>Male</option>";
      html = html + "<option value='Female'>Female</option>";
      html = html + "</select><br><br>";
      html = html + "</div>";

      html = html + "<div class='login-box'>";
      html = html + "<label for='location' class='control-label'>Location:</label><br>\n";
      html = html + "<input type='text' class='user-input' id='location' name='location' required><br><br>\n";
      html = html + "</div>";

      html = html + "<div class='login-box'>";
      html = html + "<label for='password' class='control-label'>Password:</label><br>\n";
      html = html + "<input type='password' class='user-input' id='password' name='password' required><br><br>\n";
      html = html + "</div><br><br>";

      html = html + "<input type='submit' class='user-button' id='register-submit' value='Register'>\n";
      if(context.req.getSession().getAttribute("user") != null) {
            html = html + "<a href='/'>Back</a>\n";
         }else{
            html = html + "<a href='/login.html'>Back</a>\n";
         }

      html = html + "</form>";

      String fullname = context.formParam("fullname");
      String email = context.formParam("email");
      String screenname = context.formParam("screenname"); 
      String gender = context.formParam("gender");
      String dob = context.formParam("dob");
      String location = context.formParam("location");
      String password = context.formParam("password");
      jdbc.setUsers(fullname, email, screenname, gender, dob, location, password);

      if(email != null){
         var user = jdbc.setUsers(fullname, email, screenname, password, gender, dob, location);

         if(user == null){
               html = html + "<div>Register failed: please check your information.</div>";
         }else{
            context.req.getSession().setAttribute("user", email);
            html = html + ValidateSuccess(email);
               }
   }
      html = html + "</body></html>";


      // DO NOT MODIFY THIS
      // Makes Javalin render the webpage
      context.html(html);
   }

   public String ValidateSuccess(String email){
      String html = "";
      html = html + "<script>alert('Register Successful!')</script>";
      html = html + "<script>location.href='/'</script>";
      return html;
   }

}
