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
	public void init(float mips) {
		if(mips<250) {
			lowV=1;
			low=true;
			medV=0;
			highV=0;
		}
		else if(mips>=250&&mips<625) {
			lowV=(625-mips)/(625-250);
			low=true;
			
			medV=(mips-250)/(625-250);
			if(mips!=250)
				med=true;
			highV=0;
		}
		else if(mips==625) {
			lowV=0;
			medV=1;
			med=true;
			highV=0;
		}
		else if(mips>625&&mips<=1000) {
			lowV=0;
			medV=(1000-mips)/(1000-625);
			if(mips!=1000)
			med=true;
			
			highV=(mips-625)/(1000-625);
			high=true;
		}
		else if(mips>1000) {
			lowV=0;
			medV=0;
			highV=1;
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
