package org.cloudbus.cloudsim.Fuzzylogic;

import java.text.DecimalFormat;
import java.math.RoundingMode;

public class FuzzyLogic {
	
	static double lowPEs;
	static double medPEs;
	static double highPEs;
	
	static double lowMIPS;
	static double medMIPS;
	static double highMIPS;
	
	static double lowRam;
	static double medRam;
	static double highRam;
	
	static double lowStorage;
	static double medStorage;
	static double highStorage;
	
	static double lowBandwidth;
	static double medBandwidth;
	static double highBandwidth;
	
	static double rule1;
	static double rule2;
	static double rule3;
	static double rule4;
	static double rule5;
	static double rule6;
	static double rule7;
	static double rule8;
	static double rule9;
	static double rule10;
	
	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	public static void main(String[] args) {
		df.setRoundingMode(RoundingMode.UP);
		PEs(1);
		Ram(1400);
		Storage(95000);
		Bandwidth(3000);
		MIPS(300);
		System.out.println("lowPEs "+df.format(lowPEs)+" medPEs "+medPEs+" highPEs "+highPEs+"\nlowMIPS "+lowMIPS+" "
				+ "medMIPS "+medMIPS+" highMIPS "+highMIPS+"\nlowRam "+lowRam+" medRam "+medRam+" highRam "+highRam
				+ "\nlowStorage "+df.format(lowStorage)+" medStorage "+df.format(medStorage)+" highStorage "+df.format(highStorage)+"\n"
				+ "lowBandwidth "+df.format(lowBandwidth)+" medBandwidth "+df.format(medBandwidth)+ " highBandwidth "+df.format(highBandwidth));
	}
	
	public static void rules() {
		// Rule1: if PE is low and Ram is low and Storage is low and Bandwidth is low then priority is low
		if(!(lowPEs==0.0) && !(lowRam==0) && !(lowStorage==0) && !(lowBandwidth==0)) {
			rule1 = AND(lowPEs,lowRam,lowStorage,lowBandwidth);
		}
		// Rule2: if PE is low and Ram low and storage is high and bandwidth is high then priority is med
		if(!(lowPEs==0.0) && !(lowRam==0) && !(lowStorage==0) && !(lowBandwidth==0)) {
			rule2 = AND(lowPEs,lowRam,lowStorage,lowBandwidth);
		}
	}
	
	
	public static void PEs(double i) {
		if (i==1) {
			lowPEs = 1;
			medPEs = 0;
			highPEs = 0;
		}
		else if (i==2||i==3) {
			lowPEs = 0;
			medPEs = 1;
			highPEs = 0;
		}
		else if (i==4) {
			lowPEs = 0;
			medPEs = 0;
			highPEs = 1;
		}
	}
	public static void MIPS(double i) {
		if(i<=250) {
			lowMIPS = 1;
			medMIPS = 0;
			highMIPS = 0;
		}
		else if(i>250&&i<500) {
			lowMIPS = (500-i)/(500-250);
			medMIPS = (i-250)/(500-250);
			highMIPS = 0;
		}
		else if(i>=500&&i<=750) {
			lowMIPS = 0;
			medMIPS = 1;
			highMIPS = 0;
		}
		else if (i>750&&i<1000) {
			lowMIPS = 0;
			medMIPS = (1000-i)/(1000-750);
			highMIPS = (i-750)/(1000-750);
		}
		else if(i>=1000) {
			lowMIPS = 0;
			medMIPS = 0;
			highMIPS = 1;
		}
	}
	public static void Ram(double i) {
		if(i<=512) {
			lowRam = 1;
			medRam = 0;
			highRam = 0;
		}
		else if(i>512 && i<1024) {
			lowRam = (1024-i)/(1024-512);
			medRam = (i-512)/(1024-512);
			highRam = 0;
		}
		else if(i>=1024 && i<=1536) {
			lowRam = 0;
			medRam = 1;
			highRam = 0;
		}
		else if(i>1536&&i<2048) {
			lowRam = 0;
			medRam = (2048-i)/(2048-1536);
			highRam = (i-1536)/(2048-1536);
		}
		else if(i>=2048){
			lowRam = 0;
			medRam = 0;
			highRam = 1;
		}
	}
	
	public static void Storage(double i) {
		if(i<=10000) {
			lowStorage = 1;
			medStorage = 0;
			highStorage = 0;
		}
		else if(i>10000&&i<40000) {
			lowStorage = (40000-i)/(40000-10000);
			medStorage = (i-10000)/(40000-10000);
			highStorage = 0;
		}
		else if(i>=40000&&i<=70000) {
			lowStorage = 0;
			medStorage = 1;
			highStorage = 0;
		}
		else if(i>70000&&i<100000) {
			lowStorage = 0;
			medStorage = (100000-i)/(100000-70000);
			highStorage = (i-70000)/(100000-70000);
		}
		else if(i>=100000) {
			lowStorage = 0;
			medStorage = 0;
			highStorage = 1;
		}
	}
	
	public static void Bandwidth(double i) {
		if(i<=1000) {
			lowBandwidth = 1;
			medBandwidth = 0;
			highBandwidth = 0;
		}
		else if(i>1000&&i<4000) {
			lowBandwidth = (4000-i)/(4000-1000);
			medBandwidth = (i-1000)/(4000-1000);
			highBandwidth = 0;
		}
		else if(i>=4000&&i<=7000) {
			lowBandwidth = 0;
			medBandwidth = 1;
			highBandwidth = 0;
		}
		else if(i>7000&&i<10000) {
			lowBandwidth = 0;
			medBandwidth = (10000-i)/(10000-7000);
			highBandwidth = (i-7000)/(10000-7000);
		}
		else if(i>=10000) {
			lowBandwidth = 0;
			medBandwidth = 1;
			highBandwidth = 0;
		}
	}
	
	// Two parameters min function
	public static double min(double a,double b) {
		if (a<b)
			return a;
		else 
			return b;
	}
	
	// Two parameters max function
	public static double max(double a,double b) {
		if (a>b)
			return a;
		else 
			return b;
	}
	
	// Three parameters min function
	public static double AND(double a,double b, double c) {

		return min(c,min(a,b));
	}
	
	// Three parameters max function
	public static double OR(double a,double b, double c) {

		return max(c,max(a,b));
	}

	// Four parameters min function
	public static double AND(double a,double b, double c, double d) {

		return min(d,min(c,min(a,b)));
	}
	
	// Four parameters max function
	public static double OR(double a,double b, double c, double d) {

		return max(d,max(c,max(a,b)));
	}
}
