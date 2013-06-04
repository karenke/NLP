using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using LAIR.ResourceAPIs.WordNet;

namespace Project2
{
    class Filter
    {
        static Porter2 stemmer = new Porter2();
        static Dictionary<String, bool> stopWordDic;
        public static String Execute(String token)
        {
            if (stopWordDic == null) LoadStopWord();
            token = token.ToLower();
            if (stopWordDic.ContainsKey(token)) return "";
            return token;
        }

        private static void LoadStopWord()
        {
            stopWordDic = new Dictionary<string,bool>();
            String stp = FileManager.Read(@"C:\Code\NLP\Project2\en-us.stp");
            String[] lines = stp.Split('\n');
            foreach (String line in lines)
            {
                if (!stopWordDic.ContainsKey(line))
                {
                    stopWordDic.Add(line, true);
                }
            }
        }
    }
}
