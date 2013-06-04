
from nltk.tokenize import word_tokenize, wordpunct_tokenize, sent_tokenize, regexp_tokenize
from nltk import trigrams, bigrams

ins = open( "WSJtoken", "r" ).readlines()
f = open('WSJ_Tri1','w')
for t in ins:
	for j in trigrams(word_tokenize(t)):
		f.write(str(j)+"\n")

f.close()
