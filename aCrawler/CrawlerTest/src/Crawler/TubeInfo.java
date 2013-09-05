package Crawler;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TubeInfo {

	String youtubeLink = "";
	Map<Integer, String[]> downloadMap;
	protected String vid = "";

	static final Map<Integer, String> itagMap = new HashMap<Integer, String>() {
		private static final long serialVersionUID = -6925194111122038477L;
		{
			put(0, "(FLV, 320 x 240, Mono 22KHz MP3)"); // delete ?
			put(5, "(FLV, 400 x 240, Mono 44KHz MP3)");
			put(6, "(FLV, 480 x 360, Mono 44KHz MP3)"); // delete ?
			put(34, "(FLV, 640 x 360, Stereo 44KHz AAC)");
			put(35, "(FLV, 854 x 480, Stereo 44KHz AAC)");

			put(13, "(3GP, 176 x 144, Stereo 8KHz)"); // delete ?
			put(17, "(3GP, 176 x 144, Stereo 44KHz AAC)");
			put(36, "(3GP, 320 x 240, Stereo 44KHz AAC)");

			put(18, "(MP4(H.264), 640 x 360, Stereo 44KHz AAC)");
			put(22, "(MP4(H.264), 1280 x 720, Stereo 44KHz AAC)");
			put(37, "(MP4(H.264), 1920 x 1080, Stereo 44KHz AAC)");
			put(38, "(MP4(H.264), 4096 x 3072, Stereo 44KHz AAC)");
			put(83, "(MP4(H.264), 854 x 240, Stereo 44KHz AAC)");
			put(82, "(MP4(H.264), 640 x 360, Stereo 44KHz AAC)");
			put(85, "(MP4(H.264), 1920 x 520, Stereo 44KHz AAC)");
			put(84, "(MP4(H.264), 1280 x 720, Stereo 44KHz AAC)");

			put(43, "(WebM(VP8), 640 x 360, Stereo 44KHz Vorbis)");
			put(44, "(WebM(VP8), 854 x 480, Stereo 44KHz Vorbis)");
			put(45, "(WebM(VP8), 1280 x 720, Stereo 44KHz Vorbis)");
			put(100, "(WebM(VP8), 640 x 360, Stereo 44KHz Vorbis)");
			put(101, "(WebM(VP8), 854 x 480, Stereo 44KHz Vorbis)");
			put(46, "(WebM(VP8), 1920 x 540, Stereo 44KHz Vorbis)");
			put(102, "(WebM(VP8), 1280 x 720, Stereo 44KHz Vorbis)");
		}
	};

	public TubeInfo(String youtubeLink) {
		this.youtubeLink = youtubeLink;
		this.downloadMap = new HashMap<Integer, String[]>();
		setVid();

	}

	private void setVid() {
		try {

			if (youtubeLink.indexOf("youtu.be") == 7) {
				/* http://youtu.be */
				String[] queryArray = youtubeLink.split("/");
				/* [http:, ,youtu.be, vid] */
				vid = queryArray[3];
			} else {
				/**
				 * http://www.youtube.com/watch?v=XXXXXXXX&....
				 */
				URL tempUrl = new URI(youtubeLink).toURL();
				String[] queryArray = tempUrl.getQuery().split("&");
				for (String i : queryArray) {
					String[] pair = i.split("=");

					// pair[0] = key ; pair[1] = value
					if (pair[0].equals("v"))
						vid = pair[1];
				}
			}
		} catch (Exception e) {
			/* http://www.youtube.com/v/XXXXXXXX */
			String[] queryArray = youtubeLink.split("/");
			/* [http:, ,www....., v, vid] */
			vid = queryArray[4];
		}
	}

	public String getYoutubeLink() {
		return youtubeLink;
	}

	public void setYoutubeLink(String youtubeLink) {
		this.youtubeLink = youtubeLink;
	}

	public String[] getDownloadMap(Integer fmt) {
		return downloadMap.get(fmt);
	}

	public void put(Integer fmt, String link) {
		String[] temp = { link, "" };
		downloadMap.put(fmt, temp);

	}

	public Map<Integer, String[]> getDownloadMap() {
		return downloadMap;
	}

	public void setDownloadMap(Map<Integer, String[]> downloadMap) {
		this.downloadMap = downloadMap;
	}
}
