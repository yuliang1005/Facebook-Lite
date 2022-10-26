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
public class friendRequest implements Handler {

   // URL of this page relative to http://localhost:7000/
   public static final String URL = "/friendRequest.html";

   @Override
   public void handle(Context context) throws Exception {
      // Create a simple HTML webpage in a String
      String html = "<html>\n";

      // Add some Header information
      html = html + "<head>" + "<title>Friend Request</title>\n";

      // Add some CSS (external file)
      html = html + "<link rel='stylesheet' type='text/css' href='common.css' />\n";

      // Add the body
      html = html + "<body>\n";

      var email = context.req.getSession().getAttribute("friend");
      String Email = email.toString();
      var loginEmail = context.req.getSession().getAttribute("user");
      String LoginEmail = loginEmail.toString();
      JDBCConnection jdbc = JDBCConnection.getConnection();
      jdbc.friendRequest(Email, LoginEmail);

      html = html + "<script>alert('Friend request successful!')</script>";
      context.req.getSession().removeAttribute("friend");
      html = html + "<script>location.href='/members.html'</script>";

      

      // Finish the HTML webpage
      html = html + "</body>" + "</html>\n";

      // DO NOT MODIFY THIS
      // Makes Javalin render the webpage
      context.html(html);
   }

   

}
