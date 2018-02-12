package tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import javax.imageio.ImageIO;

public class Proportion {

	private String[] fileList;
	private List<ProportionItem> results = new ArrayList<>();

	public Proportion(List<String> fileList) {
		this.fileList = new String[fileList.size()];
		fileList.toArray(this.fileList);

	}

	public Proportion(String[] fileList) {
		this.fileList = fileList;

	}

//	public void t(){
//		System.out.println(results.size());
//		results.forEach(x->System.out.println("\t"+x));
//	}

	public List<ProportionItem> getProportions(){
//		float[] outcome = new float[results.size()];
//
//		for(int i=0; i<results.size(); i++)
//			outcome[i] = results.get(i).proportion;

//		return outcome;
		return results;
	}

	public void compute(Consumer<Float> beforeEachPath) {

		int i = 1;
		float ratio;

		for(String filePath : fileList){
			try{
				ratio = imageRatio(filePath);
				getItem(ratio).addPath(filePath,ratio);
			} catch(IOException|NullPointerException|ArrayIndexOutOfBoundsException e) {
				getItem(0).addPath(filePath, 0);
			}
			if (beforeEachPath != null) beforeEachPath.accept((float)i++/fileList.length);
		}
		
		results.sort(new Comparator<ProportionItem>() {

			@Override
			public int compare(ProportionItem i1, ProportionItem i2) {			
				float prop1 = i1.getProportion();
				float prop2 = i2.getProportion();

				if (prop1>prop2)
					return 1;
				else
				if (prop1<prop2)
					return -1;
				else
					return 0;
			}
		});
	}

	private float imageRatio(String path) throws IOException, NullPointerException{
//		try{
			BufferedImage image = ImageIO.read(new File(path));
	//		ImageIO i = ImageIO.r
			int h = image.getHeight();
			int w = image.getWidth();

			return (h>w) ? (float)h/w : (float)w/h;
//		}catch(IOException e){
//			throw e;
//		}
	}

	private ProportionItem getItem(float proportion){
		for(ProportionItem item : results)
			if (item.fitProportion(proportion)) return item;

		ProportionItem newItem = new ProportionItem();
//		newItem.//setProportion(proportion);
		results.add(newItem);
		return newItem;
	}
}
