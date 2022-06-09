package org.cloudbus.cloudsim.Fuzzylogic;

public class Storage {
	
	// use these in forming rules
	boolean isLowStorage = false;
	boolean isMedStorage = false;
	boolean isHighStorage = false;
	
	// use these to get the calculated values
	float lowStorage;
	float medStorage;
	float highStorage;
		
	// calculate the membership function values
	public void init(float storage) {
		if(storage<25000) {
			lowStorage=1;
			isLowStorage=true;
			medStorage=0;
			highStorage=0;
		}
		else if(storage>=25000&&storage<62500) {
			lowStorage=(62500-storage)/(62500-25000);
			isLowStorage=true;
			
			medStorage=(storage-25000)/(625-25000);
			if(storage!=25000)
				isMedStorage=true;
			highStorage=0;
		}
		else if(storage==62500) {
			lowStorage=0;
			medStorage=1;
			isMedStorage=true;
			highStorage=0;
		}
		else if(storage>62500&&storage<=100000) {
			lowStorage=0;
			medStorage=(100000-storage)/(100000-62500);
			if(storage!=100000)
			isMedStorage=true;
			
			highStorage=(storage-62500)/(100000-62500);
			isHighStorage=true;
		}
		else if(storage>100000) {
			lowStorage=0;
			medStorage=0;
			highStorage=1;
			isHighStorage=true;
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
	//	  	 25000  62500   100000
	
	void reset() {
		this.isHighStorage=false;
		this.isMedStorage=false;
		this.isLowStorage=false;
		this.highStorage=0.0f;
		this.medStorage=0.0f;
		this.lowStorage=0.0f;
	}
}
