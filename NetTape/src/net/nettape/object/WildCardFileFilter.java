package net.nettape.object;

import java.io.*;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.regex.*;

public class WildCardFileFilter implements FileFilter 
{
	private String _pattern;
	 
    public WildCardFileFilter(String pattern, String name)
    {
        _pattern = pattern.replace("*", ".*").replace("?", ".") + forRegex(name);
    }
 
    public boolean accept(File file)
    {
    	return Pattern.compile(_pattern).matcher(file.getName()).find();
    }
    
    public String forRegex(String aRegexFragment){
        final StringBuilder result = new StringBuilder();

        final StringCharacterIterator iterator = 
          new StringCharacterIterator(aRegexFragment)
        ;
        char character =  iterator.current();
        while (character != CharacterIterator.DONE ){
          /*
           All literals need to have backslashes doubled.
          */
          if (character == '.') {
            result.append("\\.");
          }
          else if (character == '\\') {
            result.append("\\\\");
          }
          else if (character == '?') {
            result.append("\\?");
          }
          else if (character == '*') {
            result.append("\\*");
          }
          else if (character == '+') {
            result.append("\\+");
          }
          else if (character == '&') {
            result.append("\\&");
          }
          else if (character == ':') {
            result.append("\\:");
          }
          else if (character == '{') {
            result.append("\\{");
          }
          else if (character == '}') {
            result.append("\\}");
          }
          else if (character == '[') {
            result.append("\\[");
          }
          else if (character == ']') {
            result.append("\\]");
          }
          else if (character == '(') {
            result.append("\\(");
          }
          else if (character == ')') {
            result.append("\\)");
          }
          else if (character == '^') {
            result.append("\\^");
          }
          else if (character == '$') {
            result.append("\\$");
          }
          else {
            //the char is not a special one
            //add it to the result as is
            result.append(character);
          }
          character = iterator.next();
        }
        return result.toString();
      }


}
