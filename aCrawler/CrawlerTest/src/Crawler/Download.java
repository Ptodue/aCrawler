package Crawler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import Crawler.Logger;

public class Download {

	Download() {

	}

	public static void YTBFile(Data d) throws FileNotFoundException, IOException {

		String targetUrl = d.YTBdownloadLink();
		String dot = ".";
		
		boolean overWriteFlag = d.OverWrite();
		
		String filePath = d.FileName() + dot + d.FileExtension();
		String absolutePath = d.AbsoluteSavePath();
		
		String outputFile = absolutePath + filePath.trim(); // 下載儲存的檔案

		URL url = new URL(targetUrl);

		boolean filter = true;

		if (filter) {
			File desFile = new File(outputFile);
			if (!desFile.exists()) { // 判斷檔案路徑下的上層目錄存不存在
				Logger.println(Logger.LEVEL_WARNING, "指定存檔路徑位址不存在");
				Logger.println("建立目錄中...");
				desFile.mkdirs(); // 不存在則先建立上層目錄
			}
			if (desFile.exists()) {
				if (overWriteFlag) { // 要覆蓋原有檔案，先把舊檔刪除
					desFile.delete();

					HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

					if (urlConnection.getResponseCode() == 200) {
						int fileSize = urlConnection.getContentLength();
						Logger.println("檔案容量" + fileSize+" bytes.");
						Logger.println(outputFile + "開始下載");

						byte[] buffer = new byte[10240]; // InputStream

						// 設定接收資料流來源 ,就是要下載的網址
						BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
						// 設定　儲存 要下載檔案的位置.
						BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(desFile));
						int length = -1;
						long total = 0;
						int percentage = 0, oldpercentage = 0;

						while ((length = bufferedInputStream.read(buffer)) != -1) {
							total += length;
							bufferedOutputStream.write(buffer, 0, length);
							percentage = (int) ((total * 100) / fileSize);
							if (oldpercentage < percentage) {
								if (percentage % 10 == 0)
									Logger.println("" + (int) ((total * 100) / fileSize) + "%");
								System.out.print(".");
								oldpercentage = percentage;
							}

						}
						bufferedOutputStream.flush();// 將緩衝區中的資料全部寫出
						bufferedInputStream.close(); // 關閉資料流
						bufferedOutputStream.close();

						Logger.println(outputFile + "下載完成");
					}
				} else
					Logger.println(outputFile + "檔案已存在");
			}

		}

	}
}
