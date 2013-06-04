f = open("RCV_Tri","w")

words = open( "RCV-Tri2", "r" ).readlines()
import re;
for t in words:
	t = t.strip('(')
	t = t.replace(')','')
	m = t.split(',')
	mt = m[0]
	f.write(mt[1:-3] + " : " + m[1])	
f.close()