from nltk.tokenize import word_tokenize, wordpunct_tokenize, sent_tokenize
from nltk import bigrams
from nltk.tokenize.util import regexp_span_tokenize
ins = open( "newRCV.train", "r" ).read()

arr = [word_tokenize(t) for t in sent_tokenize(ins)]

f = open('RCVtokentrain','w')
for t in arr:
#	print bigrams(t)
#	print ' '.join(t)
	f.write(' '.join(t)+ '\n')


ins = open( "newMovie.train", "r" ).read()

arr = [word_tokenize(t) for t in sent_tokenize(ins)]

f = open('Movietokentrain','w')
for t in arr:
#	print bigrams(t)
#	print ' '.join(t)
	f.write(' '.join(t)+ '\n')


ins = open( "newWSJ.train", "r" ).read()

arr = [word_tokenize(t) for t in sent_tokenize(ins)]

f = open('WSJtokentrain','w')
for t in arr:
#	print bigrams(t)
#	print ' '.join(t)
	f.write(' '.join(t)+ '\n')


ins = open( "newNFS.train", "r" ).read()

arr = [word_tokenize(t) for t in sent_tokenize(ins)]

f = open('NFStokentrain','w')
for t in arr:
#	print bigrams(t)
#	print ' '.join(t)
	f.write(' '.join(t)+ '\n')

