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
public class login implements Handler {

   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/login.html";

   @Override
   public void handle(Context context) throws Exception {
      // Create a simple HTML webpage in a String
      String html = "<html>\n";

      // Add some Header information
      html = html + "<head>" + "<title>Login</title>\n";

      // Add some CSS (external file)
      html = html + "<link rel='stylesheet' type='text/css' href='common.css' />\n";

      // Add the body
      html = html + "<body>\n";

      

      // Add HTML for link back to the homepage
      html = html + "<div class='content'>";
      

      // Look up some information from JDBC
      // First we need to use your JDBCConnection class
      JDBCConnection jdbc = JDBCConnection.getConnection();

      
      html = html + "<form action='/login' method='post' class='user-form' id='login-form'>";
      html = html + "<h1>Login</h1>\n";

      html = html + "<div class='login-box'>";
      html = html + "<label for='login-email' class='login-label'>Email:</label><br>\n";
      html = html + "<input type='text' class='user-input' id='login-email' name='login-email'><br><br>\n";
      html = html + "</div>";

      html = html + "<div class='login-box'>";
      html = html + "<label for='login-password' class='login-label'>Password:</label><br>\n";
      html = html + "<input type='password' class='user-input' id='login-password' name='login-password'><br><br>\n";
      html = html + "</div><br>";
      
      html = html + "<div id='login-error'></div><br>";
      html = html + "<input type='submit' class='user-button' id='login-submit' value='login'>\n";
      html = html + "<a id='sign-up' href='register.html'>Sign up</a>\n";
      html = html + "</form>";


      String loginEmail = context.formParam("login-email");
      String loginPassword = context.formParam("login-password");
      jdbc.getUsers(loginEmail, loginPassword);
      
      if(loginEmail != null && loginPassword != null) {
         var user = jdbc.getUsers(loginEmail, loginPassword);

         if(user.size() == 0){
            html = html + "<script>document.getElementById('login-error').innerHTML=" +
            "'Login Error: please check your email or password;'</script>";
         }else{
            context.req.getSession().setAttribute("user", loginEmail);
            jdbc.UserLogin(loginEmail);
            html = html + ValidateSuccess(loginEmail);
            }
         }

      // Finish the List HTML
      html = html + "</div>";

      // Finish the HTML webpage
      html = html + "</body>" + "</html>\n";

      // DO NOT MODIFY THIS
      // Makes Javalin render the webpage
      context.html(html);
   }

   public String ValidateSuccess(String email){
      String html = "";
      html = html + "<script>alert('Login Successful!')</script>";
      html = html + "<script>location.href='/'</script>";
      return html;
   }

}
