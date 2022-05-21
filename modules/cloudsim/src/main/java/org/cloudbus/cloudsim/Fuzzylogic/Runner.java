package org.cloudbus.cloudsim.Fuzzylogic;

import java.util.HashMap;
import java.util.Map;

public class Runner {

	@SuppressWarnings({ "null", "static-access" })
	public static void main(String[] args) {
		System.out.println("Testing...");
		FuzzyInferenceSystem fis = new FuzzyInferenceSystem();
//		fis.fuzz.ram.init(512);
//		fis.fuzz.mips.init(250);
//		fis.fuzz.pe.init(1);
		test(fis);
//		fis.initFISRules(fis.defuzz.output);
		
//		System.out.println(fis.defuzz.output.toString());
//		System.out.println("crisp value is "+fis.defuzz.defuzzification());
		
	}

	public static void test(FuzzyInferenceSystem fis) {
		int []ram = {512,1024,1536,2048};
		int []pe = {1,2,3,4};
		int []mips = {250,500,750,1000};
		for (int i = 0; i < ram.length; i++) {
			for (int j = 0; j < pe.length; j++) {
				for (int k = 0; k < mips.length; k++) {
					
					fis.fuzz.ram.init(ram[i]);
					fis.fuzz.mips.init(mips[j]);
					fis.fuzz.pe.init(pe[k]);
					
					fis.initFISRules(fis.defuzz);
					
					System.out.println("ram: "+ram[i]+" mips: "+mips[j]+" pe: "+pe[k]+" crisp value: "+fis.defuzz.defuzzification());
					
					fis.fuzz.ram.reset();
					fis.fuzz.mips.reset();
					fis.fuzz.pe.reset();
					fis.defuzz.reset();
				}
			}
		}
	}
}
