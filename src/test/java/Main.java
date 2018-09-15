import org.jsoup.Jsoup;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        String html = Jsoup.connect("http://lite.dzzzr.ru/spb/go/?pin=43269642&q=XPSMFUMW&nomessage=")
                .ignoreHttpErrors(true)
                .get()
                .body()
                .html();
        int begin = html.indexOf("<!--levelNumberBegin-->") + "<!--levelNumberBegin-->".length();
        int end = html.indexOf("<!--levelNumberEnd-->");
        String num = html.substring(begin, end).trim();
        try {
            System.out.println(Integer.parseInt(num));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

}
