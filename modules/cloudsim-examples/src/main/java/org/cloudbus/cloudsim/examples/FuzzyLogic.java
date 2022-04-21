package org.cloudbus.cloudsim.examples;


public class FuzzyLogic {
	
	static double lowPEs;
	static double highPEs;
	
	static double lowRam;
	static double highRam;
	
	static double lowStorage;
	static double highStorage;
	
	static double lowBandwidth;
	static double highBandwidth;
	
	static boolean lowPe=false;
	static boolean medPe=false;
	static boolean highPe=false;
	
	static boolean lowRam0=false;
	static boolean medRam0=false;
	static boolean highRam0=false;
	
	static boolean lowStorage0=false;
	static boolean medStorage0=false;
	static boolean highStorage0=false;
	
	static boolean lowBandwidth0=false;
	static boolean medBandwidth0=false;
	static boolean highBandwidth0=false;
	
	public static void main(String[] args) {
		PEs(4);
		Ram(2);
		Storage(6);
		Bandwidth(9);
		
		
	}
	
	public static void rules() {
		// Rule1: if PE is low and Ram is low and Storage is low and Bandwidth is low then priority is low
		if(!(lowPEs==0.0) && !(lowRam==0) && !(lowStorage==0) && !(lowBandwidth==0)) {
		}
		// Rule2: if PE is low and Ram low and storage is high and bandwidth is high then priority is med
		if(!(lowPEs==0.0) && !(lowRam==0) && !(lowStorage==0) && !(lowBandwidth==0)) {
		}
	}
	
	public static void PEs(double i) {
		if (i<=1)
			lowPEs = 1;
		else if (i>1&&i<3)
			lowPEs = (3-i)/(3-1);
		else 
			lowPEs = 0;
		
		if (i<=2)
			highPEs = 0;
		else if (i>2&&i<4)
			highPEs = (i-2)/(4-2);
		else 
			highPEs = 1;
	}
	
	public static void Ram(double i) {
		if (i<=512)
			lowRam = 1;
		else if (i>512&&i<1536)
			lowRam = (1536-i)/(1536-512);
		else 
			lowRam = 0;
		
		if (i<=1024)
			highRam = 0;
		else if (i>1024&&i<2048)
			highRam = (i-1024)/(2048-1024);
		else 
			highRam = 1;
	}
	
	public static void Storage(double i) {
		if (i<=10000)
			lowStorage = 1;
		else if (i>10000&&i<70000)
			lowStorage = (70000-i)/(70000-10000);
		else 
			lowStorage = 0;
		
		if (i<=30000)
			highStorage = 0;
		else if (i>30000&&i<100000)
			highStorage = (i-30000)/(100000-30000);
		else 
			highStorage = 1;
	}
	
	public static void Bandwidth(double i) {
		if (i<=1000)
			lowBandwidth = 1;
		else if (i>1000&&i<7000)
			lowBandwidth = (7000-i)/(7000-1000);
		else 
			lowBandwidth = 0;
		
		if (i<=3000)
			highBandwidth = 0;
		else if (i>3000&&i<10000)
			highBandwidth = (i-300)/(10000-3000);
		else 
			highBandwidth = 1;
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
	public static double min(double a,double b, double c) {

		return min(c,min(a,b));
	}
	
	// Three parameters max function
	public static double max(double a,double b, double c) {

		return max(c,max(a,b));
	}

	// Four parameters min function
	public static double min(double a,double b, double c, double d) {

		return min(d,min(c,min(a,b)));
	}
	
	// Four parameters max function
	public static double max(double a,double b, double c, double d) {

		return max(d,max(c,max(a,b)));
	}
}
