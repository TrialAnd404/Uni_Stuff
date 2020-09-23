import json

data = {}
for number in range(7):
	data['Semester'+str(number+1)]=[]
	
	#pflicht loop
	data['Semester'+str(number+1)].append({
		"Pflicht":[]
	})

	while (bool(True)):
		fach=input("pflichtfach eingeben: ")
		if (fach!='X'):
			data['Semester'+str(number+1)]['Pflicht'].append(fach)
		else:
			break	
	#wahl loop
	data['Semester'+str(number+1)].append({
        "Wahl":[]
    })

	while (bool(True)):
		fach=input("wahlfach eingeben: ")
		if (fach!='X'):
			data['Semester'+str(number+1)]['Wahl'].append(fach)
		else:
			break
		
	with open('studium.json', 'a') as outfile:
		outfile.write("\n")
		json.dump(data, outfile)
