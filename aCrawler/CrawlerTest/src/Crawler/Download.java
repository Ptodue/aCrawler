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
		
		String outputFile = absolutePath + filePath.trim(); // �U���x�s���ɮ�

		URL url = new URL(targetUrl);

		boolean filter = true;

		if (filter) {
			File desFile = new File(outputFile);
			if (!desFile.exists()) { // �P�_�ɮ׸��|�U���W�h�ؿ��s���s�b
				Logger.println(Logger.LEVEL_WARNING, "���w�s�ɸ��|��}���s�b");
				Logger.println("�إߥؿ���...");
				desFile.mkdirs(); // ���s�b�h���إߤW�h�ؿ�
			}
			if (desFile.exists()) {
				if (overWriteFlag) { // �n�л\�즳�ɮסA�������ɧR��
					desFile.delete();

					HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

					if (urlConnection.getResponseCode() == 200) {
						int fileSize = urlConnection.getContentLength();
						Logger.println("�ɮ׮e�q" + fileSize+" bytes.");
						Logger.println(outputFile + "�}�l�U��");

						byte[] buffer = new byte[10240]; // InputStream

						// �]�w������Ƭy�ӷ� ,�N�O�n�U�������}
						BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
						// �]�w�@�x�s �n�U���ɮת���m.
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
						bufferedOutputStream.flush();// �N�w�İϤ�����ƥ����g�X
						bufferedInputStream.close(); // ������Ƭy
						bufferedOutputStream.close();

						Logger.println(outputFile + "�U������");
					}
				} else
					Logger.println(outputFile + "�ɮפw�s�b");
			}

		}

	}
}
