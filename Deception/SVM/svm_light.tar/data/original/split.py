filename = 'charvalidation1.txt'
with open(filename) as f:
    data = f.readlines()

isTruthful = []
isPositive = []
for row in data:
	words = row.split()
	#print words[0][0]+','+words[0][1]
	#print words[1:]
	s = ' '.join(words[1:])
	#print s
	str1 = words[0][0]+' '+s #istruthful
	str2 = words[0][1]+' '+s #ispositive
	isTruthful.append(str1)
	isPositive.append(str2)

#print isTruthful

# f2 = open('unigram_isTruthful.txt', 'w')
# f2 = open('pos_isTruthful.txt', 'w')
f2 = open('charValidation_isTruthful.txt', 'w')
for row in isTruthful: #Skip through each row in the csv file
	line = str(row)+'\n'
	f2.writelines(line)
	
f2.close()

# f2 = open('unigram_isPositive.txt', 'w')
# f2 = open('pos_isPositive.txt', 'w')
f2 = open('charValidation_isPositive.txt', 'w')
for row in isPositive: #Skip through each row in the csv file
	line = str(row)+'\n'
	f2.writelines(line)
	
f2.close()