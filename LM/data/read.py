
from nltk.tokenize import word_tokenize, wordpunct_tokenize, sent_tokenize
from nltk import bigrams
from nltk.tokenize.util import regexp_span_tokenize
ins = open( "newRCV.train", "r" ).read()

arr = [word_tokenize(t) for t in sent_tokenize(ins)]

f = open('RCVtoken','w')
for t in arr:
#	print bigrams(t)
#	print ' '.join(t)
	f.write(' '.join(t)+ '\n')
