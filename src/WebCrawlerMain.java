import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.Scanner;

public class WebCrawlerMain {
    public static final String STARTING_URL = "https://www.wsdweb.org/";

    public static void main(String[] args) {
        InputStream stream = null;

        LinkedList<Link> urls = new LinkedList<Link>();
        LinkedList<String> visitedURLs = new LinkedList<String>();
        //adds initial starting page
        try {
            urls.add(new Link(new URL(STARTING_URL), "", STARTING_URL));
        } catch (MalformedURLException e) {
            System.out.println("Invalid starting URL. Please try again");
            System.exit(-1);
        }

        while(!urls.peek().getParentURL().toString().contains("wikipedia.org")) {
            Link current = urls.pop();
            //System.out.println("Remaining URLs: " + urls);
            //System.out.println("Scanning through: " + current.getParentURL().toString());
            try {
                URL u = new URL(current.getParentURL(),current.getNewURL());
                URLConnection connection = u.openConnection();
                stream = connection.getInputStream();
                Scanner in = new Scanner(stream);

                while (in.hasNextLine()) {
                    String line = in.nextLine();
                    while (findFirstLink(line) != null) {
                        String foundURL = findFirstLink(line);

                        URL newURL = new URL(current.getParentURL(), foundURL);
                        if(!visitedURLs.contains(newURL.toString())) {
                            Link temp = new Link(newURL, "", current.getHistory());
                            temp.addToHistory(newURL.toString());
                            urls.add(temp);
                            visitedURLs.add(newURL.toString());
                            //System.out.println(newURL);
                        }

                        line = line.substring(foundURL.indexOf(foundURL) + foundURL.length()+1);
                    }
                }

            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }


            System.out.println("----------");
        }
        System.out.println("----------");
        System.out.println("REACHED DESTINATION: " + urls.peek());
        System.out.println("History: " + urls.peek().getHistory());


    }
    /**
     *
     * @param searchMaterial string to search for link in
     * @return first link in searchMaterial if one exists, else return null
     */
    public static String findFirstLink(String searchMaterial){
        if(searchMaterial.contains("<a href=")){
            int index = searchMaterial.indexOf("<a href=");
            int startQuote = searchMaterial.indexOf("\"", index);
            int endQuote = searchMaterial.indexOf("\"", startQuote+1);
            try {
                return searchMaterial.substring(startQuote + 1, endQuote);
            }
            catch(StringIndexOutOfBoundsException e){
                return null;
            }
        }
        else{
            return null;
        }
    }
}
