package Crawler;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

public class TubeExtract extends TubeInfo {

	static final String YOUTUBEINFO_STRING = "http://www.youtube.com/get_video_info?eurl=https://test.test&sts=1586&video_id=%s";
	String info;
	String staus;

	public TubeExtract(String youtubeLink) {
		super(youtubeLink);

		// TODO Auto-generated constructor stub
		extractYTBLink();
	}

	String extractInfo() {
		WebGet responseData = new WebGet();

		String sVidUrl = String.format(YOUTUBEINFO_STRING, super.vid);

		String youtubeInfo = responseData.getVideoInfo(sVidUrl);

		this.info = youtubeInfo;

		System.out.println(String.format("targetURL:\n%s\nyoutubeInfo:\n%s", sVidUrl, youtubeInfo));
		return youtubeInfo;
	}

	void extractYTBLink() {

		getYouTubeVideoLink(extractInfo());

	}

	/* 參考 Kej's FLV Retriever
	 * parse.youtube.fmt_url_map.js 演算法
	 */
	String getYouTubeVideoLink(String videoInfo) {

		try {
			String rData = videoInfo;
			String[] rdataArray = rData.split("&");
			boolean succ = false;

			for (int i = 0; i < rdataArray.length; i++) {
				String r0 = getSubstr(rdataArray[i], 0, 26);

				if (r0.contains("url_encoded_fmt_stream_map")) {
					String r1 = URLDecoder.decode(getSubstr(rdataArray[i], 27), "UTF-8");
					String[] temp1 = r1.split(",");
					ArrayList<Integer> fmt = new ArrayList<Integer>();
					ArrayList<String> fmt_url = new ArrayList<String>();
					ArrayList<String> fmt_sig = new ArrayList<String>();
					ArrayList<String> fmt_type = new ArrayList<String>();
					
					for (int j = 0; j < temp1.length; j++) {
						String[] temp2 = temp1[j].split("&");
						
						
						for (int jj = 0; jj < temp2.length; jj++) {
							int temp_itag = -1;
							if (getSubstr(temp2[jj], 0, 5).contains("itag=")) {
								temp_itag = Integer.parseInt(getSubstr(temp2[jj], 5, 10));
								fmt.add(temp_itag);

							} else if (getSubstr(temp2[jj], 0, 4).contains("url=")) {
								fmt_url.add("" + getSubstr(temp2[jj], 4));

							} else if (getSubstr(temp2[jj], 0, 4).contains("sig=")) {
								fmt_sig.add("" + getSubstr(temp2[jj], 4));

							} else if (getSubstr(temp2[jj], 0, 2).contains("s=")) {
								fmt_sig.add(SigHandlerAlternative(temp2[jj].substring(2)));

							} else if (getSubstr(temp2[jj], 0, 5).contains("type=")) {
								fmt_type.add( "(" + URLDecoder.decode(getSubstr(temp2[jj], 5), "UTF-8") + ")");
							}
						}
					}
					String dllinks = "";
					String webmlinks = "";
					String tempLink = "";
					int index = 0;
					Object[] tempfmt_url = fmt_url.toArray();
					Object[] tempfmt_sig = fmt_sig.toArray();
					Object[] tempfmt_type = fmt_type.toArray();

					for (int k : fmt) {

						if (k == 43 || k == 44 || k == 45 || k == 46 || k == 100 || k == 101 || k == 102) {
							if (webmlinks.length() > 0) {
								webmlinks += "\r\n";
							}
							tempLink = URLDecoder.decode(tempfmt_url[index].toString(), "UTF-8") + String.format("&signature=%s", tempfmt_sig[index].toString());

							this.downloadMap.put(k, new String[]{tempLink,(String) tempfmt_type[index]});
							webmlinks += tempLink + "\r\n" +tempfmt_type[index]+ "---"+itagMap.get(k);

						} else {
							if (dllinks.length() > 0) {
								dllinks += "\r\n";
							}
							tempLink = URLDecoder.decode(tempfmt_url[index].toString(), "UTF-8") + String.format("&signature=%s", tempfmt_sig[index].toString());
							// downloadMap1.put(k, tempLink);

							this.downloadMap.put(k, new String[]{tempLink,(String) tempfmt_type[index]});
							dllinks += tempLink + "\r\n" +tempfmt_type[index]+ "---"+ itagMap.get(k);
						}
						index++;
					}
					if (webmlinks.length() > 0) {
						if (dllinks.length() > 0) {
							dllinks += "\r\n";
						}
						dllinks += webmlinks;
					}

					System.out.println(URLDecoder.decode(dllinks, "UTF-8"));

					succ = true;
					this.staus = "success";
					return "Success to get youtubeLink";

				} else
					System.out.print("");

			}

			if (!succ) {
				String result;
				String rdata_status = "";
				String rdata_reason = "";
				String[] rdata_temp;
				for (int i = 0; i < rdataArray.length; i++) {
					rdata_temp = rdataArray[i].split("=");
					if (rdata_temp[0].equals("status")) {
						rdata_status = URLDecoder.decode(rdata_temp[1], "UTF-8");
					}
					if (rdata_temp[0].equals("reason")) {
						rdata_reason = URLDecoder.decode(rdata_temp[1], "UTF-8");
					}
				}
				result = String.format("status:%s \r\nreason:%s", rdata_status, rdata_reason.length() <= 0 ? "the file is not found" : rdata_reason);

				this.staus = rdata_status;
				System.out.println(result);
				return result;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		this.staus = "success";
		return "Success to get youtubeLink";
	}

	private String SigHandlerAlternative(String s) {
		char[] sArray = s.toCharArray();
		char tmpA, tmpB;

		tmpA = sArray[0];
		tmpB = sArray[52];

		sArray[0] = tmpB;
		sArray[52] = tmpA;

		tmpA = sArray[83];
		tmpB = sArray[62];

		sArray[83] = tmpB;
		sArray[62] = tmpA;

		s = slice(String.valueOf(sArray), 3);
		s = reverse(s);
		s = slice(s, 3);

		return s;
	}
	

	
	/**
	 * <p>
	 * Example: Different between with String.substring and getSubstr<br>
	 * String a ="654321"; <br>
	 * b=a.substring(1,2); //b="5"<br>
	 * b=getSubstr(a,1,2); //b="54" <br>
	 * 
	 * @param s
	 *            原始字串
	 * @param beginningIndex
	 *            起始位址
	 * @param length
	 *            需切割字串長度
	 * @return subStr 回傳切割字串
	 * 
	 */
	String getSubstr(String s, int beginningIndex, int length) {
		char[] temp = s.toCharArray();
		length = temp.length < length ? temp.length : length;

		StringBuilder subStr = new StringBuilder();
		for (int i = beginningIndex; i < length; i++) {
			subStr.append(temp[i]);
		}

		return subStr.toString();
	}

	String getSubstr(String s, int beginningIndex) {

		return getSubstr(s, beginningIndex, s.length());
	}

	private String slice(String word, int index) {
		char[] chs = word.toCharArray();
		StringBuilder sliceWord = new StringBuilder();

		for (int i = index; i < word.length(); i++) {
			sliceWord.append(chs[i]);
		}

		return sliceWord.toString();
	}

	private String reverse(String word) {
		char[] chs = word.toCharArray();

		int i = 0, j = chs.length - 1;
		while (i < j) {
			// swap chs[i] and chs[j]
			char t = chs[i];
			chs[i] = chs[j];
			chs[j] = t;
			i++;
			j--;
		}
		return String.valueOf(chs);
	}

}
