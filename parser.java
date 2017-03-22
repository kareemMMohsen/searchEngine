/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author kareemMohsen
 */
public class parser {
    public static List<String> run(StringBuilder sb) {
        // TODO code application logic here
        List<String> ls = normalizor.run(concatinator.run(sb));
        ls = new stemmer().run(filter.run(ls));
        return ls;
    }

}
