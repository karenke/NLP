package SVM;


import net.didion.jwnl.*;
import net.didion.jwnl.data.*;
import net.didion.jwnl.dictionary.*;

import java.io.*;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;

public class Stemmer {
	private int MaxWordLength = 50;
	private Dictionary dic;
	private MorphologicalProcessor morph;
	private boolean IsInitialized = false;  
	public HashMap AllWords = null;
	
	/**
	 * establishes connection to the WordNet database
	 */
	public Stemmer ()
	{
		AllWords = new HashMap ();
		
		try
		{
			JWNL.initialize(new FileInputStream
				("JWNLproperties.xml"));
			dic = Dictionary.getInstance();
			morph = dic.getMorphologicalProcessor();
			// ((AbstractCachingDictionary)dic).
			//	setCacheCapacity (10000);
			IsInitialized = true;
		}
		catch ( FileNotFoundException e )
		{
			System.out.println ( "Error initializing Stemmer: JWNLproperties.xml not found" );
		}
		catch ( JWNLException e )
		{
			System.out.println ( "Error initializing Stemmer: " 
				+ e.toString() );
		} 
		
	}
	
	public void Unload ()
	{ 
		dic.close();
		Dictionary.uninstall();
		JWNL.shutdown();
	}
	
	public String StemWordWithWordNet ( String word )
	{
		if ( !IsInitialized )
			return word;
		if ( word == null ) return null;
		if ( morph == null ) morph = dic.getMorphologicalProcessor();
		
		IndexWord w;
		try
		{
			w = morph.lookupBaseForm( POS.VERB, word );
			if ( w != null )
				return w.getLemma().toString ();
			w = morph.lookupBaseForm( POS.NOUN, word );
			if ( w != null )
				return w.getLemma().toString();
			w = morph.lookupBaseForm( POS.ADJECTIVE, word );
			if ( w != null )
				return w.getLemma().toString();
			w = morph.lookupBaseForm( POS.ADVERB, word );
			if ( w != null )
				return w.getLemma().toString();
		} 
		catch ( JWNLException e )
		{
		}
		return null;
	}
	
	public String Stem(String word)
	{
		// check if we already know the word
		String stemmedword = (String) AllWords.get(word);
		if ( stemmedword != null )
			return stemmedword; // return it if we already know it
		
		// don't check words with digits in them
		if (word.matches(".*\\d.*") == true)
			stemmedword = null;
		else	// unknown word: try to stem it
			stemmedword = StemWordWithWordNet (word);
		
		if ( stemmedword != null )
		{
			// word was recognized and stemmed with wordnet:
			// add it to hashmap and return the stemmed word
			AllWords.put( word, stemmedword );
			return stemmedword;
		}
		// word could not be stemmed by wordnet, 
		// thus it is no correct english word
		// just add it to the list of known words so 
		// we won't have to look it up again
		AllWords.put( word, word );
		return word;
	}
}
