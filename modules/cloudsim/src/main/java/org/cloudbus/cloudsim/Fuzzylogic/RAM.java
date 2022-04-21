package org.cloudbus.cloudsim.Fuzzylogic;

public class RAM {
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
		if(f<512 || f>2048)
			System.out.println("Please enter value for RAM >=512 and <=2048");
		
		if(f>=512&&f<1280) {
			lowV=(1280-f)/(1280-512);
			low=true;

			medV=(f-512)/(1280-512);
			if(f!=512)
			med=true;
			
			highV=0;
		}
//		else if(f==1280) {
//			lowV=0;
//			medV=1;
//			med=true;
//			highV=0;
//		}
		else if(f>1280&&f<=2048) {
			lowV=0;
			
			medV=(2048-f)/(2048-1280);
			if(f!=2048)
			med=true;
			
			highV=(f-1280)/(2048-1280);
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
