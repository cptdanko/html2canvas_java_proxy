import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Html2CanvasProxy extends HttpServlet{
    private static final long serialVersionUID = -6856074403887401174L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Process request with content.
        /*
        the url can be something like this below
        String queryUrl = "http://www.popgunchaos.com/wp-content/uploads/2011/04/Tick.jpg";
        */
        String queryUrl = extractUrl(request.getQueryString());
        
        File file = new File(queryUrl);
        String filename = file.getName().toString(); 
        
        URL url = new URL(queryUrl);
        URLConnection connection = url.openConnection();
        InputStream stream = connection.getInputStream();
        response.setContentType(request.getContentType());
        response.setContentType("image/"+filename.substring(filename.lastIndexOf(".")+1));
        BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        byte[] b = new byte[2048];
        int length;
        while((length = stream.read(b)) > 0){
            outputStream.write(b, 0, length);
        }
    }
}
