'''
Created on Feb 28, 2013

@author: jyotiPandey
   
'''
import re
from collections import Counter
from test.test_threading_local import target
import nltk
from nltk.metrics.distance import presence
from  nltk.corpus import stopwords
import string
from nltk import PorterStemmer 
from nltk import LancasterStemmer 
from nltk import wordnet
from nltk.stem.wordnet import WordNetLemmatizer

filtered_words=''

def stem_funct(filtered_words):
    res = ''
    res1=''
    lmtzr = WordNetLemmatizer()
    st=nltk.PorterStemmer()
    st1=nltk.LancasterStemmer()
    for word in filtered_words:
        res += ' ' + st1.stem(word)
    return res


file = open('Validation data')
i = 0
st = PorterStemmer()
for line in file.readlines():
        if not line :
            break
        if len(line) == 0:
            break
        data_line = line.split('<>')
        bits = data_line[0].split(' ')
        filtered_words=filter(lambda x:x not in stopwords.words('english'),bits)
        print filtered_words
        
        filtered_words1=stem_funct(filtered_words)
        filtered_words1=str(filtered_words1).replace("?","0")
        f = open('unigramvalidation.txt', 'a')
        f.write(''.join(filtered_words1))
        print filtered_words1
        
        

       

