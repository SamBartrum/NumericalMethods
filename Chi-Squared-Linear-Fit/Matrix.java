// Matrix class with various methods

class Matrix{

	// private immutable instance variables
	private final int rows;
	private final int columns;
	private final double[][] matrix;


	// Constructor, creates zero valued matrix
	public Matrix(int rows, int columns){
		this.rows = rows;
		this.columns = columns;
		this.matrix = new double[rows][columns];

	}

	// Constructor to populate matrix with inital values
	public Matrix(double[][] data){
		this.rows = data.length;
		this.columns = data[0].length;
		this.matrix = new double[this.rows][this.columns];
		for(int i = 0; i< this.rows; i++){
			for(int j = 0; j < this.columns; j++){
				this.matrix[i][j] = data[i][j];
			}
		}

	}



	public int getRows(){
		return this.rows;

	}

	public int getColumns(){
		return this.columns;
	}




	// setter method
	public void setElement(int row, int column, double value){
		this.matrix[row][column] = value;

	}

	// getter method 
	public double getElement(int row, int column){
		return this.matrix[row][column];
	}

	// static method returning a random matrix
	public static Matrix random(int rows, int columns, int range){
		Matrix A = new Matrix(rows, columns);
		for(int i = 0; i<A.rows; i++){
			for(int j = 0; j<A.columns; j++){

				A.matrix[i][j] = Math.random()*range;
			}
		}
		return A;
	}

	// static method returning an indentiy matrix
	public static Matrix indentity(int rows, int columns){
		Matrix A = new Matrix(rows, columns);
		for(int i = 0; i<rows; i++){
			A.matrix[i][i] = 1;
		}
		return A;
	}



	// Adds two matrices together
	public Matrix add(Matrix B){
		Matrix A = this;

		if( B.rows != A.rows || B.columns != A.columns){
			throw new RuntimeException("Incompatible matrix dimensions.");
		}
		else{
			Matrix C = new Matrix(A.rows,A.columns);
			for(int i = 0; i<A.rows; i++){
				for(int j = 0; j< A.columns; j++){
					C.matrix[i][j] = B.matrix[i][j] + A.matrix[i][j];
				}
			}
		
		return C;
		}
	}

	// subtracts two matrices
	public Matrix subtract(Matrix B){
		Matrix A = this;

		if( B.rows != A.rows || B.columns != A.columns){
			throw new RuntimeException("Incompatible matrix dimensions.");
		}
		else{
			Matrix C = new Matrix(A.rows,A.columns);
			for(int i = 0; i<A.rows; i++){
				for(int j = 0; j< A.columns; j++){
					C.matrix[i][j] = B.matrix[i][j] - A.matrix[i][j];
				}
			}
		
		return C;
		}
	}


	// Returns a matrix which is the product of two other matrices
	public Matrix product(Matrix B){
		Matrix A = this;

		if( A.columns != B.rows){
			throw new RuntimeException("Incompatible matrix dimensions.");
		}
		else{
			Matrix C = new Matrix(A.rows, B.columns);
			for(int i = 0; i < C.rows; i++){
				for(int j = 0; j < C.columns; j++){
					for(int k = 0; k < B.rows; k++){
						C.matrix[i][j] += A.matrix[i][k] * B.matrix[k][j]; 
					}
				}
			}
		
		return C;	
		}

	}

	// Returns the transpose of a matrix
	public Matrix transpose(){
		Matrix A = new Matrix(this.columns, this.rows);
		for(int i = 0; i< this.rows; i++){
			for(int j = 0; j<this.columns; j++){
				A.matrix[j][i] = this.matrix[i][j];
			}
		}
		return A;

	}



	// Compares two matrices returning true if the elements are identical
	public boolean compare(Matrix B){
		Matrix A = this;
		if(A.rows != B.rows || A.columns != B.columns){
			throw new RuntimeException("Incompatible matrix dimensions.");
		}
		else{
			for(int i = 0; i< A.rows; i++){
				for(int j = 0; j< A.columns; j++){
					if(A.matrix[i][j] != B.matrix[i][j]){
						return false;
					}
				}
			}
		}
		return true;

	}


	// Simple display method
	public void show(){
		for(int i = 0; i < this.rows; i++){
			for(int j = 0; j< this.columns; j++){
				System.out.print(matrix[i][j] + " ");
			
			}
			System.out.println();
		}
		System.out.println();
	}


	// Only inverts diagonal matrices - doing it in general is a work in progress
	public Matrix diagonalInvert(){
		if(this.rows != this.columns){
			throw new RuntimeException("This matrix aint square, this method only works on diagonal matrices!");
		}
		for(int i = 0 ; i< this.rows; i ++){
			for(int j = 0; j<this.columns; j++){
				if(this.matrix[i][j]!=0 && i!=j){
					throw new RuntimeException("This matrix aint diagonal, this method only works on diagonal matrices!");
				}
			}
		}

		Matrix B = new Matrix(this.rows, this.columns);
		for(int i = 0; i<this.rows; i++){
			B.matrix[i][i] = 1/this.matrix[i][i];
		}

		return B;
	}




// Test module
public static void main(String[] args){
	double[][] e ={{1.0,2.0},{3.0,4.0},{5.0,1.0}};
	double[][] f ={{1.0,1.0},{2.0,1.0}};

	Matrix A = new Matrix(e);
	Matrix B =  new Matrix(f);
	Matrix A2 = new Matrix(e);
	Matrix C = A.product(B);
	boolean test = A.compare(A2);
	System.out.println(test);

	A.show();
	Matrix D = A.transpose();
	D.show();

	Matrix test2 = Matrix.random(5,5,10);
	test2.show();

	Matrix ONE = Matrix.indentity(5,5);
	ONE.show();


	Matrix copy = new Matrix(A.matrix);
	copy.show();
}




}

