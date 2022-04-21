package org.cloudbus.cloudsim.examples;

//import java.lang.Math;
/**
 * @author Ali Tariq
 * www.github.com/alitariqnet
 * alibabapk@aol.com
 * */
public class NeutrosophicLogic {

	static double poorServiceM = 0;
	static double poorServiceNM = 0;
	static double poorServiceI = 0;
	static double goodServiceM = 0;
	static double goodServiceNM = 0;
	static double goodServiceI = 0;

	static double rancidFoodM = 0;
	static double rancidFoodNM = 0;
	static double rancidFoodI = 0;
	static double deliciousFoodM = 0;
	static double deliciousFoodNM = 0;
	static double deliciousFoodI = 0;

	static double dirtyEnvironmentM = 0;
	static double dirtyEnvironmentNM = 0;
	static double dirtyEnvironmentI = 0; 
	static double cleanEnvironmentM = 0;
	static double cleanEnvironmentNM = 0;
	static double cleanEnvironmentI = 0; 

	static double minF;
	static double maxI;
	static double maxT;
	
	public static void main(String[] args) {

		Service(6);
		
		System.out.println("Poor service membership: "+poorServiceM);
		System.out.println("Good service membership: "+goodServiceM);
		System.out.println("Poor service non-membership:  "+poorServiceNM);
		System.out.println("Good service non-membership:  "+goodServiceNM);
		System.out.println("Poor service indeterminate: "+poorServiceI);
		System.out.println("Good service indeterminate: "+goodServiceI);
		
		Food(2);
		
		System.out.println("Rancid food membership: "+rancidFoodM);
		System.out.println("Delicious food membership: "+deliciousFoodM);
		System.out.println("Rancid food non-membership: "+rancidFoodNM);
		System.out.println("Delicious food non-membership: "+deliciousFoodNM);
		System.out.println("Rancid food indeterminate: "+rancidFoodI);
		System.out.println("Delicious food indeterminate: "+deliciousFoodI);
		
		Environment(6); 

		System.out.println("Dirty environment membership: "+dirtyEnvironmentM);
		System.out.println("Clean environment membership: "+cleanEnvironmentM);
		System.out.println("Dirty environment non-membership: "+dirtyEnvironmentNM);
		System.out.println("Clean environment non-membership: "+cleanEnvironmentNM);
		System.out.println("Dirty environment indeterminate: "+dirtyEnvironmentI);
		System.out.println("Clean environment indeterminate: "+cleanEnvironmentI);
		
		maxT = max(goodServiceM,deliciousFoodM,cleanEnvironmentM);
		maxI = max(goodServiceI,deliciousFoodI,cleanEnvironmentI);
		minF  = min(goodServiceNM,deliciousFoodNM,cleanEnvironmentNM);
	}

	// Membership function for Service
	public static void Service(double a) {
		
			if(a<=0)
				poorServiceM =  1.0;
			else if (a>0 && a<7)
				poorServiceM =  (7-a)/(7-0);
			else
				poorServiceM =  0.0;
		
			if(a<=3)
				goodServiceM =  0.0;
			else if (a>3 && a<10)
				goodServiceM =  (a-3)/(10-3);
			else
				goodServiceM =  1.0;
		
			if(a<0)
				poorServiceNM =  0.0;
			else if (a>0 && a<7)
				poorServiceNM =  (a-0)/(7-0);
			else
				poorServiceNM =  1.0;
		
			if(a<=3)
				goodServiceNM =  1.0;
			else if (a>3 && a<10)
				goodServiceNM =  (10-a)/(10-3);
			else
				goodServiceNM =  0.0;
	
			if(a<=0)
				poorServiceI =  1.0;
			else if (a>0 && a<8)
				poorServiceI =  ((8-a)/(8-0))*0.5;
			else
				poorServiceI =  0.5;
		
			if(a<=2)
				goodServiceI =  0.0;
			else if (a>2 && a<10)
				goodServiceI =  ((a-2)/(10-2))*0.5;
			else
				goodServiceI =  0.5;
		
	}

	// Membership function for Food
	public static void Food(double a) {
		
			if(a<=0)
				rancidFoodM =  1.0;
			else if (a>0 && a<7)
				rancidFoodM =  (7-a)/(7-0);
			else
				rancidFoodM =  0.0;
		
			if(a<=3)
				deliciousFoodM =  0.0;
			else if (a>3 && a<10)
				deliciousFoodM =  (a-3)/(10-3);
			else
				deliciousFoodM =  1.0;
	
			if(a<=0)
				rancidFoodNM =  0.0;
			else if (a>0 && a<7)
				rancidFoodNM =  (7-a)/(7-0);
			else
				rancidFoodNM =  1.0;
		
			if(a<=3)
				deliciousFoodNM =  1.0;
			else if (a>3 && a<10)
				deliciousFoodNM =  (a-3)/(10-3);
			else
				deliciousFoodNM =  0.0;
		
			if(a<=0)
				rancidFoodI =  0.5;
			else if (a>0 && a<8)
				rancidFoodI =  ((8-a)/(8-0))*0.5;
			else
				rancidFoodI =  0.0;
		
			if(a<=2)
				deliciousFoodI =  0.0;
			else if (a>2 && a<10)
				deliciousFoodI =  ((a-2)/(10-2))*0.5;
			else
				deliciousFoodI =  0.5;
		
	}

	// Membership function for environment
	public static void Environment(double a) {
		
			if(a<=0)
				dirtyEnvironmentM =  1.0;
			else if (a>0 && a<7)
				dirtyEnvironmentM =  (7-a)/(7-0);
			else
				dirtyEnvironmentM =  0.0;
		
			if(a<=3)
				cleanEnvironmentM =  0.0;
			else if (a>3 && a<10)
				cleanEnvironmentM =  (a-3)/(10-3);
			else
				cleanEnvironmentM =  1.0;
		
			if(a<=0)
				dirtyEnvironmentNM =  0.0;
			else if (a>0 && a<7)
				dirtyEnvironmentNM =  (7-a)/(7-0);
			else
				dirtyEnvironmentNM =  1.0;

			if(a<=3)
				cleanEnvironmentNM =  0.0;
			else if (a>3 && a<10)
				cleanEnvironmentNM =  (a-3)/(10-3);
			else
				cleanEnvironmentNM =  1.0;

			if(a<=0)
				dirtyEnvironmentI =  0.5;
			else if (a>0 && a<8)
				dirtyEnvironmentI =  ((8-a)/(8-0))*0.5;
			else
				dirtyEnvironmentI =  0.0;
		
			if(a<=2)
				cleanEnvironmentI =  0.0;
			else if (a>2 && a<10)
				cleanEnvironmentI =  ((a-2)/(10-2))*0.5;
			else
				cleanEnvironmentI =  0.5;
	
	}
	// Two parameters min function
	public static double min(double a,double b) {
		if (a<b)
			return a;
		else 
			return b;
		//return min(a,b);
	}
	// Two parameters max function
	public static double max(double a,double b) {
		if (a>b)
			return a;
		else 
			return b;
		//return max(a,b);
	}
	// Three parameters min function
	public static double min(double a,double b, double c) {

		return min(c,min(a,b));
	}
	// Three parameters max function
	public static double max(double a,double b, double c) {

		return max(c,max(a,b));
	}

}
