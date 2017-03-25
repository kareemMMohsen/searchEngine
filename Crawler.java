package searchengine;

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
//version 5.1
// Added Robot Exclusion Standard using jrobotx library
//Edited functions and added new ones

public class Crawler implements Runnable {

    static dbHandler dbH = new dbHandler();
    static RobotExclusion robotExclusion = new RobotExclusion();
    static int uniqueUrl;
    static int maxUrl, cnt;
    //  final static Project p = new Project();

    @Override
    public void run() {

        ExploreUrl();

    }

    public static void main(String[] args) {

        BasicConfigurator.configure();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of threads for the crawler: ");
        int threadNum = scanner.nextInt();
        Thread T[] = new Thread[threadNum];
        int cnt = 0;
        maxUrl = 5000;
        while (true) {
            ++cnt;
            System.out.println("Crawl #" + cnt);

            InsertUrlUnexplored("http://localhost/seeds.html");
            for (int i = 0; i < threadNum; i++) {

                T[i] = new Thread(new Crawler(), Integer.toString(i));
                T[i].start();
            }
            for (int i = 0; i < threadNum; i++) {

                try {
                    T[i].join();
                } catch (InterruptedException ex) {
                }
            }
            try {
                dbH.Sql("TRUNCATE sites;");
                dbH.Sql("TRUNCATE unsites;");
                dbH.Sql("TRUNCATE token;");
                dbH.Sql("TRUNCATE position;");
            } catch (Exception e) {
            }

        }
    }

    static boolean robotPage(String url) {
     if(url.indexOf('/', 8)!=-1)
         url = url.substring(0, url.indexOf('/', 8)) + "/robots.txt";
        try {
            Jsoup.connect(url).get();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    static void ExploreUrl() {
        Project p = new Project();

        while (true) {
            System.gc();
            String url = getNextUnexplored();
            if (Stop()) {
                return;
            }
            if (url.equals("")) {
                continue;
            }
            try {
                URL x = new URL(url);
                if (UrlFound(url) || (robotPage(url) && !robotExclusion.allows(x, ""))) {
                    continue;
                }

            } catch (Exception e) {
            }
            int id = InsertUrlExplored(url);
            try {
                Document doc = Jsoup.connect(url).get();
                StringBuilder doc_sb = new StringBuilder(doc.toString());

                //         System.out.println(Thread.currentThread().getName() + " started");
                p.run(doc_sb, id);
                //       System.out.println(Thread.currentThread().getName() + " ended");
                Elements links = doc.select("a[href]");
                for (Element i : links) {
                    String url_temp = i.attr("abs:href");
                    if (url_temp.length() == 0) {
                        continue;
                    }
                    url_temp = TrimURL(url_temp);
                    InsertUrlUnexplored(url_temp);
                }
            } catch (IOException e) {
            }
            System.out.println(url);
        }
    }

    static void InsertUrlUnexplored(String url) {

        try {
            String sql0 = "SELECT * FROM sites WHERE URL='" + url + "';";
            ResultSet s1 = dbH.SqlQuery(sql0);
            if (s1.next()) {
                return;
            }
            String sql = "INSERT INTO unsites (URL) VALUES ('" + url + "');";
            dbH.Sql(sql);
        } catch (Exception e) {
        }

    }

    static String getNextUnexplored() {
        synchronized (dbH) {
            String Sql = "SELECT URL FROM unsites ORDER BY ID LIMIT 1;";
            try {
                ResultSet url = dbH.SqlQuery(Sql);
                if (!url.next()) {
                    return "";
                }
                RemoveUrlUnexplored(url.getString("URL"));
                return url.getString("URL");
            } catch (SQLException e) {
            }
        }
        return "";
    }

    static int InsertUrlExplored(String url) {
        synchronized (dbH) {
            try {
                String sql = "INSERT INTO sites (URL) VALUES ('" + url + "');";
                dbH.Sql(sql);
                String sql2 = "SELECT ID FROM sites where URL='" + url + "';";
                ResultSet url_r = dbH.SqlQuery(sql2);
                url_r.next();
                return url_r.getInt(1);
            } catch (Exception e) {
            }
        }
        return 0;
    }

    static void RemoveUrlUnexplored(String url) {
        try {
            String sql = "DELETE FROM unsites WHERE URL ='" + url + "';";
            dbH.Sql(sql);
        } catch (Exception e) {
        }

    }

    static boolean UrlFound(String url) {
        try {
            ResultSet st = dbH.SqlQuery("SELECT * FROM sites WHERE URL ='" + url + "';");
            return (st.next());
        } catch (SQLException e) {
        }
        return false;
    }

    static String TrimURL(String url) {

        if (url.charAt(url.length() - 1) == '/') {
            url = url.substring(0, url.length() - 1);
        }
        int hashIdx = url.indexOf('#');
        if (hashIdx != -1) {
            return url.substring(0, hashIdx);
        }
        return url;
    }

    static boolean Stop() {
        String sql = "SELECT COUNT(*) FROM sites;";
        try {
            ResultSet query_result = dbH.SqlQuery(sql);
            query_result.next();
            return (query_result.getInt(1) >= maxUrl);
        } catch (SQLException e) {
        }
        return false;
    }

}
