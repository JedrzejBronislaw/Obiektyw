package tools;

public class Progressbar {

	private int precision;
	private int curHash = 0;

	public Progressbar(int precision){
		if (precision < 4) precision = 4;

		this.precision = precision;
	}

	public void start(){
		curHash = 0;
		int onePart = (precision - 4) / 2;

		System.out.print("[");
		for (int i=0; i<onePart; i++)
			System.out.print("-");
		System.out.print("100%");
		for (int i=onePart; i<precision-4; i++)
			System.out.print("-");
		System.out.println("]");

		System.out.print(" ");

	}

	public void update(float percent){
		int hash = (int) (percent*precision);
		if (curHash < hash){
			for (int i=curHash; i<hash; i++)
				System.out.print("#");
			curHash = hash;
		}
	}

	public void oneHundred(){

		for (int i=curHash; i<precision; i++)
			System.out.print("#");
		System.out.println();

		curHash = precision;
	}
}
