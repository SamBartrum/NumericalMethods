from __future__ import division
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np


data = pd.read_csv("data.csv")

x = [x for x in data.xvalues]
y = [y for y in data.yvalues]


# methods to find best linear least squares fit from two lists of data
def AnalyticLeastSquares(x, y):

	N = len(x)
	xsum = sum(x)
	ysum = sum(y)
	x2sum = sum([s*s for s in x])
	xysum = sum( [x[i]*y[i] for i in range(len(x)) ])

	a = (N * xysum - ysum * xsum) / (N * x2sum - xsum**2)
	b = (ysum - a * xsum) / N

	xvalues = x
	yvalues = [a * xx + b for xx in x]

	return xvalues, yvalues


# batch gradient decent method to find linear best fit
def batchGradientDecent(x, y, alpha, num_iters):

	N = len(x)
	a = 1
	b = 1

	cost = []

	for i in range(num_iters):

		b_step = sum([a * x[j] + b - y[j] for j in range(N)])
		a_step = sum([(a * x[j] + b - y[j]) * x[j] for j in range(N)])

		a = a - (alpha / N) * a_step
		b = b - (alpha / N) * b_step

		cost.append((1 / 2 * N) * sum([(a * x[j] + b - y[j])**2 for j in range(N)]) )


	yvalues = [xx * a + b  for xx in x]	

	return x , yvalues, cost

numit = 10000

result = batchGradientDecent(x, y, 0.01, numit)

plt.plot(result[0], result[1], color = "black")


analytic = AnalyticLeastSquares(x, y)

plt.scatter(x, y)
plt.plot(analytic[0], analytic[1])

# use numpys regression
regression = np.polyfit(x, y, 1)
yvalues = [regression[0] * xx + regression[1] for xx in x]
plt.plot(x, yvalues)

plt.show()

x = [n for n in range(numit)]
plt.plot(x,result[2])
plt.xlabel("Iteration")
plt.ylabel("Cost")
plt.xscale('log')
plt.show()




