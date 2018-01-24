package dataAccess;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import entity.FacilitiesEntity;

public class FacilitiesDA {
	private static DB db;
	private static ConcurrentMap<LocalDateTime, FacilitiesEntity[]> facilities;
	public final static String[] facil = new String[] { "Badminton Court", "Indoor Basketball Court", "Outdoor Basketball Court", "Hockey Pitch", "Soccer Field", "Squash Court", "Tennis Court" };
	private final static FacilitiesEntity[] list = {new FacilitiesEntity("Badminton Court", 4), new FacilitiesEntity("Cage Field", 2), new FacilitiesEntity("Indoor Basketball Court", 2),
	                           new FacilitiesEntity("Outdoor Basketball Court", 4), new FacilitiesEntity("Soccer Field", 2), new FacilitiesEntity("Squash Court", 4),
	                           new FacilitiesEntity("Tennis Court", 4), new FacilitiesEntity("Hockey Pitch", 1)};
	
	public static void initDA() {
		db = DBMaker.newFileDB(new File("tmp/facilities.db")).closeOnJvmShutdown().make();

		facilities = db.getTreeMap("facilities");
		
		db.commit();
	}
	
	public static void facilitiesSetUp(LocalDateTime ld) {
		initDA();
		facilities.putIfAbsent(ld, list);
		db.commit();
	}
	
	public static int bookFacility(LocalDateTime selectedDateTime, String facility) { //book facility on selected date and time
		initDA();
		FacilitiesEntity[] correspondingList = null;
		FacilitiesEntity[] newCorrespondingList = new FacilitiesEntity[list.length];
		int positionInList = -1;
		int currentInventory = -1;
		int newInventory = -1;
		FacilitiesEntity updatedEntity = null;
		
		if (facilities.containsKey(selectedDateTime)) {
			
			correspondingList = facilities.get(selectedDateTime);
			
			System.out.println("List should be retrieved.");
			for (int i = 0; i < correspondingList.length; i++) {
				if (correspondingList[i].getFacilityName().equals(facility)) {
					positionInList = i;
					currentInventory = correspondingList[i].getFacilityQty();
				} 
			}
			
			if (currentInventory > 0) {
				newInventory = currentInventory - 1;
				updatedEntity = new FacilitiesEntity(facility, newInventory);
				
				newCorrespondingList[positionInList] = updatedEntity;
				
				for (int i = 0; i < correspondingList.length; i++) {
					if (i != positionInList) {
						newCorrespondingList[i] = correspondingList[i];
					}
				}
				
				System.out.println("Position in facilities list: " + positionInList + "\n" + "Updated inventory before borrowing for " + selectedDateTime + ": " + currentInventory);
				
				facilities.replace(selectedDateTime, newCorrespondingList);
				db.commit();
				
			} else {
				System.out.println(facility + " is fully booked.");
				return 0;
			}
			
		} else {
			
			System.out.println("List should be created!");
			
			newCorrespondingList = list;
			
			System.out.println("New facilities list created for this date and time: " + selectedDateTime.toLocalDate() + " " + selectedDateTime.toLocalTime());
			
			for (FacilitiesEntity x : newCorrespondingList) {
				System.out.print(x + " ");
			}
			
			System.out.println();
			System.out.println("Searching for... " + facility);
			
			for (int i = 0; i < list.length; i++) {
				
				if (list[i].getFacilityName().equals(facility)) {
					positionInList = i;
					currentInventory = newCorrespondingList[i].getFacilityQty();
					System.out.println("FOUND " + newCorrespondingList[i].getFacilityName() + " @ POSITION " + positionInList + " in list");
					System.out.println("Current inventory of facility found: " + currentInventory);
				}
			}
			
			if (currentInventory > 0) {
				newInventory = currentInventory - 1;
			}
		
			System.out.println("Updated inventory of facility found: " + newInventory);
			
			updatedEntity = new FacilitiesEntity(facility, newInventory);
			
			newCorrespondingList[positionInList] = updatedEntity;
			
			facilities.put(selectedDateTime, newCorrespondingList);
			db.commit();
			
		}
		
		return 2;
		
	}
	
	public static boolean facilityIsAvailable(LocalDateTime selectedDateTime, String facility) {
		initDA();
		if (facilities.containsKey(selectedDateTime)) {
			FacilitiesEntity[] correspondingList = facilities.get(selectedDateTime);
			for (int i = 0; i < correspondingList.length; i++) {
				if (correspondingList[i].getFacilityName().equals(facility) && correspondingList[i].getFacilityQty() > 0) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			return true;
		}
		return false;
	}
	

	public static Object[][] getAllData() {
		Object[][] data = new Object[facilities.size()][2];

		int i = 0;

		for (LocalDateTime x : facilities.keySet()) {
			data[i][0] = x;
			data[i][1] = facilities.get(x);

			i++;
		}

		return data;
	}

	public static void main(String[] args) {
		initDA();
		
//		bookFacility(LocalDateTime.of(LocalDate.of(2018, 4, 1), LocalTime.of(16, 0)), "Indoor Basketball Court");
		
		for (int i = 0; i < getAllData().length; i++) {
			System.out.println("\n" + getAllData()[i][0] + ": ");
			FacilitiesEntity[] list = (FacilitiesEntity[]) getAllData()[i][1];
			for (FacilitiesEntity x : list) {
				System.out.print(x + " ");
			}
		}

	}
}
