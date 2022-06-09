package org.cloudbus.cloudsim.Fuzzylogic;

public class Defuzzifier {
	
	public float crisp;
	
	// use these in forming rules as output parameter
	boolean isLowDefuzz = false;
	boolean isMedDefuzz = false;
	boolean isHighDefuzz = false;
	
	// use these to get the calculated values for calculating crisp output
	float temp;
	float lowDefuzz;
	float medDefuzz;
	float highDefuzz;
			
	void reset() {
		this.isHighDefuzz=false;
		this.isMedDefuzz=false;
		this.isLowDefuzz=false;
		this.highDefuzz=0.0f;
		this.medDefuzz=0.0f;
		this.lowDefuzz=0.0f;
	}
	
	public float defuzzification() {
//		System.out.println("lowV: "+lowV+" medV: "+medV+" highV: "+highV);
		float f = ((0+1+2)*lowDefuzz)+((3+4+5+6+7)*medDefuzz)+((8+9+10)*highDefuzz);
//		System.out.println("f: "+f);
		float l = ((lowDefuzz*3)+(medDefuzz*5)+(highDefuzz*3));
//		System.out.println("l: "+l);
//		crisp = (float) f/l;
//		System.out.println(crisp);
//		return crisp/10;
		return (float) f/l;
	}
}
