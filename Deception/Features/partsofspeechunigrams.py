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
file = open('unigramvalidation.txt')
file1= open('posvalidation.txt','a')

for line in file.readlines():
        tagged_words1=""
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
        for line in filtered_words:
            words = [x.strip(string.punctuation) for x in line.split()]
            words=nltk.pos_tag(words)
            words=str(words).strip('[]')
            words=str(words).replace("(","")
            words=str(words).replace(")","")               
            words=str(words).replace("'","") 
                 
            tagged_words=nltk.tag.str2tuple(str(words).replace(",","/"))
            tagged_words1=tagged_words1+" "+ str(tagged_words[1])
        print bits4+","+tagged_words1
        file1.write(bits4+","+tagged_words1+"\n")