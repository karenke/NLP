using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Project1
{
    class Writer
    {
        public void write(string[] sentences, string path)
        {
            String output = "";
            for (int i = 0; i < sentences.Count(); i++)
            {
                output += sentences[i] + "\r\n";
            }
            FileManager.Write(output, path);
        }
    }
}
