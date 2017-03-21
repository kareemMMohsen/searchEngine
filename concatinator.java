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

    public static StringBuilder run(StringBuilder sb) {
        String html = sb.toString();
        Document doc = Jsoup.parse(html);
        //Element link = doc.select("a").first();

        String text = doc.body().text(); // "An example link"
        //System.out.println(text);
        sb = new StringBuilder(text);
        return sb;
    }
}
