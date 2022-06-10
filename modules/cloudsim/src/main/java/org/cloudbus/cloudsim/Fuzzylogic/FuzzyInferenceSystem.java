package org.cloudbus.cloudsim.Fuzzylogic;

public class FuzzyInferenceSystem {

	public void initFISRules(Fuzzifier fuzz, Defuzzifier defuzz) {
		
		// for first low
		// for second low
		if(fuzz.ram.isLowRam==true && fuzz.storage.isLowStorage==true && fuzz.pe.isLowPe==true) {
			defuzz.isLowDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.lowRam,fuzz.storage.lowStorage),fuzz.pe.lowPe);
			if(defuzz.temp<defuzz.lowDefuzz)
				defuzz.lowDefuzz = defuzz.temp;
			System.out.println("inside 1 defuzz.lowV "+defuzz.lowDefuzz);
		}
		
		if(fuzz.ram.isLowRam==true && fuzz.storage.isLowStorage==true && fuzz.pe.isMedPe==true) {
			defuzz.isLowDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.lowRam,fuzz.storage.lowStorage),fuzz.pe.medPe);
			if(defuzz.temp<defuzz.lowDefuzz)
				defuzz.lowDefuzz = defuzz.temp;
			System.out.println("inside 2 defuzz.lowV "+defuzz.lowDefuzz);
		}
		
		if(fuzz.ram.isLowRam==true && fuzz.storage.isLowStorage==true && fuzz.pe.isHighPe==true) {
			defuzz.isLowDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.lowRam,fuzz.storage.lowStorage),fuzz.pe.highPe);
			if(defuzz.temp<defuzz.lowDefuzz)
				defuzz.lowDefuzz = defuzz.temp;
			System.out.println("inside 3 defuzz.lowV "+defuzz.lowDefuzz);
		}
		
		//for second medium
		if(fuzz.ram.isLowRam==true && fuzz.storage.isMedStorage==true && fuzz.pe.isLowPe==true) {
			defuzz.isLowDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.lowRam,fuzz.storage.medStorage),fuzz.pe.lowPe);
			if(defuzz.temp<defuzz.lowDefuzz)
				defuzz.lowDefuzz = defuzz.temp;
			System.out.println("inside 4 defuzz.lowV "+defuzz.lowDefuzz);
		}
		
		if(fuzz.ram.isLowRam==true && fuzz.storage.isMedStorage==true && fuzz.pe.isMedPe==true) {
			defuzz.isLowDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.lowRam,fuzz.storage.medStorage),fuzz.pe.medPe);
			if(defuzz.temp<defuzz.lowDefuzz)
				defuzz.lowDefuzz = defuzz.temp;
			System.out.println("inside 5 defuzz.lowV "+defuzz.lowDefuzz);
		}
		
		if(fuzz.ram.isLowRam==true && fuzz.storage.isMedStorage==true && fuzz.pe.isHighPe==true) {
			defuzz.isLowDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.lowRam,fuzz.storage.medStorage),fuzz.pe.highPe);
			if(defuzz.temp<defuzz.lowDefuzz)
				defuzz.lowDefuzz = defuzz.temp;
			System.out.println("inside 6 defuzz.lowV "+defuzz.lowDefuzz);
		}
		
		// for second high
		if(fuzz.ram.isLowRam==true && fuzz.storage.isHighStorage==true && fuzz.pe.isLowPe==true) {
			defuzz.isLowDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.lowRam,fuzz.storage.highStorage),fuzz.pe.lowPe);
			if(defuzz.temp<defuzz.lowDefuzz)
				defuzz.lowDefuzz = defuzz.temp;
			System.out.println("inside 7 defuzz.lowV "+defuzz.lowDefuzz);
		}
		
		if(fuzz.ram.isLowRam==true && fuzz.storage.isHighStorage==true && fuzz.pe.isMedPe==true) {
			defuzz.isLowDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.lowRam,fuzz.storage.highStorage),fuzz.pe.medPe);
			if(defuzz.temp<defuzz.lowDefuzz)
				defuzz.lowDefuzz = defuzz.temp;
			System.out.println("inside 8 defuzz.lowV "+defuzz.lowDefuzz);
		}
		
		if(fuzz.ram.isLowRam==true && fuzz.storage.isHighStorage==true && fuzz.pe.isHighPe==true) {
			defuzz.isLowDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.lowRam,fuzz.storage.highStorage),fuzz.pe.highPe);
			if(defuzz.temp<defuzz.lowDefuzz)
				defuzz.lowDefuzz = defuzz.temp;
			System.out.println("inside 9 defuzz.lowV "+defuzz.lowDefuzz);
		}
		
		//for first medium
		// for second low
		if(fuzz.ram.isMedRam==true && fuzz.storage.isLowStorage==true && fuzz.pe.isLowPe==true) {
			defuzz.isLowDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.medRam,fuzz.storage.lowStorage),fuzz.pe.lowPe);
			if(defuzz.temp<defuzz.lowDefuzz)
				defuzz.lowDefuzz = defuzz.temp;
			System.out.println("inside 10 defuzz.lowV "+defuzz.lowDefuzz);
		}
		
		if(fuzz.ram.isMedRam==true && fuzz.storage.isLowStorage==true && fuzz.pe.isMedPe==true) {
			defuzz.isLowDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.medRam,fuzz.storage.lowStorage),fuzz.pe.medPe);
			if(defuzz.temp<defuzz.lowDefuzz)
				defuzz.lowDefuzz = defuzz.temp;
			System.out.println("inside 11 defuzz.lowV "+defuzz.lowDefuzz);
		}
		
		if(fuzz.ram.isMedRam==true && fuzz.storage.isLowStorage==true && fuzz.pe.isHighPe==true) {
			defuzz.isLowDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.medRam,fuzz.storage.lowStorage),fuzz.pe.highPe);
			if(defuzz.temp<defuzz.lowDefuzz)
				defuzz.lowDefuzz = defuzz.temp;
			System.out.println("inside 12 defuzz.lowV "+defuzz.lowDefuzz);
		}
		
		//for second medium
		if(fuzz.ram.isMedRam==true && fuzz.storage.isMedStorage==true && fuzz.pe.isLowPe==true) {
			defuzz.isLowDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.medRam,fuzz.storage.medStorage),fuzz.pe.lowPe);
			if(defuzz.temp<defuzz.lowDefuzz)
				defuzz.lowDefuzz = defuzz.temp;
			System.out.println("inside 13 defuzz.lowV "+defuzz.lowDefuzz);
		}
		
		if(fuzz.ram.isMedRam==true && fuzz.storage.isMedStorage==true && fuzz.pe.isMedPe==true) {
			defuzz.isMedDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.medRam,fuzz.storage.medStorage),fuzz.pe.medPe);
			if(defuzz.temp>defuzz.medDefuzz)
				defuzz.medDefuzz = defuzz.temp;
