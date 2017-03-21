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
        Element taglang = doc.select("html").first();
        String text = "";
        if((taglang.attr("lang")).toLowerCase().contains(("en"))){
            text = doc.body().text();
        }
        sb = new StringBuilder(text);
        return sb;
    }
}
