package persistance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import scratchState.AppState;

public class Persistance {
	
	private static final int FILE_VERSION = 1;
	
	private static File file = null;
	
	public static void save() throws IOException {
		if (file.isDirectory()) throw new IOException("Could not create save file: Is Directory");
		if (!file.exists()) file.createNewFile();
		FileOutputStream os = new FileOutputStream(file);
		try {
			OutputStreamWriter osr = new OutputStreamWriter(os);
			try {
				String fileVersionText = String.format("%1$012d", FILE_VERSION);
				char[] rawVersionData = new char[12];
				fileVersionText.getChars(0, fileVersionText.length(), rawVersionData, 0);
				osr.write(rawVersionData);
				try {
					JSONObject jso = AppState.save();
					osr.write(jso.toString());
				} catch (JSONException jse) {
					throw new IOException("Error saving file", jse);
				}
			} finally {
				osr.close();
			}
		} finally {
			os.close();
		}
	}
	
	public static void load() throws IOException {
		FileInputStream is = new FileInputStream(file);
		
		try {
			InputStreamReader isr = new InputStreamReader(is);
			try {
				char[] rawVersionData = new char[12];
				int charsRead = 0;
				int tries = 0;
				final int MAX_TRIES = 1000;
				while (charsRead < rawVersionData.length && ++tries <= MAX_TRIES) {
					// Note, this will behave properly if the end of the stream is reached, but charsRead will start going backward
					charsRead += isr.read(rawVersionData, charsRead, rawVersionData.length - charsRead);
				}
				
				if (tries > MAX_TRIES) throw new IOException("Error reading save file");
				
				String fileVersionText = String.copyValueOf(rawVersionData);
				try {
					int fileVersion = Integer.parseInt(fileVersionText);
					if (fileVersion > FILE_VERSION) throw new IOException("File version is higher than program can read");
					JSONTokener jst = new JSONTokener(isr);
					JSONObject jso = (JSONObject) jst.nextValue();
					AppState.initalizeFrom(jso);
				} catch (NumberFormatException ns) {
					throw new IOException("Improper version number format in save file");
				} catch (JSONException jse) {
					throw new IOException("Improper json format in save file", jse);
				} catch (ClassCastException cce) {
					throw new IOException("Improper format in save file");
				}
			} finally {
				isr.close();
			}
		} finally {
			is.close();
		}
	}
	
	public static void setFile(File file) throws IOException {
		Persistance.file = file;
	}
	
	public static File getFile() {
		return file;
	}

}
