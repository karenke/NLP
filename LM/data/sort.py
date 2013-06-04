from collections import Counter
f = open("RCV-Tri2","w")

cnt = Counter()
#with open("output1") as words:
words = open( "RCV-Tri1", "r" ).readlines()
#for t in words:

counts = Counter(words)

for t in counts.items():
	f.write(str(t)+"\n")
	
f.close()