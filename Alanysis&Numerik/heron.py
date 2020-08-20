import re
import matplotlib.pyplot as plt
from matplotlib.collections import PatchCollection
from matplotlib.patches import Rectangle

def heron(x, approx, nachkommastellen):
	global iterationen
	maxGenauigkeit = 10
	if(nachkommastellen>maxGenauigkeit):
		genauigkeit = 0.1**maxGenauigkeit
	else:
		genauigkeit = 0.1**nachkommastellen
	m=approx
	n=x/approx
	
	if m>n:
		difference=m-n
	else:
		difference=n-m

	if (difference > genauigkeit):
		iterationen = iterationen+1
		zwischenergebnisse.append(approx)
		approx=(m+n)/2
		return heron(x, approx, nachkommastellen)
	else:	
		zwischenergebnisse.append(approx)
		return m

def plot():

	fig, ax = plt.subplots()
	ax.set(xlabel='x', ylabel='y', title='Heron Verfahren')
	ax.grid()
	ax.set_xlim([0.0,wert+1])
	ax.set_ylim([0.0,wert+1])
	for erg in zwischenergebnisse:

		rect = Rectangle((0.0, 0.0), erg, wert/erg)
		rect.set_facecolor('none')
		rect.set_edgecolor('black')
		rect.set_linewidth(2)
		ax.add_patch(rect)

	rect = Rectangle((0.0, 0.0), zwischenergebnisse[-1], wert/zwischenergebnisse[-1])
	rect.set_facecolor('none')
	rect.set_edgecolor('red')
	rect.set_linewidth(2)
	ax.add_patch(rect) 
	
	plt.show()

def neueAbfrage():
	global wert
	wert=float(input("Von welcher Zahl wollen Sie die Wurzel ziehen? "))
	nachkommastellen=int(input("Wie viele Nachkommastellen Genauigkeit? "))
	global zwischenergebnisse
	zwischenergebnisse=[]
	global iterationen
	iterationen=0

	if(wert>0):
		wurzel=heron(wert, wert, nachkommastellen)
		print("Die Wurzel von " +str(wert)+ "mit " +str(nachkommastellen)+" Nachkommastellen Genauigkeit ist: "+str(wurzel))
		print("Iterationen: "+str(iterationen))
		full = input("Zwischenergebnisse anzeigen? y/n ")
		x = re.search("^y.*", full)
		if (x):
			for erg in zwischenergebnisse:
  				print(erg)
			plotten = input("Zwischenergebnisse plotten? y/n")
			x = re.search("^y.*", plotten)
			if(x):
				plot()

	elif(wert == 0):
                print("Die Wurzel von 0 ist 0. Dafuer braucht man wirklich keinen Rechner...")
	elif(wert<0):
		print("Wurzeln aus negativen Zahlen zieht nur Donald Trump")
	

	nochmal = input("Noch eine Wurzel berechnen?")
	x = re.search("^y.*", nochmal)
	if(x):
		neueAbfrage();
#PROGRAMMSTART
print("Willkommen zum superbesten Heronverfahren Skript 2019!")
neueAbfrage()	

