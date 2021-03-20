import java.util.*;

class Utility {
    public static void main(String args[]){
        Counter<String, String> x = new WordSet<String, String>();
        Utility.run(x);
     }
   public static void run(Counter<String, String> words){
      //Variables
      Counter<String, String> sortedWords = new WordSet<String, String>();
      Scanner scnr = new Scanner(System.in);
      String[] testInput = "virus virus's viruss virusing ".split("[\\s\t\n]");
      // I felt happy because I saw the others were happy and because I knew I should feel happy, but I wasn¡¯t really happy.
      //Read in input
      if(scnr.hasNext()){
    	  System.out.println("User Input\n***************************");
    	  while (scnr.hasNext()) {
    		String word = scnr.next().replaceAll("[^a-zA-Z\\d ]+$", "");
    		
    	  	words.put(word, word);
    	  
    	  	
    	  }
      }
      //Default test case
      else {
    	  System.out.println("Default Input\n***************************");
    	  for(String word : testInput) {
			  words.put(word, word.replaceAll("[^a-zA-Z\\d ]+$", ""));
//			  System.out.println(word);
//		
    	  }
      }
      //Get keys and sort list
      ArrayList<String> sortedKeys = new ArrayList<String>(words.keySet());     
      Collections.sort(sortedKeys);  
      
      //Instance JSON Object to store keys
      JSONObject wordList = new JSONObject();
      
      // Display the TreeMap which is naturally sorted 
      for (String w : sortedKeys){
         if (words.getCount(w) >= 3){
            String[] strVals = words.get(w).split(",");
            ArrayList<String> vals = new ArrayList<String>();
            //Split instances of word and create array
            for(int i = 1; i < strVals.length; i++)
               vals.add(strVals[i]);
            //Sort vals ArrayList
            Collections.sort(vals);
            //Instance JSON Object
            JSONObject wordItemsObj = new JSONObject();
            //Add entry for unique forms
            wordItemsObj.put("forms", vals);
            //Add entry for count
            wordItemsObj.put("count", words.get(w).split(",")[0]);
            wordList.put(w,wordItemsObj);
         }
      }
      //Output JSON
      System.out.println(wordList);
   }
}