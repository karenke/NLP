from nltk.tokenize import word_tokenize, wordpunct_tokenize, sent_tokenize
from nltk import bigrams
from nltk.tokenize.util import regexp_span_tokenize
ins = open( "newRCV.test", "r" ).read()

arr = [word_tokenize(t) for t in sent_tokenize(ins)]

f = open('RCVtokentest','w')
for t in arr:
#	print bigrams(t)
#	print ' '.join(t)
	f.write(' '.join(t)+ '\n')

ins = open( "newMovie.test", "r" ).read()

arr = [word_tokenize(t) for t in sent_tokenize(ins)]

f = open('Movietokentest','w')
for t in arr:
#	print bigrams(t)
#	print ' '.join(t)
	f.write(' '.join(t)+ '\n')

ins = open( "newWSJ.test", "r" ).read()

arr = [word_tokenize(t) for t in sent_tokenize(ins)]

f = open('WSJtokentest','w')
for t in arr:
#	print bigrams(t)
#	print ' '.join(t)
	f.write(' '.join(t)+ '\n')

ins = open( "newNFS.test", "r" ).read()

arr = [word_tokenize(t) for t in sent_tokenize(ins)]

f = open('NFStokentest','w')
for t in arr:
#	print bigrams(t)
#	print ' '.join(t)
	f.write(' '.join(t)+ '\n')

