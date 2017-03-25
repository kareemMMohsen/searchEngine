package searchengine;

import java.net.URL;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.trigonic.jrobotx.RobotExclusion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.log4j.BasicConfigurator;
//version 5.1
// Added Robot Exclusion Standard using jrobotx library
//Edited functions and added new ones

public class Crawler implements Runnable {

    static dbHandler dbH = new dbHandler();
    static RobotExclusion robotExclusion = new RobotExclusion();
    static int uniqueUrl;
    static int maxUrl, cnt;

    @Override
    public void run() {
        maxUrl = 10000;
        ExploreUrl();

    }

    public static void main(String[] args) {
        // comment this try block to enable the functionality of completing from where it left off after being stopped
        try {
            dbH.Sql("TRUNCATE sites;");
            dbH.Sql("TRUNCATE unsites;");
            dbH.Sql("TRUNCATE token;");
            dbH.Sql("TRUNCATE position;");
        } catch (Exception e) {
            e.printStackTrace();
        }
        BasicConfigurator.configure();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of threads for the crawler: ");
        int threadNum = scanner.nextInt();
        Thread T[] = new Thread[threadNum];
        int cnt = 0;

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
                    Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                dbH.Sql("TRUNCATE sites;");
                dbH.Sql("TRUNCATE unsites;");
                dbH.Sql("TRUNCATE token;");
                dbH.Sql("TRUNCATE position;");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    static boolean checkPage(String url) {
        if (url.indexOf('/', 8) != -1) {
            url = url.substring(0, url.indexOf('/', 8) - 1);
        }
        url = url + "/robots.txt";

        try {
            Jsoup.connect(url).get();
        } catch (IOException e) {
            return false;

        }
        return true;
    }

    static void ExploreUrl() {
        while (true) {
            String url = getNextUnexplored();
            if (Stop()) {
                return;
            }
            if (url.equals("")) {
                continue;
            }

            try {
                URL x = new URL(url);
                if (UrlFound(url) || (checkPage(url) && robotExclusion.allows(x, ""))) {
                    continue;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(url);
            int id = InsertUrlExplored(url);
            try {
                Document doc = Jsoup.connect(url).get();
                StringBuilder doc_sb = new StringBuilder(doc.toString());
                Project p = new Project();
                p.run(doc_sb, id);

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
                //    e.printStackTrace();
            }

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
            //e.printStackTrace();
        }

    }

    static synchronized String getNextUnexplored() {
        String Sql = "SELECT URL FROM unsites ORDER BY ID LIMIT 1;";
        try {
            ResultSet url = dbH.SqlQuery(Sql);
            if (!url.next()) {
                return "";
            }
            RemoveUrlUnexplored(url.getString("URL"));
            return url.getString("URL");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    static int InsertUrlExplored(String url) {

        try {
            String sql = "INSERT INTO sites (URL) VALUES ('" + url + "');";
            dbH.Sql(sql);
            String sql2 = "SELECT ID FROM sites where URL='" + url + "';";
            ResultSet url_r = dbH.SqlQuery(sql2);
            url_r.next();
            return url_r.getInt(1);
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return 0;
    }

    static void RemoveUrlUnexplored(String url) {
        try {
            String sql = "DELETE FROM unsites WHERE URL ='" + url + "';";
            dbH.Sql(sql);
        } catch (Exception e) {
            //e.printStackTrace();
        }

    }

    static boolean UrlFound(String url) {
        try {
            ResultSet st = dbH.SqlQuery("SELECT * FROM sites WHERE URL ='" + url + "';");
            return (st.next());
        } catch (SQLException e) {
            //e.printStackTrace();
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
