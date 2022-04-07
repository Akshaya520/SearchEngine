package features;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class WebCrawler {
	
	public static final int MAX_DEPTH = 2;
	public static final String URL_REGEX = "((http|https)://)(www.)?[a-zA-Z0-9@:%._\\+~#?&//=]{2,256}\\.[a-z]{2,6}([-a-zA-Z0-9@:%._\\+~#?&//=]*)";

	private static Document fetchDoc(String url, ArrayList<String> visitedUrls) {
		System.out.println("2" + url);
		Connection conn = Jsoup.connect(url);
		
		try {
			Document doc = conn.ignoreContentType(true).get();
			if (conn.response().statusCode() == 200) {
				System.out.println("Link on this page--> " + url);
				HTMLToText.convertHTMLFileToText(url);
				visitedUrls.add(url);
				return doc;
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		return null;

	}
	
	private static boolean isEmpty(String url) {
		if (url != null && url != "" && Pattern.matches(URL_REGEX, url))
			return false;
		return true;
	}
	
	public static void crawlWeb(int depth, String url, ArrayList<String> visitedUrls) throws IOException {
		if (!isEmpty(url) && depth <= MAX_DEPTH) {
			System.out.println("1" + url);
			Document doc = fetchDoc(url, visitedUrls);
			if (doc != null) {
				Elements links = doc.select("a[href]");
				for (Element element : links) {
					String link = element.absUrl("href");
					if (!visitedUrls.contains(link)) {
						crawlWeb(depth++, link, visitedUrls);
						
					}
				}
			}
		}
	}
}
