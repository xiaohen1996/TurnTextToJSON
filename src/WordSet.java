import java.util.*;

public class WordSet<K extends Comparable<K>, V> implements Counter<K, V> {

	SortedMap<K, List<V>> temp1 = new TreeMap<>();

	public String get(K word) {
		// Return values of a json entry for a word.

		// In other words, the returned String should contain:

		// - the count for the word,

		// - followed by a comma,

		// - followed by all unique occurrences of the word separated by commas.


		StringBuilder result = new StringBuilder(); // use a string builder to create the final string
		result.append(getCount(word) + ","); // first add the number of occurrences to result string. Ex. 3,
		
		for (V v : temp1.get(word)) {        // for each item in the arraylist that corresponds to the keyword, add them to the result string;
			if (v.equals(",")) {             // there are "," added to the following index of a word to identify each put operation Ex.3, is , IS , iS , , , 
				continue;                    // skip all the comma and only add the words into the result string
			}
			result.append(v.toString() + ",");    // add words to result string and add a comma right after for each word. comma is needed for JSON split.
		}
		return result.toString();                 // return the result string

	}
	public int getCount(K word) {             
		int commaCounter = 0;                 // initialize a counter variable
		for (V v : temp1.get(word)) {         // count how many comma in the arraylist, 
			                                  //because there is a comma added each time a word is put in no matter if that occurrence is unique or not.
			if (v.equals(",")) {
				commaCounter++;
			}
		}
		return commaCounter;                 // return the counter
	}
	public void put(K keyWord, V word) {              // add key/values pair into the map, here, keyWord and word are identical
		                                              //keyWord will be take care to its base Ex. calls -- call, CALL -- call.  call being the base.
		String string = keyWord.toString();
		String stringLowerCase = string.toLowerCase();    // takes care of all capital case of keyWord Ex. CAll -- call
		String regEx = "[\\p{Punct}]";                    // a regular expression used later to remove all punctuations Ex:  . ! ? etc ..
        if(stringLowerCase.length() == 2) {               // this is a unique case of taking care of word "is" this case will forward "is" to keyword "i"
        	if (stringLowerCase.substring(stringLowerCase.length() - 1).equals("s")   
        			// check the last letter of the word, if that letter is "s" that means this two letter word is "is"
					&& !stringLowerCase.contains("'s")) {                             
				stringLowerCase = stringLowerCase.substring(0, stringLowerCase.length() - 1);    // take out the substring before last letter "s".
			}
        }
		if (stringLowerCase.length() > 2) {                                                
			// here will take care all words that have more than 2 letters. 
			if (stringLowerCase.endsWith("s")       
			// takes care of words ends in "s" Ex. virus, cats, dogs..					
					&& !stringLowerCase.contains("'s")) {            
		    // there is a conflict of "s" and "'s" because both ends in "s". An extra condition check is needed Ex. John's -- John  but not  John's -- John'
				stringLowerCase = stringLowerCase.substring(0, stringLowerCase.length() - 1);   
			// take out the last letter "s" and use everything/substring before the "s" as keyWord Ex. viru, cat, dog
			} 			
			else if (stringLowerCase.endsWith("'s")     
					// here will takes care of words ending in "'s" or "ed" 
					|| stringLowerCase.substring(stringLowerCase.length() - 2).equals("ed")) {  
				    //  Ex. John's -- John ,  called -- call

				stringLowerCase = stringLowerCase.substring(0, stringLowerCase.length() - 2);  
				    // ignore the last two character ,"'s" or "ed" and use everything/substring before as the keyWord.                                                                                               
			} 		
			else if (stringLowerCase.substring(stringLowerCase.length() - 3).equals("ing")) { 
				// here will takes care of words ending in "ing"
				stringLowerCase = stringLowerCase.substring(0, stringLowerCase.length() - 3);   
				// ignore the last three character ,"'s" or "ed" and use everything/substring before as the keyWord.
			}		
			else if(stringLowerCase.substring(stringLowerCase.length() - 1).matches(regEx)) {   
				// here will determine if a punctuation is with the word, Ex. String. , ends in "." (period)		
				stringLowerCase = stringLowerCase.replaceAll(regEx, "");                        
				// replace all punctuation with empty string to remove them.
				if(stringLowerCase.substring(stringLowerCase.length() - 1).equals("s")) {       
					// a unique case of ending in "s" need to be consider Ex. Calls. -- Calls,  Calls will be the value, call will be the key		
					stringLowerCase = stringLowerCase.substring(0, stringLowerCase.length() - 1); 
					// the word's punctuation was removed already, further remove the last letter "s"
				}
			}	
		}
		if (!temp1.containsKey(stringLowerCase)) {                          // all stringLowerCase were took care after going through some above conditions and will be used as key of key/values pair
                                                                            // if key is not in the map,
			temp1.put((K) stringLowerCase, new ArrayList());                // create a new entry with value of an arraylist. this arraylist will store each unique occurence Ex. call Calling calls CAlling...

			temp1.get((K) stringLowerCase).add(word);                       // add word(the value) to this arraylist

			temp1.get((K) stringLowerCase).add((V) ",");                    // add a "," to arraylist for getCount() method.

		} else {                                                            // if key is in the map, 
			if (temp1.get((K) stringLowerCase).contains(word)) {            // check if this occurence happened already,
				temp1.get((K) stringLowerCase).add((V) ",");                // if yes, just add a "," for getCount() method;
			} else {
				temp1.get((K) stringLowerCase).add(word);                   // if no, add this unique occurence into arraylist and add a ","
				temp1.get((K) stringLowerCase).add((V) ",");
			}
		}
	}
	public Set<K> keySet() {             // call the predefined method keySet() of SortedMap/ TreeMap class
		return (Set<K>) temp1.keySet(); // call and return the predefined method keySet() of SortedMap/ TreeMap class
	}

}