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
	
	private float maxPriority;
	private float crisp;
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
			boolean isLowSTORAGE = false;
			boolean isMedSTORAGE = false;
			boolean isHighSTORAGE = false;
			
			// use these to get the calculated values
			float lowSTORAGE ;
			float medSTORAGE;
			float highSTORAGE;
			
			
		// calculate the membership function values
		public void initSTORAGE(float storage) {
			if(storage<25000) {
				lowSTORAGE=1;
				isLowSTORAGE=true;
				medSTORAGE=0;
				highSTORAGE=0;
			}
			else if(storage>=25000&&storage<62500) {
				lowSTORAGE=(62500-storage)/(62500-25000);
				isLowSTORAGE=true;
				
				medSTORAGE=(storage-25000)/(62500-25000);
				if(storage!=25000)
					isMedSTORAGE=true;
				highSTORAGE=0;
			}
			else if(storage==62500) {
				lowSTORAGE=0;
				medSTORAGE=1;
				isMedSTORAGE=true;
				highSTORAGE=0;
			}
			else if(storage>62500&&storage<=100000) {
				lowSTORAGE=0;
				medSTORAGE=(100000-storage)/(100000-62500);
				if(storage!=100000)
				isMedSTORAGE=true;
				
				highSTORAGE=(storage-62500)/(100000-62500);
				isHighSTORAGE=true;
			}
			else if(storage>100000) {
				lowSTORAGE=0;
				medSTORAGE=0;
				highSTORAGE=1;
				isHighSTORAGE=true;
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
			this.isHighSTORAGE=false;
			this.isMedSTORAGE=false;
			this.isLowSTORAGE=false;
			this.highSTORAGE=0.0f;
			this.medSTORAGE=0.0f;
			this.lowSTORAGE=0.0f;
		}
		
		// use these in forming rules
				boolean isLowRAM = false;
				boolean isMedRAM = false;
				boolean isHighRAM = false;
				
				// use these to get the calculated values
				float lowRAM;
				float medRAM;
				float highRAM;
				
				
			// calculate the membership function values
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
			
		// calculate the membership function values
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
		
		public void initFISRules() {
			
			// for first low
			// for second low
			if(isLowRAM==true && isLowSTORAGE==true && isLowPE==true) {
				isLowDEFUZZ = true;
				tmp = AND(AND(lowRAM,lowSTORAGE),lowPE);
				if(tmp>lowDEFUZZ)
					lowDEFUZZ = tmp;
//				System.out.println("inside 1 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isLowRAM==true && isLowSTORAGE==true && isMedPE==true) {
				isLowDEFUZZ = true;
				tmp = AND(AND(lowRAM,lowSTORAGE),medPE);
				if(tmp>lowDEFUZZ)
					lowDEFUZZ = tmp;
//				System.out.println("inside 2 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isLowRAM==true && isLowSTORAGE==true && isHighPE==true) {
				isLowDEFUZZ = true;
				tmp = AND(AND(lowRAM,lowSTORAGE),highPE);
				if(tmp>lowDEFUZZ)
					lowDEFUZZ = tmp;
//				System.out.println("inside 3 defuzz.lowV "+defuzz.lowV);
			}
			
			//for second medium
			if(isLowRAM==true && isMedSTORAGE==true && isLowPE==true) {
				isLowDEFUZZ = true;
				tmp = AND(AND(lowRAM,medSTORAGE),lowPE);
				if(tmp>lowDEFUZZ)
					lowDEFUZZ = tmp;
//				System.out.println("inside 4 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isLowRAM==true && isMedSTORAGE==true && isMedPE==true) {
				isLowDEFUZZ = true;
				tmp = AND(AND(lowRAM,medSTORAGE),medPE);
				if(tmp>lowDEFUZZ)
					lowDEFUZZ = tmp;
//				System.out.println("inside 5 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isLowRAM==true && isMedSTORAGE==true && isHighPE==true) {
				isLowDEFUZZ = true;
				tmp = AND(AND(lowRAM,medSTORAGE),highPE);
				if(tmp>lowDEFUZZ)
					lowDEFUZZ = tmp;
//				System.out.println("inside 6 defuzz.lowV "+defuzz.lowV);
			}
			
			// for second high
			if(isLowRAM==true && isHighSTORAGE==true && isLowPE==true) {
				isLowDEFUZZ = true;
				tmp = AND(AND(lowRAM,highSTORAGE),lowPE);
				if(tmp>lowDEFUZZ)
					lowDEFUZZ = tmp;
//				System.out.println("inside 7 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isLowRAM==true && isHighSTORAGE==true && isMedPE==true) {
				isLowDEFUZZ = true;
				tmp = AND(AND(lowRAM,highSTORAGE),medPE);
				if(tmp>lowDEFUZZ)
					lowDEFUZZ = tmp;
//				System.out.println("inside 8 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isLowRAM==true && isHighSTORAGE==true && isHighPE==true) {
				isLowDEFUZZ = true;
				tmp = AND(AND(lowRAM,highSTORAGE),highPE);
				if(tmp>lowDEFUZZ)
					lowDEFUZZ = tmp;
//				System.out.println("inside 9 defuzz.lowV "+defuzz.lowV);
			}
			
			//for first medium
			// for second low
			if(isMedRAM==true && isLowSTORAGE==true && isLowPE==true) {
				isLowDEFUZZ = true;
				tmp = AND(AND(medRAM,lowSTORAGE),lowPE);
				if(tmp>lowDEFUZZ)
					lowDEFUZZ = tmp;
//				System.out.println("inside 10 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isMedRAM==true && isLowSTORAGE==true && isMedPE==true) {
				isLowDEFUZZ = true;
				tmp = AND(AND(medRAM,lowSTORAGE),medPE);
				if(tmp>lowDEFUZZ)
					lowDEFUZZ = tmp;
//				System.out.println("inside 11 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isMedRAM==true && isLowSTORAGE==true && isHighPE==true) {
				isLowDEFUZZ = true;
				tmp = AND(AND(medRAM,lowSTORAGE),highPE);
				if(tmp>lowDEFUZZ)
					lowDEFUZZ = tmp;
//				System.out.println("inside 12 defuzz.lowV "+defuzz.lowV);
			}
			
			//for second medium
			if(isMedRAM==true && isMedSTORAGE==true && isLowPE==true) {
				isLowDEFUZZ = true;
				tmp = AND(AND(medRAM,medSTORAGE),lowPE);
				if(tmp>lowDEFUZZ)
					lowDEFUZZ = tmp;
//				System.out.println("inside 13 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isMedRAM==true && isMedSTORAGE==true && isMedPE==true) {
				isMedDEFUZZ = true;
				tmp = AND(AND(medRAM,medSTORAGE),medPE);
				if(tmp>medDEFUZZ)
					medDEFUZZ = tmp;
//				System.out.println("inside 14 defuzz.medV "+defuzz.medV);
			}
			
			if(isMedRAM==true && isMedSTORAGE==true && isHighPE==true) {
				isMedDEFUZZ = true;
				tmp = AND(AND(medRAM,medSTORAGE),highPE);
				if(tmp>medDEFUZZ)
					medDEFUZZ = tmp;
//				System.out.println("inside 15 defuzz.medV "+defuzz.medV);
			}
			
			// for second high
			if(isMedRAM==true && isHighSTORAGE==true && isLowPE==true) {
				isLowDEFUZZ = true;
				tmp = AND(AND(medRAM,highSTORAGE),lowPE);
				if(tmp>lowDEFUZZ)
					lowDEFUZZ = tmp;
//				System.out.println("inside 16 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isMedRAM==true && isHighSTORAGE==true && isMedPE==true) {
				isMedDEFUZZ = true;
				tmp = AND(AND(medRAM,highSTORAGE),medPE);
				if(tmp>medDEFUZZ)
					medDEFUZZ = tmp;
//				System.out.println("inside 17 defuzz.medV "+defuzz.medV);
			}
			
			if(isMedRAM==true && isHighSTORAGE==true && isHighPE==true) {
				isMedDEFUZZ = true;
				tmp = AND(AND(medRAM,highSTORAGE),highPE);
				if(tmp>medDEFUZZ)
					medDEFUZZ = tmp;
//				System.out.println("inside 18 defuzz.medV "+defuzz.medV);
			}
			
			// for first high
			// for second low
			if(isHighRAM==true && isLowSTORAGE==true && isLowPE==true) {
				isLowDEFUZZ = true;
				tmp = AND(AND(highRAM,lowSTORAGE),lowPE);
				if(tmp>lowDEFUZZ)
					lowDEFUZZ = tmp;
//				System.out.println("inside 19 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isHighRAM==true && isLowSTORAGE==true && isMedPE==true) {
				isLowDEFUZZ = true;
				tmp = AND(AND(highRAM,lowSTORAGE),medPE);
				if(tmp>lowDEFUZZ)
					lowDEFUZZ = tmp;
//				System.out.println("inside 20 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isHighRAM==true && isLowSTORAGE==true && isHighPE==true) {
				isLowDEFUZZ = true;
				tmp = AND(AND(highRAM,lowSTORAGE),highPE);
				if(tmp>lowDEFUZZ)
					lowDEFUZZ = tmp;
//				System.out.println("inside 21 defuzz.lowV "+defuzz.lowV);
			}
			
			//for second medium
			if(isHighRAM==true && isMedSTORAGE==true && isLowPE==true) {
				isLowDEFUZZ = true;
				tmp = AND(AND(lowRAM,lowSTORAGE),lowPE);
				if(tmp>lowDEFUZZ)
					lowDEFUZZ = tmp;
//				System.out.println("inside 22 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isHighRAM==true && isMedSTORAGE==true && isMedPE==true) {
				isMedDEFUZZ = true;
				tmp = AND(AND(lowRAM,lowSTORAGE),lowPE);
				if(tmp>medDEFUZZ)
					medDEFUZZ = tmp;
//				System.out.println("inside 23 defuzz.medV "+defuzz.medV);
			}
			
			if(isHighRAM==true && isMedSTORAGE==true && isHighPE==true) {
				isMedDEFUZZ = true;
				tmp = AND(AND(highRAM,medSTORAGE),highPE);
				if(tmp>medDEFUZZ)
					medDEFUZZ = tmp;
//				System.out.println("inside 24 defuzz.medV "+defuzz.medV);
			}
			
			// for second high
			if(isHighRAM==true && isHighSTORAGE==true && isLowPE==true) {
				isLowDEFUZZ = true;
				tmp = AND(AND(highRAM,highSTORAGE),lowPE);
				if(tmp>lowDEFUZZ)
					lowDEFUZZ = tmp;
//				System.out.println("inside 25 defuzz.lowV "+defuzz.lowV);
			}
			
			if(isHighRAM==true && isHighSTORAGE==true && isMedPE==true) {
				isMedDEFUZZ = true;
				tmp = AND(AND(highRAM,highSTORAGE),medPE);
				if(tmp>medDEFUZZ)
					medDEFUZZ = tmp;
//				System.out.println("inside 26 defuzz.medV "+defuzz.medV);
			}
			
			if(isHighRAM==true && isHighSTORAGE==true && isHighPE==true) {
				isHighDEFUZZ = true;
				tmp = AND(AND(highRAM,highSTORAGE),highPE);
				if(tmp>highDEFUZZ)
					highDEFUZZ = tmp;
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
		boolean isLowDEFUZZ = false;
		boolean isMedDEFUZZ = false;
		boolean isHighDEFUZZ = false;
		
		// use these to get the calculated values for calculating crisp output
		float tmp;
		float lowDEFUZZ;
		float medDEFUZZ;
		float highDEFUZZ;
				
		void resetDEFUZZ() {
			this.isHighDEFUZZ=false;
			this.isMedDEFUZZ=false;
			this.isLowDEFUZZ=false;
			this.highDEFUZZ=0.0f;
			this.medDEFUZZ=0.0f;
			this.lowDEFUZZ=0.0f;
		}
		
		public float defuzzification() {
//			System.out.println("lowV: "+lowV+" medV: "+medV+" highV: "+highV);
			float f = ((0+1+2)*lowDEFUZZ)+((3+4+5+6+7)*medDEFUZZ)+((8+9+10)*highDEFUZZ);
//			System.out.println("f: "+f);
			float l = ((lowDEFUZZ*3)+(medDEFUZZ*5)+(highDEFUZZ*3));
//			System.out.println("l: "+l);
//			crisp = (float) f/l;
//			System.out.println(crisp);
//			return crisp/10;
			return (float) f/l;
		}
}
