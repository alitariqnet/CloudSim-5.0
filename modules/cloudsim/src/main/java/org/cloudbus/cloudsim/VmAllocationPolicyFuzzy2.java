/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009-2012, The University of Melbourne, Australia
 */

package org.cloudbus.cloudsim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloudbus.cloudsim.Fuzzylogic.Defuzzifier;
import org.cloudbus.cloudsim.Fuzzylogic.Fuzzifier;
import org.cloudbus.cloudsim.Fuzzylogic.FuzzyInferenceSystem;
import org.cloudbus.cloudsim.Fuzzylogic.FuzzyLogicSystem;
import org.cloudbus.cloudsim.core.CloudSim;

/**
 * VmAllocationPolicyNeutrosophic is an VmAllocationPolicy that chooses, as the host for a VM, the host
 * with less PEs in use, more free Ram, more free Storage, and more available Bandwidth. It is therefore 
 * a Best Fit policy, allocating VMs into the host with considering all available PE, Ram, Storage, and Bandwidth.
 * 
 * @author Ali Tariq
 */
public class VmAllocationPolicyFuzzy2 extends VmAllocationPolicy {
	
	private double maxPriority;
	private double crisp;
	int idx = -1;
	/** The map between each VM and its allocated host.
         * The map key is a VM UID and the value is the allocated host for that VM. */
	private Map<String, Host> vmTable;

	/** The map between each VM and the number of Pes used. 
         * The map key is a VM UID and the value is the number of used Pes for that VM. */
	private Map<String, Integer> usedPes;

	/** The number of free Pes for each host from {@link #getHostList() }. */
	private List<Integer> freePes;
	
	/**
	 * Creates a new VmAllocationPolicySimple object.
	 * 
	 * @param list the list of hosts
	 * @pre $none
	 * @post $none
	 */
	public VmAllocationPolicyFuzzy2(List<? extends Host> list) {
		super(list);

		setFreePes(new ArrayList<Integer>());
		for (Host host : getHostList()) {
			getFreePes().add(host.getNumberOfPes());
//			System.out.println(host.get)
		}

		setVmTable(new HashMap<String, Host>());
		setUsedPes(new HashMap<String, Integer>());
	}
	
	/**
	 * Allocates the host with less PEs in use for a given VM.
	 * 
	 * @param vm {@inheritDoc}
	 * @return {@inheritDoc}
	 * @pre $none
	 * @post $none
	 */
	@Override
	public boolean allocateHostForVm(Vm vm) {
//		System.out.println("vm requested Mips are "+vm.getMips());
		System.out.println("vm requested size is "+vm.getSize());
		System.out.println("vm requested ram is "+vm.getRam());
		System.out.println("vm requested Pes are "+vm.getNumberOfPes());
		System.out.println("");
		long timeStart= System.nanoTime();
//    	System.out.println(timeStart);
		int requiredPes = vm.getNumberOfPes();
		boolean result = false;
		int tries = 0;
		List<Integer> freePesTmp = new ArrayList<Integer>();
		for (Integer freePes : getFreePes()) {
			freePesTmp.add(freePes);
		}
		
		if (!getVmTable().containsKey(vm.getUid())) { // if this vm was not created
			do {// we still trying until we find a host or until we try all of them
//				int moreFree = Integer.MIN_VALUE;
//				idx = -1;
				maxPriority=0.0f;
				int counter = 0;
				boolean limitsCheck = false;
				for (Host host : getHostList()) {
//					fis = new FuzzyInferenceSystem();
					initRAM(host.getRamProvisioner().getAvailableRam());
					initSTORAGE((float)host.getStorage());
					initPE(freePesTmp.get(counter));
					initFISRules();
					crisp = defuzzification();
					System.out.println(counter+": crisp: "+crisp);
					System.out.print("host Storage: "+host.getStorage());
					System.out.print(" PE: "+freePesTmp.get(counter));
					System.out.print(" RAM: "+host.getRamProvisioner().getAvailableRam());
					System.out.println(" ");
					host.setPriority(crisp);
					counter++;
					resetPE();
					resetSTORAGE();
					resetRAM();
					resetDEFUZZ();
				}
				
				for (int i = 0; i < getHostList().size(); i++) {
					
					
					if(getHostList().get(i).getRamProvisioner().getAvailableRam()>=512 && getHostList().get(i).getAvailableMips() >=250 && freePesTmp.get(i)>=1)
						limitsCheck = true;
					
					if (limitsCheck && getHostList().get(i).getPriority() > maxPriority && idx!=getHostList().get(i).getId()) {
						maxPriority = getHostList().get(i).getPriority();
						idx = i;
					
						limitsCheck = false;
					}
				}
				System.out.print("Selected Host is "+getHostList().get(idx).getId()+" with ");
				System.out.print(" Storage: "+getHostList().get(idx).getStorage());
				System.out.print(" PE: "+freePesTmp.get(idx));
				System.out.print(" RAM: "+getHostList().get(idx).getRamProvisioner().getAvailableRam());
				System.out.println("");
				Host host = getHostList().get(idx);
				result = host.vmCreate(vm);

				if (result) { // if vm were succesfully created in the host
					getVmTable().put(vm.getUid(), host);
					getUsedPes().put(vm.getUid(), requiredPes);
					getFreePes().set(idx, getFreePes().get(idx) - requiredPes);
					result = true;
					break;
				} else {
					freePesTmp.set(idx, Integer.MIN_VALUE);
				}
				tries++;
			} while (!result && tries < getFreePes().size());

		}
		long timeEnd= System.nanoTime();
//    	System.out.println(timeEnd);
    	System.out.println("Simulation took time "+ (timeEnd - timeStart)+" nanoseconds");
		return result;
	}

