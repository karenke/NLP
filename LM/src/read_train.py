# python NLTK tokenize, output: each line is a sentence

from nltk.tokenize import word_tokenize, wordpunct_tokenize, sent_tokenize
from nltk import bigrams
from nltk.tokenize.util import regexp_span_tokenize
ins = open( "data/newRCV.train", "r" ).read()

arr = [word_tokenize(t) for t in sent_tokenize(ins)]

f = open('data/RCVtokentrain','w')
for t in arr:
#	print bigrams(t)
#	print ' '.join(t)
	f.write(' '.join(t)+ '\n')


ins = open( "data/newMovie.train", "r" ).read()

arr = [word_tokenize(t) for t in sent_tokenize(ins)]

f = open('data/Movietokentrain','w')
for t in arr:
#	print bigrams(t)
#	print ' '.join(t)
	f.write(' '.join(t)+ '\n')


ins = open( "data/newWSJ.train", "r" ).read()

arr = [word_tokenize(t) for t in sent_tokenize(ins)]

f = open('data/WSJtokentrain','w')
for t in arr:
#	print bigrams(t)
#	print ' '.join(t)
	f.write(' '.join(t)+ '\n')


ins = open( "data/newNFS.train", "r" ).read()

arr = [word_tokenize(t) for t in sent_tokenize(ins)]

f = open('data/NFStokentrain','w')
for t in arr:
#	print bigrams(t)
#	print ' '.join(t)
	f.write(' '.join(t)+ '\n')

