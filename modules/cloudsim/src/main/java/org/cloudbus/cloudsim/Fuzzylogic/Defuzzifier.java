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
			
	public void reset() {
		this.isHighDefuzz=false;
		this.isMedDefuzz=false;
		this.isLowDefuzz=false;
		this.highDefuzz=0.0f;
		this.medDefuzz=0.0f;
		this.lowDefuzz=0.0f;
	}
	
	public double defuzzification() {
		double f = 0;
		double a = 0,b,c,e,g,h,l;
		
		if(isLowDefuzz && isMedDefuzz) {
			
		}
		if(isMedDefuzz && isHighDefuzz)
		System.out.println("");
		System.out.println("lowDefuzz: "+ lowDefuzz + " medDefuzz:"+medDefuzz+" highDefuzz:"+highDefuzz);
		
		
		
		if (lowDefuzz>=0.75) {
			a = ((1*0.75)+(2*0.5)+(3*0.25));
			e = 0.75+0.5+0.25;
			System.out.println("lowDefuzz>=0.75");
		}
		else if (lowDefuzz>=0.5) {
			a = ((1*lowDefuzz)+(2*0.5)+(3*0.25));
			e = lowDefuzz+0.5+0.25;
			System.out.println("lowDefuzz>=0.5");
		}
		else if(lowDefuzz>=0.25) {
			a = ((1*lowDefuzz)+(2*lowDefuzz)+(3*0.25));
			e = lowDefuzz+lowDefuzz+0.25;
			System.out.println("lowDefuzz>=0.25");
		}
		else {
			a = ((1*lowDefuzz)+(2*lowDefuzz)+(3*lowDefuzz));
			e = lowDefuzz+lowDefuzz+lowDefuzz;
			System.out.println("lowDefuzz<0.25");
		}
			
		if(medDefuzz>=0.75) {
			b = (((2*0.25)+(3*0.5)+(4*0.75)+(5*medDefuzz)+(6*0.75)+(7*0.5)+(8*0.25)));
			g = 0.25+0.5+0.75+medDefuzz+0.75+0.5+0.25;
			System.out.println("medDefuzz>=0.75");
		}
		else if(medDefuzz>=0.5) {
			b = (((2*0.25)+(3*0.5)+(4*medDefuzz)+(5*medDefuzz)+(6*medDefuzz)+(7*0.5)+(8*0.25)));
			g = 0.25+0.5+medDefuzz+medDefuzz+medDefuzz+0.5+0.25;
			System.out.println("medDefuzz>=0.5");
		}
		else if(medDefuzz>=0.25) {
			b = (((2*0.25)+(3*medDefuzz)+(4*medDefuzz)+(5*medDefuzz)+(6*medDefuzz)+(7*medDefuzz)+(8*0.25)));
			g = 0.25+medDefuzz+medDefuzz+medDefuzz+medDefuzz+medDefuzz+0.25;
			System.out.println("medDefuzz>=0.25");
		}
		else {
			b = (((2*medDefuzz)+(3*medDefuzz)+(4*medDefuzz)+(5*medDefuzz)+(6*medDefuzz)+(7*medDefuzz)+(8*medDefuzz)));
			g = medDefuzz+medDefuzz+medDefuzz+medDefuzz+medDefuzz+medDefuzz+medDefuzz;
			System.out.println("medDefuzz<0.25");
		}
		
		if(highDefuzz>=0.75) {
			c = (((7*0.25)+(8*0.5)+(9*0.75)+(10*highDefuzz)));
			h = 0.25+0.5+0.75+highDefuzz;
			System.out.println("highDefuzz>=0.75");
		}
		else if(highDefuzz>=0.5) {
			c = (((7*0.25)+(8*0.5)+(9*highDefuzz)+(10*highDefuzz)));
			h = 0.25+0.5+highDefuzz+highDefuzz;
			System.out.println("highDefuzz>=0.5");
		}
		else if(highDefuzz>=0.25) {
			c = (((7*0.25)+(8*highDefuzz)+(9*highDefuzz)+(10*highDefuzz)));
			h = 0.25+highDefuzz+highDefuzz+highDefuzz;
			System.out.println("highDefuzz>=0.25");
		}
		else {
			c = (((7*highDefuzz)+(8*highDefuzz)+(9*highDefuzz)+(10*highDefuzz)));
			h = highDefuzz+highDefuzz+highDefuzz+highDefuzz;
			System.out.println("highDefuzz<0.25");
		}
		
		f = a+b+c;
		System.out.println("a: "+a+" b: "+b+" c: "+c+" f: "+f);
		l = e+g+h;
		System.out.println("e: "+e+" g: "+g+" h: "+h+" l: "+l);
//		crisp = (float) f/l;
//		return crisp/10;
		return f/l;
	}
}