	@Override
	public void deallocateHostForVm(Vm vm) {
		Host host = getVmTable().remove(vm.getUid());
		int idx = getHostList().indexOf(host);
		int pes = getUsedPes().remove(vm.getUid());
		if (host != null) {
			host.vmDestroy(vm);
			getFreePes().set(idx, getFreePes().get(idx) + pes);
		}
	}

	@Override
	public Host getHost(Vm vm) {
		return getVmTable().get(vm.getUid());
	}

	@Override
	public Host getHost(int vmId, int userId) {
		return getVmTable().get(Vm.getUid(userId, vmId));
	}

	/**
	 * Gets the vm table.
	 * 
	 * @return the vm table
	 */
	public Map<String, Host> getVmTable() {
		return vmTable;
	}

	/**
	 * Sets the vm table.
	 * 
	 * @param vmTable the vm table
	 */
	protected void setVmTable(Map<String, Host> vmTable) {
		this.vmTable = vmTable;
	}

	/**
	 * Gets the used pes.
	 * 
	 * @return the used pes
	 */
	protected Map<String, Integer> getUsedPes() {
		return usedPes;
	}

	/**
	 * Sets the used pes.
	 * 
	 * @param usedPes the used pes
	 */
	protected void setUsedPes(Map<String, Integer> usedPes) {
		this.usedPes = usedPes;
	}

	/**
	 * Gets the free pes.
	 * 
	 * @return the free pes
	 */
	protected List<Integer> getFreePes() {
		return freePes;
	}

	/**
	 * Sets the free pes.
	 * 
	 * @param freePes the new free pes
	 */
	protected void setFreePes(List<Integer> freePes) {
		this.freePes = freePes;
	}

