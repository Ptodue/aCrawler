package Crawler;


public class Data {
	Intialize init;

	// shared information
	private String sUrl;
	private String sAbsoluteSavePath;
	private boolean bOverWrite;
	private String sFileName;
	private String sFileExtension;
	private boolean bWrongUrl;

	// youtube information
	private boolean bYTBLink;
	private String sDownloadLink;

	// url information
	private String sKeyWord;

	// constructor
	Data() {
		init = new Intialize();
		init.loadSetting();
		sUrl = init.get("targetUrl");
		sAbsoluteSavePath = init.get("absolutePath");
		bOverWrite = Boolean.valueOf(init.get("overWrite"));

		if (confirmYTB(sUrl)) {
			bYTBLink = true;
			SetUpYTBData();
		} else {
			bYTBLink = false;
			SetUpUrlData();
		}
	}

	// shared method
	public String sUrl() {
		return this.sUrl;
	}

	public void setUrl(String url) {
		this.sUrl = url;
	}

	public String AbsoluteSavePath() {
		return this.sAbsoluteSavePath;
	}
	public void setAbsoluteSavePath(String path) {
		this.sAbsoluteSavePath=path;
	}

	public boolean OverWrite() {
		return this.bOverWrite;
	}

	public void setOverWrite(boolean overwrite) {
		this.bOverWrite = overwrite;
	}

	public String FileName() {
		return this.sFileName;
	}

	public void setFileName(String name) {
		this.sFileName = name;
	}

	public String FileExtension() {
		return sFileExtension;
	}

	public void setFileExtension(String extension) {
		this.sFileExtension = extension;
	}

	public boolean WrongUrl() {
		return bWrongUrl;
	}

	public void setWrongUrl(boolean wrongUrl) {
		this.bWrongUrl = wrongUrl;
	}

	// youtube method
	private void SetUpYTBData() {

	}

	public String YTBdownloadLink() {
		return this.sDownloadLink;
	}

	public void setYTBDownLink(String downloadLink) {
		this.sDownloadLink = downloadLink;
	}

	private String toYTBString(String s) {
		return "Youtube [Url=" + sUrl + ", DownLoadURL=" + s + ", FileName=" + sFileName + "]";
	}

//	public void printf(String sentence) {
//		if (bYTBLink && confirmYTB(sentence))
//			System.out.println(toYTBString(sentence));
//		else if (sentence.contains("getit"))
//			System.out.println(toFileString());
//		else
//			System.out.println(sentence);
//	}

	private boolean confirmYTB(String s) {
		if (s.contains("youtube.com"))
			return true;
		else if (s.contains("youtu.be"))
			return true;
		else
			return false;
	}

	// url method
	private void SetUpUrlData() {
		sKeyWord = init.get("keywords");
		sFileExtension = init.get("fileType");
		init.close();
	}

	private String toFileString() {
		return "File [Url=" + sUrl + ", DownLoadURL=" + "??temp" + ", FileName=" + sFileName + "]";
	}
	
	public String keyword() {
		return this.sKeyWord;
	}

	public void setKeyWord(String keyword) {
		this.sKeyWord = keyword;
	}
}
