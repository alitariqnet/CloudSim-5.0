package org.cloudbus.cloudsim.Fuzzylogic;

public class RAM {
	// use these in forming rules
		boolean low = false;
		boolean med = false;
		boolean high = false;
		
		// use these to get the calculated values
		float lowV;
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
	public void init(float ram) {
		if(ram<512) {
			lowV=1;
			low=true;
			medV=0;
			highV=0;
		}
		else if(ram>=512&&ram<1280) {
			lowV=(1280-ram)/(1280-512);
			low=true;

			medV=(ram-512)/(1280-512);
			if(ram!=512)
			med=true;
			
			highV=0;
		}
		else if(ram==1280) {
			lowV=0;
			medV=1;
			med=true;
			highV=0;
		}
		else if(ram>1280&&ram<=2048) {
			lowV=0;
			
			medV=(2048-ram)/(2048-1280);
			if(ram!=2048)
			med=true;
			
			highV=(ram-1280)/(2048-1280);
			high=true;
		}
		else if(ram>2048) {
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
	//	  	  512   1280    2048
	
	@Override
	public String toString() {
		return "RAM [low=" + low + ", med=" + med + ", high=" + high + ", lowV=" + lowV + ", medV=" + medV
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
