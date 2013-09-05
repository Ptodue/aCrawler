package Crawler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Intialize {

	private static Properties props;
	private static String version;
	private static String targetUrl;
	private static String absolutePath;
	private static String keyWords;
	private static String depth;
	private static String overWrite;
	private static String fileType;
	private static String propertiesInfo;

	Intialize() {
		props = new Properties();
		version = "1.0";
		targetUrl = "http://www.youtube.com/watch?v=sr1Qz63AzK8";
		absolutePath = System.getProperty("user.dir") + "\\";
		overWrite = "true";
		
		keyWords = "銀行\\";
		depth = "0";
		fileType = "pdf";
		
		propertiesInfo = "store\n" + "absolutePath means the download file where you want to save location\n" + "overWrite can set up true or false means if the target download file exists, true is overwrited file\n" + "the other keyword is not TO MODIFY now.\n";

	}

	void loadSetting() {

		try {
			props.load(new FileInputStream("config.properties"));
		} catch (FileNotFoundException e) {
			System.err.println("無法讀取properties檔");
			e.printStackTrace();
			try {
				buildDefaultSetting();
				System.out.println("重新建立config.properties");
				
			} catch (IOException e1) {
				System.err.println("IO寫入錯誤");
				e1.printStackTrace();
			}

		} catch (IOException e) {
			System.err.println("IO讀取錯誤");
			e.printStackTrace();
		}

	}

	void buildDefaultSetting() throws FileNotFoundException, IOException {
		props.setProperty("version", version);
		props.setProperty("targetUrl", targetUrl);
		props.setProperty("keywords", keyWords);
		props.setProperty("absolutePath", absolutePath);
		props.setProperty("depth", depth);
		props.setProperty("overWrite", overWrite);
		props.setProperty("fileType", fileType);

		storeSetting();

	}

	void storeSetting() throws FileNotFoundException, IOException {
		props.store(new FileOutputStream("config.properties"), propertiesInfo);
	}

	String get(String key) {
		return props.getProperty(key);
	}

	void close() {
		props.clear();
	}
}
