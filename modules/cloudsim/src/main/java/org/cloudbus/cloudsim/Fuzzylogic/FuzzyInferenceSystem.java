package org.cloudbus.cloudsim.Fuzzylogic;

public class FuzzyInferenceSystem {

	public void initFISRules(Fuzzifier fuzz, Defuzzifier defuzz) {
		
		// for first low
		// for second low
		if(fuzz.ram.low==true && fuzz.mips.low==true && fuzz.pe.low==true) {
			defuzz.low = true;
			defuzz.tmp = AND(AND(fuzz.ram.lowV,fuzz.mips.lowV),fuzz.pe.lowV);
			if(defuzz.tmp>defuzz.lowV)
				defuzz.lowV = defuzz.tmp;
//			System.out.println("inside 1 defuzz.lowV "+defuzz.lowV);
		}
		
		if(fuzz.ram.low==true && fuzz.mips.low==true && fuzz.pe.med==true) {
			defuzz.low = true;
			defuzz.tmp = AND(AND(fuzz.ram.lowV,fuzz.mips.lowV),fuzz.pe.medV);
			if(defuzz.tmp>defuzz.lowV)
				defuzz.lowV = defuzz.tmp;
//			System.out.println("inside 2 defuzz.lowV "+defuzz.lowV);
		}
		
		if(fuzz.ram.low==true && fuzz.mips.low==true && fuzz.pe.high==true) {
			defuzz.low = true;
			defuzz.tmp = AND(AND(fuzz.ram.lowV,fuzz.mips.lowV),fuzz.pe.highV);
			if(defuzz.tmp>defuzz.lowV)
				defuzz.lowV = defuzz.tmp;
//			System.out.println("inside 3 defuzz.lowV "+defuzz.lowV);
		}
		
		//for second medium
		if(fuzz.ram.low==true && fuzz.mips.med==true && fuzz.pe.low==true) {
			defuzz.low = true;
			defuzz.tmp = AND(AND(fuzz.ram.lowV,fuzz.mips.medV),fuzz.pe.lowV);
			if(defuzz.tmp>defuzz.lowV)
				defuzz.lowV = defuzz.tmp;
//			System.out.println("inside 4 defuzz.lowV "+defuzz.lowV);
		}
		
		if(fuzz.ram.low==true && fuzz.mips.med==true && fuzz.pe.med==true) {
			defuzz.med = true;
			defuzz.tmp = AND(AND(fuzz.ram.lowV,fuzz.mips.medV),fuzz.pe.medV);
			if(defuzz.tmp>defuzz.medV)
				defuzz.medV = defuzz.tmp;
//			System.out.println("inside 5 defuzz.medV "+defuzz.medV);
		}
		
		if(fuzz.ram.low==true && fuzz.mips.med==true && fuzz.pe.high==true) {
			defuzz.med = true;
			defuzz.tmp = AND(AND(fuzz.ram.lowV,fuzz.mips.medV),fuzz.pe.highV);
			if(defuzz.tmp>defuzz.medV)
				defuzz.medV = defuzz.tmp;
//			System.out.println("inside 6 defuzz.medV "+defuzz.medV);
		}
		
		// for second high
		if(fuzz.ram.low==true && fuzz.mips.high==true && fuzz.pe.low==true) {
			defuzz.low = true;
			defuzz.tmp = AND(AND(fuzz.ram.lowV,fuzz.mips.highV),fuzz.pe.lowV);
			if(defuzz.tmp>defuzz.lowV)
				defuzz.lowV = defuzz.tmp;
//			System.out.println("inside 7 defuzz.lowV "+defuzz.lowV);
		}
		
		if(fuzz.ram.low==true && fuzz.mips.high==true && fuzz.pe.med==true) {
			defuzz.med = true;
			defuzz.tmp = AND(AND(fuzz.ram.lowV,fuzz.mips.highV),fuzz.pe.medV);
			if(defuzz.tmp>defuzz.medV)
				defuzz.medV = defuzz.tmp;
//			System.out.println("inside 8 defuzz.medV "+defuzz.medV);
		}
		
		if(fuzz.ram.low==true && fuzz.mips.high==true && fuzz.pe.high==true) {
			defuzz.high = true;
			defuzz.tmp = AND(AND(fuzz.ram.lowV,fuzz.mips.highV),fuzz.pe.highV);
			if(defuzz.tmp>defuzz.highV)
				defuzz.highV = defuzz.tmp;
//			System.out.println("inside 9 defuzz.highV "+defuzz.highV);
		}
		
		//for first medium
		// for second low
		if(fuzz.ram.med==true && fuzz.mips.low==true && fuzz.pe.low==true) {
			defuzz.low = true;
			defuzz.tmp = AND(AND(fuzz.ram.medV,fuzz.mips.lowV),fuzz.pe.lowV);
			if(defuzz.tmp>defuzz.lowV)
				defuzz.lowV = defuzz.tmp;
//			System.out.println("inside 10 defuzz.lowV "+defuzz.lowV);
		}
		
		if(fuzz.ram.med==true && fuzz.mips.low==true && fuzz.pe.med==true) {
			defuzz.med = true;
			defuzz.tmp = AND(AND(fuzz.ram.medV,fuzz.mips.lowV),fuzz.pe.medV);
			if(defuzz.tmp>defuzz.medV)
				defuzz.medV = defuzz.tmp;
//			System.out.println("inside 11 defuzz.medV "+defuzz.medV);
		}
		
		if(fuzz.ram.med==true && fuzz.mips.low==true && fuzz.pe.high==true) {
			defuzz.med = true;
			defuzz.tmp = AND(AND(fuzz.ram.medV,fuzz.mips.lowV),fuzz.pe.highV);
			if(defuzz.tmp>defuzz.medV)
				defuzz.medV = defuzz.tmp;
//			System.out.println("inside 12 defuzz.medV "+defuzz.medV);
		}
		
		//for second medium
		if(fuzz.ram.med==true && fuzz.mips.med==true && fuzz.pe.low==true) {
			defuzz.med = true;
			defuzz.tmp = AND(AND(fuzz.ram.medV,fuzz.mips.medV),fuzz.pe.lowV);
			if(defuzz.tmp>defuzz.medV)
				defuzz.medV = defuzz.tmp;
//			System.out.println("inside 13 defuzz.medV "+defuzz.medV);
		}
		
		if(fuzz.ram.med==true && fuzz.mips.med==true && fuzz.pe.med==true) {
			defuzz.high = true;
			defuzz.tmp = AND(AND(fuzz.ram.medV,fuzz.mips.medV),fuzz.pe.medV);
			if(defuzz.tmp>defuzz.highV)
				defuzz.highV = defuzz.tmp;
//			System.out.println("inside 14 defuzz.highV "+defuzz.highV);
		}
		
		if(fuzz.ram.med==true && fuzz.mips.med==true && fuzz.pe.high==true) {
			defuzz.high = true;
			defuzz.tmp = AND(AND(fuzz.ram.medV,fuzz.mips.medV),fuzz.pe.highV);
			if(defuzz.tmp>defuzz.highV)
				defuzz.highV = defuzz.tmp;
//			System.out.println("inside 15 defuzz.highV "+defuzz.highV);
		}
		
		// for second high
		if(fuzz.ram.med==true && fuzz.mips.high==true && fuzz.pe.low==true) {
			defuzz.med = true;
			defuzz.tmp = AND(AND(fuzz.ram.medV,fuzz.mips.highV),fuzz.pe.lowV);
			if(defuzz.tmp>defuzz.medV)
				defuzz.medV = defuzz.tmp;
//			System.out.println("inside 16 defuzz.medV "+defuzz.medV);
		}
		
		if(fuzz.ram.med==true && fuzz.mips.high==true && fuzz.pe.med==true) {
			defuzz.high = true;
			defuzz.tmp = AND(AND(fuzz.ram.medV,fuzz.mips.highV),fuzz.pe.medV);
			if(defuzz.tmp>defuzz.highV)
				defuzz.highV = defuzz.tmp;
//			System.out.println("inside 17 defuzz.highV "+defuzz.highV);
		}
		
		if(fuzz.ram.med==true && fuzz.mips.high==true && fuzz.pe.high==true) {
			defuzz.high = true;
			defuzz.tmp = AND(AND(fuzz.ram.medV,fuzz.mips.highV),fuzz.pe.highV);
			if(defuzz.tmp>defuzz.highV)
				defuzz.highV = defuzz.tmp;
//			System.out.println("inside 18 defuzz.highV "+defuzz.highV);
		}
		
		// for first high
		// for second low
		if(fuzz.ram.high==true && fuzz.mips.low==true && fuzz.pe.low==true) {
			defuzz.low = true;
			defuzz.tmp = AND(AND(fuzz.ram.highV,fuzz.mips.lowV),fuzz.pe.lowV);
			if(defuzz.tmp>defuzz.lowV)
				defuzz.lowV = defuzz.tmp;
//			System.out.println("inside 19 defuzz.lowV "+defuzz.lowV);
		}
		
		if(fuzz.ram.high==true && fuzz.mips.low==true && fuzz.pe.med==true) {
			defuzz.med = true;
			defuzz.tmp = AND(AND(fuzz.ram.highV,fuzz.mips.lowV),fuzz.pe.medV);
			if(defuzz.tmp>defuzz.medV)
				defuzz.medV = defuzz.tmp;
//			System.out.println("inside 20 defuzz.medV "+defuzz.medV);
		}
		
		if(fuzz.ram.high==true && fuzz.mips.low==true && fuzz.pe.high==true) {
			defuzz.med = true;
			defuzz.tmp = AND(AND(fuzz.ram.highV,fuzz.mips.lowV),fuzz.pe.highV);
			if(defuzz.tmp>defuzz.medV)
				defuzz.medV = defuzz.tmp;
//			System.out.println("inside 21 defuzz.medV "+defuzz.medV);
		}
		
		//for second medium
		if(fuzz.ram.high==true && fuzz.mips.med==true && fuzz.pe.low==true) {
			defuzz.med = true;
			defuzz.tmp = AND(AND(fuzz.ram.lowV,fuzz.mips.lowV),fuzz.pe.lowV);
			if(defuzz.tmp>defuzz.medV)
				defuzz.medV = defuzz.tmp;
//			System.out.println("inside 22 defuzz.medV "+defuzz.medV);
		}
		
		if(fuzz.ram.high==true && fuzz.mips.med==true && fuzz.pe.med==true) {
			defuzz.high = true;
			defuzz.tmp = AND(AND(fuzz.ram.lowV,fuzz.mips.lowV),fuzz.pe.lowV);
			if(defuzz.tmp>defuzz.highV)
				defuzz.highV = defuzz.tmp;
//			System.out.println("inside 23 defuzz.highV "+defuzz.highV);
		}
		
		if(fuzz.ram.high==true && fuzz.mips.med==true && fuzz.pe.high==true) {
			defuzz.high = true;
			defuzz.tmp = AND(AND(fuzz.ram.highV,fuzz.mips.medV),fuzz.pe.highV);
			if(defuzz.tmp>defuzz.highV)
				defuzz.highV = defuzz.tmp;
//			System.out.println("inside 24 defuzz.highV "+defuzz.highV);
		}
		
		// for second high
		if(fuzz.ram.high==true && fuzz.mips.high==true && fuzz.pe.low==true) {
			defuzz.med = true;
			defuzz.tmp = AND(AND(fuzz.ram.highV,fuzz.mips.highV),fuzz.pe.lowV);
			if(defuzz.tmp>defuzz.medV)
				defuzz.medV = defuzz.tmp;
//			System.out.println("inside 25 defuzz.medV "+defuzz.medV);
		}
		
		if(fuzz.ram.high==true && fuzz.mips.high==true && fuzz.pe.med==true) {
			defuzz.high = true;
			defuzz.tmp = AND(AND(fuzz.ram.highV,fuzz.mips.highV),fuzz.pe.medV);
			if(defuzz.tmp>defuzz.highV)
				defuzz.highV = defuzz.tmp;
//			System.out.println("inside 26 defuzz.highV "+defuzz.highV);
		}
		
		if(fuzz.ram.high==true && fuzz.mips.high==true && fuzz.pe.high==true) {
			defuzz.high = true;
			defuzz.tmp = AND(AND(fuzz.ram.highV,fuzz.mips.highV),fuzz.pe.highV);
			if(defuzz.tmp>defuzz.highV)
				defuzz.highV = defuzz.tmp;
//			System.out.println("inside 27 defuzz.highV "+defuzz.highV);
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
