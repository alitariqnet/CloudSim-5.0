package org.cloudbus.cloudsim.examples;

public class HostExample {

	private int hostId;
	private int ram; 
	private long storage;
	private int bw;
	
	HostExample(int hostId,int ram,int storage, int bw){
		this.hostId=hostId;
		this.ram=ram;
		this.storage=storage;
		this.bw=bw;
	}
	
	public HostExample() {
	}

	public int getHostId() {
		return hostId;
	}

	public void setHostId(int hostId) {
		this.hostId = hostId;
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public long getStorage() {
		return storage;
	}

	public void setStorage(long storage) {
		this.storage = storage;
	}

	public int getBw() {
		return bw;
	}

	public void setBw(int bw) {
		this.bw = bw;
	}
}
