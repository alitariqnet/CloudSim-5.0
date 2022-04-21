package org.cloudbus.cloudsim.examples;
/*	1 DataCenter
 * 	1 Broker
 * 	2 Host
 * 	2 VMs
 * 	2 Cloudlets
 * */
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

public class Simulation2 {

	private static List<Cloudlet> cloudletList;
	private static List<Vm> vmlist;
	private static List<Cloudlet> cloudletList1;
	private static List<Vm> vmlist1;
	
	public static void main(String[] args) {
		/*	1 DataCenter
		 * 	1 Broker
		 * 	2 Host
		 * 	2 VMs
		 * 	2 Cloudlets
		 * */
		
		Log.printLine("Starting simulation2..");

		try {
			// First step: Initialize the CloudSim package. It should be called
        	// before creating any entities.
			int num_user = 1;
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false;
			
			// Initialize the CloudSim library
			CloudSim.init(num_user, calendar, trace_flag);
			
			Datacenter dc1 = createDatacenter("datacenter");

	      	DatacenterBroker broker = createBroker("Broker");
	        
	        int broker1Id = broker.getId();
	        
	        vmlist = new ArrayList<Vm>();

        	//VM description
        	int vmid = 0;
        	int mips = 250;
        	long size = 10000; //image size (MB)
        	int ram = 512; //vm memory (MB)
        	int bw = 1000;
        	int pesNumber = 1; //number of cpus
        	String vmm = "Xen"; //VMM name

        	//create two VMs
        	Vm vm1 = new Vm(vmid, broker1Id, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());

        	vmid++;
        	Vm vm2 = new Vm(vmid, broker1Id, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());

        	//add the VMs to the vmList
        	vmlist.add(vm1);
        	vmlist.add(vm2);

        	//submit vm list to the broker
        	broker.submitVmList(vmlist);
        	
        	vmid++;
        	Vm vm3 = new Vm(vmid, broker1Id, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());

        	vmid++;
        	Vm vm4 = new Vm(vmid, broker1Id, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
        	vmlist1 = new ArrayList<Vm>();
        	vmlist1.add(vm3);
        	vmlist1.add(vm4);
        	
//        	//Fifth step: Create two Cloudlets
        	cloudletList = new ArrayList<Cloudlet>();

        	//Cloudlet properties
        	int id = 0;
        	pesNumber=1;
        	long length = 250000;
        	long fileSize = 300;
        	long outputSize = 300;
        	UtilizationModel utilizationModel = new UtilizationModelFull();

        	Cloudlet cloudlet1 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
        	cloudlet1.setUserId(broker1Id);

        	id++;
        	Cloudlet cloudlet2 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
        	cloudlet2.setUserId(broker1Id);

        	//add the cloudlets to the list
        	cloudletList.add(cloudlet1);
        	cloudletList.add(cloudlet2);

        	//submit cloudlet list to the broker
        	broker.submitCloudletList(cloudletList);


        	//bind the cloudlets to the vms. This way, the broker
        	// will submit the bound cloudlets only to the specific VM
        	broker.bindCloudletToVm(cloudlet1.getCloudletId(),vm1.getId());
        	broker.bindCloudletToVm(cloudlet2.getCloudletId(),vm2.getId());
        	
        	CloudSim.startSimulation();
        	
        	System.out.println("cloudletList.get(id).getActualCPUTime() "+cloudletList.get(id).getActualCPUTime());
        	
        	// Final step: Print results when simulation is over
        	List<Cloudlet> newList1 = broker.getCloudletReceivedList();
        	
        	CloudSim.stopSimulation();

        	printCloudletList(newList1);
        	
        	Log.printLine("----------------------------------------------");
        	Log.printLine("Simulation2 finished!");
	        
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static Datacenter createDatacenter(String name){

        // Here are the steps needed to create a PowerDatacenter:
        // 1. We need to create a list to store
    	//    our machine
    	List<Host> hostList = new ArrayList<Host>();

        // 2. A Machine contains one or more PEs or CPUs/Cores.
    	// In this example, it will have only one core.
    	List<Pe> peList = new ArrayList<Pe>();

    	int mips = 1000;

        // 3. Create PEs and add these into a list.
    	peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating

        //4. Create Host with its id and list of PEs and add them to the list of machines
        int hostId=0;
        int ram = 2048; //host memory (MB)
        long storage = 1000000; //host storage
        int bw = 10000;

        hostList.add(
    			new Host(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList,
    				new VmSchedulerTimeShared(peList)
    			)
    		); // This is our machine
//        hostId++;
//        hostList.add(
//    			new Host(
//    				hostId,
//    				new RamProvisionerSimple(ram),
//    				new BwProvisionerSimple(bw),
//    				storage,
//    				peList,
//    				new VmSchedulerTimeShared(peList)
//    			)
//    		); // This is our machine

        // 5. Create a DatacenterCharacteristics object that stores the
        //    properties of a data center: architecture, OS, list of
        //    Machines, allocation policy: time- or space-shared, time zone
        //    and its price (G$/Pe time unit).
        String arch = "x86";      // system architecture
        String os = "Linux";          // operating system
        String vmm = "Xen";
        double time_zone = 10.0;         // time zone this resource located
        double cost = 3.0;              // the cost of using processing in this resource
        double costPerMem = 0.05;		// the cost of using memory in this resource
        double costPerStorage = 0.001;	// the cost of using storage in this resource
        double costPerBw = 0.0;			// the cost of using bw in this resource
        LinkedList<Storage> storageList = new LinkedList<Storage>();	//we are not adding SAN devices by now

        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);


        // 6. Finally, we need to create a PowerDatacenter object.
        Datacenter datacenter = null;
        try {
            datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datacenter;
    }
	
	  //We strongly encourage users to develop their own broker policies, to submit vms and cloudlets according
    //to the specific rules of the simulated scenario
    private static DatacenterBroker createBroker(String name){

    	DatacenterBroker broker = null;
        try {
		broker = new DatacenterBroker(name);
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
    	return broker;
    }
    
	  private static void printCloudletList(List<Cloudlet> list) {
	        int size = list.size();
	        Cloudlet cloudlet;

	        String indent = "    ";
	        Log.printLine();
	        Log.printLine("========== OUTPUT ==========");
	        Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
	                "Data center ID" + indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

	        DecimalFormat dft = new DecimalFormat("###.##");
	        for (int i = 0; i < size; i++) {
	            cloudlet = list.get(i);
	            Log.print(indent + cloudlet.getCloudletId() + indent + indent);

	            if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
	                Log.print("SUCCESS");

	            	Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
	                     indent + indent + dft.format(cloudlet.getActualCPUTime()) + indent + indent + dft.format(cloudlet.getExecStartTime())+
                           indent + indent + dft.format(cloudlet.getFinishTime()));
	            }
	        }

	    }
}
