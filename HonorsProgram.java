package test;
import java.io.BufferedReader;  
import java.io.FileReader; 
import java.io.FileWriter;  
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Jules1 {
	public static File testPath;
	public static ArrayList<String> firstHonors = new ArrayList<String>();
	public static ArrayList<String> secondHonors = new ArrayList<String>();
	public static ArrayList<String> honorableMention = new ArrayList<String>();
	public static ArrayList<String> noHonors = new ArrayList<String>();
	public static ArrayList<String> gradesArray = new ArrayList<String>();
	public static ArrayList<String> creditArray = new ArrayList<String>();
	public static ArrayList<String> errorList = new ArrayList<String>();
	public Jules1() {
		
	}
	
	public static void printHonors(ArrayList<String> al) {
		for(int i=0; i<al.size(); i=i+1) {
			System.out.println(al.get(i));
		}
	}
	public static void assignFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("C://Users"));
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	            "csv files", "csv");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	        Jules1.testPath = chooser.getSelectedFile();
	    }
	    }
	
	public static double findAverage(int [] s, double [] c, String currentName) {
		double scoreSum = 0.0;
		double numElements = 0.0;
		for (int i =0;i < s.length;i= i+1) {
			if (s[i]>0) {
				scoreSum = (s[i]*c[i])+scoreSum;
				numElements=numElements+c[i];		
		}else {
			errorList.add(currentName);
		}
	}return
			scoreSum/numElements;
	}
	public static boolean isEOF(BufferedReader br)  
	{
	     boolean result = false;

	     try 
	     {
	         result = br.ready();
	     } 
	     catch (IOException e)
	     {
	         System.err.println(e);
	     }
	     return result;
	}
	public static void printHonors() {
		System.out.println("first honors: ");
		printHonors(firstHonors);
		System.out.println();
		System.out.println("second honors: ");
		printHonors(secondHonors);
		System.out.println();
		System.out.println("honorable mention: ");
		printHonors(honorableMention);
		System.out.println();
		System.out.println("no honors: ");
		printHonors(noHonors);
		System.out.println("error list: ");
		printHonors(errorList);

	}
	public static void checkErrors () {
		//removes names from honor list if they have empty cells
		for(int i=0; i<errorList.size(); i=i+1) {
				firstHonors.remove(errorList.get(i));
				secondHonors.remove(errorList.get(i));
				honorableMention.remove(errorList.get(i));
				noHonors.remove(errorList.get(i));
			}

		}
		
	public static String assignHonors(int [] integerGrades, String currentFirstName, String currentLastName, int counter, double [] doubleCredits) {
		String results = "";
		String currentName = currentFirstName +" "+currentLastName;
		double average = findAverage((Arrays.copyOfRange(integerGrades, 0, counter)),doubleCredits, currentName);
		int gradesUnder95= gradesBelow((Arrays.copyOfRange(integerGrades, 0, counter)),95);
		int gradesUnder90= gradesBelow((Arrays.copyOfRange(integerGrades, 0, counter)),90);
		int gradesUnder85= gradesBelow((Arrays.copyOfRange(integerGrades, 0, counter)),85);
	
		if((gradesUnder95)<2 &&(average>=95)) {//add method here to return type of honors
			results = currentName+" first honors "+average;
			firstHonors.add(currentName);
		}
		else if((gradesUnder90)<2 &&(average>=90)) {
			results = (currentName+" second honors "+average);
			secondHonors.add(currentName);
		}else if((gradesUnder85)<2 &&(average>=85)) {
			results = (currentName+" honorable mention "+average);
			honorableMention.add(currentName);
		}else {
		
			results = (currentName+" no honors "+average);
			noHonors.add(currentName);
			}
		System.out.println (results+" "+gradesUnder95+ " "+gradesUnder90 + " "+gradesUnder85);
		
		return results;
		
	}
	
	public static int gradesBelow(int [] s, int restraint) {
		int counter=0;
		for(int i=0; i<s.length; i=i+1) {
			if(s[i]  !=0 && s[i]<restraint) {
				counter=counter+1;
			}
		}return counter;
	}
	
	public static void main(String[] args)  {
		String currentFirstName = "";
		String currentLastName = "";
		boolean firstTime = true;
		assignFile();
		String line = "";   
		try   {  
		//parsing a CSV file into BufferedReader class constructor 
		// choose testGradesReal.csv
		BufferedReader br = new BufferedReader(new FileReader(Jules1.testPath));  
		while ((line = br.readLine()) != null)   //returns a Boolean value  
		{  
			String[] grade = line.split(",");  
			//sets current name equal to the first name in the file (bc no name comes before)
			if (firstTime) {
				currentFirstName = grade[0];
				currentLastName = grade[1];
				firstTime = false;
			
			}
			if(grade[0].equals(currentFirstName) && grade[1].equals(currentLastName) && isEOF(br)){ 
				gradesArray.add(grade[4]); //adding grade
				creditArray.add(grade[5]); //adding credit value
			}else  { //add method here to return integerGrades
				if (!isEOF(br)) { //last person
					gradesArray.add(grade[4]);
					creditArray.add(grade[5]);
				}
				int counter=0;
				int[] integerGrades = new int [14]; 
				double[] doubleCredits = new double [14]; 
				for(int i=0;i<gradesArray.size();i++) {
					//sets i's and f's equal to 60
					if(gradesArray.get(i).toLowerCase().equals("i")||gradesArray.get(i).toLowerCase().equals("f")) {
						gradesArray.set(i,"60");
					}else if(gradesArray.get(i).length()==0) {
						gradesArray.set(i, "0");
						System.out.println(grade[0]+" "+grade[1]+" error");
					}
					if(Character.isDigit(gradesArray.get(i).charAt(0))) {
						//switches from strings to int/double
						integerGrades[counter] = Integer.parseInt(gradesArray.get(i) );
						doubleCredits[counter] = Double.parseDouble(creditArray.get(i) );
						counter=counter+1;
				}}
				if (gradesArray.size() !=0) {
					//System.out.println(assignHonors(integerGrades,currentFirstName,currentLastName,counter,doubleCredits));
					assignHonors(integerGrades,currentFirstName,currentLastName,counter,doubleCredits);
					//resets grades and credits to a new name
					gradesArray.clear();
					creditArray.clear();
					currentFirstName=grade[0];
					currentLastName = grade[1];
					//adds grades and credits to the new name
					gradesArray.add(grade[4]);
					creditArray.add(grade[5]);
			}//System.out.println(integerGrades[0]+" " + integerGrades[1]+" " + integerGrades[2]+" " +integerGrades[3]+" " +integerGrades[4]+" " +integerGrades[5]+" "+integerGrades[6]+" "+integerGrades[7]+" "+integerGrades[8]+" "); 
			//System.out.println(doubleCredits[0]+" " + doubleCredits[1]+" " + doubleCredits[2]+" " +doubleCredits[3]+" " +doubleCredits[4]+" " +doubleCredits[5]+" "+doubleCredits[6]+" "+doubleCredits[7]+" "+doubleCredits[8]+" "); 
			  
			}
		}
		
	}
		catch (IOException e)   
		{  
			e.printStackTrace();
			
		}printHonors();
		checkErrors();
		printHonors();
	
			      
	
}
}