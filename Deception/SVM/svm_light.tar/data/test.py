filename1 = 'tfidf_test.txt'
with open(filename1) as f1:
    inputData1 = f1.readlines()


filename2 = './intermediate/unigramsTest_isPositive.txt'
with open(filename2) as f2:
    inputData2 = f2.readlines()

label = []

for row in inputData2:
	label.append(row.split()[0])

# print label

f3 = open('tfidf_test_isPositive.txt', 'w')
count = 0
for row in inputData1:
	words = row.split()[1:]
	l = label[count]
	if int(l) == 0:
		l = -1

	line = str(l)
	for t in words:
		line +=' '+ str(t)
	line += '\n'
	f3.writelines(line)
	count += 1

f1.close()
f2.close()
f3.close()


