filename = 'tfidfTest_positive_lineLabel.txt'
with open(filename) as f:
    inputData = f.readlines()

pos_line = []
for row in inputData:
	pos_line.append(int(row))

f.close()


filename = 'tfidfTest_negative_lineLabel.txt'
with open(filename) as f:
    inputData = f.readlines()

neg_line = []
for row in inputData:
	neg_line.append(int(row))

f.close()

#print neg_line

res = {}
filename = '../tfidf_pos_TorN_prediction'
with open(filename) as f:
    inputData = f.readlines()


count = 0
for row in inputData:
	line_num = pos_line[count]
	# print line_num
	if float(row) >= 0: #truthful
		res[line_num] = 1
	#	print str(line_num)+':1'
	else:
	#	print str(line_num)+':0'
		res[line_num] = 0
	count += 1
f.close()


filename = '../tfidf_neg_TorN_prediction'
with open(filename) as f:
    inputData = f.readlines()

count = 0
for row in inputData:
	line_num = neg_line[count]
	if float(row) >= 0: #truthful
		res[line_num] = 1
	else:
		res[line_num] = 0
	count += 1
f.close()

#print res



f3 = open('tfidfTest_extension_result.txt', 'w')
for row in res: #Skip through each row in the csv file
	line = str(res[row])+'\n'
	f3.writelines(line)
	
f3.close()
