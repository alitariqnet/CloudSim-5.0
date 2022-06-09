package org.cloudbus.cloudsim.Fuzzylogic;

public class Ram {
	
	// use these in forming rules
	boolean isLowRam = false;
	boolean isMedRam = false;
	boolean isHighRam = false;
	
	// use these to get the calculated values
	float lowRam;
	float medRam;
	float highRam;
		
	// calculate the membership function values
	public void init(float ram) {
		if(ram<512) {
			lowRam=1;
			isLowRam=true;
			medRam=0;
			highRam=0;
		}
		else if(ram>=512&&ram<1280) {
			lowRam=(1280-ram)/(1280-512);
			isLowRam=true;

			medRam=(ram-512)/(1280-512);
			if(ram!=512)
			isMedRam=true;
			
			highRam=0;
		}
		else if(ram==1280) {
			lowRam=0;
			medRam=1;
			isMedRam=true;
			highRam=0;
		}
		else if(ram>1280&&ram<=2048) {
			lowRam=0;
			
			medRam=(2048-ram)/(2048-1280);
			if(ram!=2048)
			isMedRam=true;
			
			highRam=(ram-1280)/(2048-1280);
			isHighRam=true;
		}
		else if(ram>2048) {
			lowRam=0;
			medRam=0;
			highRam=1;
			isHighRam=true;
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
	
	void reset() {
		this.isHighRam=false;
		this.isMedRam=false;
		this.isLowRam=false;
		this.highRam=0.0f;
		this.medRam=0.0f;
		this.lowRam=0.0f;
	}
}
