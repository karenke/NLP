using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Project1
{
    class Program
    {
        static void Main(string[] args)
        {
            Dictionary<String, Dictionary<String, int>> trainingData;
            Reader reader = new Reader();
            RandomSentenceGen generator = new RandomSentenceGen();
            /*
            trainingData = reader.ReadBinary(@"C:\Users\Bluner\Dropbox\LM\data\bigrams\WSJ-Bi");
            string[] sentences = generator.GenerateBinarySentences(trainingData, 100);
             * */
            string[] sentences = generator.GenerateUniSentences(reader.ReadUni(@"C:\Users\Bluner\Dropbox\LM\data\Unigram\WSJ-Uni"), 100);
            Writer writer = new Writer();
            writer.write(sentences, @"C:\Code\NLP\Project1\Uni-WSJ-Bi.txt");
        }
    }
}
