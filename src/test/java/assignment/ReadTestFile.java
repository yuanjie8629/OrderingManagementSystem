package assignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadTestFile {
	static Scanner input;
	static String fileName;
	
	public static ArrayList<Item> ReadItemFile() {
		//Read Items File
				fileName = "items.txt";
				ArrayList<Item> itemList = new ArrayList<>();
				try {
					input = new Scanner(new File(fileName));
				} catch (FileNotFoundException e) {
					System.out.println("Error opening the file " + fileName);
					System.exit(0);
				}
				
				while (input.hasNext()) {
					String row = input.nextLine();
					String[] column = row.split("\\|");
					int id = Integer.parseInt(column[0]);
					String name = column[1];
					String type = column[2];
					double memberPrice = Double.parseDouble(column[3]);
					double nonMemberPrice = Double.parseDouble(column[4]);
					boolean promotional = Boolean.parseBoolean(column[5]);
					Item item = new Item(id, name, type, memberPrice, nonMemberPrice, promotional);
					itemList.add(item);
				}
				input.close();
				return itemList;
	}
	
	public static ArrayList<DeliveryRate> ReadDeliveryRateFile(){
		//Read Delivery Rates File
				fileName = "deliveryRates.txt";
				ArrayList<DeliveryRate> deliveryList = new ArrayList<>();
				try {
					input = new Scanner(new File(fileName));
				} catch (FileNotFoundException e) {
					System.out.println("Error opening the file " + fileName);
					System.exit(0);
				}
				while (input.hasNextLine()) {
					String row = input.nextLine();
					String[] column = row.split("\\|");
					String district = column[0];
					String area = column[1];
					double charges = Double.parseDouble(column[2]);
					DeliveryRate delivery = new DeliveryRate(district, area, charges);
					deliveryList.add(delivery);
				}
				input.close();
				return deliveryList;
	}	
	
	public static ArrayList<Member> ReadMemberFile(){
		fileName = "members.txt";
		ArrayList<Member> memberList = new ArrayList<>();
		try {
			input = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("Error opening the file " + fileName);
			System.exit(0);
		}
		while (input.hasNextLine()) {
			String row = input.nextLine();
			String[] column = row.split("\\|");
			String username = column[0];
			String password = column[1];
			String name = column[2];
			String fullAddress = column[3];
			String phoneNum = column[4];
			String[] addressSplit = fullAddress.split(",");
			int unitNum = Integer.parseInt(addressSplit[0]);
			String street = addressSplit[1];
			String area = addressSplit[2];
			int postalCode = Integer.parseInt(addressSplit[3]);
			String district = addressSplit[4];
			String state = addressSplit[5];
			Address address = new Address(state, district, area, postalCode, street, unitNum);
			Member member = new Member(username, password, name, address, phoneNum);
			memberList.add(member);
		}
		input.close();
		return memberList;
	}
}
