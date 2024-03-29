package org.cloudbus.cloudsim.examples;
/*	1 DataCenter
 * 	2 Broker
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

public class Simulation6 {

	private static List<Cloudlet> cloudletList;
	private static List<Vm> vmlist;
	private int brokerId;
	private static UtilizationModel utilizationModel;
	public static void main(String[] args) {
		
		Log.printLine("Starting simulation5..");
//		String name = "Datacenter1";
		try {
			// First step: Initialize the CloudSim package. It should be called
        	// before creating any entities.
			int num_user = 1;
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false;
			
			// Initialize the CloudSim library
			CloudSim.init(num_user, calendar, trace_flag);
			// first parameter is datacenter name and
			// second parameter is number of hosts
			Datacenter dc = createDatacenter("datacenter",14);

	      	DatacenterBroker broker = createBroker("Broker1");
	        
	        int brokerId = broker.getId();
	        
	        vmlist = new ArrayList<Vm>();
	        
        	//VM description
        	int vmid = 0;
        	int mips = 250;
        	long size = 10000; //image size (MB)
        	int ram = 512; //vm memory (MB)
        	int bw = 1000;
        	int pesNumber = 1; //number of cpus
        	String vmm = "Xen"; //VMM name

        	//create 10 VMs
        	vmlist = createVM(10,brokerId);

        	//add the VMs to the vmList

        	//submit vm list to the broker
        	broker.submitVmList(vmlist);
        	
//        	//Fifth step: Create two Cloudlets
        	cloudletList = new ArrayList<Cloudlet>();
        	
        	//Cloudlet properties
//        	int id = 0;
//        	pesNumber=1;
//        	long length = 250000;
//        	long fileSize = 300;
//        	long outputSize = 300;
//        	utilizationModel = new UtilizationModelFull();
//
        	
        	cloudletList= createCloudlet(brokerId,10);

        	//add the cloudlets to the list
        	

        	//submit cloudlet list to the broker
        	broker.submitCloudletList(cloudletList);
        	//bind the cloudlets to the vms. This way, the broker
        	// will submit the bound cloudlets only to the specific VM
//        	broker.bindCloudletToVm(cloudlet1.getCloudletId(),vm1.getId());
        	
        	CloudSim.startSimulation();

        	// Final step: Print results when simulation is over
        	List<Cloudlet> newList1 = broker.getCloudletReceivedList();
        	
        	CloudSim.stopSimulation();

        	printCloudletList(newList1);
        	Log.printLine("----------------------------------------------");
        	Log.printLine("Simulation5 finished!");
	        
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static List<Cloudlet> createCloudlets(int pesNumber, long length, long fileSize, long outputSize,int n){
		Cloudlet cloudlet;
		List<Cloudlet> cl = new ArrayList<Cloudlet>();
		for (int i = 0; i < n; i++) {
			cloudlet = new Cloudlet(i, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			cloudletList.add(cloudlet);
		}
		return cloudletList;
	}
	
	private static List<Vm> createVMs(int mips, long size, int ram, int bw, int pesNumber, String vmm, int brokerId, int n){
		Vm vm; 
		List<Vm> vmlist = new ArrayList<Vm>();
		for (int i = 0; i < n; i++) {
			vm = new Vm(i, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			vmlist.add(vm);
		}
		return vmlist;
		
	}
	private static List<Vm> createVM(int userId, int vms) {

		//Creates a container to store VMs. This list is passed to the broker later
		LinkedList<Vm> list = new LinkedList<Vm>();

		//VM Parameters
		long size = 10000; //image size (MB)
		int ram = 512; //vm memory (MB)
		int mips = 1000;
		long bw = 1000;
		int pesNumber = 1; //number of cpus
		String vmm = "Xen"; //VMM name

		//create VMs
		Vm[] vm = new Vm[vms];

		for(int i=0;i<vms;i++){
			vm[i] = new Vm(i, userId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			//for creating a VM with a space shared scheduling policy for cloudlets:
			//vm[i] = Vm(i, userId, mips, pesNumber, ram, bw, size, priority, vmm, new CloudletSchedulerSpaceShared());

			list.add(vm[i]);
		}

		return list;
	}


	private static List<Cloudlet> createCloudlet(int userId, int cloudlets){
		// Creates a container to store Cloudlets
		LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();

		//cloudlet parameters
		long length = 1000;
		long fileSize = 300;
		long outputSize = 300;
		int pesNumber = 1;
		UtilizationModel utilizationModel = new UtilizationModelFull();

		Cloudlet[] cloudlet = new Cloudlet[cloudlets];

		for(int i=0;i<cloudlets;i++){
			cloudlet[i] = new Cloudlet(i, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			// setting the owner of these Cloudlets
			cloudlet[i].setUserId(userId);
			list.add(cloudlet[i]);
		}

		return list;
	}
	private static Datacenter createDatacenter(String name, int n){

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
        for (int i = hostId; i < n; i++) {
        	
        	hostList.add(
        			new Host(
        				i,
        				new RamProvisionerSimple(ram),
        				new BwProvisionerSimple(bw),
        				storage,
        				peList,
        				new VmSchedulerTimeShared(peList)
        			)
        		); // This is our machine
	
		}
        
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
