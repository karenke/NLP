using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Wintellect.PowerCollections;
using Project2;

namespace kNN
{
    class Program
    {
        static void Main(string[] args)
        {
            Dictionary<int, KeyValuePair<bool, Dictionary<int, bool>>> trainData = Loader.load(@"C:\Code\NLP\Project5\train_feature.csv");
            Dictionary<int, KeyValuePair<bool, Dictionary<int, bool>>> testData = Loader.load(@"C:\Code\NLP\Project5\test_feature.csv");
            String output = "";
            int count = 0;
            foreach (KeyValuePair<bool, Dictionary<int, bool>> pair in testData.Values)
            {
                Console.WriteLine(count++);
                kNN.targetData = pair;
                output += kNN.FindkNN(trainData, 51) + "\n";
            }
            FileManager.Write(output, @"C:\Code\NLP\Project5\result.txt");
        }
    }
}
