using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace HMM
{
    class TFIDF
    {
        private Dictionary<KeyValuePair<int, String[]>, int> maxCount = new Dictionary<KeyValuePair<int,string[]>,int>();
        public double calTFIDF(String term, KeyValuePair<int, String[]> document, Dictionary<int, KeyValuePair<int, String[]>> documents)
        {
            return calTF(term, document) * calIDF(term, documents);
        }

        public double calTF(String term, KeyValuePair<int, String[]> document)
        {
            int maxC = 0;
            if (!maxCount.ContainsKey(document))
            {
                Dictionary<String, int> countDic = new Dictionary<string, int>();
                foreach (String word in document.Value)
                {
                    int count = 0;
                    if (countDic.ContainsKey(word))
                    {
                        count = countDic[word];
                        countDic.Remove(word);
                    }
                    count++;
                    countDic.Add(word, count);
                }
                foreach (String word in countDic.Keys)
                {
                    if (maxC < countDic[word]) maxC = countDic[word];
                }
                maxCount.Add(document, maxC);
            }
            maxC = maxCount[document];
            int termCount = 0;
            foreach (String word in document.Value)
            {
                if (word.Equals(term)) termCount++;
            }
            return termCount / (double)maxC;
        }

        public double calIDF(String term, Dictionary<int, KeyValuePair<int, String[]>> documents)
        {
            int count = 0;
            foreach (KeyValuePair<int, String[]> document in documents.Values)
            {
                foreach (String word in document.Value)
                {
                    if (term.Equals(word))
                    {
                        count++;
                        break;
                    }
                }
            }
            return (double)count / documents.Count;
        }
    }
}
