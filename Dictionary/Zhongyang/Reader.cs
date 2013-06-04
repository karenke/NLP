using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Project2
{
    class Reader
    {
        public static String[] ReadData(String path)
        {
            String fileString = FileManager.Read(path);
            String[] lines = fileString.Split('\n');
            String[] output = new String[lines.Length];
            for (int i = 0; i < lines.Length; i++)
            {
                String[] tokens = lines[i].Split(' ');
                if (tokens.Length < 7)
                {
                    output[i] = lines[i];
                    continue;
                }
                output[i] = "";
                for (int j = 0; j < 1; j++)
                    output[i] += tokens[j] + " ";
                for (int j = 1; j < tokens.Length; j++)
                {
                    String token = Filter.Execute(tokens[j]);
                    if (token.Equals("")) continue;
                    output[i] += token + " ";
                }
            }
            return output;
        }

        public static String FilterLine(String line)
        {
            line = line.Replace('\"', ' ');
            line = line.Replace('(', ' ');
            line = line.Replace(')', ' ');
            line = line.Replace('.', ' ');
            line = line.Replace(',', ' ');
            line = line.Replace('\'', ' ');
            line = line.Replace(';', ' ');
                if (line.Equals("")) return line;
                String[] tokens = line.Split(' ');
                String output = "";
                for (int j = 0; j < tokens.Length; j++)
                {
                    String token = Filter.Execute(tokens[j]);
                    if (token.Equals("")) continue;
                    output += token + " ";
                }
                if (output.Length > 0)
                    output = output.Substring(0, output.Length - 1);
                return output;
        }

        public static String[] ReadDictionary(String path)
        {
            String fileString = FileManager.Read(path);
            String[] lines = fileString.Split('\n');
            String[] output = new String[lines.Length];
            for (int i = 0; i < lines.Length; i++)
            {
                int index = lines[i].IndexOf("gloss=\"");
                if (index == -1)
                {
                    output[i] = lines[i];
                    continue;
                }
                output[i] = lines[i].Substring(0, index + 7);
                int lastIndex = lines[i].IndexOf("\"/>", index + 7);
                output[i] += FilterLine(lines[i].Substring(index + 7, lastIndex - index - 7));
                output[i] += lines[i].Substring(lastIndex, lines[i].Length - lastIndex);
            }
            return output;
        }
    }
}