//			System.out.println("inside 14 defuzz.medV "+defuzz.medV);
		}
		
		if(fuzz.ram.isMedRam==true && fuzz.storage.isMedStorage==true && fuzz.pe.isHighPe==true) {
			defuzz.isMedDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.medRam,fuzz.storage.medStorage),fuzz.pe.highPe);
			if(defuzz.temp>defuzz.medDefuzz)
				defuzz.medDefuzz = defuzz.temp;
//			System.out.println("inside 15 defuzz.medV "+defuzz.medV);
		}
		
		// for second high
		if(fuzz.ram.isMedRam==true && fuzz.storage.isHighStorage==true && fuzz.pe.isLowPe==true) {
			defuzz.isLowDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.medRam,fuzz.storage.highStorage),fuzz.pe.lowPe);
			if(defuzz.temp<defuzz.lowDefuzz)
				defuzz.lowDefuzz = defuzz.temp;
			System.out.println("inside 16 defuzz.lowV "+defuzz.lowDefuzz);
		}
		
		if(fuzz.ram.isMedRam==true && fuzz.storage.isHighStorage==true && fuzz.pe.isMedPe==true) {
			defuzz.isMedDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.medRam,fuzz.storage.highStorage),fuzz.pe.medPe);
			if(defuzz.temp>defuzz.medDefuzz)
				defuzz.medDefuzz = defuzz.temp;
//			System.out.println("inside 17 defuzz.medV "+defuzz.medV);
		}
		
		if(fuzz.ram.isMedRam==true && fuzz.storage.isHighStorage==true && fuzz.pe.isHighPe==true) {
			defuzz.isMedDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.medRam,fuzz.storage.highStorage),fuzz.pe.highPe);
			if(defuzz.temp>defuzz.medDefuzz)
				defuzz.medDefuzz = defuzz.temp;
