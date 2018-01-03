package dataAccess;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EquipmentDA.initDA();
		System.out.println(EquipmentDA.rentEquipment("tennis"));
	}

}
