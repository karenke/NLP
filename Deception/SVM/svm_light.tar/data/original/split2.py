filename = '../tfidf_train.txt'
with open(filename) as f:
    data = f.readlines()

positive = []
negative = []

filename2 = '../intermediate/unigramsTrain_isPositive.txt'
with open(filename2) as f1:
    inputData2 = f1.readlines()

label = []

for row in inputData2:
	label.append(row.split()[0])


count = 0
for row in data:
	words = row.split()
	#print words[0][0]+','+words[0][1]
	#print words[1:]

	l = label[count]
	if int(l) == 0:
		l = -1
	s = str(l)+ ' '+' '.join(words[1:])
	if int(words[0]) == 1: #positive
		positive.append(s)
	else: #negative
		negative.append(s)
	count += 1


f2 = open('tfidfTrain_positive.txt', 'w')
for row in positive: #Skip through each row in the csv file
	line = str(row)+'\n'
	f2.writelines(line)
	
f2.close()

f2 = open('tfidfTrain_negative.txt', 'w')
for row in negative: #Skip through each row in the csv file
	line = str(row)+'\n'
	f2.writelines(line)
	
f2.close()