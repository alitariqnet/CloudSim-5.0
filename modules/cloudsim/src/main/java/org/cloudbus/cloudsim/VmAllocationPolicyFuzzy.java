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
public class VmAllocationPolicyFuzzy extends VmAllocationPolicy {
	
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
	
	FuzzyLogicSystem fls;
	/**
	 * Creates a new VmAllocationPolicySimple object.
	 * 
	 * @param list the list of hosts
	 * @pre $none
	 * @post $none
	 */
	public VmAllocationPolicyFuzzy(List<? extends Host> list) {
		super(list);

		setFreePes(new ArrayList<Integer>());
		for (Host host : getHostList()) {
			getFreePes().add(host.getNumberOfPes());
//			System.out.println(host.get)
		}

		setVmTable(new HashMap<String, Host>());
		setUsedPes(new HashMap<String, Integer>());
	}
	
//	public void getPriority() {
//		int counter = 0;
//		for (Host host : getHostList()) {
//			System.out.println("Host: "+(counter+1)+"--> RAM: "+host.getRamProvisioner().getAvailableRam()+" MIPS: "+host.getAvailableMips()+" PE: "+host.getNumberOfFreePes());
//		fis.fuzz.input.ram.init(host.getRamProvisioner().getAvailableRam());
//		fis.fuzz.input.mips.init((float)host.getAvailableMips());
//		fis.fuzz.input.pe.init(host.getNumberOfFreePes());
//		fis.initFISRules(fis.defuzz.output);
//		crisp = fis.defuzz.defuzzification();
//		System.out.println("crisp value: "+crisp);
//		if(crisp!=0)
//		host.setPriority(crisp);
//		counter++;
//		}
		
//		System.out.println(fis.defuzz.output.toString());
//		System.out.println("crisp value is "+fis.defuzz.defuzzification());
//	}
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
		System.out.println("vm requested Mips are "+vm.getMips());
		System.out.println("vm requested ram is "+vm.getRam());
//		System.out.println("vm requested BW is "+vm.getBw());
//		System.out.println("vm requested size is "+vm.getSize());
		System.out.println("vm requested Pes are "+vm.getNumberOfPes());
		System.out.println("");
		long timeStart= System.nanoTime();
    	System.out.println(timeStart);
		int requiredPes = vm.getNumberOfPes();
		boolean result = false;
		int tries = 0;
		List<Integer> freePesTmp = new ArrayList<Integer>();
		for (Integer freePes : getFreePes()) {
//			System.out.println("freePes "+freePes);
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
					fls = new FuzzyLogicSystem();
//					fis = new FuzzyInferenceSystem();
					fls.fuzz.ram.init(host.getRamProvisioner().getAvailableRam());
					fls.fuzz.mips.init((float)host.getAvailableMips());
					fls.fuzz.pe.init(freePesTmp.get(counter));
					
					fls.fis.initFISRules(fls.fuzz, fls.defuzz);
					crisp = fls.defuzz.defuzzification();
					
//					System.out.println("Host: "+(counter)+"--> RAM: "+host.getRamProvisioner().getAvailableRam()+" MIPS: "+host.getAvailableMips()+" PE: "+freePesTmp.get(counter) +" crisp value: "+crisp);
					host.setPriority(crisp);
					counter++;
				}
				
				for (int i = 0; i < getHostList().size(); i++) {
					
//					System.out.println("*************");
//					System.out.println("getHostList().get(i).getRamProvisioner().getAvailableRam() "+getHostList().get(i).getRamProvisioner().getAvailableRam());
//					System.out.println("getHostList().get(i).getAvailableMips() "+getHostList().get(i).getAvailableMips());
//					System.out.println("freePesTmp.get(i) "+freePesTmp.get(i));
//					System.out.println("*************");
					
					if(getHostList().get(i).getRamProvisioner().getAvailableRam()>=512 && getHostList().get(i).getAvailableMips() >=250 && freePesTmp.get(i)>=1)
						limitsCheck = true;
					
					if (limitsCheck && getHostList().get(i).getPriority() > maxPriority && idx!=getHostList().get(i).getId()) {
						System.out.println("index: "+idx+"selected host id: "+getHostList().get(i).getId());
						maxPriority = getHostList().get(i).getPriority();
						idx = i;
//						System.out.println("Index is now "+idx);
						System.out.println("Host: "+i+" host priority: "+getHostList().get(i).getPriority()+" max priority: "+maxPriority);
						System.out.print("Selected Host is "+getHostList().get(i).getId()+" with ");
						System.out.print(" Mips: "+getHostList().get(i).getAvailableMips());
						System.out.print(" PE: "+freePesTmp.get(i));
						System.out.print(" RAM: "+getHostList().get(i).getRamProvisioner().getAvailableRam());
						System.out.println("");
						limitsCheck = false;
					}
				}
				
				System.out.println("index is now: "+idx);
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
    	System.out.println(timeEnd);
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
	
}
