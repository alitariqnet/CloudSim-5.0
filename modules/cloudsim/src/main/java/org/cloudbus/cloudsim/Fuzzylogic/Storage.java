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
		System.out.println("storage: "+storage);
		if(storage<10000) {
			lowStorage=1;
			isLowStorage=true;
			medStorage=0;
			highStorage=0;
		}
		else if(storage>=10000&&storage<55000) {
			lowStorage=(55000-storage)/(55000-10000);
			isLowStorage=true;
			System.out.println("lowStorage: "+lowStorage);
			medStorage=(storage-10000)/(55000-10000);
			System.out.println("medStorage: "+medStorage);
			if(storage!=10000)
				isMedStorage=true;
			highStorage=0;
		}
		else if(storage==55000) {
			lowStorage=0;
			medStorage=1;
			isMedStorage=true;
			highStorage=0;
		}
		else if(storage>55000&&storage<=100000) {
			lowStorage=0;
			medStorage=(100000-storage)/(100000-55000);
			if(storage!=100000)
			isMedStorage=true;
			System.out.println("medStorage: "+medStorage);
			highStorage=(storage-55000)/(100000-55000);
			isHighStorage=true;
			System.out.println("highStorage: "+highStorage);
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
	//	  	 10000  55000   100000
	
	public void reset() {
		this.isHighStorage=false;
		this.isMedStorage=false;
		this.isLowStorage=false;
		this.highStorage=0.0f;
		this.medStorage=0.0f;
		this.lowStorage=0.0f;
	}
}
