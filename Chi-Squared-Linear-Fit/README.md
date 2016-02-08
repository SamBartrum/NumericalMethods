# Chi-Squared-Linear-Fit
Simple script to perform a linear fit on data by minimising the chi squared.


This started out as a simple exercise but has made for a fun morning's work (with more work to do). 

Matrix.java is a Matrix class which I initially wrote to reaquaint myself with some Java fundamentals.

ChiSquared.java reads in data from a txt file and performs a brute force chi squared minimisation routine to find the best linear fit to the data.

plots.py is the python script to generate the scatter plot and best line fit. This is because plotting in Java is awful.

Simply populate a file with your desired data (example of format shown in "data.txt"). Then run: java ChiSquared "your file name". Once this is done run: python plots.py to generate the graph.

