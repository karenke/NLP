filename = 'charTest_isPositive.txt'
with open(filename) as f:
    inputData = f.readlines()

data = []
for row in inputData:
	words = row.split()
	#print words[0][0]+','+words[0][1]
	#print words[1:]
	s = ' '.join(words[1:])
	#print s
	if int(words[0]) == 0:
		s = '-1 '+s
	else:
		s = '1 '+ s
	data.append(s)


f2 = open('charTest_isPositiveRelabeled.txt', 'w')
for row in data: #Skip through each row in the csv file
	line = str(row)+'\n'
	f2.writelines(line)
	
f2.close()
