package searchengine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


/**
 *
 * @author kareemMohsen
 */
public class concatinator {

    public StringBuilder run(StringBuilder sb) {
        String html = sb.toString();
        Document doc = Jsoup.parse(html);
        String text = "empty";
        if(((doc.select("html").first()).attr("lang")).toLowerCase().contains(("en"))){
            text = doc.text();
        }
        sb = new StringBuilder(text);
        return sb;
    }
}
