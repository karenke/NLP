using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Project2;

namespace kNN
{
    class Loader
    {
        private static Dictionary<int, KeyValuePair<bool, Dictionary<int, bool>>> featureDic;
        public static int featureTotalNum;
        public static Dictionary<int, KeyValuePair<bool, Dictionary<int, bool>>> load(String path)
        {
            featureTotalNum = 0;
            featureDic = new Dictionary<int, KeyValuePair<bool, Dictionary<int, bool>>>();
            String content = FileManager.Read(path);
            String[] lines = content.Split('\n');
            int count = 0;
            foreach (String line in lines)
            {
                if (line == "") continue;
                Console.WriteLine(count++);
                String tmp = line;
                if (line.StartsWith(" "))
                    tmp = line.Substring(1);
                String[] tokens = tmp.Split(' ');
                bool isTrue = false;
                if (tokens[0].StartsWith("1")) isTrue = true;
                KeyValuePair<bool, Dictionary<int, bool>> pair = new KeyValuePair<bool, Dictionary<int, bool>>(isTrue, new Dictionary<int,bool>());
                featureDic.Add(featureDic.Count, pair);

                for (int i = 1; i < tokens.Length; i++)
                {
                    String[] feature = tokens[i].Split(':');
                    if (feature.Length == 1) continue;
                    int featureNum = Int32.Parse(feature[0]);
                    if (featureNum > featureTotalNum) featureTotalNum = featureNum;
                    if (!pair.Value.ContainsKey(featureNum))
                        pair.Value.Add(featureNum, true);
                }
            }
            return featureDic;
        }
    }
}