	@Override
	public List<Map<String, Object>> optimizeAllocation(List<? extends Vm> vmList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean allocateHostForVm(Vm vm, Host host) {
		if (host.vmCreate(vm)) { // if vm has been succesfully created in the host
			getVmTable().put(vm.getUid(), host);

			int requiredPes = vm.getNumberOfPes();
			int idx = getHostList().indexOf(host);
			getUsedPes().put(vm.getUid(), requiredPes);
			getFreePes().set(idx, getFreePes().get(idx) - requiredPes);

			Log.formatLine(
					"%.2f: VM #" + vm.getId() + " has been allocated to the host #" + host.getId(),
					CloudSim.clock());
			return true;
		}

		return false;
	}
	
		// use these in forming rules
		boolean isLowStorage = false;
		boolean isMedStorage = false;
		boolean isHighStorage = false;
		
		// use these to get the calculated values
		float lowStorage ;
		float medStorage;
		float highStorage;
			
		// calculate the membership function values for Storage
		public void initSTORAGE(float storage) {
			if(storage<25000) {
				lowStorage=1;
				isLowStorage=true;
				medStorage=0;
				highStorage=0;
			}
			else if(storage>=25000&&storage<62500) {
				lowStorage=(62500-storage)/(62500-25000);
				isLowStorage=true;
				
				medStorage=(storage-25000)/(62500-25000);
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
		//	    25000   62500   100000
		
		
		void resetSTORAGE() {
			this.isHighStorage=false;
			this.isMedStorage=false;
			this.isLowStorage=false;
			this.highStorage=0.0f;
			this.medStorage=0.0f;
			this.lowStorage=0.0f;
		}
		
		// use these in forming rules
		boolean isLowRAM = false;
		boolean isMedRAM = false;
		boolean isHighRAM = false;
		
		// use these to get the calculated values
		float lowRAM;
		float medRAM;
		float highRAM;
			
			
		// calculate the membership function values for Random Access Memory
		public void initRAM(float ram) {
			if(ram<512) {
				lowRAM=1;
				isLowRAM=true;
				medRAM=0;
				highRAM=0;
			}
			else if(ram>=512&&ram<1280) {
				lowRAM=(1280-ram)/(1280-512);
				isLowRAM=true;

				medRAM=(ram-512)/(1280-512);
				if(ram!=512)
				isMedRAM=true;
				
				highRAM=0;
			}
			else if(ram==1280) {
				lowRAM=0;
				medRAM=1;
				isMedRAM=true;
				highRAM=0;
			}
			else if(ram>1280&&ram<=2048) {
				lowRAM=0;
				
				medRAM=(2048-ram)/(2048-1280);
				if(ram!=2048)
				isMedRAM=true;
				
				highRAM=(ram-1280)/(2048-1280);
				isHighRAM=true;
			}
			else if(ram>2048) {
				lowRAM=0;
				medRAM=0;
				highRAM=1;
				isHighRAM=true;
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
			
		void resetRAM() {
			this.isHighRAM=false;
			this.isMedRAM=false;
			this.isLowRAM=false;
			this.highRAM=0.0f;
			this.medRAM=0.0f;
			this.lowRAM=0.0f;
		}
			
		// use these in forming rules
		boolean isLowPE = false;
		boolean isMedPE = false;
		boolean isHighPE = false;
		
		// use these to get the calculated values
		float lowPE ;
		float medPE;
		float highPE;
			
		// calculate the membership function values for Processing Element
		public void initPE(float pe) {
			
			if(pe<1) {
				isLowPE=true;
				lowPE=1;
				medPE=0;
				highPE=0;
			}
			else if(pe>=1&&pe<2.5) {
				lowPE=(float) ((2.5-pe)/(2.5-1));
				isLowPE=true;
				
				medPE=(float) ((pe-1)/(2.5-1));
				if(pe!=1)
				isMedPE=true;
				highPE=0;
			}
			else if(pe==2.5) {
				lowPE=0;
				medPE=1;
				highPE=0;
				isMedPE=true;
			}
			else if(pe>2.5&&pe<=4) {
				lowPE=0;
				medPE=(float) ((4-pe)/(4-2.5));
				if(pe!=4)
				isMedPE=true;
				
				highPE=(float) ((pe-2.5)/(4-2.5));
				isHighPE=true;
			}
			else if(pe>4) {
				lowPE=0;
				medPE=0;
				highPE=1;
				isHighPE=true;
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
		
		void resetPE() {
			this.isHighPE=false;
			this.isMedPE=false;
			this.isLowPE=false;
			this.highPE=0.0f;
			this.medPE=0.0f;
			this.lowPE=0.0f;
		}
		// aggregate the output
		public void initFISRules() {
			
			// for first low
			// for second low
			if(isLowRAM==true && isLowStorage==true && isLowPE==true) {
				isLowDefuzz = true;
				tmp = AND(AND(lowRAM,lowStorage),lowPE);
				if(tmp>lowDefuzz)
					lowDefuzz = tmp;
//				System.out.println("inside 1 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isLowRAM==true && isLowStorage==true && isMedPE==true) {
				isLowDefuzz = true;
				tmp = AND(AND(lowRAM,lowStorage),medPE);
				if(tmp>lowDefuzz)
					lowDefuzz = tmp;
//				System.out.println("inside 2 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isLowRAM==true && isLowStorage==true && isHighPE==true) {
				isLowDefuzz = true;
				tmp = AND(AND(lowRAM,lowStorage),highPE);
				if(tmp>lowDefuzz)
					lowDefuzz = tmp;
//				System.out.println("inside 3 defuzz.lowV "+defuzz.lowV);
			}
			
			//for second medium
			if(isLowRAM==true && isMedStorage==true && isLowPE==true) {
				isLowDefuzz = true;
				tmp = AND(AND(lowRAM,medStorage),lowPE);
				if(tmp>lowDefuzz)
					lowDefuzz = tmp;
//				System.out.println("inside 4 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isLowRAM==true && isMedStorage==true && isMedPE==true) {
				isLowDefuzz = true;
				tmp = AND(AND(lowRAM,medStorage),medPE);
				if(tmp>lowDefuzz)
					lowDefuzz = tmp;
//				System.out.println("inside 5 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isLowRAM==true && isMedStorage==true && isHighPE==true) {
				isLowDefuzz = true;
				tmp = AND(AND(lowRAM,medStorage),highPE);
				if(tmp>lowDefuzz)
					lowDefuzz = tmp;
//				System.out.println("inside 6 defuzz.lowV "+defuzz.lowV);
			}
			
			// for second high
			if(isLowRAM==true && isHighStorage==true && isLowPE==true) {
				isLowDefuzz = true;
				tmp = AND(AND(lowRAM,highStorage),lowPE);
				if(tmp>lowDefuzz)
					lowDefuzz = tmp;
//				System.out.println("inside 7 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isLowRAM==true && isHighStorage==true && isMedPE==true) {
				isLowDefuzz = true;
				tmp = AND(AND(lowRAM,highStorage),medPE);
				if(tmp>lowDefuzz)
					lowDefuzz = tmp;
//				System.out.println("inside 8 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isLowRAM==true && isHighStorage==true && isHighPE==true) {
				isLowDefuzz = true;
				tmp = AND(AND(lowRAM,highStorage),highPE);
				if(tmp>lowDefuzz)
					lowDefuzz = tmp;
//				System.out.println("inside 9 defuzz.lowV "+defuzz.lowV);
			}
			
			//for first medium
			// for second low
			if(isMedRAM==true && isLowStorage==true && isLowPE==true) {
				isLowDefuzz = true;
				tmp = AND(AND(medRAM,lowStorage),lowPE);
				if(tmp>lowDefuzz)
					lowDefuzz = tmp;
//				System.out.println("inside 10 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isMedRAM==true && isLowStorage==true && isMedPE==true) {
				isLowDefuzz = true;
				tmp = AND(AND(medRAM,lowStorage),medPE);
				if(tmp>lowDefuzz)
					lowDefuzz = tmp;
//				System.out.println("inside 11 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isMedRAM==true && isLowStorage==true && isHighPE==true) {
				isLowDefuzz = true;
				tmp = AND(AND(medRAM,lowStorage),highPE);
				if(tmp>lowDefuzz)
					lowDefuzz = tmp;
//				System.out.println("inside 12 defuzz.lowV "+defuzz.lowV);
			}
			
			//for second medium
			if(isMedRAM==true && isMedStorage==true && isLowPE==true) {
				isLowDefuzz = true;
				tmp = AND(AND(medRAM,medStorage),lowPE);
				if(tmp>lowDefuzz)
					lowDefuzz = tmp;
//				System.out.println("inside 13 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isMedRAM==true && isMedStorage==true && isMedPE==true) {
				isMedDefuzz = true;
				tmp = AND(AND(medRAM,medStorage),medPE);
				if(tmp<medDefuzz)
					medDefuzz = tmp;
//				System.out.println("inside 14 defuzz.medV "+defuzz.medV);
			}
			
			if(isMedRAM==true && isMedStorage==true && isHighPE==true) {
				isMedDefuzz = true;
				tmp = AND(AND(medRAM,medStorage),highPE);
				if(tmp<medDefuzz)
					medDefuzz = tmp;
//				System.out.println("inside 15 defuzz.medV "+defuzz.medV);
			}
			
			// for second high
			if(isMedRAM==true && isHighStorage==true && isLowPE==true) {
				isLowDefuzz = true;
				tmp = AND(AND(medRAM,highStorage),lowPE);
				if(tmp>lowDefuzz)
					lowDefuzz = tmp;
//				System.out.println("inside 16 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isMedRAM==true && isHighStorage==true && isMedPE==true) {
				isMedDefuzz = true;
				tmp = AND(AND(medRAM,highStorage),medPE);
				if(tmp<medDefuzz)
					medDefuzz = tmp;
//				System.out.println("inside 17 defuzz.medV "+defuzz.medV);
			}
			
			if(isMedRAM==true && isHighStorage==true && isHighPE==true) {
				isMedDefuzz = true;
				tmp = AND(AND(medRAM,highStorage),highPE);
				if(tmp<medDefuzz)
					medDefuzz = tmp;
//				System.out.println("inside 18 defuzz.medV "+defuzz.medV);
			}
			
			// for first high
			// for second low
			if(isHighRAM==true && isLowStorage==true && isLowPE==true) {
				isLowDefuzz = true;
				tmp = AND(AND(highRAM,lowStorage),lowPE);
				if(tmp>lowDefuzz)
					lowDefuzz = tmp;
//				System.out.println("inside 19 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isHighRAM==true && isLowStorage==true && isMedPE==true) {
				isLowDefuzz = true;
				tmp = AND(AND(highRAM,lowStorage),medPE);
				if(tmp>lowDefuzz)
					lowDefuzz = tmp;
//				System.out.println("inside 20 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isHighRAM==true && isLowStorage==true && isHighPE==true) {
				isLowDefuzz = true;
				tmp = AND(AND(highRAM,lowStorage),highPE);
				if(tmp>lowDefuzz)
					lowDefuzz = tmp;
//				System.out.println("inside 21 defuzz.lowV "+defuzz.lowV);
			}
			
			//for second medium
			if(isHighRAM==true && isMedStorage==true && isLowPE==true) {
				isLowDefuzz = true;
				tmp = AND(AND(lowRAM,lowStorage),lowPE);
				if(tmp>lowDefuzz)
					lowDefuzz = tmp;
//				System.out.println("inside 22 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isHighRAM==true && isMedStorage==true && isMedPE==true) {
				isMedDefuzz = true;
				tmp = AND(AND(lowRAM,lowStorage),lowPE);
				if(tmp<medDefuzz)
					medDefuzz = tmp;
//				System.out.println("inside 23 defuzz.medV "+defuzz.medV);
			}
			
			if(isHighRAM==true && isMedStorage==true && isHighPE==true) {
				isMedDefuzz = true;
				tmp = AND(AND(highRAM,medStorage),highPE);
				if(tmp<medDefuzz)
					medDefuzz = tmp;
//				System.out.println("inside 24 defuzz.medV "+defuzz.medV);
			}
			
			// for second high
			if(isHighRAM==true && isHighStorage==true && isLowPE==true) {
				isLowDefuzz = true;
				tmp = AND(AND(highRAM,highStorage),lowPE);
				if(tmp>lowDefuzz)
					lowDefuzz = tmp;
//				System.out.println("inside 25 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isHighRAM==true && isHighStorage==true && isMedPE==true) {
				isMedDefuzz = true;
				tmp = AND(AND(highRAM,highStorage),medPE);
				if(tmp<medDefuzz)
					medDefuzz = tmp;
//				System.out.println("inside 26 defuzz.medV "+defuzz.medV);
			}
			
			if(isHighRAM==true && isHighStorage==true && isHighPE==true) {
				isHighDefuzz = true;
				tmp = AND(AND(highRAM,highStorage),highPE);
				if(tmp>highDefuzz)
					highDefuzz = tmp;
//				System.out.println("inside 27 defuzz.highV "+defuzz.highV);
			}
		}

		public static float AND(float a, float b) {
			if(a<b)
				return a;
			else 
				return b;
		}
		
		public static float OR(float a, float b) {
			if(a>b)
				return a;
			else 
				return b;
		}
		
		// use these in forming rules as output parameter
		boolean isLowDefuzz = false;
		boolean isMedDefuzz = false;
		boolean isHighDefuzz = false;
		
		// use these to get the calculated values for calculating crisp output
		float tmp;
		float lowDefuzz;
		float medDefuzz;
		float highDefuzz;
				
		void resetDEFUZZ() {
			this.isHighDefuzz=false;
			this.isMedDefuzz=false;
			this.isLowDefuzz=false;
			this.highDefuzz=0.0f;
			this.medDefuzz=0.0f;
			this.lowDefuzz=0.0f;
		}
		
		public double defuzzification() {
			
			double f = 0;
			double a = 0,b,c,e,g,h,l;
			
			System.out.println("");
			System.out.println("lowDefuzz: "+ lowDefuzz + " medDefuzz:"+medDefuzz+" highDefuzz:"+highDefuzz);
			
			// calculate for low membership function in defuzzification
			if (lowDefuzz>=0.75) {
				a = ((1*0.75)+(2*0.5)+(3*0.25));
				e = 0.75+0.5+0.25;
				System.out.println("lowDefuzz>=0.75");
			}
			else if (lowDefuzz>=0.5) {
				a = ((1*lowDefuzz)+(2*0.5)+(3*0.25));
				e = lowDefuzz+0.5+0.25;
				System.out.println("lowDefuzz>=0.5");
			}
			else if(lowDefuzz>=0.25) {
				a = ((1*lowDefuzz)+(2*lowDefuzz)+(3*0.25));
				e = lowDefuzz+lowDefuzz+0.25;
				System.out.println("lowDefuzz>=0.25");
			}
			else {
				a = ((1*lowDefuzz)+(2*lowDefuzz)+(3*lowDefuzz));
				e = lowDefuzz+lowDefuzz+lowDefuzz;
				System.out.println("lowDefuzz<0.25");
			}
			
			// calculate for medium membership function in defuzzification
			if(medDefuzz>=0.75) {
				b = (((2*0.25)+(3*0.5)+(4*0.75)+(5*medDefuzz)+(6*0.75)+(7*0.5)+(8*0.25)));
				g = 0.25+0.5+0.75+medDefuzz+0.75+0.5+0.25;
				System.out.println("medDefuzz>=0.75");
			}
			else if(medDefuzz>=0.5) {
				b = (((2*0.25)+(3*0.5)+(4*medDefuzz)+(5*medDefuzz)+(6*medDefuzz)+(7*0.5)+(8*0.25)));
				g = 0.25+0.5+medDefuzz+medDefuzz+medDefuzz+0.5+0.25;
				System.out.println("medDefuzz>=0.5");
			}
			else if(medDefuzz>=0.25) {
				b = (((2*0.25)+(3*medDefuzz)+(4*medDefuzz)+(5*medDefuzz)+(6*medDefuzz)+(7*medDefuzz)+(8*0.25)));
				g = 0.25+medDefuzz+medDefuzz+medDefuzz+medDefuzz+medDefuzz+0.25;
				System.out.println("medDefuzz>=0.25");
			}
			else {
				b = (((2*medDefuzz)+(3*medDefuzz)+(4*medDefuzz)+(5*medDefuzz)+(6*medDefuzz)+(7*medDefuzz)+(8*medDefuzz)));
				g = medDefuzz+medDefuzz+medDefuzz+medDefuzz+medDefuzz+medDefuzz+medDefuzz;
				System.out.println("medDefuzz<0.25");
			}
			
			// calculate for high membership function in defuzzification
			if(highDefuzz>=0.75) {
				c = (((7*0.25)+(8*0.5)+(9*0.75)+(10*highDefuzz)));
				h = 0.25+0.5+0.75+highDefuzz;
				System.out.println("highDefuzz>=0.75");
			}
			else if(highDefuzz>=0.5) {
				c = (((7*0.25)+(8*0.5)+(9*highDefuzz)+(10*highDefuzz)));
				h = 0.25+0.5+highDefuzz+highDefuzz;
				System.out.println("highDefuzz>=0.5");
			}
			else if(highDefuzz>=0.25) {
				c = (((7*0.25)+(8*highDefuzz)+(9*highDefuzz)+(10*highDefuzz)));
				h = 0.25+highDefuzz+highDefuzz+highDefuzz;
				System.out.println("highDefuzz>=0.25");
			}
			else {
				c = (((7*highDefuzz)+(8*highDefuzz)+(9*highDefuzz)+(10*highDefuzz)));
				h = highDefuzz+highDefuzz+highDefuzz+highDefuzz;
				System.out.println("highDefuzz<0.25");
			}
			
			f = a+b+c;
			System.out.println("a: "+a+" b: "+b+" c: "+c+" f: "+f);
			l = e+g+h;
			System.out.println("e: "+e+" g: "+g+" h: "+h+" l: "+l);
			return f/l;
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
		//	  	  1     4  6     9
		
}
