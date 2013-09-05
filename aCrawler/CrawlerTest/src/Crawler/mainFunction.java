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
	// "http://exam.getit.com.tw/����Ʒ~/���عq�H/101/��T��/�M�~¾�T/�Ĥ@���M��/��w�ʱ��P�޲z/�����P��T�w���ε{���]�p���פθ�Ʈw�޲z.pdf";
	//
	// URL url = new URL(path);
	//
	// String filePath = url.getFile();
	// // String fileName = getFileName(filePath);
	//
	// String overWriteFile = init.get("overWrite");
	// String absolutePath = init.get("absolutePath");
	//
	// String outputFile = absolutePath + filePath; // �U���x�s���ɮ�
	//
	// boolean filter = fileFilter(filePath);
	// boolean overWriteFlag = Boolean.getBoolean(overWriteFile);
	//
	// if (filter) {
	// File desFile = new File(outputFile);
	// if (!desFile.exists()) // �P�_�ɮ׸��|�U���W�h�ؿ��s���s�b
	// desFile.mkdirs(); // ���s�b�h���إߤW�h�ؿ�
	//
	// if (desFile.exists()) {
	// if (overWriteFlag) { // �n�л\�즳�ɮסA�������ɧR��
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
	// // �]�w������Ƭy�ӷ� ,�N�O�n�U�������}
	// BufferedInputStream bufferedInputStream = new
	// BufferedInputStream(urlConnection.getInputStream());
	// // �]�w�@�x�s �n�U���ɮת���m.
	// BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new
	// FileOutputStream(desFile));
	// int length = -1;
	// while ((length = bufferedInputStream.read(buffer)) != -1) {
	// bufferedOutputStream.write(buffer, 0, length);
	// }
	// bufferedOutputStream.flush();// �N�w�İϤ�����ƥ����g�X
	// bufferedInputStream.close(); // ������Ƭy
	// bufferedOutputStream.close();
	//
	// System.out.println(outputFile + "�U������");
	// }
	// } else
	// System.out.println(outputFile + "�ɮפw�s�b");
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
			System.out.printf("�z�ҿ�J�����}�A�ثe�|���䴩");
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