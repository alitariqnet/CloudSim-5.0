package org.cloudbus.cloudsim.Fuzzylogic;

public class Output {
	
	// use these in forming rules
	boolean low = false;
	boolean med = false;
	boolean high = false;
	
	// use these to get the calculated values
	float tmp;
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
	
	@Override
	public String toString() {
		return "Output [low=" + low + ", med=" + med + ", high=" + high + ", lowV=" + lowV + ", medV=" + medV
				+ ", highV=" + highV + "]";
	}
	
	// calculate the membership function values
	public void init(float f) {
		
		if(f>=0&&f<=5) {
			lowV=(5-f)/(5-0);
			if(f!=5)
			low=true;
			
			medV=0;
			highV=0;
		}
		if(f>=2&&f<=5) {
			medV=(f-2)/(5-2);
			if(f!=1)
			med=true;
			
			highV=0;
		}
		if(f>=5&&f<=9) {
			lowV=0;
			medV=(9-f)/(9-5);
			if(f!=9)
			med=true;
		}
		if(f>=6&&f<=10) {
			highV=(f-6)/(10-6);
			if(f!=6)
			high=true;
		}
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
