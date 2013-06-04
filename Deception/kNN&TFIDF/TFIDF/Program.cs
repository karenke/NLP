using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Project2;

namespace HMM
{
    class Program
    {
        private static String inputPath = @"C:\Code\NLP\Project5\train.csv";
        private static String testPath = @"C:\Code\NLP\Project5\test.csv";
        private static Dictionary<int, KeyValuePair<int, String[]>> allDocs;
        private static Dictionary<int, Dictionary<int, KeyValuePair<int, String[]>>> allParagraphs;
        private static Dictionary<int, Dictionary<int, int>> paragraphCount;
        private static String[] title;
        private static FeatureFactory featureFactory = new FeatureFactory();
        static void Main(string[] args)
        {
            LoadkNN(inputPath);
            featureFactory.ExtractSentimentWord(allDocs);
            GenFeature(inputPath);
            GenFeature(testPath);
        }

        static void GenFeature(String path)
        {
            LoadkNN(path);
            String output = "";
            Dictionary<String, bool> validDic = new Dictionary<string, bool>();
            foreach (KeyValuePair<int, String[]> pair in allDocs.Values)
            {
                output += pair.Key + " ";
                foreach (String word in pair.Value)
                {
                    validDic.Clear();
                    if (featureFactory.IsSentimentWord(word) > 0)
                    {
                        if (!validDic.ContainsKey(word))
                        {
                            validDic.Add(word, true);
                            output += featureFactory.IsSentimentWord(word) + ":1 ";
                        }
                    }
                }
                output += "\n";
            }
            FileManager.Write(output, path.Replace(".", "_feature."));
        }

        static void LoadkNN(String path)
        {
            allDocs = new Dictionary<int, KeyValuePair<int, string[]>>();
            Parser parser = new Parser();
            paragraphCount = new Dictionary<int, Dictionary<int, int>>();
            String file = FileManager.Read(path);
            String[] paragraphs = file.Split('\n');
            allDocs = new Dictionary<int, KeyValuePair<int, string[]>>();
            for (int i = 1; i < paragraphs.Count(); i++)
            {
                System.Console.WriteLine(i);
                if (paragraphs[i] == "") continue;
                if (paragraphs[i].StartsWith("I")) continue;
                KeyValuePair<int, String[]> document = parser.parsekNN(paragraphs[i]);
                allDocs.Add(allDocs.Count, document);
            }
        }

        static void Load(String path)
        {
            Parser parser = new Parser();
            paragraphCount = new Dictionary<int, Dictionary<int, int>>();
            String file = FileManager.Read(path);
            String[] paragraphs = file.Split('[');
            title = new String[paragraphs.Length];
            for (int i = 1; i < paragraphs.Count(); i++)
            {
                int index = paragraphs[i].IndexOf('\n');
                if ((index < 0) || (index == paragraphs[i].Length - 1))
                {
                    paragraphs[i] = "";
                    continue;
                }
                title[i] = paragraphs[i].Substring(0, index);
                paragraphs[i] = paragraphs[i].Substring(index + 1);
            }
            allDocs = new Dictionary<int, KeyValuePair<int, string[]>>();
            allParagraphs = new Dictionary<int, Dictionary<int, KeyValuePair<int, string[]>>>();
            for (int i = 1; i < paragraphs.Count(); i++)
            {
                System.Console.WriteLine(i);
                KeyValuePair<Dictionary<int, KeyValuePair<int, String[]>>, Dictionary<int, int>> parsePair = parser.parseFile(paragraphs[i]);
                Dictionary<int, KeyValuePair<int, String[]>> documents = parsePair.Key;
                foreach (KeyValuePair<int, String[]> pair in documents.Values)  
                {
                    allDocs.Add(allDocs.Count, pair);
                }
                allParagraphs.Add(allParagraphs.Count, documents);
                paragraphCount.Add(paragraphCount.Count, parsePair.Value);
            }
        }
    }
}
