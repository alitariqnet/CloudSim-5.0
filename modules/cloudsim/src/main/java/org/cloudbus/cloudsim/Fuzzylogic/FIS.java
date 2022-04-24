package org.cloudbus.cloudsim.Fuzzylogic;

	

public class FIS {

	public Fuzzifier fuzz = new Fuzzifier();
	public Defuzzifier defuzz = new Defuzzifier();
	
	public void initFISRules(Output output) {
//		System.out.println(fuzz.input.ram.toString());
//		System.out.println(fuzz.input.mips.toString());
//		System.out.println(fuzz.input.pe.toString());
		
		// for first low
		// for second low
		if(fuzz.input.ram.low==true && fuzz.input.mips.low==true && fuzz.input.pe.low==true) {
			output.low = true;
			output.tmp = AND(AND(fuzz.input.ram.lowV,fuzz.input.mips.lowV),fuzz.input.pe.lowV);
			if(output.tmp>output.lowV)
				output.lowV = output.tmp;
//			System.out.println("inside 1 output.lowV "+output.lowV);
		}
		
		if(fuzz.input.ram.low==true && fuzz.input.mips.low==true && fuzz.input.pe.med==true) {
			output.low = true;
			output.tmp = AND(AND(fuzz.input.ram.lowV,fuzz.input.mips.lowV),fuzz.input.pe.medV);
			if(output.tmp>output.lowV)
				output.lowV = output.tmp;
//			System.out.println("inside 2 output.lowV "+output.lowV);
		}
		
		if(fuzz.input.ram.low==true && fuzz.input.mips.low==true && fuzz.input.pe.high==true) {
			output.low = true;
			output.tmp = AND(AND(fuzz.input.ram.lowV,fuzz.input.mips.lowV),fuzz.input.pe.highV);
			if(output.tmp>output.lowV)
				output.lowV = output.tmp;
//			System.out.println("inside 3 output.lowV "+output.lowV);
		}
		
		//for second medium
		if(fuzz.input.ram.low==true && fuzz.input.mips.med==true && fuzz.input.pe.low==true) {
			output.low = true;
			output.tmp = AND(AND(fuzz.input.ram.lowV,fuzz.input.mips.medV),fuzz.input.pe.lowV);
			if(output.tmp>output.lowV)
				output.lowV = output.tmp;
//			System.out.println("inside 4 output.lowV "+output.lowV);
		}
		
		if(fuzz.input.ram.low==true && fuzz.input.mips.med==true && fuzz.input.pe.med==true) {
			output.med = true;
			output.tmp = AND(AND(fuzz.input.ram.lowV,fuzz.input.mips.medV),fuzz.input.pe.medV);
			if(output.tmp>output.medV)
				output.medV = output.tmp;
//			System.out.println("inside 5 output.medV "+output.medV);
		}
		
		if(fuzz.input.ram.low==true && fuzz.input.mips.med==true && fuzz.input.pe.high==true) {
			output.med = true;
			output.tmp = AND(AND(fuzz.input.ram.lowV,fuzz.input.mips.medV),fuzz.input.pe.highV);
			if(output.tmp>output.medV)
				output.medV = output.tmp;
//			System.out.println("inside 6 output.medV "+output.medV);
		}
		
		// for second high
		if(fuzz.input.ram.low==true && fuzz.input.mips.high==true && fuzz.input.pe.low==true) {
			output.low = true;
			output.tmp = AND(AND(fuzz.input.ram.lowV,fuzz.input.mips.highV),fuzz.input.pe.lowV);
			if(output.tmp>output.lowV)
				output.lowV = output.tmp;
//			System.out.println("inside 7 output.lowV "+output.lowV);
		}
		
		if(fuzz.input.ram.low==true && fuzz.input.mips.high==true && fuzz.input.pe.med==true) {
			output.med = true;
			output.tmp = AND(AND(fuzz.input.ram.lowV,fuzz.input.mips.highV),fuzz.input.pe.medV);
			if(output.tmp>output.medV)
				output.medV = output.tmp;
//			System.out.println("inside 8 output.medV "+output.medV);
		}
		
		if(fuzz.input.ram.low==true && fuzz.input.mips.high==true && fuzz.input.pe.high==true) {
			output.high = true;
			output.tmp = AND(AND(fuzz.input.ram.lowV,fuzz.input.mips.highV),fuzz.input.pe.highV);
			if(output.tmp>output.highV)
				output.highV = output.tmp;
//			System.out.println("inside 9 output.highV "+output.highV);
		}
		
		//for first medium
		// for second low
		if(fuzz.input.ram.med==true && fuzz.input.mips.low==true && fuzz.input.pe.low==true) {
			output.low = true;
			output.tmp = AND(AND(fuzz.input.ram.medV,fuzz.input.mips.lowV),fuzz.input.pe.lowV);
			if(output.tmp>output.lowV)
				output.lowV = output.tmp;
//			System.out.println("inside 10 output.lowV "+output.lowV);
		}
		
		if(fuzz.input.ram.med==true && fuzz.input.mips.low==true && fuzz.input.pe.med==true) {
			output.med = true;
			output.tmp = AND(AND(fuzz.input.ram.medV,fuzz.input.mips.lowV),fuzz.input.pe.medV);
			if(output.tmp>output.medV)
				output.medV = output.tmp;
//			System.out.println("inside 11 output.medV "+output.medV);
		}
		
		if(fuzz.input.ram.med==true && fuzz.input.mips.low==true && fuzz.input.pe.high==true) {
			output.med = true;
			output.tmp = AND(AND(fuzz.input.ram.medV,fuzz.input.mips.lowV),fuzz.input.pe.highV);
			if(output.tmp>output.medV)
				output.medV = output.tmp;
//			System.out.println("inside 12 output.medV "+output.medV);
		}
		
		//for second medium
		if(fuzz.input.ram.med==true && fuzz.input.mips.med==true && fuzz.input.pe.low==true) {
			output.med = true;
			output.tmp = AND(AND(fuzz.input.ram.medV,fuzz.input.mips.medV),fuzz.input.pe.lowV);
			if(output.tmp>output.medV)
				output.medV = output.tmp;
//			System.out.println("inside 13 output.medV "+output.medV);
		}
		
		if(fuzz.input.ram.med==true && fuzz.input.mips.med==true && fuzz.input.pe.med==true) {
			output.high = true;
			output.tmp = AND(AND(fuzz.input.ram.medV,fuzz.input.mips.medV),fuzz.input.pe.medV);
			if(output.tmp>output.highV)
				output.highV = output.tmp;
//			System.out.println("inside 14 output.highV "+output.highV);
		}
		
		if(fuzz.input.ram.med==true && fuzz.input.mips.med==true && fuzz.input.pe.high==true) {
			output.high = true;
			output.tmp = AND(AND(fuzz.input.ram.medV,fuzz.input.mips.medV),fuzz.input.pe.highV);
			if(output.tmp>output.highV)
				output.highV = output.tmp;
//			System.out.println("inside 15 output.highV "+output.highV);
		}
		
		// for second high
		if(fuzz.input.ram.med==true && fuzz.input.mips.high==true && fuzz.input.pe.low==true) {
			output.med = true;
			output.tmp = AND(AND(fuzz.input.ram.medV,fuzz.input.mips.highV),fuzz.input.pe.lowV);
			if(output.tmp>output.medV)
				output.medV = output.tmp;
//			System.out.println("inside 16 output.medV "+output.medV);
		}
		
		if(fuzz.input.ram.med==true && fuzz.input.mips.high==true && fuzz.input.pe.med==true) {
			output.high = true;
			output.tmp = AND(AND(fuzz.input.ram.medV,fuzz.input.mips.highV),fuzz.input.pe.medV);
			if(output.tmp>output.highV)
				output.highV = output.tmp;
//			System.out.println("inside 17 output.highV "+output.highV);
		}
		
		if(fuzz.input.ram.med==true && fuzz.input.mips.high==true && fuzz.input.pe.high==true) {
			output.high = true;
			output.tmp = AND(AND(fuzz.input.ram.medV,fuzz.input.mips.highV),fuzz.input.pe.highV);
			if(output.tmp>output.highV)
				output.highV = output.tmp;
//			System.out.println("inside 18 output.highV "+output.highV);
		}
		
		// for first high
		// for second low
		if(fuzz.input.ram.high==true && fuzz.input.mips.low==true && fuzz.input.pe.low==true) {
			output.low = true;
			output.tmp = AND(AND(fuzz.input.ram.highV,fuzz.input.mips.lowV),fuzz.input.pe.lowV);
			if(output.tmp>output.lowV)
				output.lowV = output.tmp;
//			System.out.println("inside 19 output.lowV "+output.lowV);
		}
		
		if(fuzz.input.ram.high==true && fuzz.input.mips.low==true && fuzz.input.pe.med==true) {
			output.med = true;
			output.tmp = AND(AND(fuzz.input.ram.highV,fuzz.input.mips.lowV),fuzz.input.pe.medV);
			if(output.tmp>output.medV)
				output.medV = output.tmp;
//			System.out.println("inside 20 output.medV "+output.medV);
		}
		
		if(fuzz.input.ram.high==true && fuzz.input.mips.low==true && fuzz.input.pe.high==true) {
			output.med = true;
			output.tmp = AND(AND(fuzz.input.ram.highV,fuzz.input.mips.lowV),fuzz.input.pe.highV);
			if(output.tmp>output.medV)
				output.medV = output.tmp;
//			System.out.println("inside 21 output.medV "+output.medV);
		}
		
		//for second medium
		if(fuzz.input.ram.high==true && fuzz.input.mips.med==true && fuzz.input.pe.low==true) {
			output.med = true;
			output.tmp = AND(AND(fuzz.input.ram.lowV,fuzz.input.mips.lowV),fuzz.input.pe.lowV);
			if(output.tmp>output.medV)
				output.medV = output.tmp;
//			System.out.println("inside 22 output.medV "+output.medV);
		}
		
		if(fuzz.input.ram.high==true && fuzz.input.mips.med==true && fuzz.input.pe.med==true) {
			output.high = true;
			output.tmp = AND(AND(fuzz.input.ram.lowV,fuzz.input.mips.lowV),fuzz.input.pe.lowV);
			if(output.tmp>output.highV)
				output.highV = output.tmp;
//			System.out.println("inside 23 output.highV "+output.highV);
		}
		
		if(fuzz.input.ram.high==true && fuzz.input.mips.med==true && fuzz.input.pe.high==true) {
			output.high = true;
			output.tmp = AND(AND(fuzz.input.ram.highV,fuzz.input.mips.medV),fuzz.input.pe.highV);
			if(output.tmp>output.highV)
				output.highV = output.tmp;
//			System.out.println("inside 24 output.highV "+output.highV);
		}
		
		// for second high
		if(fuzz.input.ram.high==true && fuzz.input.mips.high==true && fuzz.input.pe.low==true) {
			output.med = true;
			output.tmp = AND(AND(fuzz.input.ram.highV,fuzz.input.mips.highV),fuzz.input.pe.lowV);
			if(output.tmp>output.medV)
				output.medV = output.tmp;
//			System.out.println("inside 25 output.medV "+output.medV);
		}
		
		if(fuzz.input.ram.high==true && fuzz.input.mips.high==true && fuzz.input.pe.med==true) {
			output.high = true;
			output.tmp = AND(AND(fuzz.input.ram.highV,fuzz.input.mips.highV),fuzz.input.pe.medV);
			if(output.tmp>output.highV)
				output.highV = output.tmp;
//			System.out.println("inside 26 output.highV "+output.highV);
		}
		
		if(fuzz.input.ram.high==true && fuzz.input.mips.high==true && fuzz.input.pe.high==true) {
			output.high = true;
			output.tmp = AND(AND(fuzz.input.ram.highV,fuzz.input.mips.highV),fuzz.input.pe.highV);
			if(output.tmp>output.highV)
				output.highV = output.tmp;
//			System.out.println("inside 27 output.highV "+output.highV);
		}
	}

	public static float AND(float a, float b) {
		if(a<b)
			return a;
		else 
			return b;
	}
	
	public static float OR(float a, float b) {
		if(a>b)
			return a;
		else 
			return b;
	}
}
