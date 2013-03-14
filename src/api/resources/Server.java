package api.resources;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import org.glassfish.grizzly.http.server.HttpServer;
 
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
 
public class Server {
 
	 final static private int port = 7777;
	 final static private String host = "http://local.scripturesos.com";
	 
     private static URI getBaseURI()
     {
         return UriBuilder.fromUri(Server.host).port(Server.port).build();
     }
 
     public static final URI BASE_URI = getBaseURI();
 
     protected static HttpServer startServer() throws IOException
     {
    	 System.out.println("Starting grizzly server at port "+Server.port+" ...");
         ResourceConfig rc = new PackagesResourceConfig("api.resources");
         return GrizzlyServerFactory.createHttpServer(BASE_URI, rc);
     }
     
     public static void main(String[] args) throws IOException
     {
         HttpServer httpServer = startServer();
         System.out.println(String.format("Jersey app started with WADL available at "
                 + "%sapplication.wadl\nTry out %sbible\nHit enter to stop it...",
                 BASE_URI, BASE_URI));
         System.in.read();
         httpServer.stop();
     }    
}