import java.util.Scanner;
import java.io.*;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class ChiSquared{


	// Method to compure ChiSquared given the data, prediction and errors in matrix form, will allow for errors on correlated measurements
	public static Matrix computeChiSquare(Matrix data, Matrix prediction, Matrix errors){

		Matrix diff = data.subtract(prediction);
	
		Matrix Errors = Matrix.indentity(data.getColumns(), data.getColumns());

		// Creates a matrix of the errors
		for(int i = 0; i<data.getColumns(); i ++){
			Errors.setElement(i,i,data.getElement(0,i) * data.getElement(0,i));
		}

		Matrix inverse = Errors.diagonalInvert();

		Matrix difftrans = diff.transpose();

		Matrix chisquared = diff.product(inverse.product(difftrans));

		return chisquared;


	}


	// For the given xvalues this assumes a quadratic relation and makes predictions
	public static Matrix makePredictions(Matrix xvalues, double a, double m, double c){

		Matrix prediction = new Matrix(1, xvalues.getColumns());

		for(int i = 0; i<xvalues.getColumns();i++){

			double p = a * xvalues.getElement(0,i)* xvalues.getElement(0,i) + m * xvalues.getElement(0,i) + c;

			prediction.setElement(0,i, p);
		}

		return prediction;


	}


	public static HashMap<String, Matrix> readFile(String filename) throws FileNotFoundException{

		File text = new File(filename);
 		Scanner reader = new Scanner(text);

 		//Create the hashmap to store the data - might read in the column headings and set as keys in the future
 		HashMap<String, Matrix> data = new HashMap<String,Matrix>();

 		//temperary storage for the data read in from the file
		ArrayList<Double> xvalues = new ArrayList<Double>();
 		ArrayList<Double> yvalues = new ArrayList<Double>();
 		ArrayList<Double> errors = new ArrayList<Double>();


 		reader.nextLine();  //ignore the first line of text which will be column headings
   		while (reader.hasNext()) {      
    		double x = reader.nextDouble();   
    		double y = reader.nextDouble();  
    		double error = reader.nextDouble();  
    		xvalues.add(x);
    		yvalues.add(y);
    		errors.add(error);
		}

		// turn the arraylists into matrices - this makes it easier to calculate chisquared (although is a bit kluge)
		Matrix x = new Matrix(1, xvalues.size());
		Matrix y = new Matrix(1, yvalues.size());
		Matrix E = new Matrix(1, errors.size());
		
		for(int i = 0; i<xvalues.size(); i ++){
			x.setElement(0,i,xvalues.get(i));
			y.setElement(0,i,yvalues.get(i));
			E.setElement(0,i,errors.get(i));
		}


		data.put("xvalues", x);
		data.put("yvalues", y);
		data.put("errors", E);

		//return the data hashmap
		return data;


	}

	// Brute force method for minimising the chisquared - will find a more sophisiticated method later
	public static HashMap<String, Double> minimalChiSquared(HashMap<String, Matrix> data){

		HashMap<String, Double> results = new HashMap<String, Double>();

		double min = 1000;
		double mopt = 0;
		double copt = 0;
		double aopt = 0;

		for(int m = -100; m<100;m++){
			for(int c = -100; c<100; c++){
				for(int a = -100; a<100; a++){
					double intercept = (double) c;
					double gradient = (double) m;
					double quadratic = (double) a;

					Matrix prediction = makePredictions(data.get("xvalues"), gradient, intercept, quadratic);
					Matrix chisq = computeChiSquare(data.get("yvalues"), prediction, data.get("errors"));

					if(chisq.getElement(0,0) < min){
						min = chisq.getElement(0,0);
						mopt = m;
						copt = c;
						aopt = a;
					}
				}
			}
		}

		results.put("minChi", min);
		results.put("minm", mopt);
		results.put("minc", copt);
		results.put("mina", aopt);

		return results;


	}



	public static void main(String[] args) throws FileNotFoundException{

		if(args.length>1){
			System.out.println("Please only enter one file name to read data from!");
		}

		else if (args.length ==0){
			System.out.println("Please enter a filename to read data from!");
		}

		else{
			try{
				HashMap<String, Matrix> data = readFile(args[0]);

				HashMap<String, Double> result = minimalChiSquared(data);

				System.out.println(data.get("xvalues"));

				System.out.println("Minimum ChiSquared: " + result.get("minChi"));
				System.out.println("Fit: " + "y = " + result.get("minm") + "x + " + result.get("minc") );

				File file = new File("output.txt");
			    file.createNewFile();
		        FileWriter writer = new FileWriter(file); 
		        writer.write(String.valueOf(result.get("minm"))+ "\n");
		        writer.write(String.valueOf(result.get("minc"))+ "\n");
		        writer.write(String.valueOf(result.get("mina")));
		        writer.flush();
		        writer.close();
		
			
			}

			catch(Exception e){
				System.out.println("File not found");
			}
			
			}

			
	}

}