//			System.out.println("inside 18 defuzz.medV "+defuzz.medV);
		}
		
		// for first high
		// for second low
		if(fuzz.ram.isHighRam==true && fuzz.storage.isLowStorage==true && fuzz.pe.isLowPe==true) {
			defuzz.isLowDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.highRam,fuzz.storage.lowStorage),fuzz.pe.lowPe);
			if(defuzz.temp<defuzz.lowDefuzz)
				defuzz.lowDefuzz = defuzz.temp;
			System.out.println("inside 19 defuzz.lowV "+defuzz.lowDefuzz);
		}
		
		if(fuzz.ram.isHighRam==true && fuzz.storage.isLowStorage==true && fuzz.pe.isMedPe==true) {
			defuzz.isLowDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.highRam,fuzz.storage.lowStorage),fuzz.pe.medPe);
			if(defuzz.temp<defuzz.lowDefuzz)
				defuzz.lowDefuzz = defuzz.temp;
			System.out.println("inside 20 defuzz.lowV "+defuzz.lowDefuzz);
		}
		
		if(fuzz.ram.isHighRam==true && fuzz.storage.isLowStorage==true && fuzz.pe.isHighPe==true) {
			defuzz.isLowDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.highRam,fuzz.storage.lowStorage),fuzz.pe.highPe);
			if(defuzz.temp<defuzz.lowDefuzz)
				defuzz.lowDefuzz = defuzz.temp;
			System.out.println("inside 21 defuzz.lowV "+defuzz.lowDefuzz);
		}
		
		//for second medium
		if(fuzz.ram.isHighRam==true && fuzz.storage.isMedStorage==true && fuzz.pe.isLowPe==true) {
			defuzz.isLowDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.lowRam,fuzz.storage.lowStorage),fuzz.pe.lowPe);
			if(defuzz.temp<defuzz.lowDefuzz)
				defuzz.lowDefuzz = defuzz.temp;
			System.out.println("inside 22 defuzz.lowV "+defuzz.lowDefuzz);
		}
		
		if(fuzz.ram.isHighRam==true && fuzz.storage.isMedStorage==true && fuzz.pe.isMedPe==true) {
			defuzz.isMedDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.lowRam,fuzz.storage.lowStorage),fuzz.pe.lowPe);
			if(defuzz.temp>defuzz.medDefuzz)
				defuzz.medDefuzz = defuzz.temp;
//			System.out.println("inside 23 defuzz.medV "+defuzz.medV);
		}
		
		if(fuzz.ram.isHighRam==true && fuzz.storage.isMedStorage==true && fuzz.pe.isHighPe==true) {
			defuzz.isMedDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.highRam,fuzz.storage.medStorage),fuzz.pe.highPe);
			if(defuzz.temp>defuzz.medDefuzz)
				defuzz.medDefuzz = defuzz.temp;
//			System.out.println("inside 24 defuzz.medV "+defuzz.medV);
		}
		
		// for second high
		if(fuzz.ram.isHighRam==true && fuzz.storage.isHighStorage==true && fuzz.pe.isLowPe==true) {
			defuzz.isLowDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.highRam,fuzz.storage.highStorage),fuzz.pe.lowPe);
			if(defuzz.temp<defuzz.lowDefuzz)
				defuzz.lowDefuzz = defuzz.temp;
			System.out.println("inside 25 defuzz.lowV "+defuzz.lowDefuzz);
		}
		
		if(fuzz.ram.isHighRam==true && fuzz.storage.isHighStorage==true && fuzz.pe.isMedPe==true) {
			defuzz.isMedDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.highRam,fuzz.storage.highStorage),fuzz.pe.medPe);
			if(defuzz.temp>defuzz.medDefuzz)
				defuzz.medDefuzz = defuzz.temp;
//			System.out.println("inside 26 defuzz.medV "+defuzz.medV);
		}
		
		if(fuzz.ram.isHighRam==true && fuzz.storage.isHighStorage==true && fuzz.pe.isHighPe==true) {
			defuzz.isHighDefuzz = true;
			defuzz.temp = AND(AND(fuzz.ram.highRam,fuzz.storage.highStorage),fuzz.pe.highPe);
			if(defuzz.temp>defuzz.highDefuzz)
				defuzz.highDefuzz = defuzz.temp;
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
