
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

 class Fiscas {
	public static void main(String[] args) throws IOException {	
			//If not file is provided then we will have to print error
	 String inputFilePath=args[0];  
	 String ouputFilePath= args[1]; 
	 //Created two different files using the one input file given
	 FileInputStream inputFileStream=new FileInputStream(inputFilePath); 
	 FileInputStream inputFileStream2=new FileInputStream(inputFilePath);
	 //Scanners to look at files
	 Scanner input=new Scanner(inputFileStream);  
	 Scanner input2=new Scanner(inputFileStream2); 
	 //Grabbing labels from label list inputing 
	 ArrayList<String> labels=labelFinder(input);
	 ArrayList<String> hex =fileReader(input2,labels,ouputFilePath);
	 
	if(args.length==3) {
	 String list=args[2];  
	 if(list.contains("-l")) {
	
	 //Exit the method
	  System.out.println("***LABEL LIST***"); 
		for(int j=0;j<labels.size();j++) {
			System.out.println(labels.get(j));
		} 
		System.out.println("***MACHINE CODE***");
		 for(int j=0;j<hex.size();j++) {
			System.out.println(hex.get(j));
		 } 
	   }
	}

	input.close(); 
	input2.close(); 
}

public static ArrayList<String> labelFinder(Scanner input) {
	//String to hold current value
String a=" ";  
//Labels array 
ArrayList<String> labels=new ArrayList<String>();  
//Line counter/Program counter
int programCounter=-1; 
while (input.hasNextLine()) {
  programCounter++; 
  while (input.hasNext()) {
	a=(String)input.next(); 
	 if(a.contains(":")) {
	   a=a.substring(0,a.length()-1);
	   labels.add(a+" "+String.format("%02d",programCounter)); 
	}else if (a.contains(";")) {
			
		  
	}else {	
	  switch (a.toLowerCase()) { 
		case "add": 
			programCounter++; 
			break;
		case "bnz": 
			programCounter++; 
			break; 
		case "not": 
			programCounter++; 
			break;	
		case "and":
			programCounter++; 
			break;
		default :
			break; 
		}
	}
} 
	return labels; 
	}   
	return labels; 
	}

public static ArrayList<String> fileReader(Scanner input,
											ArrayList<String> list,
											String outputFilePath) 
											throws IOException { 
//String to hold current value
 String a = " ";  
 //Array of hex value and Line counter
 ArrayList<String> s= new ArrayList<String>(); 
 //Array that holds each line 
 ArrayList<String> instruction=new ArrayList<String>(); 
 ArrayList<String> machineCode= new ArrayList<String>(); 
 ArrayList<String> hexadecimals= new ArrayList<String>(); 
 //Program counter
 int programCounter=-1; 
 //This is for changing string to int
  int hexadecimal=0;
 //Looping through the input file and gathering lines 
	while (input.hasNextLine()){  
	programCounter++; 
	String inst=""; 
	String hold=""; 
	while(input.hasNext()) {
	   String hex="";
	   a=(String)input.next(); 
	   switch (a.toLowerCase()) { 
		case "add": 
		   hex=hex+"00"; 
		   inst="add"; 
			for(int i=0;i<3;i++) {
				String f=(String)input.next();
				if(i==0) {
					hold=bits(f); 
					inst=inst+" "+f; 
				}else {
					hex=hex+bits(f);
					inst=inst+" "+f;
				}
			}
			hex=hex+hold; 
			instruction.add(inst); 
			//System.out.println(hex); 
			hexadecimal=Integer.valueOf(hex,2); 
			s.add(String.format("%02d",programCounter)+
				":"+Integer.toHexString(hexadecimal));  
			hexadecimals.add(Integer.toHexString(hexadecimal)); 
			programCounter++;
			break;
		case "bnz":
			hex=hex+"11";
			inst="bnz"; 
			String counter=(String)input.next();
			inst=inst+" "+counter; 
			instruction.add(inst);  
			
			for (int i=0;i<list.size();i++) { 
				if(list.get(i).contains(counter)){
					String position=list.get(i).substring(list.get(i).length()-2);
					
					String bin= Integer.toBinaryString(Integer.parseInt(position));
					 
					bin=String.format("%6S", bin).replace(' ','0'); 
					
					hex=hex+bin;
				 
				}
			}
			hexadecimal=Integer.valueOf(hex,2);
			s.add(String.format("%02d",programCounter)+
				":"+Integer.toHexString(hexadecimal));
			hexadecimals.add(Integer.toHexString(hexadecimal)); 
			programCounter++;    
			break; 
		case "not":
			hex=hex+"10"; 
			inst="not"; 
			//op rd rn rm 
			//10
		
				String l=(String)input.next();
				hold=bits(l); 
				inst=inst+" "+l;
				
				l=(String)input.next(); 
				hex=hex+bits(l);
				inst=inst+" "+l;
				hex+="00";
					
			hex= hex+hold;
			instruction.add(inst);
			hexadecimal=Integer.valueOf(hex,2); 
			s.add(String.format("%02d",programCounter)
					+":"+String.format("%02X",hexadecimal));
			hexadecimals.add(Integer.toHexString(hexadecimal)); 
			programCounter++; 
			break;	
		case "and":
			hex=hex+"01"; 
			inst="and";  
			for(int i=0;i<3;i++) {
				String x=(String)input.next();
					if(i==0) {
						hold=bits(x); 
						inst=inst+" "+x; 
					}else {
						hex=hex+bits(x);
						inst=inst+" "+x;
					}
				}
			hex=hex+hold; 
			hexadecimal=Integer.valueOf(hex,2); 
			s.add(String.format("%02d",programCounter)+
					":"+Integer.toHexString(hexadecimal));
			instruction.add(inst); 
			hexadecimals.add(Integer.toHexString(hexadecimal)); 
			programCounter++; 	
			break;
		default :
			break; 
		}	
	}
break; 
}
for(int j=0;j<s.size();j++) {

	machineCode.add(s.get(j)+" "+instruction.get(j)); 
	} 
	ouputFile(hexadecimals,outputFilePath); 
	return machineCode; 
		
	}

public static String bits(String f) {
	String r0= "00"; 
String r1= "01"; 
String r2= "10";
String r3= "11"; 
if(f.toLowerCase().contains("r0")) {
	f=r0; 
	return f;  
}else if(f.toLowerCase().contains("r1")) {
	f=r1; 
	return f; 
}else if(f.toLowerCase().contains("r2")) {
	f=r2;  
	return f; 
}else if(f.toLowerCase().contains("r3")) {
	f=r3; 
	return f; 
}else {
	System.out.println("Error not correct input!!"); 
	}
	return null; 
}
public static void ouputFile(ArrayList<String> s,String o) throws IOException{
	Path path=Paths.get(o); 
	if(Files.exists(path)) {
		FileWriter writer=new FileWriter(o); 
		writer.write("v2.0 raw"+System.lineSeparator());
	for(String str:s) {
		writer.write(str+System.lineSeparator());
	}
	writer.close(); 
}else { 
	File ouputFile=new File(o); 
	ouputFile.createNewFile();
	FileWriter writer=new FileWriter(o); 
	writer.write("v2.0 raw"+System.lineSeparator());
			for(String str:s) {
				writer.write(str+System.lineSeparator());
			}
			writer.close(); 
			
		}
			
	}
 }


