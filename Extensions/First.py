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
from nltk import LancasterStemmer 


file = open('ScottRenshaw_train.txt')
i = 0
st = LancasterStemmer()
for line in file.readlines():
        if not line :
            break
        if len(line) == 0:
            break
        data_line = line.split('<>')
        bits = data_line[0].split(' ')
        filtered_words=filter(lambda x:x not in stopwords.words('english'),bits)
        
       
        #text_file=open("Output.txt","w")
        #text_file.write(bits)
        print filtered_words
        f = open('output.txt', 'a')
        f.write(' '.join(filtered_words))
        print filtered_words;

