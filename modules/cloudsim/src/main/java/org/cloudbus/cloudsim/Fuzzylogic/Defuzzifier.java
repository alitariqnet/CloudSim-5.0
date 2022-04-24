package org.cloudbus.cloudsim.Fuzzylogic;

public class Defuzzifier {
	public float crisp;
	
	public Output output = new Output();
	
	public float defuzzification() {
//		System.out.println(output.toString());
		float f = ((0+1+2)*output.lowV)+((3+4+5+6+7)*output.medV)+((8+9+10)*output.highV);
//		System.out.println(f);
		float l = ((output.lowV*3)+(output.medV*5)+(output.highV*3));
//		System.out.println(l);
//		crisp = (float) f/l;
//		System.out.println(crisp);
//		return crisp/10;
		return (float) f/l;
	}
}
