package org.cloudbus.cloudsim.Fuzzylogic;

public class MIPS {
		// use these in forming rules
		boolean low = false;
		boolean med = false;
		boolean high = false;
		
		// use these to get the calculated values
		float lowV ;
		float medV;
		float highV;
		
		public boolean isLow() {
			return low;
		}
		
		public void setLow(boolean low) {
			this.low = low;
		}
		
		public boolean isMed() {
			return med;
		}
		
		public void setMed(boolean med) {
			this.med = med;
		}
		
		public boolean isHigh() {
			return high;
		}
		
		public void setHigh(boolean high) {
			this.high = high;
		}
		
	// calculate the membership function values
	public void init(float f) {
		if(f<250 || f>1000)
			System.out.println("Please enter value for MIPS >=250 and <=1000");
		
		if(f>=250&&f<625) {
			lowV=(625-f)/(625-250);
			low=true;
			
			medV=(f-250)/(625-250);
			if(f!=250)
				med=true;
			highV=0;
		}
//		if(f==625) {
//			lowV=0;
//			medV=1;
//			med=true;
//			highV=0;
//		}
		if(f>625&&f<=1000) {
			lowV=0;
			medV=(1000-f)/(1000-625);
			if(f!=1000)
			med=true;
			
			highV=(f-625)/(1000-625);
			high=true;
		}
	}

	// Above method formulates the function below
	
	//		1|low    med    high
	//		 |\      /\      /
	//	     | \    /  \    /
	//		 |	\  /    \  /
	//	  0.5|	 \/      \/
	//	 	 |	 /\      /\
	//	 	 |  /  \    /  \
	//   	 | /    \  /    \
	//  	0|/______\/______\____________
	//	  	  250    625    1000
	
	
	@Override
	public String toString() {
		return "MIPS [low=" + low + ", med=" + med + ", high=" + high + ", lowV=" + lowV + ", medV=" + medV + ", highV="
				+ highV + "]";
	}
	
	void reset() {
		this.high=false;
		this.med=false;
		this.low=false;
		this.highV=0.0f;
		this.medV=0.0f;
		this.lowV=0.0f;
	}
}
