package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 * @author Halil Ali, 2021. email: halil.ali@rmit.edu.au
 */
public class members implements Handler {

    // URL of this page relative to http://localhost:7000/
    public static final String URL = "/members.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>\n";

        // Add some Header information
        html = html + "<head>" + 
               "<title>Members</title>\n";

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

        // Add HTML for the movies list
        html = html + "<div class='content'>";
        html = html + "<h1>Find friends</h1>\n";

        /* Add HTML for the web form
         * We are giving two ways here
         *  - one for a text box
         *  - one for a drop down
         * 
         * Whitespace is used to help us understand the HTML!
         * 
         * IMPORTANT! the action speicifes the URL for POST!
         */
        html = html + "<form action='/members.html' method='post'>\n";
        html = html + "   <div class='form-group'>\n";
        html = html + "      <label class='login-label' for='searchUsers'>Please input email:</label><br>\n";
        html = html + "      <input class='user-input' id='searchUsers' name='searchUsers'>\n";
        html = html + "   </div><br><br>\n";
        html = html + "   <button type='submit' class='user-button'>Search</button>\n";
        html = html + "</form>\n";

        

        String searchUsers = context.formParam("searchUsers");
        
        
        if (searchUsers == null || searchUsers == "") {
            // If NULL, nothing to show, therefore we make some "no results" HTML
            html = html + "<h2><i>No Result</i></h2>\n";
        } else {
            // If NOT NULL, then lookup the movie by type!
            html = html + outputUsers(searchUsers);
            context.req.getSession().setAttribute("friend", searchUsers);
        }

        

        // Finish the HTML webpage
        html = html + "</body>" + "</html>\n";

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

    public String outputUsers(String email) {
        String html = "";
        html = html + "<h2>Search <div style='color:blue'>" + email + "</div> result:</h2>";

        // Look up movies from JDBC
        JDBCConnection jdbc = JDBCConnection.getConnection();
        ArrayList<String> users = jdbc.searchUsers(email);
        
        // Add HTML for the movies list
        html = html + "<div class='search-box'>";
        for (String user : users) {
            html = html + "<div>" + user + 
            "<button id='plus' onclick=friendRequest() ><b>+</b></button></div>";
            html = html + "<script>function friendRequest(){\n" +
            "window.location.href='friendRequest.html'}</script>";
        }
        html = html + "</div>";
        return html;
    }
}