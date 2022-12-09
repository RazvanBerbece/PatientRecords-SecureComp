package comp3911.cwk2;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@SuppressWarnings("serial")
public class AppServlet extends HttpServlet {

  private static final String CONNECTION_URL = "jdbc:sqlite:db.sqlite3";

  // Use ? as the placeholder character in the SQL query strings
  // in order to use PreparedStatement objects
  private static final String AUTH_QUERY = "select * from user where username=? and password=?";
  private static final String SEARCH_QUERY = "select * from patient where surname=? collate nocase";

  private final Configuration fm = new Configuration(Configuration.VERSION_2_3_28);
  private Connection database;

  private static byte[] salt = {-74, -23, -102, -124, -5, 73, 105, -91, -22, 
    -77, -118, -8, 94, -70, 17, -90};

  @Override
  public void init() throws ServletException {
    configureTemplateEngine();
    connectToDatabase();
  }

  // Returns a password that is hashed using salt
  // Uses a statically declared salt as we needed to convert existing database passwords
  // Proper implementation would use a randomly generated salt and would require a fresh database
  private String getHash(String pw) {
    String hashed = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      md.update(salt);
      byte[] bytes = md.digest(pw.getBytes());
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < bytes.length; i++) {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      hashed = sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return hashed;
  }

  private void configureTemplateEngine() throws ServletException {
    try {
      fm.setDirectoryForTemplateLoading(new File("./templates"));
      fm.setDefaultEncoding("UTF-8");
      fm.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
      fm.setLogTemplateExceptions(false);
      fm.setWrapUncheckedExceptions(true);
    } catch (IOException error) {
      throw new ServletException(error.getMessage());
    }
  }

  private void connectToDatabase() throws ServletException {
    try {
      database = DriverManager.getConnection(CONNECTION_URL);
    } catch (SQLException error) {
      throw new ServletException(error.getMessage());
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
      Template template = fm.getTemplate("login.html");
      template.process(null, response.getWriter());
      response.setContentType("text/html");
      response.setStatus(HttpServletResponse.SC_OK);
    } catch (TemplateException error) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // Get form parameters
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String surname = request.getParameter("surname");

    try {
      if (authenticated(username, password)) {
        // Get search results and merge with template
        Map<String, Object> model = new HashMap<>();
        model.put("records", searchResults(surname));
        Template template = fm.getTemplate("details.html");
        template.process(model, response.getWriter());
      } else {
        Template template = fm.getTemplate("invalid.html");
        template.process(null, response.getWriter());
      }
      response.setContentType("text/html");
      response.setStatus(HttpServletResponse.SC_OK);
    } catch (Exception error) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

  private boolean authenticated(String username, String password) throws SQLException {

    // Use PreparedStatement to precompile the auth SQL query and prevent SQL
    // injection attacks
    try (PreparedStatement stmt = database.prepareStatement(AUTH_QUERY)) {

      // Set the parameters of the precompiled auth SQL query using the index and the
      // user input
      // index = 1 -> username in AUTH_QUERY
      // index = 2 -> password in AUTH_QUERY
      stmt.setString(1, username);
      // Compares password received in form with it's hashed + salted equivalent
      stmt.setString(2, getHash(password));

      // Execute the prepared & formatted statement & get result set
      ResultSet results = stmt.executeQuery();

      return results.next();
    }
  }

  private List<Record> searchResults(String surname) throws SQLException {

    List<Record> records = new ArrayList<>();

    // Use PreparedStatement to precompile the auth SQL query and prevent SQL
    // injection attacks
    try (PreparedStatement stmt = database.prepareStatement(SEARCH_QUERY)) {

      // Set the parameters of the precompiled auth SQL query using the index and the
      // user input
      // index = 1 -> surname in SEARCH_QUERY
      stmt.setString(1, surname);

      // Execute the prepared & formatted statement & get result set
      ResultSet results = stmt.executeQuery();

      while (results.next()) {
        Record rec = new Record();
        rec.setSurname(results.getString(2));
        rec.setForename(results.getString(3));
        rec.setAddress(results.getString(4));
        rec.setDateOfBirth(results.getString(5));
        rec.setDoctorId(results.getString(6));
        rec.setDiagnosis(results.getString(7));
        records.add(rec);
      }
    }
    return records;
  }
}
