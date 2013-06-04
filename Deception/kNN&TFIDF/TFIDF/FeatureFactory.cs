using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Project2;

namespace HMM
{
    class FeatureFactory
    {
        private TFIDF tfidf;
        private Dictionary<String, double> scoreDic;
        private Dictionary<String, int> scoreCount;
        private Dictionary<String, int> wordDic;
        private double threadhold = 0.1;

        private bool IsNegativeWord(String word)
        {
            if (word.Equals("no")) return true;
            if (word.Equals("not")) return true;
            return false;
        }

        public int GetSentimentWordCount()
        {
            if (wordDic != null)
            {
                return wordDic.Count;
            }
            return 0;
        }

        public int IsSentimentWord(String word)
        {
            if (wordDic.ContainsKey(word)) return wordDic[word];
            return -1;
        }

        public void ExtractSentimentWord(Dictionary<int, KeyValuePair<int, String[]>> documents)
        {
            scoreCount = new Dictionary<string, int>();
            scoreDic = new Dictionary<string, double>();
            if (tfidf == null)
            {
                tfidf = new TFIDF();
            }
            int c = 0;
            foreach (KeyValuePair<int, String[]> document in documents.Values)
            {
                System.Console.WriteLine(c++);
                foreach (String word in document.Value)
                {
                    int count = 0;
                    double score = 0;
                    if (scoreCount.ContainsKey(word))
                    {
                        score = scoreDic[word];
                        count = scoreCount[word];
                        scoreCount.Remove(word);
                        scoreDic.Remove(word);
                    }
                    double tfidfScore = tfidf.calTFIDF(word, document, documents);
                    score += tfidfScore;
                    count++;
                    scoreCount.Add(word, count);
                    scoreDic.Add(word, score);
                }
            }
            wordDic = new Dictionary<string, int>();
            String output = "";
            foreach (String word in scoreDic.Keys)
            {
                output += word + " " + scoreDic[word] + "\n";
                if (scoreDic[word] / scoreCount[word] >= threadhold)
                {
                    wordDic.Add(word, wordDic.Count + 1);
                }
            }
            FileManager.Write(output, @"C:\Code\NLP\Project2\HMM\wordDic.txt");
        }

        public String SentimentWord(KeyValuePair<int, String[]> document)
        {
            String output = "";
            bool outputed = false;
            foreach (String word in wordDic.Keys)
            {
                outputed = false;
                String preWord = "";
                foreach (String w in document.Value)
                {                    
                    if (word.Equals(w))
                    {
                        if (IsNegativeWord(preWord))
                        {
                            output += "-1 ";
                        }
                        else
                        {
                            output += "1 ";
                        }
                        outputed = true;
                        break;
                    }
                    preWord = w;
                }
                if (!outputed)
                {
                    output += "0 ";
                }
            }
            return output;
        }

        public String Label(KeyValuePair<int, String[]> documents)
        {
            return documents.Key + " ";
        }

        public String AdversiveWord(KeyValuePair<int, String[]> document)
        {
            if (document.Value.Length >= 2)
            {
                if (document.Value[0].Equals("but")) return "1 ";
                if (document.Value[0].Equals("however")) return "1 ";
                if (document.Value[1].Equals("but")) return "1 ";
                if (document.Value[1].Equals("however")) return "1 ";
            }
            return "0 ";    
        }
    }
}
