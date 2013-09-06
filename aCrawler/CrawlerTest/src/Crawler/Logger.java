package Crawler;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Logger {
	public static final int LEVEL_NONE = 0;
	public static final int LEVEL_TRACE = 1;
	public static final int LEVEL_INFO = 2;
	public static final int LEVEL_WARNING = 3;
	public static final int LEVEL_WRONG = 4;

	public static int level =  LEVEL_TRACE;

	public static void println(String content) {

		println(LEVEL_TRACE, content);
	}
	
	public static void println(Exception e){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		
		println(LEVEL_WRONG, sw.toString());
	}
	
	public static void println(Throwable throwable){
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		
		println(LEVEL_WRONG, sw.toString());
	}

	public static void println(int level, String content) {
		if( level >= Logger.level )
			debug.Logger.println(level, content);
	}
	
}
