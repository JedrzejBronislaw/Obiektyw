package main;

import java.io.File;
import java.util.List;

import fileSystem.FileList;
import tools.ChangeProportion;
import tools.Copy;
import tools.Naming;
import tools.Progressbar;
import tools.Proportion;
import tools.ProportionItem;

public class Main {

//	private static final String newImagesDirectoryName = "fixed";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileList fileList = new FileList("H:\\abcd");
		Proportion proportion;
		Naming naming = new Naming();
		Progressbar progressBar = new Progressbar(50);

		System.out.println(fileList.getPath());
		System.out.println("There is " + fileList.getFilesList().size() + " files.");
		ChangeProportion chProportion = new ChangeProportion(1.5f);
//		naming.setRootDirectory(fileList.getPath(), fileList.getPath() + File.separator + newImagesDirectoryName);
		naming.setRootDirectory(fileList.getPath(), "H:\\abcd");
		chProportion.setNaming(naming);
		fileList.getFilesList().forEach(fileName->System.out.println("\t" + fileName));

		proportion = new Proportion(fileList.getFilesList());
		long time;
		time = System.nanoTime();
		progressBar.start();
		proportion.compute(percent->progressBar.update(percent));
		progressBar.oneHundred();
		time = System.nanoTime() - time;
		System.out.println("Time: " + (time/1000000/1000f) + " s");
		System.out.println("Proportions (" + proportion.getProportions().size() + "):");
		proportion.getProportions().forEach(
				prop->System.out.println("\t"+
						prop.getProportion() +
						" (" + prop.getPathsCount() + ")" +
						" " + prop.getLabel()));

		int processedImages = 0;
		int demagedImages = 0;
		for(ProportionItem prop : proportion.getProportions())
			if (prop.getProportion() == 0)
				demagedImages = prop.getPathsCount();
			else
				processedImages+=prop.getPathsCount();

		System.out.println("Number of processed images: " + processedImages);
		System.out.println("Number of demaged images: " + demagedImages);

		System.out.println();
		System.out.println("Number copies: " + fileList.getCopies());
		fileList.getMultiCopy().forEach(filename->System.out.println("\t" + filename));
if (1==1) return;
		System.out.println();
		System.out.println("Changing proportion...");
		time = System.nanoTime();
		progressBar.start();
		int i = 1;
		int succcess = 0, failure = 0;
//		fileList.getFilesList().forEach(path->
		for(String path : fileList.getFilesList()){
			if (chProportion.change(path))
				succcess++; else failure++;
			
			progressBar.update((float)i++/fileList.getFilesList().size());
		}
//		chProportion.change(path);
		progressBar.oneHundred();
		time = System.nanoTime() - time;
		System.out.println("Time: " + (time/1000000/1000f) + " s");
		
		System.out.println("Success: " + succcess);
		System.out.println("Failure: " + failure);

		List<String> filesToCopy = fileList.getMultiCopy();
		System.out.println();
		System.out.println("Number of multicopy files: " + filesToCopy.size());
		filesToCopy.forEach(filename->System.out.println("\t" + naming.newName(filename)));
		
		System.out.println();
		System.out.println("Coping...");
		Copy copy = new Copy(naming);
		time = System.nanoTime();
		progressBar.start();
		i = 1;
		succcess = 0;
		failure = 0;
//		fileList.getFilesList().forEach(path->
		for(String path : filesToCopy){
			path = naming.newName(path);
			if (copy.multiplication(path, Copy.numberOfCopies(path)))
				succcess++; else failure++;
			progressBar.update((float)i++/filesToCopy.size());
		}
//		chProportion.change(path);
		progressBar.oneHundred();
		time = System.nanoTime() - time;
		System.out.println("Time: " + (time/1000000/1000f) + " s");
		
		System.out.println("Success: " + succcess);
		System.out.println("Failure: " + failure);
//		for(float p : proportion.getProportions())
//			System.out.println("\t" + p);

//		System.out.println("$");
//		proportion.t();
	}

}
