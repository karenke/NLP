using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Project1
{
    class RandomSentenceGen
    {
        Dictionary<String, Dictionary<String, int>> binaryModel;
        Dictionary<String, int> uniModel;
        Dictionary<String, int> countDic;
        int uniCount;
        Random random = new Random();

        public string[] GenerateUniSentences(Dictionary<String, int> uModel, int num)
        {
            uniCount = 0;
            uniModel = uModel;
            foreach (String key in uniModel.Keys)
            {
                uniCount += uniModel[key];
            }
            string[] sentences = new string[num];
            for (int i = 0; i < num; i++)
            {
                string next = generateNextWordUni();
                while (!(next == "END"))
                {
                    sentences[i] += next + " ";
                    next = generateNextWordUni();
                }
            }
            return sentences;
        }

        public String generateNextWordUni()
        {
            int count = random.Next(uniCount);
            foreach (String key in uniModel.Keys)
            {
                count -= uniModel[key];
                if (count <= 0) return key;
            }
            return "END";
        }

        public string[] GenerateBinarySentences(Dictionary<String, Dictionary<String, int>> bModel, int num)
        {
            countDic = new Dictionary<String, int>();
            binaryModel = bModel;
            string[] result = new string[num];
            foreach (String key in binaryModel.Keys)
            {
                Dictionary<String, int> cDic;
                countDic.Add(key, 0);
                binaryModel.TryGetValue(key, out cDic);
                foreach (String word in cDic.Keys)
                {
                    countDic[key] = countDic[key] + cDic[word];
                }
            }
            for (int i = 0; i < num; i++)
            {
                String w = "START";
                result[i] = "";
                while (!w.Equals("END"))
                {
                    w = generateNextWordBinary(w);
                    if (w.Equals("END")) break;
                    result[i] += w + " ";
                }
            }
            return result;
        }

        string generateNextWordBinary(string word)
        {
            Dictionary<String, int> cDic;
            binaryModel.TryGetValue(word, out cDic);
            int count = random.Next(countDic[word]);
            foreach (String key in cDic.Keys)
            {
                count -= cDic[key];
                if (count <= 0) return key;
            }
            return "END";
        }
    }
}
