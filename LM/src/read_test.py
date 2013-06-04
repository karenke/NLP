# python NLTK tokenize, output: each line is a sentence
from nltk.tokenize import word_tokenize, wordpunct_tokenize, sent_tokenize
from nltk import bigrams
from nltk.tokenize.util import regexp_span_tokenize
ins = open( "data/newRCV.test", "r" ).read()

arr = [word_tokenize(t) for t in sent_tokenize(ins)]

f = open('RCVtokentest','w')
for t in arr:
#	print bigrams(t)
#	print ' '.join(t)
	f.write(' '.join(t)+ '\n')

ins = open( "data/newMovie.test", "r" ).read()

arr = [word_tokenize(t) for t in sent_tokenize(ins)]

f = open('data/Movietokentest','w')
for t in arr:
#	print bigrams(t)
#	print ' '.join(t)
	f.write(' '.join(t)+ '\n')

ins = open( "data/newWSJ.test", "r" ).read()

arr = [word_tokenize(t) for t in sent_tokenize(ins)]

f = open('data/WSJtokentest','w')
for t in arr:
#	print bigrams(t)
#	print ' '.join(t)
	f.write(' '.join(t)+ '\n')

ins = open( "data/newNFS.test", "r" ).read()

arr = [word_tokenize(t) for t in sent_tokenize(ins)]

f = open('data/NFStokentest','w')
for t in arr:
#	print bigrams(t)
#	print ' '.join(t)
	f.write(' '.join(t)+ '\n')

