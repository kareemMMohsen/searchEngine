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
    public List<String> run(StringBuilder sb) {
        // TODO code application logic here
        List<String> ls = normalizor.run(concatinator.run(sb));
        ls = (new stemmer()).run(filter.run(ls));
        Runtime.getRuntime().gc();
        return ls;
    }

}
