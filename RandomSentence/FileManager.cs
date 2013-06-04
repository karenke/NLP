using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace Project1
{
    public class FileManager
    {
        public static String Read(String path)
        {
            String ans = "";
            StreamReader reader;
            try
            {
                reader = File.OpenText(path);
            }
            catch (Exception e)
            {
                return "0";
            }
            String line = "";
            //Console.Out.WriteLine("Begin read");
            while ((line = reader.ReadLine()) != null)
            {
                ans += line + "\n";
            }
            reader.Close();
            return ans;
        }

        public static void Write(String content, String path)
        {
            File.Delete(path);
            FileStream fs = new FileStream(path, FileMode.Create);
            StreamWriter writer = new StreamWriter(fs);
            writer.Write(content);
            writer.Close();
        }
    }
}
