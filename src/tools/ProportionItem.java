package tools;

import java.util.ArrayList;
import java.util.List;

public class ProportionItem {

	private List<String> filePaths = new ArrayList<>();
	private float proportionMin = -1;
	private float proportionMax = -1;
	private String label = "";

	private void setLabel() {
//		this.proportion = proportion;
		float proportion = getProportion();
		
		if (proportion > (0.99f*4/3) &&
			proportion < (1.01f*4/3))
			label = "3x4";

		if (proportion > (0.99f*15/10) &&
			proportion < (1.01f*15/10))
			label = "10x15";

		if (proportion > (0.99f*13/9) &&
			proportion < (1.01f*13/9))
			label = "9x13";

	}

	public float getProportion() {
		return proportionMin + (proportionMax-proportionMin)/2;
	}

	public float widthProportionValues(){
		return proportionMax - proportionMin;
	}

	public boolean addPath(String path, float ratio){
		float min, max;
		min = proportionMin;
		max = proportionMax;

		if (min == -1){
			proportionMin = ratio;
			proportionMax = ratio;
			setLabel();
		} else

		if (ratio > max){
			if (ratio-min > 0.01) return false;
			else
				proportionMax = ratio;
		} else
		if (ratio < min){
			if (max-ratio > 0.01) return false;
			else
				proportionMin = ratio;
		}

		filePaths.add(path);
		return true;
	}

	public List<String> getFilePaths() {
		return filePaths;
	}

	public int getPathsCount(){
		return filePaths.size();
	}

	public String getLabel() {
		return label;
	}

	public boolean fitProportion(float ratio) {
		float min, max;
		min = proportionMin;
		max = proportionMax;
		
		if (ratio > max){
			if (ratio-min > 0.01) return false;

		} else
		if (ratio < min){
			if (max-ratio > 0.01) return false;
		}
		return true;
	}
}
