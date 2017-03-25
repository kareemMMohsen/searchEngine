package searchengine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

//omer
class indexer {

    static List<String> List;
    static int pageID;
    static dbHandler db = new dbHandler();

    static public void sendList(List<String> list) {
        List = list;
    }

    static public void sendID(int ID) {
        pageID = ID;
    }

    public static void Update_tables() {
        int m = 0;
        for (int i = 0; i < List.size(); i++) {

            try {

                if (!TokenFound(List.get(i))) {
                    String s = "INSERT INTO `token`(`Token`) VALUES (\"" + List.get(i) + "\")";
                    db.Sql(s);
                }

                ResultSet st = db.SqlQuery("SELECT * FROM token WHERE Token ='" + List.get(i) + "';");
                st.next();

                int tokenID = Integer.parseInt(st.getString("ID"));
                int position = i + 1;
                
                String s = "INSERT INTO `position`(`Token_ID`,`Site_ID`,`Position`) VALUES (" + tokenID + "," + pageID + "," + position + ")";
                db.Sql(s);

            } catch (SQLException e) {

            }
        }
    }



    static boolean TokenFound(String list) {
        try {
            ResultSet st = db.SqlQuery("SELECT * FROM token WHERE Token ='" + list + "';");
            return (st.next());
        } catch (SQLException e) {
        }
        return false;
    }

    static synchronized public void Update_tables(List<String> List, int ID) {
        sendList(List);
        sendID(ID);
        Update_tables();
    }

}

public class Project {

    public void run(StringBuilder sb, int ID) {

        List<String> list = (new parser()).run(sb);
        indexer.Update_tables(list, ID);

    }

}
