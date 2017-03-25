package searchengine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


/**
 *
 * @author kareemMohsen
 */
public class concatinator {

    public static StringBuilder run(StringBuilder sb) {
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
