package net.nunnerycode.java.libraries.cannonball;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class DebugTextFile {

	private File debugFolder;
	private File debugFile;

	public DebugTextFile(File file) {
		this(file.getParentFile(), file);
	}

	public DebugTextFile(String folderPath, String fileName) {
		this(new File(folderPath), new File(folderPath, fileName));
	}

	public DebugTextFile(File folder, File file) {
		if (!folder.exists() && !folder.mkdirs() || !folder.isDirectory()) {
			return;
		}
		this.debugFolder = folder;
		this.debugFile = file;
	}

	public static void debug(File file, String... messages) {
		if (file == null) {
			throw new IllegalArgumentException("file cannot be null");
		}
		try {
			if (!file.exists() && !file.createNewFile()) {
				return;
			}
			FileWriter fw = new FileWriter(file.getPath(), false);
			PrintWriter pw = new PrintWriter(fw);
			for (String message : messages) {
				pw.println(message);
			}
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void debug(String... messages) {
		try {
			if (!getDebugFolder().exists() && !getDebugFolder().mkdirs()) {
				return;
			}
			File saveTo = getDebugFile();
			if (!saveTo.exists() && !saveTo.createNewFile()) {
				return;
			}
			FileWriter fw = new FileWriter(saveTo.getPath(), false);
			PrintWriter pw = new PrintWriter(fw);
			for (String message : messages) {
				pw.println(message);
			}
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> read() {
		List<String> list = new ArrayList<String>();
		try {
			if (!getDebugFolder().exists() && !getDebugFolder().mkdirs()) {
				return list;
			}
			File readFile = getDebugFile();
			if (!readFile.exists() && !readFile.createNewFile()) {
				return list;
			}
			FileReader fileReader = new FileReader(readFile);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String p;
			while ((p = bufferedReader.readLine()) != null) {
				if (!p.contains("#") && p.length() > 0) {
					list.add(p);
				}
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public File getDebugFolder() {
		return debugFolder;
	}

	public File getDebugFile() {
		return debugFile;
	}
}
