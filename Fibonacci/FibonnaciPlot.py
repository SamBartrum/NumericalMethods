import matplotlib.pyplot as plt
import numpy as np
import math

d = np.loadtxt("SmallN.txt")


genFib= []
FibRec = []
FibRecStore = []
FibMatrix = []

num = [x for x in range(1,len(d)+1)]

for i in range(0,len(d)):
	genFib.append(d[i][0])
	FibRec.append(d[i][1])
	FibRecStore.append(d[i][2])
	FibMatrix.append(d[i][3])


plt.plot(num, genFib, color = "blue", label = "Non-Recursive")
plt.plot(num, FibRec, color = "red", label = "Recursive")
plt.plot(num, FibRecStore, color = "green", label = "Storing Recursive")
plt.plot(num, FibMatrix, color = "black", label = "Matrix Method")
legend = plt.legend(loc='upper left')
plt.xscale('log')
plt.yscale('log')

plt.title("Time taken to generate a Fibonnaci number \n for difference methods (small N)")
plt.xlabel("Fibonnaci number")
plt.xlim([1,40])
plt.ylabel("Time taken (ns)")
plt.savefig('LowN.pdf')

plt.show()



d = np.loadtxt("LargeN.txt")


genFib= []
FibRecStore = []
FibMatrix = []
num = [x for x in range(1,len(d)+1)]



for i in range(0,len(d)):
	genFib.append(d[i][0])
	FibRecStore.append(d[i][1])
	FibMatrix.append(d[i][2])

test = [n**2 + 1000 for n in num]

plt.plot(num, genFib, color = "blue", label = "Non-Recursive")
plt.plot(num, FibRecStore, color = "green", label = "Storing Recursive")
plt.plot(num, FibMatrix, color = "black", label = "Matrix Method")
plt.plot(num, test)

legend = plt.legend(loc='upper left')
plt.xscale('log')
plt.yscale('log')
plt.xlim([1,10000])

plt.title("Time taken to generate a Fibonnaci number \n for difference methods (large N)")
plt.xlabel("Fibonnaci number")
plt.ylabel("Time taken (ns)")
plt.savefig('HighN.pdf')

plt.show()

