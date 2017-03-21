package searchengine;

import java.util.ArrayList;
import java.util.List;

//Porter stemmer algorithm
//credit: Martin Porter
class stemmer {

    private char[] buffer;
    private int endBuffer, endStemmed, j, k;
    private static final int EX = 50;

    public stemmer() {
        buffer = new char[EX];
        endBuffer = 0;
        endStemmed = 0;
    }

    public void add(char[] w, int wLen) {
        if (endBuffer + wLen >= buffer.length) {
            char[] new_b = new char[endBuffer + wLen + EX];
            System.arraycopy(buffer, 0, new_b, 0, endBuffer);
            buffer = new_b;
        }
        for (int c = 0; c < wLen; c++) {
            buffer[endBuffer++] = w[c];
        }
    }

    @Override
    public String toString() {
        return new String(buffer, 0, endStemmed);
    }

    public int getEndStemmed() {
        return endStemmed;
    }

    public char[] getBuffer() {
        return buffer;
    }

    private boolean isConsonant(int i) {
        if (buffer[i] == 'a' || buffer[i] == 'e' || buffer[i] == 'i' || buffer[i] == 'o' || buffer[i] == 'u') {
            return false;
        } else if (buffer[i] == 'y') {
            return (i == 0) ? true : !isConsonant(i - 1);
        }
        return true;
    }

    private int m() {
        int n = 0;
        int i = 0;
        while (true) {
            if (i > j) {
                return n;
            }
            if (!isConsonant(i)) {
                break;
            }
            i++;
        }
        i++;
        while (true) {
            while (true) {
                if (i > j) {
                    return n;
                }
                if (isConsonant(i)) {
                    break;
                }
                i++;
            }
            i++;
            n++;
            while (true) {
                if (i > j) {
                    return n;
                }
                if (!isConsonant(i)) {
                    break;
                }
                i++;
            }
            i++;
        }
    }

