package org.cloudbus.cloudsim.Fuzzylogic;

public class Pe {
	
	// use these in forming rules
	boolean isLowPe = false;
	boolean isMedPe = false;
	boolean isHighPe = false;
	
	// use these to get the calculated values
	float lowPe ;
	float medPe;
	float highPe;
		
	// calculate the membership function values
	public void init(float pe) {
		
		if(pe<1) {
			isLowPe=true;
			lowPe=1;
			medPe=0;
			highPe=0;
		}
		else if(pe>=1&&pe<2.5) {
			lowPe=(float) ((2.5-pe)/(2.5-1));
			isLowPe=true;
			
			medPe=(float) ((pe-1)/(2.5-1));
			if(pe!=1)
			isMedPe=true;
			highPe=0;
		}
		else if(pe==2.5) {
			lowPe=0;
			medPe=1;
			highPe=0;
			isMedPe=true;
		}
		else if(pe>2.5&&pe<=4) {
			lowPe=0;
			medPe=(float) ((4-pe)/(4-2.5));
			if(pe!=4)
			isMedPe=true;
			
			highPe=(float) ((pe-2.5)/(4-2.5));
			isHighPe=true;
		}
		else if(pe>4) {
			lowPe=0;
			medPe=0;
			highPe=1;
			isHighPe=true;
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
		return "PE [low=" + isLowPe + ", med=" + isMedPe + ", high=" + isHighPe + ", lowV=" + lowPe + ", medV=" + medPe
				+ ", highV=" + highPe + "]";
	}
	
	void reset() {
		this.isHighPe=false;
		this.isMedPe=false;
		this.isLowPe=false;
		this.highPe=0.0f;
		this.medPe=0.0f;
		this.lowPe=0.0f;
	}
}
