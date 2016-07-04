/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IR;

/**
 *
 * @author Ehab Bshara
 */
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

public class Google {

    private static Pattern patternDomainName;
    private static Matcher matcher;
    private static final String DOMAIN_NAME_PATTERN
            = "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";

    static {
        patternDomainName = Pattern.compile(DOMAIN_NAME_PATTERN);
    }
    List<String> resultUrls = new ArrayList<>();
    static Set<Document> results = new HashSet<>();

    public void getDatafromUrl() {
        //   for (String s : resultUrls) {
//            FileOutputStream out;
////            String[] regex = s.split("&sa=");
////            System.out.println(regex[0]);
//            Response doc;

//                doc = Jsoup.connect("https://www.hw.ac.uk/students/doc/plagiarismguidearabic.pdf")
//                        .ignoreContentType(true).userAgent("Mozilla/5.0")
//                        .execute();
//                int len = doc.bodyAsBytes().length;
//                out = new FileOutputStream(new File("D:/ppp.pdf"));
//                out.write(doc.bodyAsBytes(), 0, len);
//                out.close();
//             //   break; 
//           
//      //  }
        FileWriter file;
        int i = 1;
        for (String s : resultUrls) {
            try {
                Document doc = Jsoup.connect(s).get();
                Elements ps = doc.select("p");
                
                BodyContentHandler contenthandler = new BodyContentHandler(-1);
                InputStream stream = new ByteArrayInputStream(ps.toString().getBytes(StandardCharsets.UTF_8));
                Metadata metadata = new Metadata();
                HtmlParser htmlparser = new HtmlParser();
                htmlparser.parse(stream, contenthandler, metadata, new ParseContext());
                //pdfparser.parse(in, contenthandler, metadata, new ParseContext());
                file = new FileWriter(new File("D://files//" + i++ + ".txt"));
                file.write(contenthandler.toString());
                file.close();
                //System.out.println(contenthandler.toString());
            } catch (IOException e) {
                System.out.println("file in " + s + "is not found");

            } catch (SAXException ex) {
                Logger.getLogger(Google.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TikaException ex) {
                Logger.getLogger(Google.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception e) {
                System.out.println("file in " + s + "is not found");
            }
        }
    }

    public static void main(String[] args) throws IOException {

        Google obj = new Google();
        obj.getDataFromGoogle("Ø§Ù„Ø§Ø­ØªØ¨Ø§Ø³ Ø§Ù„Ø­Ø±Ø§Ø±ÙŠ");
        obj.getDatafromUrl();

        System.out.println("done");
    }

    public static String getDomainName(String url) {

        String domainName = "";
        matcher = patternDomainName.matcher(url);
        if (matcher.find()) {
            domainName = matcher.group(0).toLowerCase().trim();
        }
        return domainName;

    }

    public void getDataFromGoogle(String query) {

//        request=request.replaceAll("\\s", "%20");
        try {
            String request = "https://www.google.com/search?q=" + URLEncoder.encode(query, "UTF-8") + "&num=9";
            System.out.println("Sending request..." + request);
//            String encode = URLEncoder.encode(request, "UTF-8");
            // need http protocol, set this as a Google bot agent :)
            Document doc = Jsoup
                    .connect(request)
                    .userAgent(
                            "Mozilla/5.0 (compatible; Googlebot/2.1; +https://www.google.com/bot.html)")
                    .timeout(0).get();

            // get all links
            Elements links = doc.select("h3.r > a");
            for (Element link : links) {

                String url = link.absUrl("href");
                url = URLDecoder.decode(url.substring(url.indexOf('=') + 1, url.indexOf('&')), "UTF-8");
                System.out.println(url);
                resultUrls.add(url);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
