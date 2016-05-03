package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class FileOption {

	public static String readFile(String path){
		
		File file = new File(path);
		Reader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			// 一次读一个字符
			reader = new InputStreamReader(new FileInputStream(file));
			int tempchar;
			while ((tempchar = reader.read()) != -1) {
				// 对于windows下，\r\n这两个字符在一起时，表示一个换行。
				// 但如果这两个字符分开显示时，会换两次行。
				// 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
				if (((char) tempchar) != '\r') {
					char c = (char) tempchar;
					sb.append(String.valueOf(c));
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb.toString();
		
	}
	
	public static void writeFile(String path,StringBuffer sb){
		
		File wf = new File(path);
		if (wf.exists()) {
			try {
				wf.delete();
				wf.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				wf.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			FileOutputStream fos = new FileOutputStream(wf);
			
			fos.write(sb.toString().getBytes("utf-8"));
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
