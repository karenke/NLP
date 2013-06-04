using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace Project1
{
    class Reader
    {
        public Dictionary<String, int> ReadUni(String path)
        {
            Dictionary<String, int> uniDic = new Dictionary<string, int>();
             string[] lines = File.ReadAllLines(path);
             foreach (string line in lines)
             {
                 string[] tokens = line.Split(' ');
                 uniDic.Add(tokens[0], Int32.Parse(tokens[2]));
             }
             uniDic.Add("END", 40000);
             return uniDic;
        }

        public Dictionary<String, Dictionary<String, int>> ReadBinary(String path)
        {
            Dictionary<String, Dictionary<String, int>> binaryDic = new Dictionary<string, Dictionary<string, int>>();
            string[] lines = File.ReadAllLines(path);
            foreach (string line in lines)
            {
                string[] tokens = line.Split(' ');
                Dictionary<String, int> pair;
                if (binaryDic.ContainsKey(tokens[0]))
                {
                    binaryDic.TryGetValue(tokens[0], out pair);
                }
                else
                {
                    pair = new Dictionary<string, int>();
                    binaryDic.Add(tokens[0], pair);
                }
                int count = 0;
                if (pair.ContainsKey(tokens[1]))
                {
                    pair.TryGetValue(tokens[1], out count);
                    count++;
                    pair.Remove(tokens[1]);
                }
                else
                {
                    count = 1;
                }
                pair.Add(tokens[1], count);
            }
            return binaryDic;
        }
    }
}
