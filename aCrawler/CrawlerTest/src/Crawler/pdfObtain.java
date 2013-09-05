package Crawler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author PTODUE
 * 
 */
public class pdfObtain extends Crawler {

	Data data;
	String root = "";

	static final Map<Integer, String> downLink = new HashMap<Integer, String>() {
		private static final long serialVersionUID = -6925194111122038477L;
		{
			put(1, "銀行/");
			put(2, "國營事業/中華電信/");
			put(3, "國營事業/郵局/");
		}
	};

	@Override
	public void loading() {
		data = new Data();
	}

	@Override
	public void filter() {

		int i = 0;
		while (i < 1 || i > 3) {
			data.printf("which one do you want to download\r\n" + "1.銀行 2.中華電信 3.郵局  4.自訂");
			Scanner scanner = new Scanner(System.in);
			i = scanner.nextInt();
			if (i > 3)
				data.printf("尚不提供此服務");
		}
		root = downLink.get(i);

		if (root.split("/").length == 1)
			data.setKeyWord(root.split("/")[0]);
		else
			data.setKeyWord(root.split("/")[1]);
	}

	@Override
	public void doParse() {
		Queue pathQueue = new Queue();
		Queue visitedPathQueue = new Queue();
		Queue pdfQueue = new Queue();

		String responseString;
		WebGet responseData = new WebGet();

		Boolean flag = true;

		if (pathQueue.empty())
			filter();
		// pathQueue.enQueue("銀行/");
		pathQueue.enQueue(root);

		while (!pathQueue.empty()) {

			String s = data.sUrl() + pathQueue.QueueFirst();
			responseString = responseData.getPageHTML(data.sUrl() + pathQueue.QueueFirst());
			pathQueue.deQueue();

			Document doc = Jsoup.parse(responseString);
			Elements links = doc.select("a[href]"); // a with href

			for (Element link : links) {

				String linkHref = link.attr("href");
				String linkText = link.text();
				try {
					linkHref = URLDecoder.decode(linkHref, "UTF-8");
					linkText = URLDecoder.decode(linkText, "UTF-8");
				} catch (UnsupportedEncodingException e) {

					e.printStackTrace();
				}

				// if (linkhref.contains("國營事業") && flag) {
				if (linkHref.contains(data.keyword()) && flag) {
					pathQueue.enQueue(linkHref);
					visitedPathQueue.enQueue(linkHref);
					flag = !flag;
				}
				// if (linkhref.contains("中華電信")) {
				if (linkHref.contains(data.keyword())) {

					if (linkHref.contains(data.FileExtension())) {
						System.out.println("i got " + data.FileExtension().substring(1) + " " + linkHref);
						visitedPathQueue.enQueue(linkHref);
						pdfQueue.enQueue(linkHref);

					} else if (!visitedPathQueue.contians(linkHref)) {
						System.out.println("link is:" + linkHref);
						pathQueue.enQueue(linkHref);
						visitedPathQueue.enQueue(linkHref);
					}
				}
			}

			data.printf("");
		}
		data.printf("search over");

		try {
			responseData.getQueueFiles(pdfQueue);
		} catch (IOException e) {

			data.printf("IO讀取錯誤");
		}
	}

	@Override
	public Queue run() {
		loading();

		doParse();

		return null;
	}

	// 取得檔名
	public static String getFileName(String filePath) {

		int start = filePath.lastIndexOf("/") + 1;
		int end = filePath.lastIndexOf("?", start);
		if (end == -1)
			end = filePath.length();

		return filePath.substring(start, end);
	}
}
