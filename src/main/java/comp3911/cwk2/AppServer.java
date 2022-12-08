package comp3911.cwk2;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.StdErrLog;
import org.eclipse.jetty.util.ssl.SslContextFactory;

public class AppServer {
  public static void main(String[] args) throws Exception {
    Log.setLog(new StdErrLog());

    ServletHandler handler = new ServletHandler();
    handler.addServletWithMapping(AppServlet.class, "/*");

    int port = 8080; // HTTPS served on this port

    // Configure server and its REST handler
    Server server = new Server();
    server.setHandler(handler);

    // Server HTTPS config
    HttpConfiguration httpsConfiguration = new HttpConfiguration();
    httpsConfiguration.setSecureScheme("https");
    httpsConfiguration.setSecurePort(port);
    httpsConfiguration.addCustomizer(new SecureRequestCustomizer());

    // Load keystore from local filepath
    String keystoreFilepath = "./keystore.jks";
    SslContextFactory.Server ssl = new SslContextFactory.Server();
    ssl.setKeyStorePath(keystoreFilepath);
    ssl.setKeyStorePassword("gogo98"); // force manual entry of keystore password in order to avoid hardcoding and dealing with env vars
    
    // Create HTTPS connector
    ServerConnector httpsConnector = new ServerConnector(
        server,
        new SslConnectionFactory(ssl, "http/1.1"),
        new HttpConnectionFactory(httpsConfiguration)
    );
    httpsConnector.setPort(port);
    server.addConnector(httpsConnector);

    server.start();
    server.join();
  }
}
