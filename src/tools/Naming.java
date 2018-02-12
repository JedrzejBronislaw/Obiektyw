package tools;

import java.io.File;

public class Naming {

	public static final String modifictionFilePrefix = "_cp";

	private String rootDirectory = "";
	private String oldRootDirectory = "";

	public void setRootDirectory(String oldRootDirectory, String rootDirectory) {
		this.rootDirectory = rootDirectory;
		this.oldRootDirectory = oldRootDirectory;
	}

	public String newName(String path) {
		int posDot = path.lastIndexOf(".");
		String name = path.substring(0, posDot) + modifictionFilePrefix + path.substring(posDot);
		
		if (name.indexOf(oldRootDirectory) != -1){
			String begin = rootDirectory;
			String end   = name.substring(oldRootDirectory.length());
			
			if (begin.endsWith(File.separator) || end.startsWith(File.separator))
				name = begin + end;
			else
				name = begin + File.separator + end;
		}
		return name;
	}

	public String firstCopy(String path, int n) {
		File f;
		String fileName;
		int prefixLen;
		int lenOldNameFile;
		int nameBegin;

		f = new File(path);
		fileName = f.getName();

		lenOldNameFile = fileName.length();
		nameBegin = path.length()-lenOldNameFile;
		prefixLen = fileName.toLowerCase().indexOf('x')+1;

			while (path.charAt(prefixLen+1) == ' ' ||
				path.charAt(prefixLen+1) == '_')
				prefixLen++;



		int posDot = path.lastIndexOf(".");
		String name = path.substring(0, nameBegin) + path.substring(nameBegin+prefixLen, posDot) + " 1 z " + n + path.substring(posDot);

		return name;
	}

	public String copy(String path, int copyNumber, int n) {
		File f;
		String fileName;
		int prefixLen;
		int lenOldNameFile;
		int nameBegin;

		f = new File(path);
		fileName = f.getName();

		lenOldNameFile = fileName.length();
		nameBegin = path.length()-lenOldNameFile;
		prefixLen = fileName.toLowerCase().indexOf('x')+1;

			while (path.charAt(prefixLen+1) == ' ' ||
				path.charAt(prefixLen+1) == '_')
				prefixLen++;



		int posDot = path.lastIndexOf(".");
		String name = path.substring(0, nameBegin) + path.substring(nameBegin+prefixLen, posDot) + " " + copyNumber + " z " + n + path.substring(posDot);

		return name;
	}
}
