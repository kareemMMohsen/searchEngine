/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
