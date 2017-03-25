package searchengine;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.sql.PreparedStatement;

//version 1.0
//omer
class indexer {

    static List<String> List ;
    static int pageID;
    static dbHandler db = new dbHandler ();

   static public void sendList(List<String> list)
    {
        List=list;
    }
    static public void sendID(int ID)
    {
        pageID=ID;
    }
   public  static  void Update_tables()
    {
        int m=0;
        for (int i = 0; i < List.size(); i++) 
        {
            
        
        try {
            
          if(!TokenFound(List.get(i)))
          {
             String s= "INSERT INTO `token`(`Token`) VALUES (\""+List.get(i)+"\")";
              db.Sql(s);
          }
         
            ResultSet st = db.SqlQuery("SELECT * FROM token WHERE Token ='" + List.get(i) + "';");
              st.next();
              
             int tokenID= Integer.parseInt(st.getString("ID"));
             int position=i+1;
               if(PoistionFound(pageID,position))
             {
                  String query="DELETE FROM `position` WHERE  Site_ID =" + pageID + " AND Position =" + position ;
                 db.Sql(query);
                 
             }
              String s= "INSERT INTO `position`(`Token_ID`,`Site_ID`,`Position`) VALUES ("+tokenID+","+pageID+","+position+")";
              db.Sql(s);
              
           }
         catch (SQLException e) {
                e.printStackTrace();
            

        }
        }
    }
    
     static boolean PoistionFound(int pageID, int position) {
     try {
            String query="SELECT * FROM position WHERE  Site_ID =" + pageID + " AND Position =" + position ;
            ResultSet st = db.SqlQuery(query);
            return (st.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
   
   static boolean TokenFound(String list) {
     try {
            ResultSet st = db.SqlQuery("SELECT * FROM token WHERE Token ='" + list + "';");
            return (st.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    static synchronized public void Update_tables(List<String> List,int ID)
    {
       sendList(List);
       sendID(ID);
       Update_tables();
    }
    


}
public class Project {

   

    public void run(StringBuilder sb , int ID) {

        List<String> list=(new parser()).run(sb) ;
        indexer.Update_tables(list,ID);
        Runtime.getRuntime().gc();

        
    }

}
