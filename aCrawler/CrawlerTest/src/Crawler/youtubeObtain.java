package Crawler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

class YoutubeObtain extends Crawler {

	Data data;
	TubeExtract extractor;

	@Override
	void loading() {
		data = new Data();

		extractor = new TubeExtract(data.sUrl());
	}

	@Override
	void filter() {
	}

	@Override
	void doParse() {
		filter();//temp

		String responseString = WebGet.getPageHTML(data.sUrl());
		Document doc = Jsoup.parse(responseString);

		try {
			String title = URLDecoder.decode(doc.title().replaceAll("- YouTube", ""), "utf-8");
			data.setFileName(title);
			Logger.println(data.FileName());
		} catch (UnsupportedEncodingException e) {
			Logger.println(e);
		}

		if (this.extractor.staus.contains("fail")) {
			data.setWrongUrl(true);
		} else {
			int num = 1;
			Map<Integer, String[]> userMap = new HashMap<Integer, String[]>();
			for (int i : this.extractor.downloadMap.keySet()) {

				Logger.println(String.format("%d.%s %s", num, data.FileName(), TubeInfo.itagMap.get(i)));

				String sFileExtension = getExtension(this.extractor.downloadMap.get(i)[1]);
				userMap.put(num, new String[] { this.extractor.downloadMap.get(i)[0], sFileExtension });
				num++;

			}
			Logger.println("請依上列，輸入所要下載的項目");
			Scanner scanner = new Scanner(System.in);
			int i = scanner.nextInt();

			String downloadLink = userMap.get(i)[0];
			String fileExt = userMap.get(i)[1];

			data.setYTBDownLink(downloadLink);
			data.setFileExtension(fileExt);

//			Logger.println("\r\n" + downloadLink + "\r\n." + fileExt);

			try {
				Logger.println(data.FileName() + "." + data.FileExtension() + "開始下載");
				Download.YTBFile(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	Queue run() {
		loading();
		doParse();
		return null;
	}

	String getExtension(String s) {

		s = s.replaceFirst("video/", "").replaceAll("x-", "");
		if (s.contains(";"))
			s = s.split(";")[0];

		return s.substring(1);
	}

}
