import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.math.BigInteger;


class Fibonnaci{

	// Array to store the fibonnaci numbers for the recursuive storage method
	private static HashMap<Long, BigInteger> fibstore = new HashMap<Long,BigInteger>();


	public static void main(String[] args) throws FileNotFoundException{

		long average1 = 0;
		long average2 = 0;
		long average3 = 0;
		long average4 = 0;

		int iterations  = 5;
		
		// Write the time results to file, this is for the smaller range of N
		try{
			// Write the results to file for plotting
			File file = new File("SmallN.txt");
			file.createNewFile();
			FileWriter writer = new FileWriter(file); 
			

			// The recrusive method without storing gets horribly slow for larger values of n
			for(int num = 0; num < 40; num ++ ){
				
				// Average over several runs
				for(int i = 0; i< iterations; i++){
	
					long startTime = System.nanoTime(); 
					BigInteger result = genFib(num); 
					average1 += System.nanoTime() - startTime; 

					startTime = System.nanoTime();
					result = RecFib(num);
					average2 += System.nanoTime() - startTime; 
			

					startTime = System.nanoTime();
					result = RecFibStore(num);
					average3 += System.nanoTime() - startTime; 


					startTime = System.nanoTime();
					result = FibMatrix(num);
					average4 += System.nanoTime() - startTime;

				}

				writer.write(String.valueOf(average1/iterations)+ " ");
		        writer.write(String.valueOf(average2/iterations)+ " ");
		        writer.write(String.valueOf(average3/iterations)+ " ");
		        writer.write(String.valueOf(average4/iterations)+ "\n" );


			}	


			    writer.flush();
		        writer.close();
    
    	}

	    catch(Exception e){
			System.out.println("File not found");
		}

			 // Run again for larger n behaviour non include the non storing recursive method
			try{
				File file = new File("LargeN.txt");
				file.createNewFile();
				FileWriter writer = new FileWriter(file); 
				
				// reinitialise these variables
				average1 = 0;
				average2 = 0;
				average3 = 0;

			// Above n=92 long overflows - so we use BigInteger
			for(int num = 0; num <= 10000; num ++){

				// Average over several runs
				for(int i = 0; i< iterations; i++){

					long startTime = System.nanoTime(); 
					BigInteger result = genFib(num); 
					average1 += System.nanoTime() - startTime; 

					startTime = System.nanoTime();
					result = RecFibStore(num);
					average2 += System.nanoTime() - startTime; 

					startTime = System.nanoTime();
					result = FibMatrix(num);
					average3 += System.nanoTime() - startTime;

			     
				}
					writer.write(String.valueOf(average1/iterations)+ " ");
			        writer.write(String.valueOf(average2/iterations) + " ");
			        writer.write(String.valueOf(average3/iterations)+ "\n" );

			}
					writer.flush();
			        writer.close();
			  

	    	}    


	    catch(Exception e){
			System.out.println("File not found");
		}



		
	}


	// non recursive method for generating Fibonnaci series
	private static BigInteger genFib(long num){

		BigInteger a = BigInteger.ZERO;
		BigInteger b = BigInteger.ONE;
		BigInteger c = BigInteger.ZERO;

		for(long i = 0; i<num; i++){
			c = a.add(b);
			a = b;
			b = c;
			
		}

		return a;

	}


	// Generates a series of Fibonnaci numbers up to the one in consideration
	private static BigInteger RecFib(long num){

		if(num == 0){
			return BigInteger.ZERO;
		}
		else if(num <= 2){
			return BigInteger.ONE;
		}

		else{
			return RecFib(num-1).add(RecFib(num-2));
		}

	}

	
	// Generates a series of Fibonnaci numbers up to the one in consideration and stores intermediate results
	private static BigInteger RecFibStore(long num){

		if(num == 0){
			return BigInteger.ZERO;
		}
		else if(num <= 2){
			return BigInteger.ONE;
		}

		if(fibstore.get(num) != null){
			return fibstore.get(num);
		}

		else{
			BigInteger ans = RecFibStore(num-1).add(RecFibStore(num-2));
			fibstore.put(num,ans);
			return ans;
		}

		}


	// Quick method to multiply two 2x2 matrices assuming correct dimension	
	private static BigInteger[][] multiply(BigInteger[][] one, BigInteger[][] two){

		BigInteger[][] result = new BigInteger[2][2];

		result[0][0] = (one[0][0].multiply(two[0][0])).add(one[0][1].multiply(two[1][0]));
		result[0][1] = (one[0][0].multiply(two[0][1])).add(one[0][1].multiply(two[1][1]));

		result[1][0] = (one[1][0].multiply(two[0][0])).add(one[1][1].multiply(two[1][0]));
		result[1][1] = (one[1][0].multiply(two[0][1])).add(one[1][1].multiply(two[1][1])); 

		return result;

	}

	// display method for matrices for debugging
	private static void showMatrix(BigInteger[][] test){

		for(int i = 0; i<test.length; i++){
			for(int j = 0; j< test[0].length; j++){
				System.out.print(test[i][j] + " ");
			}
			System.out.println();
		}

	}

	// matrix multiplication method to return fibonnaci number
	private static BigInteger FibMatrix(long num){

		// matrix which generates the fib series
		BigInteger[][] fibmatrix = new BigInteger[2][2];
		fibmatrix[0][0] = BigInteger.ZERO;
		fibmatrix[0][1] = BigInteger.ONE;
		fibmatrix[1][0] = BigInteger.ONE;
		fibmatrix[1][1] = BigInteger.ONE; 

		BigInteger[][] old = new BigInteger[2][2];
		old[0][0] = BigInteger.ZERO;
		old[0][1] = BigInteger.ONE;
		old[1][0] = BigInteger.ONE;
		old[1][1] = BigInteger.ONE;  


		// Define first two fib numbers
		if(num == 0){
			return BigInteger.ZERO;
		}
		else if(num == 1){
			return BigInteger.ONE;
		}

		// multiply (num-1)/2 times
		for(long i = 1; i<num/2; i++){
			fibmatrix = multiply(fibmatrix, old);	
		}

		//multiply these matrices together to get num-1
		fibmatrix = multiply(fibmatrix, fibmatrix);

		// if the power was odd we need one more matrix multiplication
		if(num%2 !=0){
			fibmatrix = multiply(fibmatrix,old);
		}

		return fibmatrix[0][1];

	}






}	






















