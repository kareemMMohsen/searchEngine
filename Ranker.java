/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

/**
 *
 * @author M
 */
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.List;
import java.util.Comparator;
import java.net.URL;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.trigonic.jrobotx.RobotExclusion;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.log4j.BasicConfigurator;
import java.io.FileNotFoundException;
class pair{
    String url;
    int rank;
}
 class EdgeComparator implements Comparator<pair> {
    public int compare(pair l, pair r) { 
        if(l.rank>r.rank) return 1;
        else if(l.rank<r.rank) return -1;
        else return 0;
    }
}
public class Ranker {
    static dbHandler dbH = new dbHandler();
   
static int GetRank(String url){
        try {
            String sql = "SELECT rank FROM urlRank WHERE URL='" + url + "';";
            ResultSet s1 = dbH.SqlQuery(sql);
            if (s1.next()) {
                return s1.getInt("rank");
            }
        } catch (Exception e) {
        }
        return 0;
    }
static public int CountWord(String page){
    return page.split("\\W+").length;
}

static public int CountTerm(String term, String page){
    int idx=page.indexOf(term), count=0;
    while(idx!=-1){
        ++count;
        idx = page.indexOf(term, idx+1);
    }
    return count;
 }

static public String[] Rank(String[] list, String[] words){
    pair ListP[] = new pair[list.length];
     int[][] doc_idf = new int[list.length][words.length];
        int[][] docTF = new int[list.length][words.length];
       
    for (int i = 0; i < list.length; i++) {
        ListP[i].url=list[i];
           
    try {
           Document doc =  Jsoup.connect(list[i]).get();
           String page = Jsoup.parse(doc.toString()).text();
           int wordCount = CountWord(page);
           for (int j = 0; j < words.length; j++) {
            docTF[i][j] = CountTerm(words[j], page)/wordCount;
            if(docTF[i][j]!=0)
                doc_idf[i][j]++;
           
        }
           
        } catch (Exception e) {
            
        }
    }
    for (int i = 0; i < list.length; i++) {
        for (int j = 0; j < words.length; j++) {
            ListP[i].rank += docTF[i][j] * doc_idf[i][j];
        }
    }
    Arrays.sort(ListP, new EdgeComparator());
    for (int i = 0; i < list.length; i++) {
        list[i] = ListP[i].url;
    }
    return list;
}
public Ranker(){
    
}



    
}
