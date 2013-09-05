package Crawler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class WebGet extends DefaultHttpClient {

	URL source;
	
	WebGet(){
		
	}
	
	// connect target site and return the web context
	public static String getPageHTML(String url) {
		HttpGet httpGet = null;
		HttpResponse response = null;
		String pageContent = null;
		DefaultHttpClient dhc = null;

		try {
			dhc = new DefaultHttpClient();
			dhc.getParams().setParameter("http.protocol.content-charset", "UTF-8");
			httpGet = new HttpGet(url);

			response = dhc.execute(httpGet);
			pageContent = EntityUtils.toString(response.getEntity());

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			return pageContent;
		else
			return String.valueOf(response.getStatusLine().getStatusCode());

	}

	public String postRequest(String url) {
		// // Post Request Example，查詢台大圖書館書籍
		// ArrayList<NameValuePair> pairList = new ArrayList<NameValuePair>();
		// pairList.add(new BasicNameValuePair("searchtype", "t"));
		// pairList.add(new BasicNameValuePair("searchscope", "keyword"));
		// pairList.add(new BasicNameValuePair("searcharg", "Head First Java"));
		// pairList.add(new BasicNameValuePair("SORT", "D"));
		//
		// HttpPost httpPost = new
		// HttpPost("http://tulips.ntu.edu.tw:1081/search*cht/a?");
		// StringEntity entity = new
		// StringEntity(URLEncodedUtils.format(pairList, "UTF-8"));
		// httpPost.setEntity(entity);
		// response = demo.execute(httpPost);
		// responseString = EntityUtils.toString(response.getEntity());
		// if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
		// // 如果回傳是 200 OK 的話才輸出
		// System.out.println(responseString);
		// } else {
		// System.out.println(response.getStatusLine());
		// }
		return "";
	}

	public String downloadFile(String url) {
		String strFileContents = "";
		StringBuilder responseString = new StringBuilder();
		try {
			URL targetUrl = new URL(url);

			HttpURLConnection urlConnection = (HttpURLConnection) targetUrl.openConnection();

			if (urlConnection.getResponseCode() == 200) {
				System.out.println("The size is:" + urlConnection.getContentLength());

				byte[] buffer = new byte[1024]; // InputStream

				// 設定接收資料流來源 ,就是要下載的網址
				BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
				// 將資料來源寫入buffer，並存入到Stringbuilder
				int bytesRead = 0;
				while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
					strFileContents = new String(buffer, 0, bytesRead);
					responseString.append(strFileContents);
				}

				bufferedInputStream.close(); // 關閉資料流

				System.out.println(strFileContents.toString());
				System.out.println(responseString.toString() + "下載完成");

			}

		} catch (IOException e) {
			
			throw new RuntimeException(e);
		}

		return responseString.toString();
	}

	public String getVideoInfo(String u) {

		StringBuilder contents = new StringBuilder();

		try {
			URL targetUrl = new URL(u);

			HttpURLConnection conn = (HttpURLConnection) targetUrl.openConnection();

			InputStream is = conn.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String line = null;

			while ((line = br.readLine()) != null) {
				contents.append(line);
				contents.append("\n");
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return contents.toString();

	}

	public void getQueueFiles(Queue downloadQueue) throws IOException {

		Data data = new Data();		
		String root = data.sUrl();

		String path = "";
		while (!downloadQueue.empty()) {
			path = root + downloadQueue.QueueFirst();

			URL url = new URL(path);

			String filePath = url.getFile();

			String absolutePath = data.AbsoluteSavePath();
			String outputFile = absolutePath + filePath; // 下載儲存的檔案

			boolean filter = true;
			boolean overWriteFlag = data.OverWrite();

			if (filter) {
				File desFile = new File(outputFile);
				if (!desFile.exists()) // 判斷檔案路徑下的上層目錄存不存在
					desFile.mkdirs(); // 不存在則先建立上層目錄

				if (desFile.exists()) {
					if (overWriteFlag) { // 要覆蓋原有檔案，先把舊檔刪除
						desFile.delete();

						HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

						if (urlConnection.getResponseCode() == 200) {
							System.out.println("The size is:" + urlConnection.getContentLength()+" bytes");

							byte[] buffer = new byte[1024]; // InputStream

							// 設定接收資料流來源 ,就是要下載的網址
							BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
							// 設定　儲存 要下載檔案的位置.
							BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(desFile));
							int length = -1;
							while ((length = bufferedInputStream.read(buffer)) != -1) {
								bufferedOutputStream.write(buffer, 0, length);
							}
							bufferedOutputStream.flush();// 將緩衝區中的資料全部寫出
							bufferedInputStream.close(); // 關閉資料流
							bufferedOutputStream.close();

							System.out.println(outputFile + "下載完成");
						}
					} else
						System.out.println(outputFile + "檔案已存在");
				}

			}
			downloadQueue.deQueue();

		}
	}

}
