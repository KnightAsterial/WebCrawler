import java.net.URL;

public class Link {
    private URL parentURL;
    private String newURL;
    private String history;

    public Link(URL parentURL, String newURL, String history){
        this.parentURL = parentURL;
        this.newURL = newURL;
        this.history = history;
    }

    public String getHistory() {
        return history;
    }

    public void addToHistory(String URL) {
        this.history += " --> " + URL;
    }

    public URL getParentURL() {
        return parentURL;
    }

    public void setParentURL(URL parentURL) {
        this.parentURL = parentURL;
    }

    public String getNewURL() {
        return newURL;
    }

    public void setNewURL(String newURL) {
        this.newURL = newURL;
    }
    
    public String toString() {
    	return parentURL.toString();
    }
}
