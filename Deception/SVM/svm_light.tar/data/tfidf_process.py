
filename = 'train_feature.csv'
with open(filename) as f:
    inputData = f.readlines()

data = []

f2 = open('tfidf_train.txt', 'w')

for row in inputData:
	words = row.split()
	# print words
	label = words[0]
	if int(words[0]) == 0:
		label = -1
	feature = {}
	for t in words[1:]:
		# print t
		pair = t.split(':')
		fea = pair[0]
		val = pair[1]
		if int(fea) not in feature:
			feature[int(fea)] = 1
		else:
			feature[int(fea)] += 1

	line = str(label)
	for num in feature:
		line += ' '+str(num)+':'+str(feature[num])
	line += '\n'
	f2.writelines(line)

f.close()
f2.close()