package org.cloudbus.cloudsim.Fuzzylogic;

import java.util.HashMap;
import java.util.Map;

public class Runner {

	@SuppressWarnings({ "null", "static-access" })
	public static void main(String[] args) {
		System.out.println("Testing...");
		FuzzyLogicSystem fls = new FuzzyLogicSystem();
//		fis.fuzz.ram.init(512);
//		fis.fuzz.mips.init(250);
//		fis.fuzz.pe.init(1);
		test(fls);
//		fis.initFISRules(fis.defuzz.output);
		
//		System.out.println(fis.defuzz.output.toString());
//		System.out.println("crisp value is "+fis.defuzz.defuzzification());
		
	}

	public static void test(FuzzyLogicSystem fls) {
		int []ram = {512,1024,1536,2048};
		int []pe = {1,2,3,4};
		int []mips = {250,500,750,1000};
//		for (int i = 0; i < ram.length; i++) {
//			for (int j = 0; j < pe.length; j++) {
//				for (int k = 0; k < mips.length; k++) {
					
					fls.fuzz.ram.init(1024);
					fls.fuzz.storage.init(750);
					fls.fuzz.pe.init(3);
					
					fls.fis.initFISRules(fls.fuzz, fls.defuzz);
					
//					System.out.println("ram: "+ram[i]+" mips: "+mips[j]+" pe: "+pe[k]+" crisp value: "+fls.defuzz.defuzzification());
					System.out.println(fls.defuzz.defuzzification());
					fls.fuzz.ram.reset();
					fls.fuzz.storage.reset();
					fls.fuzz.pe.reset();
					fls.defuzz.reset();
				}
			}
		