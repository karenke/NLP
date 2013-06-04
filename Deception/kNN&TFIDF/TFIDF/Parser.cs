using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Project2;

namespace HMM
{
    class Parser
    {
        public KeyValuePair<int, String[]> parsekNN(String content)
        {
            Dictionary<int, int> countDic = new Dictionary<int, int>();
            content = content.ToLower();
            String[] scores = content.Split(',');
            String[] tokens;
            if (scores.Count() > 2)
            {
                scores[2] = scores[2].Replace('.', ' ');
                scores[2] = scores[2].Replace('/', ' ');
                scores[2] = scores[2].Replace(':', ' ');
                scores[2] = scores[2].Replace('\"', ' ');
                scores[2] = scores[2].Replace('\'', ' ');
                scores[2] = scores[2].Replace('-', ' ');
                tokens = scores[2].Split(' ');
            }
            else
            {
                scores[1] = scores[1].Replace('.', ' ');
                scores[1] = scores[1].Replace('/', ' ');
                scores[1] = scores[1].Replace(':', ' ');
                scores[1] = scores[1].Replace('\"', ' ');
                scores[1] = scores[1].Replace('\'', ' ');
                scores[1] = scores[1].Replace('-', ' ');
                tokens = scores[1].Split(' ');
            }
            int score = 0;
            if (scores[0].StartsWith("0") || scores[0].StartsWith("1"))
            {
                score = Int16.Parse(scores[0]);
            }
            KeyValuePair<int, String[]> pair = new KeyValuePair<int, string[]>(score, tokens);
            return pair;
        }

        public KeyValuePair<Dictionary<int, KeyValuePair<int, String[]>>, Dictionary<int, int>> parseFile(String content)
        {
            Dictionary<int, KeyValuePair<int, String[]>> document = new Dictionary<int, KeyValuePair<int, String[]>>();
            String[] sentences = content.Split('>');
            int count = -1;
            Dictionary<int, int> countDic = new Dictionary<int, int>();
            for (int i = 0; i < sentences.Length - 1; i++)
            {
                String[] tokens = sentences[i].Split(' ');
                if ((tokens[0].StartsWith("{")) || (tokens[0].StartsWith("\n{")))
                {
                    tokens[0] = "";
                    if (count != -1)
                    {
                        countDic.Add(countDic.Count, count);
                    }
                    count = 0;
                }
                count++;
                int score = 0;
                if (tokens[tokens.Length - 1].Length == 1)
                {
                    score = 0;
                }
                else
                {
                    score = Int16.Parse(tokens[tokens.Length - 1].Substring(1));
                }
                tokens[tokens.Length - 1] = "";
                KeyValuePair<int, String[]> pair = new KeyValuePair<int, string[]>(score, tokens);
                document.Add(document.Count, pair);
            }
            if (count != 0) countDic.Add(countDic.Count, count);
            return new KeyValuePair<Dictionary<int, KeyValuePair<int, string[]>>, Dictionary<int, int>>(document, countDic);
        }
    }
}
