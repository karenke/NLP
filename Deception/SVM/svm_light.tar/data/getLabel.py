filename = 'tfidf_prediction'
with open(filename) as f:
    inputData = f.readlines()

data = []
for row in inputData:
	if float(row) >= 0: #truthful
		data.append(1)
	else:
		data.append(0) #deceptive -1


f2 = open('tfidf_prediction_result.txt', 'w')
for row in data: #Skip through each row in the csv file
	line = str(row)+'\n'
	f2.writelines(line)
	
f2.close()
