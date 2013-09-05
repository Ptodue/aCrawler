package Crawler;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

/**
 * @author PTODUE
 * 
 */
public class mainFunction {

	// public void SaveProcess(String path, Intialize init) throws IOException,
	// Exception {
	//
	// // String path =
	// //
	// "http://exam.getit.com.tw/國營事業/中華電信/101/資訊類/專業職三/第一類專員/資安監控與管理/網路與資訊安全及程式設計概論及資料庫管理.pdf";
	//
	// URL url = new URL(path);
	//
	// String filePath = url.getFile();
	// // String fileName = getFileName(filePath);
	//
	// String overWriteFile = init.get("overWrite");
	// String absolutePath = init.get("absolutePath");
	//
	// String outputFile = absolutePath + filePath; // 下載儲存的檔案
	//
	// boolean filter = fileFilter(filePath);
	// boolean overWriteFlag = Boolean.getBoolean(overWriteFile);
	//
	// if (filter) {
	// File desFile = new File(outputFile);
	// if (!desFile.exists()) // 判斷檔案路徑下的上層目錄存不存在
	// desFile.mkdirs(); // 不存在則先建立上層目錄
	//
	// if (desFile.exists()) {
	// if (overWriteFlag) { // 要覆蓋原有檔案，先把舊檔刪除
	// desFile.delete();
	//
	// HttpURLConnection urlConnection = (HttpURLConnection)
	// url.openConnection();
	//
	// if (urlConnection.getResponseCode() == 200) {
	// System.out.println("The size is:" + urlConnection.getContentLength());
	//
	// byte[] buffer = new byte[1024]; // InputStream
	//
	// // 設定接收資料流來源 ,就是要下載的網址
	// BufferedInputStream bufferedInputStream = new
	// BufferedInputStream(urlConnection.getInputStream());
	// // 設定　儲存 要下載檔案的位置.
	// BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new
	// FileOutputStream(desFile));
	// int length = -1;
	// while ((length = bufferedInputStream.read(buffer)) != -1) {
	// bufferedOutputStream.write(buffer, 0, length);
	// }
	// bufferedOutputStream.flush();// 將緩衝區中的資料全部寫出
	// bufferedInputStream.close(); // 關閉資料流
	// bufferedOutputStream.close();
	//
	// System.out.println(outputFile + "下載完成");
	// }
	// } else
	// System.out.println(outputFile + "檔案已存在");
	// }
	//
	// }
	// }




	public static void main(String[] args) throws Exception {

		Intialize init =new Intialize();
		init.loadSetting();
	
		URI uri=new URI(init.get("targetUrl"));
		
		init.close();
		
		Crawler aa = null;
		boolean wrongWeb=false;
		
		if(uri.getHost().contains("www.youtube.com") ||uri.getHost().contains("youtu.be") ){
			aa = new youtubeObtain();
		} else if(uri.getHost().contains("exam.getit.com.tw")){
			aa = new pdfObtain();
		} else{
			System.out.printf("您所輸入的網址，目前尚未支援");
			wrongWeb = true;
		}
		
		if(!wrongWeb)
			aa.run();
		
		pause();

	}

	public static void pause() throws IOException {
		System.out.println("Press any key to continue...");
		System.in.read();
	}

}