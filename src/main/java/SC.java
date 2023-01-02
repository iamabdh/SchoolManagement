import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.xml.validation.Validator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class SC {
	public final static String DATA_FILE_STUDENT = "student/data.json";
	public static HashMap<Integer, ArrayList<String>> studentData = new HashMap<Integer, ArrayList<String>>();
	public static Integer ID_STUDENT_COUNT = 0;
	public static Gson gson = new Gson();
	public static void main(String[] args) {
		// convert json string  into hashmap
		Scanner inputScanner = new Scanner(System.in);
		studentData  = gson.fromJson(LoadFileData(), new TypeToken<HashMap<Integer, ArrayList<String>>>(){}.getType());
	
		if (studentData != null) {
			for (Integer icountInteger : studentData.keySet()) {
				ID_STUDENT_COUNT = icountInteger;
			}
		} else {
			studentData = new HashMap<Integer, ArrayList<String>>();
		}
		char exit = ' ';
		while(exit != 'e') {
			Menu();
			System.out.println("\n\nto continue press any key, to exit press e");
			exit = inputScanner.next().charAt(0);
		}
		
		
	}
	public static void display(String[] messages) {
		System.out.println("\n\n***************************************");
		for (String msg: messages) {
			System.out.println(msg);
		}
		System.out.println("***************************************\n\n");
	}
	public static String LoadFileData() {
		File dataFile = new File(SC.DATA_FILE_STUDENT);
		String dataStudentJsonString = new String();

		if (dataFile.exists()) {
			System.out.println("File Found");
			Scanner filScanner;
			try {
				filScanner = new Scanner(dataFile);
				while (filScanner.hasNextLine()) {
					dataStudentJsonString += filScanner.nextLine();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("File not Found");
			try {
				System.out.println("creating new File: " + SC.DATA_FILE_STUDENT);
				FileWriter newFileWriter = new FileWriter( SC.DATA_FILE_STUDENT);
				newFileWriter.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		return dataStudentJsonString;
	}
	
	public static void Menu() {
		Scanner inputScanner = new Scanner(System.in);
		System.out.println("\n\nSelect Option Number: \n");
		System.out.println("\n1) Register new Students \n");
		System.out.println("\n2) Search an existing student \n");
		try {
			int option = inputScanner.nextInt();
			if (option == 1) {
				NewStudents();
			} else if (option == 2) {
				SearchStudents();
			} else {
				System.out.println("Wrong option");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Wrong option");
		}
		
		
	}
	
	
	
	
	
	public static void NewStudents() {
		Scanner inputScanner = new Scanner(System.in);
		System.out.println("Enter new Stundet: ");
		System.out.println("*****************************");
		System.out.println("Student Name: ");
		String nameString = inputScanner.nextLine();
		System.out.println("Student Email: ");	
		String emailString = inputScanner.nextLine();
		SaveNewStudents(nameString, emailString);
		SaveFile();
	}
	

	public static void SaveNewStudents(String name, String email) {
		ArrayList<String> studentNameEmaiList = new ArrayList<String>();
		studentNameEmaiList.add(name);
		studentNameEmaiList.add(email);
		studentData.put(++ID_STUDENT_COUNT, studentNameEmaiList);
	}
	
	public static void SaveFile() {
		// covert hashmap to json
		String jsonString = gson.toJson(studentData);

		try {
			FileWriter newFileWriter = new FileWriter( SC.DATA_FILE_STUDENT);
			newFileWriter.write(jsonString);
			newFileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void SearchStudents() {
		Scanner inputScanner = new Scanner(System.in);
		String studentNameFoundString = "\nStudent Not Found\n";
		System.out.println("\nEnter an option: \n");
		System.out.println("*****************************");
		System.out.println("\n1) Search by name: ");
		System.out.println("\n2) Search by id: ");
		Integer option = inputScanner.nextInt();
		if (option == 1) {
			System.out.println("\n\nSearch by name: \n");
			System.out.println("*****************************");
			System.out.println("\nStudent Name: ");
			String name = inputScanner.next();
			
			for (Integer idsInteger : studentData.keySet()) {
				if (studentData.get(idsInteger).get(0).equals(name)) {
					studentNameFoundString =  "\nstudent info: \n" +
							"\nstudent id: " +
							idsInteger.toString() +
							"\nstudent name: " +
							studentData.get(idsInteger).get(0).toString() +
							"\nstudent email: " +
							studentData.get(idsInteger).get(1).toString();
				}
			}
		} else {
			System.out.println("\n\nSearch by ID: \n");
			System.out.println("*****************************");
			System.out.println("\nStudent ID: ");
			Integer id = inputScanner.nextInt();
			if (studentData.get(id) != null)  {
				studentNameFoundString =  "\nstudent info: \n" +
						"\nstudent id: " +
						id.toString() +
						"\nstudent name: " +
						studentData.get(id).get(0).toString() +
						"\nstudent email: " +
						studentData.get(id).get(1).toString();
			}
		}

		display(new String[] {studentNameFoundString});
	}

}
