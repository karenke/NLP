from nltk.tokenize import word_tokenize, wordpunct_tokenize, sent_tokenize
from nltk import bigrams
from nltk.tokenize.util import regexp_span_tokenize
ins = open( "newRCV.val", "r" ).read()

arr = [word_tokenize(t) for t in sent_tokenize(ins)]

f = open('RCVtokenval','w')
for t in arr:
#	print bigrams(t)
#	print ' '.join(t)
	f.write(' '.join(t)+ '\n')


ins = open( "newMovie.val", "r" ).read()

arr = [word_tokenize(t) for t in sent_tokenize(ins)]

f = open('Movietokenval','w')
for t in arr:
#	print bigrams(t)
#	print ' '.join(t)
	f.write(' '.join(t)+ '\n')


ins = open( "newWSJ.val", "r" ).read()

arr = [word_tokenize(t) for t in sent_tokenize(ins)]

f = open('WSJtokenval','w')
for t in arr:
#	print bigrams(t)
#	print ' '.join(t)
	f.write(' '.join(t)+ '\n')


ins = open( "newNFS.val", "r" ).read()

arr = [word_tokenize(t) for t in sent_tokenize(ins)]

f = open('NFStokenval','w')
for t in arr:
#	print bigrams(t)
#	print ' '.join(t)
	f.write(' '.join(t)+ '\n')

