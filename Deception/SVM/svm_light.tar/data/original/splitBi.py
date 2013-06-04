filename = 'outBiTest1.txt'
with open(filename) as f:
    data = f.readlines()

isTruthful = []
isPositive = []
for row in data:
	words = row.split()
	#print words[0][0]+','+words[0][1]
	#print words[1:]
	s = ' '.join(words[1:])

	if int(words[0]) == -2: # 00
		str1 = '0'+' '+s #istruthful
		str2 = '0'+' '+s#ispositive
	if int(words[0]) == -1: # 01
		str1 = '0'+' '+s #istruthful
		str2 = '1'+' '+s#ispositive
	if int(words[0]) == 1: # 10
		str1 = '1'+' '+s #istruthful
		str2 = '0'+' '+s#ispositive
	if int(words[0]) == 2: # 11
		str1 = '1'+' '+s #istruthful
		str2 = '1'+' '+s#ispositive
	isTruthful.append(str1)
	isPositive.append(str2)

f2 = open('outBiTest_isTruthful.txt', 'w')
for row in isTruthful: #Skip through each row in the csv file
	line = str(row)+'\n'
	f2.writelines(line)
	
f2.close()

f2 = open('outBiTest_isPositive.txt', 'w')
for row in isPositive: #Skip through each row in the csv file
	line = str(row)+'\n'
	f2.writelines(line)
	
f2.close()