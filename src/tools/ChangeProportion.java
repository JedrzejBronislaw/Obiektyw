package tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class ChangeProportion {

	private float newProportion;
	private Naming naming = new Naming();

	public void setNaming(Naming naming) {
		this.naming = naming;
	}

	public ChangeProportion(float newProportion) {
		this.newProportion = newProportion;
	}

	public boolean change(String path) {
		BufferedImage image, newImage;
		int w, h;
		float proportion;

		if (newProportion < 1)
			newProportion = 1 / newProportion;

		// System.out.println("path: " + path);
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException | ArrayIndexOutOfBoundsException e) {
			// System.out.println("read excpetion");
			return false;
		}

		if (image == null)
			return false;

		w = image.getWidth();
		h = image.getHeight();
		proportion = (w > h) ? (float) w / h : (float) h / w;

		try {
			if (w > h)// horizontal image
				if (newProportion > proportion)
					newImage = adaptWidth(image, newProportion);
				else
					newImage = adaptHeight(image, newProportion);
			else// vertical image
			if (newProportion > proportion)
				newImage = adaptHeight(image, newProportion);
			else
				newImage = adaptWidth(image, newProportion);
		} catch (OutOfMemoryError e) {
			return false;
		}

		String newPath = naming.newName(path);
		File file = new File(newPath);
		
//		System.out.println(file.getParent());
		try {
			// createPath(newPath);
			// System.out.println("MKDIRS: " + (file.mkdirs() ? "+" : "-"));
			file.getParentFile().mkdirs();
			ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
			ImageWriteParam param = writer.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(0.95f);

//			file.createNewFile();
			ImageOutputStream ios = ImageIO.createImageOutputStream(file);
			writer.setOutput(ios);
//			writer.write(newImage);
			writer.write(null, new IIOImage(newImage, null, null), param);
			ios.close();
//			ImageIO.write(newImage, "jpg", file);
		} catch (IOException e) {
//			 System.out.println("write excpetion");
//			 e.printStackTrace();
			return false;
		}

		return true;
	}

	// private void createPath(String newPath) {
	// File f = new File(newPath);
	// String partPath;
	//
	// String[] dirs = newPath.split(Pattern.quote(File.separator));
	// for(int i=0; i<dirs.length; i++){//(String s : dirs){
	// partPath = "";
	// for(int j=0; j<i; j++)
	// partPath += dirs[j] + File.separator;
	//
	// System.out.print("$ " + partPath);
	// f = new File(partPath);
	// if (!f.exists()){
	// f.mkdir();
	// System.out.println(" -");
	// } else
	// System.out.println(" +");
	// }
	// }

	private BufferedImage adaptHeight(BufferedImage image, float newProportion) {
		int w, h, newH;
		BufferedImage newImage;
		int offsetH;
		// graph

		w = image.getWidth();
		h = image.getHeight();

		newH = (int) ((h > w) ? (w * newProportion) : (w / newProportion));
		offsetH = (newH - image.getHeight()) / 2;

		newImage = new BufferedImage(w, newH, image.getType());
		newImage.getGraphics().setColor(Color.WHITE);
		newImage.getGraphics().fillRect(0, 0, w, newH);
		((Graphics2D) newImage.getGraphics()).drawImage(image, 0, offsetH, null);

		return newImage;
	}

	private BufferedImage adaptWidth(BufferedImage image, float newProportion) {
		int h, w, newW;
		h = image.getHeight();
		BufferedImage newImage;
		int offsetW;

		h = image.getHeight();
		w = image.getWidth();
		newW = (int) ((w > h) ? (h * newProportion) : (h / newProportion));
		offsetW = (newW - image.getWidth()) / 2;

		newImage = new BufferedImage(newW, h, image.getType());
		newImage.getGraphics().setColor(Color.WHITE);
		newImage.getGraphics().fillRect(0, 0, newW, h);
		((Graphics2D) newImage.getGraphics()).drawImage(image, offsetW, 0, null);

		return newImage;
	}

}
