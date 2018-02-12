package fileSystem;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tools.Copy;
import tools.Naming;

public class FileList {

	private String path;
	private List<String> filesList = new ArrayList<>();
	private List<String> multiCopy = new ArrayList<>();
	private int copies;
//	private String[] filesList;

	public String getPath() {
		return path;
	}

	public List<String> getFilesList() {
		return filesList;
	}

	public List<String> getMultiCopy() {
		return multiCopy;
	}

	public int getCopies() {
		return copies;
	}

	public FileList(String path) {
		try {
			this.path = new File(path).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		load(this.path);
		copies = countCopies();
	}

	private void load(String dirPath) {
		File f = new File(dirPath);

		FilenameFilter filter;
		filter = new FilenameFilter() {

			@Override
			public boolean accept(File dir, String name) {
				File file = new File(dir.getAbsolutePath()+File.separator+name);

				if (file.isDirectory())
					load(file.getAbsolutePath());

				return	(name.toLowerCase().endsWith(".jpg") ||
						name.toLowerCase().endsWith(".jpeg") ||
						name.toLowerCase().endsWith(".bmp")  ||
						name.toLowerCase().endsWith(".png")) &&
						file.isFile();
			}
		};

		for(String file : f.list(filter))
			filesList.add(dirPath + File.separator + file);
	}

	private int countCopies(){
//		File f;
//		String fileName;
//		int posX;
		int copies;
		int outcome = 0;

		for(String path : filesList){
			copies = Copy.numberOfCopies(path);
			if (copies > 1) multiCopy.add(path);
			outcome += copies;
//			f = new File(path);
//			fileName = f.getName();
//
//			posX = fileName.toLowerCase().indexOf('x');//TODO znajdŸ "X" srawdŸ czy to co przed da si sprowadzi do inta
//			if (posX > 0){
//				try {
//					copies = Integer.parseInt(fileName.substring(0, posX));
//				} catch (NumberFormatException e) {
//					outcome ++;
//					continue;
//				}
//				outcome += copies;
//				multiCopy.add(path);
//			} else
//				outcome++;
		}

		return outcome;

	}
}