    private boolean hasVowel() {
        for (int i = 0; i <= j; i++) {
            if (!isConsonant(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean endDoubled(int j) {
        if (j < 1) {
            return false;
        }
        if (buffer[j] != buffer[j - 1]) {
            return false;
        }
        return isConsonant(j);
    }

    private boolean cvc(int i) {
        if (i < 2 || !isConsonant(i) || isConsonant(i - 1) || !isConsonant(i - 2)) {
            return false;
        }
        {
            char ch;
            ch = buffer[i];
            if (ch == 'w' || ch == 'x' || ch == 'y') {
                return false;
            }
        }
        return true;
    }

    private boolean endsWith(String str) {
        int len = k - str.length() + 1;
        if (len < 0) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (buffer[len + i] != str.charAt(i)) {
                return false;
            }
        }
        j = k - str.length();
        return true;
    }

    private void setTo(String str) {
        int len = j + 1;
        for (int i = 0; i < str.length(); i++) {
            buffer[len + i] = str.charAt(i);
        }
        k = j + str.length();
    }

    private void replace(String str) {
        if (m() > 0) {
            setTo(str);
        }
    }

    private void step1() {
        if (buffer[k] == 's') {
            if (endsWith("sses")) {
                k -= 2;
            } else if (endsWith("ies")) {
                setTo("i");
            } else if (buffer[k - 1] != 's') {
                k--;
            }
        }
        if (endsWith("eed")) {
            if (m() > 0) {
                k--;
            }
        } else if ((endsWith("ed") || endsWith("ing")) && hasVowel()) {
            k = j;
            if (endsWith("at")) {
                setTo("ate");
            } else if (endsWith("bl")) {
                setTo("ble");
            } else if (endsWith("iz")) {
                setTo("ize");
            } else if (endDoubled(k)) {
                k--;
                {
                    char ch;
                    ch = buffer[k];
                    if (ch == 'l' || ch == 's' || ch == 'z') {
                        k++;
                    }
                }
            } else if (m() == 1 && cvc(k)) {
                setTo("e");
            }
        }
    }

    private void step2() {
        if (endsWith("y") && hasVowel()) {
            buffer[k] = 'i';
        }
    }

    private void step3() {
        if (k == 0) {
            return;
        }
        switch (buffer[k - 1]) {
            case 'a':
                if (endsWith("ational")) {
                    replace("ate");
                    break;
                }
                if (endsWith("tional")) {
                    replace("tion");
                    break;
                }
                break;
            case 'c':
                if (endsWith("enci")) {
                    replace("ence");
                    break;
                }
                if (endsWith("anci")) {
                    replace("ance");
                    break;
                }
                break;
            case 'e':
                if (endsWith("izer")) {
                    replace("ize");
                    break;
                }
                break;
            case 'l':
                if (endsWith("bli")) {
                    replace("ble");
                    break;
                }
                if (endsWith("alli")) {
                    replace("al");
                    break;
                }
                if (endsWith("entli")) {
                    replace("ent");
                    break;
                }
                if (endsWith("eli")) {
                    replace("e");
                    break;
                }
                if (endsWith("ousli")) {
                    replace("ous");
                    break;
                }
                break;
            case 'o':
                if (endsWith("ization")) {
                    replace("ize");
                    break;
                }
                if (endsWith("ation")) {
                    replace("ate");
                    break;
                }
                if (endsWith("ator")) {
                    replace("ate");
                    break;
                }
                break;
            case 's':
                if (endsWith("alism")) {
                    replace("al");
                    break;
                }
                if (endsWith("iveness")) {
                    replace("ive");
                    break;
                }
                if (endsWith("fulness")) {
                    replace("ful");
                    break;
                }
                if (endsWith("ousness")) {
                    replace("ous");
                    break;
                }
                break;
            case 't':
                if (endsWith("aliti")) {
                    replace("al");
                    break;
                }
                if (endsWith("iviti")) {
                    replace("ive");
                    break;
                }
                if (endsWith("biliti")) {
                    replace("ble");
                    break;
                }
                break;
            case 'g':
                if (endsWith("logi")) {
                    replace("log");
                    break;
                }
        }
    }

    private void step4() {
        switch (buffer[k]) {
            case 'e':
                if (endsWith("icate")) {
                    replace("ic");
                    break;
                }
                if (endsWith("ative")) {
                    replace("");
                    break;
                }
                if (endsWith("alize")) {
                    replace("al");
                    break;
                }
                break;
            case 'i':
                if (endsWith("iciti")) {
                    replace("ic");
                    break;
                }
                break;
            case 'l':
                if (endsWith("ical")) {
                    replace("ic");
                    break;
                }
                if (endsWith("ful")) {
                    replace("");
                    break;
                }
                break;
            case 's':
                if (endsWith("ness")) {
                    replace("");
                    break;
                }
                break;
        }
    }

    private void step5() {
        if (k == 0) {
            return;
        }
        switch (buffer[k - 1]) {
            case 'a':
                if (endsWith("al")) {
                    break;
                }
                return;
            case 'c':
                if (endsWith("ance")) {
                    break;
                }
                if (endsWith("ence")) {
                    break;
                }
                return;
            case 'e':
                if (endsWith("er")) {
                    break;
                }
                return;
            case 'i':
                if (endsWith("ic")) {
                    break;
                }
                return;
            case 'l':
                if (endsWith("able")) {
                    break;
                }
                if (endsWith("ible")) {
                    break;
                }
                return;
            case 'n':
                if (endsWith("ant")) {
                    break;
                }
                if (endsWith("ement")) {
                    break;
                }
                if (endsWith("ment")) {
                    break;
                }
                if (endsWith("ent")) {
                    break;
                }
                return;
            case 'o':
                if (endsWith("ion") && j >= 0 && (buffer[j] == 's' || buffer[j] == 't')) {
                    break;
                }

                if (endsWith("ou")) {
                    break;
                }
                return;
            case 's':
                if (endsWith("ism")) {
                    break;
                }
                return;
            case 't':
                if (endsWith("ate")) {
                    break;
                }
                if (endsWith("iti")) {
                    break;
                }
                return;
            case 'u':
                if (endsWith("ous")) {
                    break;
                }
                return;
            case 'v':
                if (endsWith("ive")) {
                    break;
                }
                return;
            case 'z':
                if (endsWith("ize")) {
                    break;
                }
                return;
            default:
                return;
        }
        if (m() > 1) {
            k = j;
        }
    }

    private void step6() {
        j = k;
        if (buffer[k] == 'e') {
            int a = m();
            if (a > 1 || a == 1 && !cvc(k - 1)) {
                k--;
            }
        }
        if (buffer[k] == 'l' && endDoubled(k) && m() > 1) {
            k--;
        }
    }

    public void stem() {
        k = endBuffer - 1;
        if (k > 1) {
            step1();
            step2();
            step3();
            step4();
            step5();
            step6();
        }
        endStemmed = k + 1;
        endBuffer = 0;
    }

    public List<String> run(List<String> str) {

        List<String> ret = new ArrayList<>();
        stemmer s = new stemmer();
        for (int l = 0; l < str.size(); l++) {
            String get = str.get(l);
            s.add(get.toCharArray(), get.length());
            s.stem();
            ret.add(s.toString());
            s = new stemmer();
        }
        return ret;
    }
}
