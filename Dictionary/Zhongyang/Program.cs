using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Project2
{
    class Program
    {
        static void Main(string[] args)
        {
            String[] data = Reader.ReadData(@"C:\Code\NLP\Project2\TestData.data");
            //String[] dictionary = Reader.ReadDictionary(@"C:\Code\NLP\Project2\Dictionary.xml");
            Writer.Write(data, @"C:\Code\NLP\Project2\TunedTestData.data");
            //Writer.Write(dictionary, @"C:\Code\NLP\Project2\TunedDictionary.xml");
        }
    }
}
