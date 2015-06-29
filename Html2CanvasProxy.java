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
    /*
    if the url is something like,
    "url=http%3A%2F%2Fdomain.com%2Fimages%2Fimage.gif";
    so extract the required contents of the url to fetch the image
    */
    public void extractUrl(String url){
        return url.replaceAll("url=", "").replaceAll("%3A", ":").replaceAll("%2F", "/");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Process request with content.
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
