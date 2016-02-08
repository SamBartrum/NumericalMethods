# Runge-Kutta-Fehlberg Method (RKF45)
from __future__ import division
import math
import matplotlib.pyplot as plt


class IntComp():

	def __init__(self, f, xa, xb, ya, n):
		self.f = f
		self.xa = xa
		self.xb = xb
		self.ya = ya
		self.n = n
		self.results = {}


	def Euler(self):
		xlist = [self.xa]
		ylist = [self.ya]	

		# const step size
		h = (self.xb - self.xa) / self.n
		y,x = self.ya, self.xa

		for i in range(self.n):
			y += h * self.f(x, y)
			x += h

			ylist.append(y)
			xlist.append(x)

		return xlist, ylist

	def RK4(self):

		xlist = [self.xa]
		ylist = [self.ya]

		# const step size
		h  = (self.xb - self.xa) / self.n
		x,y = self.xa,self.ya

		for i in range(self.n):

			k1 = self.f(x, y)
			k2 = self.f(x + h/2, y + k1 * h/2)
			k3 = self.f(x + h/2, y + k2 * h/2)
			k4 = self.f(x + h,  y + k3 * h )

			y += h / 6 * (k1 + 2 * k2 + 2* k3 + k4)
			x += h

			xlist.append(x)
			ylist.append(y)

		return xlist, ylist
	
	# single computation of the RKF model	
	def RKFRun(self, x, y, h):

		# compute the function at the next step	
		k1 = h * self.f(x,y)
		k2 = h * self.f(x + h/4 , y + k1/4 ) 
		k3 = h * self.f(x + 3*h/8 , y + 3*k1/32 + 9*k2/32 )
		k4 = h * self.f(x + 12*h/13 , y + 1932*k1/2197 - 7200*k2/ 2197+ 7296*k3/2197 )
		k5 = h * self.f(x + h , y + 439*k1/216 - 8*k2 + 3680*k3/513 - 845*k4/4104 )
		k6 = h * self.f(x + h/2 , y - 8*k1/27 + 2*k2- 3544*k3/2565 + 1859*k4/4140 - 11*k5/40 )

		# fourth and fith order approximations
		y4 = y + 25*k1/216 + 1408*k3/2565 + 2197*k4/4101 - k5/5
		y5 = y + 16*k1/35 + 6656*k3/12825 + 28561*k4/56430 - 9*k5/50 + 2*k6/55

		R = abs(y5-y4)

		return {'x':x, 'y':y4,'R':R}


	# RK4 methods with variable step size
	def RKF(self):

		#initial stepsize
		h = 0.01
		hmin = 0.0001
		hmax = 0.3

		x,y = self.xa,self.ya

		steps = [h]
		xlist = [self.xa]
		ylist = [self.ya]

		tolerance = 0.01

		while x < self.xb:

			# prevent step sizes from becoming too small/large
			if h > hmax:
				h = hmax
			elif h < hmin:
				h = hmin

			steps.append(h)			


			data = self.RKFRun(x,y,h)
			if data['R'] != 0:
				delta = 0.9 * ( tolerance / data['R'] )**0.25
					

			# we reject the step size and half it and perform the run again until we are within the tolerance
			if data['R'] > tolerance and h > hmin:
				while data['R'] > tolerance and h > hmin:		
					h = h/2
					data = self.RKFRun(x,y,h)
			
			# Otherwise we accept the step
			else:
				h = h * delta
					
			x += h
			y = data['y']

			xlist.append(x)
			ylist.append(y)

		
		return xlist, ylist, steps
		

	def GenerateResults(self):
		
		self.results = {'Euler':[self.Euler()[0], self.Euler()[1]],
						'RK4': [self.RK4()[0], self.RK4()[1]],
						'RKF': [self.RKF()[0], self.RKF()[1], self.RKF()[2]]}
	
	def plotresults(self):

		self.GenerateResults()

		plt.plot(self.results['Euler'][0],self.results['Euler'][1],marker='o', color = "blue")
		plt.plot(self.results['RK4'][0],self.results['RK4'][1],marker='o', color = "red")
		plt.plot(self.results['RKF'][0],self.results['RKF'][1],marker='o', color = "green")
		plt.show()





def exactSol():
	xlist = [x for x in range(0,10)]
	ylist = [math.exp(-2*x) for x in range(0,10)]

	return xlist, ylist



if __name__ == '__main__':

	test = IntComp(lambda x,y: -2*y + math.exp(-2*(x - 6)**2), 0.0, 10.0, 1.0, 100)
	# test2 = IntComp(lambda x,y: x*y, 0.0, 10.0, 1.0, 100)

	test.plotresults()
	test2.plotresults()





