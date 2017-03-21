/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchengine;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kareemMohsen
 */
public class normalizor {

    public static List<String> run(StringBuilder sb) {
        List<String> normal = new ArrayList<>();
        StringBuilder acc = new StringBuilder();
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == ' ') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
            }
            if (Character.isAlphabetic(sb.charAt(i))) {
                if (Character.isUpperCase(sb.charAt(i))) {
                    sb.setCharAt(i, Character.toLowerCase(sb.charAt(i)));
                }
                acc.append(sb.charAt(i));
            }
            if (Character.isDigit(sb.charAt(i))) {
                acc.append(sb.charAt(i));
            }
            if (sb.charAt(i) == '!') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("exclamation");
            }
            if (sb.charAt(i) == '"') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("quotation");
            }
            if (sb.charAt(i) == '#') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("number");
                normal.add("hashtag");
                normal.add("octothorpe");
                normal.add("sharp");
            }
            if (sb.charAt(i) == '$') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("dollar");
            }
            if (sb.charAt(i) == '%') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("percent");
            }
            if (sb.charAt(i) == '&') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("ampersand");
            }
            if (sb.charAt(i) == '\'') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("apostrophe");
            }
            if (sb.charAt(i) == '(') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("leftparenthesis");
            }
            if (sb.charAt(i) == ')') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("righttparenthesis");
            }
            if (sb.charAt(i) == '*') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("asterisk");
            }
            if (sb.charAt(i) == '+') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("plus");
            }
            if (sb.charAt(i) == ',') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("comma");
            }
            if (sb.charAt(i) == '-') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("hyphen");
                normal.add("minus");
            }
            if (sb.charAt(i) == '.') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("fullstop");
            }
            if (sb.charAt(i) == '/') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("slash");
            }
            if (sb.charAt(i) == ':') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("colon");
            }
            if (sb.charAt(i) == ';') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("semicolon");
            }
            if (sb.charAt(i) == '<') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("lessthan");
            }
            if (sb.charAt(i) == '>') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("greaterthan");
            }
            if (sb.charAt(i) == '=') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("equal");
            }
            if (sb.charAt(i) == '?') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("question");
            }
            if (sb.charAt(i) == '@') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("at");
            }
            if (sb.charAt(i) == '[') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("leftsquare");
            }
            if (sb.charAt(i) == '\\') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("backslash");
            }
            if (sb.charAt(i) == ']') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("rightsquare");
            }
            if (sb.charAt(i) == '^') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("circumflex");
            }
            if (sb.charAt(i) == '_') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("lowline");
            }
            if (sb.charAt(i) == '`') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("grave");
            }
            if (sb.charAt(i) == '{') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("leftcurly");
            }
            if (sb.charAt(i) == '|') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("verticalbar");
            }
            if (sb.charAt(i) == '}') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("rightcurly");
            }
            if (sb.charAt(i) == '~') {
                if (acc.length() > 0) {
                    normal.add(acc.toString());
                    acc = new StringBuilder();
                }
                normal.add("tilde");
            }

        }
        return normal;
    }
}
