package org.cloudbus.cloudsim.Fuzzylogic;

public class PE {
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
		
		if(f<1 || f>4)
			System.out.println("Please enter value for PE >=1 and <=4");
		
		if(f>=1&&f<2.5) {
			lowV=(float) ((2.5-f)/(2.5-1));
			low=true;
			
			medV=(float) ((f-1)/(2.5-1));
			if(f!=1)
			med=true;
			highV=0;
		}
		
		if(f>2.5&&f<=4) {
			lowV=0;
			medV=(float) ((4-f)/(4-2.5));
			if(f!=4)
			med=true;
			
			highV=(float) ((f-2.5)/(4-2.5));
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
	//	  	  1    2    3    4
	
	@Override
	public String toString() {
		return "PE [low=" + low + ", med=" + med + ", high=" + high + ", lowV=" + lowV + ", medV=" + medV
				+ ", highV=" + highV + "]";
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
