using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;

namespace Project2
{
    public class BracketFilter
    {
        public static String ExtractArgInsideBracket(String content, String argName)
        {
            Regex reg = new Regex(@argName);
            Match match = reg.Match(content);
            if (match.Success)
            {
                reg = new Regex("\".*?\"");
                match = reg.Match(content, match.Index + match.Value.Length);
                if (match.Success) return match.Value.Substring(1, match.Value.Length - 2);
            }
            return "";
        }

        public static String removeFirstRoundBracket(String content)
        {
            Regex reg = new Regex(@"\(.*?\)");
            Match match = reg.Match(content);
            if (match.Success)
            {
                return content.Substring(match.Index + match.Value.Length);
            }
            return content;
        }

        public static String SubStringuntilDiv(String content, int startPos)
        {
            Regex reg = new Regex(@"</div>");
            Match match = reg.Match(content, startPos);
            if (!match.Success) return "";
            return content.Substring(startPos, match.Index - startPos - 1);
        }

        public static String extractImgURL(String content)
        {
            Regex imgReg = new Regex(@"<img.*?/>");
            Match imgMatch = imgReg.Match(content);
            Regex reg = new Regex(@"http://.*?" + "\"");
            if (imgMatch.Success)
            {
                imgMatch = reg.Match(imgMatch.Value);
                if (imgMatch.Success) return imgMatch.Value.Substring(0, imgMatch.Value.Length - 1);
            }
            return "";
        }


        public static String extractURL(String content)
        {
            Regex reg = new Regex(@"http://.*?" + "\"");
            Match match = reg.Match(content);
            if (match.Success)
            {
                return match.Value.Substring(0, match.Value.Length - 1);
            }
            return "";
        }

        public static List<String> extractURLs(String content)
        {
            List<String> urls = new List<String>();
            Regex reg = new Regex(@"http://.*?" + "\"");
            Match match = reg.Match(content);
            if (match.Success)
            {
                urls.Add(match.Value.Substring(0, match.Value.Length - 1));
                match = match.NextMatch();
            }
            return urls;
        }

        public static String ReplaceChar(String content, String source, String target)
        {
            if (content == null) return "";
            Regex reg = new Regex(source);
            Match match = reg.Match(content);
            while (match.Success)
            {
                content = content.Remove(match.Index, match.Length);
                content = content.Insert(match.Index, target);
                match = reg.Match(content);
            }
            return content;
        }

        public static String Replacenbsp(String content)
        {
            return ReplaceChar(content, @"&nbsp;", " ");
        }

        //Filter <pattern>xxxxx</pattern>
        public static String PurePatternFilter(String content, String pattern)
        {
            String ans = "";
            Regex reg = new Regex(@"<" + pattern + @">(.|\n|\t)*?</" + pattern + @">");
            Match match = reg.Match(content);
            while (match.Success)
            {
                ans += match.Value;
                match = match.NextMatch();
            }
            return ans;
        }

        public static String PatternFilter(String content, String pattern)
        {
            String ans = "";
            Regex reg = new Regex(@"<" + pattern + @"[^<^>]*?" + @">(.|\n|\t)*?</" + pattern + @">");
            Match match = reg.Match(content);
            while (match.Success)
            {
                ans += match.Value;
                match = match.NextMatch();
            }
            return ans;
        }

        public static String PurePatternFilterWithNoBracketInIt(String content, String pattern)
        {
            String ans = "";
            Regex re = new Regex(@"<" + pattern + @">[^<]*?</" + pattern + ">");
            Match match = re.Match(content);
            while (match.Success)
            {
                ans += match.Value;
                match = match.NextMatch();
            }
            return ans;
        }

        public static String RemoveComment(String content)
        {
            Regex reg = new Regex(@"<!.*?>");
            Match match = reg.Match(content);

            while (match.Success)
            {
                content = content.Remove(match.Index, match.Length);
                match = reg.Match(content);
            }

            reg = new Regex(@"/\*.*?\*/");
            match = reg.Match(content);
            while (match.Length != 0)
            {
                content = content.Remove(match.Index, match.Length);
                match = reg.Match(content);
            }
            return content;
        }

        public static String SubContentWithArgument(String content, String arg, int startPos = 0)
        {
            return (SubContent(content, FindIndexOfDivWithArgument(content, arg, startPos)));
        }

        //find such like <div content="abc">
        public static int FindIndexOfDivWithArgument(String content, String arg, int startPos = 0)
        {
            if (startPos >= content.Length) return content.Length;
            String regString = @"<[^>]*?" + arg + @".*?>";
            Regex reg = new Regex(regString);
            Match match = reg.Match(content, startPos);
            if (!match.Success) return content.Length;
            return match.Index;
        }

        //Remove all <d> </d> <asdfasdf>
        public static String RemoveBracket(String content)
        {
            Regex reg = new Regex(@"<.*?>");
            Match match = reg.Match(content);
            while (match.Length != 0)
            {
                content = content.Remove(match.Index, match.Length);
                match = reg.Match(content);
            }
            return content;
        }

        public static String RemoveBracketPattern(String content, String pattern)
        {
            String regString = @"<" + pattern + @".*?</" + pattern + @">";
            Regex reg = new Regex(regString);
            Match match = reg.Match(content);
            while (match.Length != 0)
            {
                content = content.Remove(match.Index, match.Length);
                match = reg.Match(content);
            }
            return content;
        }

        public static String RemoveBracketWithP(String content)
        {
            Regex re = new Regex(@"<.*?>");
            Match match = re.Match(content);
            //while (match
            while (match.Success)
            {
                content = content.Remove(match.Index, match.Length);
                if (match.Value == "</p>") content = content.Insert(match.Index, "\n");
                match = re.Match(content);
            }
            return content;
        }

        //automaticlly match like <d> </d> <p> </p>
        public static String SubContent(String content, int startPos)
        {
            int count = 0;
            Regex beginReg = new Regex(@"<.*?>");
            Match beginMatch = beginReg.Match(content, startPos);
            if (!beginMatch.Success) return "";
            int start = beginMatch.Index;
            int end = 0;
            Regex endReg = new Regex(@"</.*?>");
            Match endMatch = endReg.Match(content, beginMatch.Index + beginMatch.Length);
            while (endMatch.Index == beginMatch.Index)
            {
                beginMatch = beginMatch.NextMatch();
                endMatch = endMatch.NextMatch();
            }
            count = 1;
            if (isOneBracket(beginMatch.Value)) count--;
            while ((endMatch.Success) && (count > 0))
            {
                beginMatch = beginMatch.NextMatch();
                if (isOneBracket(beginMatch.Value))
                {
                    continue;
                }
                while ((beginMatch.Success) && (beginMatch.Index < endMatch.Index))
                {
                    beginMatch = beginMatch.NextMatch();
                    while ((beginMatch.Success) && isOneBracket(beginMatch.Value))
                    {
                        beginMatch = beginMatch.NextMatch();
                    }
                    count++;
                }
                end = endMatch.Index + endMatch.Length;
                endMatch = endMatch.NextMatch();
                count--;
            }
            if (count > 0) end = content.Length;
            if (end == 0) end = content.Length;
            return content.Substring(start, end - start);
        }

        public static bool isOneBracket(String content)
        {
            if (content.Substring(content.Length - 2, 1) == "/") return true;
            if ((content.Length >= 4) && (content.Substring(0, 4) == "<img")) return true;
            return false;
        }

    }
}
