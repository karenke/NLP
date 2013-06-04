using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Project2
{
    class Writer
    {
        public static void Write(String[] lines, String path)
        {
            String output = "";
            foreach (String line in lines)
            {
                output += line + "\r\n";
            }
            FileManager.Write(output, path);
        }
    }
}
