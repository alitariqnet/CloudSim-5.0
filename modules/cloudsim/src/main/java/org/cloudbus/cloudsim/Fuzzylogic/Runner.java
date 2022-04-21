package org.cloudbus.cloudsim.Fuzzylogic;

import java.util.HashMap;
import java.util.Map;

public class Runner {

	@SuppressWarnings({ "null", "static-access" })
	public static void main(String[] args) {

		FIS fis = new FIS();
//		fis.fuzz.input.ram.init(512);
//		fis.fuzz.input.mips.init(250);
//		fis.fuzz.input.pe.init(1);
		test(fis);
//		fis.initFISRules(fis.defuzz.output);
		
//		System.out.println(fis.defuzz.output.toString());
//		System.out.println("crisp value is "+fis.defuzz.defuzzification());
		
	}

	public static void test(FIS fis) {
		int []ram = {512,1024,1536,2048};
		int []pe = {1,2,3,4};
		int []mips = {250,500,750,1000};
		for (int i = 0; i < ram.length; i++) {
			for (int j = 0; j < pe.length; j++) {
				for (int k = 0; k < mips.length; k++) {
					System.out.println("ram: "+ram[i]+" mips: "+mips[j]+" pe: "+pe[k]);
					System.out.println("");
					fis.fuzz.input.ram.init(ram[i]);
					fis.fuzz.input.mips.init(mips[j]);
					fis.fuzz.input.pe.init(pe[k]);
					fis.initFISRules(fis.defuzz.output);
					System.out.println("crisp value is "+fis.defuzz.defuzzification());
					System.out.println("");
					fis.fuzz.input.ram.reset();
					fis.fuzz.input.mips.reset();
					fis.fuzz.input.pe.reset();
					fis.defuzz.output.reset();
				}
			}
		}
	}
}
