package debug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;

public class Logger {

	static final String FILENAME_LOG = "debug" + System.currentTimeMillis() + ".log";

	static String [] levels = {
		"NONE",
		"TRACE",
		"INFO",
		"WARNING",
		"WRONG",
	};

	public static synchronized void println(int level, String content) {
		String present = DateFormat.getDateTimeInstance().format(new Date(System.currentTimeMillis()));

		// screen
		if(level == Crawler.Logger.LEVEL_WRONG)
			System.err.println( "[" + present + "] " + "[" +  levels[level]+ "] " + content);
		else
			System.out.println( "[" + present + "] " + "[" +  levels[level]+ "] " + content);
		
		// TODO it can remark
//		file(present,level,content);

		
	}
	
	public static synchronized void file(String present,int level,String content) {
		// file
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream( FILENAME_LOG, true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		PrintStream printStream = null;
		printStream = new PrintStream(fileOutputStream);
		printStream.println( "[" + present + "] " + "[" +  levels[level]+ "] " + content);

		printStream.close();
	}

	static {
		new File(FILENAME_LOG).delete();
	}
}