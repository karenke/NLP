from nltk.util import ngrams
import nltk
import re
from collections import Counter
from test.test_threading_local import target
from nltk.metrics.distance import presence
from  nltk.corpus import stopwords
import string
from nltk import PorterStemmer 
from nltk import LancasterStemmer 
from nltk import wordnet
from nltk.stem.wordnet import WordNetLemmatizer 
from nltk.tag.util import str2tuple
#t = "Does NLTK have a provision to extract character n-grams from text? I would like to extract character n-grams (instead of traditional unigrams,bigrams) as features to aid my text classification task."
file = open('unigramtest.txt')
file1= open('unigramtest2.txt','a')
i = 0
for line in file.readlines():
        if not line :
            break
        if len(line) == 0:
            break
        data_line = line.split('\n')
        bits = data_line[0]
        bits=bits.split(',')
        bits1=bits[0]
        bits2=bits[1]
        bits4=bits1+","+bits2
        bits3=bits[2].split(' ');
        filtered_words=filter(lambda x:x not in stopwords.words('english'),bits3)
        words=str(filtered_words)
        words = re.sub('[0-9]+','',words)
        for punct in string.punctuation:
            words = words.replace(punct,'')
        words=str(words).strip('[]')
        words=str(words).replace("(","")
        words=str(words).replace(")","")               
        words=str(words).replace("'","")
        words=str(words).replace(",","") 
        words=str(words).replace("\"","")
        words=str(words).replace("/","")
        words=str(words).replace(".","")
        words=str(words).replace(";","")
        words=str(words).replace("\\","")
        words=str(words).replace("-","")
        vin=[]
        vin1=""
        chrs = [c for c in words]
        vin=ngrams(chrs,3)
        vin=str(vin).replace("[","")
        vin=str(vin).replace("]","")
        vin=str(vin).replace("'","")
        vin=str(vin).replace("(","")
        vin=str(vin).split(')')
        vin=str(vin).replace(",","")
        vin1=str(vin)
        vin1=vin1.replace("[","")
        vin1=vin1.replace("]","")
        vin1=vin1.replace("'","")
        vin1=vin1[::2]
        vin1=re.sub("\s\s+" , " ", vin1)
        print bits4+","+words+"\n"
        file1.write(bits4+","+words+"\n")