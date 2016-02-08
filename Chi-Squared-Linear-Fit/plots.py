import matplotlib.pyplot as plt
import numpy as np

d = np.loadtxt("data.txt", skiprows = 1)
params = np.loadtxt("output.txt")

titles = open("data.txt", 'r')

data = titles.readline().split()

xaxis = data[0]
yaxis = data[1]


x = []
y = []
errors = []

for i in range(1,len(d)):
	x.append(d[i][0])
	y.append(d[i][1])
	errors.append(d[i][2])



m = params[0]
c = params[1]

xp = [x1 for x1 in range(int(min(x)), int(max(x))+1,1)]
yp = [m*XP+c for XP in xp]


plt.scatter(x, y)
plt.errorbar(x,y, yerr= errors, linestyle="None")
plt.plot(xp,yp)
plt.title("Best linear fit to the data")
plt.xlabel(xaxis)
plt.ylabel(yaxis)
plt.show()
