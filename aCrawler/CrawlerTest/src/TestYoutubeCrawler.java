import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestYoutubeCrawler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String targetURL = "http://www.youtube.com/get_video_info?video_id=x_fQlUtoUqU";

		try {
			URL targetUrl = new URL(targetURL);

			HttpURLConnection conn = (HttpURLConnection) targetUrl.openConnection();

			InputStream is = conn.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String line = null;

			StringBuilder contents = new StringBuilder();

			// if (conn.getResponseCode() == 200) {

			while ((line = br.readLine()) != null) {
				contents.append(line);
				contents.append("\n");
			}

			System.out.print(contents);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		System.out.println("");
	}
}
