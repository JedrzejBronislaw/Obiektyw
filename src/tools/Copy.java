package tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Copy {

	private Naming naming = new Naming();
	
	public void setNaming(Naming naming) {
		this.naming = naming;
	}
	
	public Copy(Naming naming) {
		this.naming = naming;
	}
	
	static public int numberOfCopies(String path){
		File f;
		String fileName;
		int posX;
		int copies;

		f = new File(path);
		fileName = f.getName();

		posX = fileName.toLowerCase().indexOf('x');//TODO znajdŸ "X" srawdŸ czy to co przed da si sprowadzi do inta
		if (posX > 0){
			try {
				copies = Integer.parseInt(fileName.substring(0, posX));
				copies = Math.max(copies, 1);
			} catch (NumberFormatException e) {
				copies = 1;
			}
		} else
			copies = 1;

		return copies;
	}
	
	public boolean multiplication(String path, int n){
		File source = new File(path);
		File target;
		int numberOfCopies = Copy.numberOfCopies(path);

		if (!source.exists()) return false;
		
		try {
			for(int i=1; i<=numberOfCopies; i++){
				target = new File(naming.copy(path,i,n));
				Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
			target = new File(naming.firstCopy(path,n));
			Files.move(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
