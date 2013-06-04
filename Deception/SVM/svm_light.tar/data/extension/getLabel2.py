filename = '../tfidf_isPositive_prediction'
with open(filename) as f:
    inputData = f.readlines()

filename2 = '../tfidf_test.txt'
with open(filename2) as f2:
    inputData2 = f2.readlines()


data = []
for row in inputData:
	if float(row) >= 0: #positive
		data.append(1)
	else:
		data.append(0) #negative-1

f.close()

count = 0
positive1 = []
negative1 = []
positive2 = []
negative2 = []
for row in inputData2:
	words = row.split()
	if data[count] == 1: #positive
		pos_s1 = str(count)#line number
		pos_s2 = words[0]+ ' '+' '.join(words[1:]) # label,...
		positive1.append(pos_s1)
		positive2.append(pos_s2)
	else:
		neg_s1 = str(count)
		neg_s2 = words[0]+ ' '+' '.join(words[1:]) # label,...
		negative1.append(neg_s1)
		negative2.append(neg_s2)
	count += 1

f2.close()

f3 = open('tfidfTest_positive_lineLabel.txt', 'w')
for row in positive1: #Skip through each row in the csv file
	line = str(row)+'\n'
	f3.writelines(line)
	
f3.close()

f3 = open('tfidfTest_positive.txt', 'w')
for row in positive2: #Skip through each row in the csv file
	line = str(row)+'\n'
	f3.writelines(line)
	
f3.close()

f3 = open('tfidfTest_negative_lineLabel.txt', 'w')
for row in negative1: #Skip through each row in the csv file
	line = str(row)+'\n'
	f3.writelines(line)
	
f3.close()

f3 = open('tfidfTest_negative.txt', 'w')
for row in negative2: #Skip through each row in the csv file
	line = str(row)+'\n'
	f3.writelines(line)
	
f3.close()
