/*
 * Name: Jose Terrones Jr.
 * Purpose: Using regex the porter stemmer will modify words
 * into a more basic form that will allow for easier retireval
 * of words when they are searched.
 */

package search_engine_2;

import java.util.regex.*;

public class PorterStemmer {
	static String[] step2a = {"ational","tional","enci","anci","izer","abli","alli","entli","eli","ousli","ization"
			,"ator","alism","iveness","fulness","ousness","aliti","iviti","biliti"};
	static String[] step2b = {"ate","tion","ence","ance","ize","able","al","ent","e","ous","ize","ate","ate"
			,"al","ive","ful","ous","al","ive","ble"};
	static String[] step3a = {"icate","ative","alize","iciti","ical","ful","ness"};
	static String[] step3b = {"ic","","al","ic","ic","",""};
	static String[] step4a = {"al","ance","ence","er","ic","able","ible","ant","ement","ment","ent"
		,"sion","tion","ou","ism","ate","iti","ous","ive","ize"};
	static String[] step4b = {"", "","","","","","","","","","","s","t","","","","","","",""};

   // a single consonant
   private static final String c = "[^aeiou]";
   // a single vowel
   private static final String v = "[aeiouy]";

   // a sequence of consonants; the second/third/etc consonant cannot be 'y'
   private static final String C = c + "[^aeiouy]*";
   // a sequence of vowels; the second/third/etc cannot be 'y'
   private static final String V = v + "[aeiou]*";

   // this regex pattern tests if the token has measure > 0 [at least one VC].
   //private static final Pattern mGr0 = Pattern.compile("^(" + C + ")?" + V + C);
   
   private static final Pattern mGr0 = Pattern.compile("^(" + C + ")?" +
    "(" + V + C + ")+(" + V + ")?");

   // add more Pattern variables for the following patterns:
   // m equals 1: token has measure == 1
   private static final Pattern mEq1 = Pattern.compile(mGr0 + "(" + V + ")?$");
   // m greater than 1: token has measure > 1
   private static final Pattern mGr1 = Pattern.compile("^(" + C + ")?" + 
   "(" + V + C + V + C + ")+(" + V + ")?");
   // vowel: token has a vowel after the first (optional) C
   private static final Pattern has_Vowel = Pattern.compile(V);
   // double consonant: token ends in two consonants that are the same, 
   //			unless they are L, S, or Z. (look up "backreferencing" to help 
   //			with this)
   private static final Pattern dC = Pattern.compile("(.*)?([^aeioulsz])\\2$");
   // m equals 1, Cvc: token is in Cvc form, where the last c is not w, x, 
   //			or y.
   private static final Pattern aME1 = Pattern.compile("(" + C + ")?" + v + "[^aieouwxy]$");
   
   public static void main(String[] args) {
      //processToken("caca");
      System.out.println(processToken("rational"));
   }
   public static String processToken(String token) {
      //System.out.println(mGr0.pattern());
      if (token.length() < 3) {
         return token; // token must be at least 3 chars
      }
      // step 1a
      if (token.endsWith("sses")) {
         token = token.substring(0, token.length() - 2);
      }
      else if(token.endsWith("ies")){
    	  token = token.substring(0, token.length()-2);
      }
      else if(token.endsWith("s")){
    	  if(token.endsWith("ss")){
    		  token = token.substring(0, token.length());
    	  }
    	  else{
    		  token = token.substring(0, token.length()-1);  
    	  }
      }
      // step 1b
      boolean doStep1bb = false;
      //		step 1b
      if (token.endsWith("eed")) { // 1b.1
         // token.substring(0, token.length() - 3) is the stem prior to "eed".
         // if that has m>0, then remove the "d".
         String stem = token.substring(0, token.length() - 3);
         if (mGr0.matcher(stem).find()) { // if the pattern matches the stem
            token = stem + "ee";
         }
      }
      if(token.endsWith("ed")){ //1b.2
    	  String stem = token.substring(0, token.length()-2);
    	  if(mGr0.matcher(stem).find()){
    		  token = token.substring(0, token.length()-2);
    		  doStep1bb = true;
    	  }
      }
      if(token.endsWith("ing")){ //1b.3
    	  String stem = token.substring(0,token.length()-3);
    	  if(mGr0.matcher(stem).find()){
    		  token = token.substring(0, token.length()-3);
    		  doStep1bb = true;
    	  }
      }
      // step 1b*, only if the 1b.2 or 1b.3 were performed.
      if (doStep1bb) {
         if (token.endsWith("at") || token.endsWith("bl")
          || token.endsWith("iz")) {
            token = token + "e";
         }
         String stem = token.substring(0,token.length());
         if (dC.matcher(stem).find()){
        	 token = token.substring(0, token.length()-1);
         }
         if(aME1.matcher(stem).find()){
        	 token = token + "e";
         }
      }
      // step 1c
      // program this step. test the suffix of 'y' first, then test the 
      // condition *v*.
      if(token.endsWith("y")){
    	  String stem = token.substring(0,token.length()-1);
    	  if(has_Vowel.matcher(stem).find()){
    		  token = token.substring(0,token.length()-1) + "i";
    	  }
      }
      // step 2
      for(int i = 0; i < step2a.length; i++){
    	  String suffix = step2a[i];
    	  String newSuffix = step2b[i];
    	  if(token.endsWith(suffix)){
    		  if(mGr0.matcher(token.substring(0,token.length() - suffix.length())).find()){
    			  token = token.substring(0, token.length() - suffix.length()) + newSuffix;
    		  }
    	  }
      }
      // step 3
      for(int i = 0; i < step3a.length; i++){
    	  String suffix = step3a[i];
    	  String newSuffix = step3b[i];
    	  if(token.endsWith(suffix)){
    		  if(mGr0.matcher(token.substring(0,token.length()-suffix.length())).find()){
    			  token = token.substring(0, token.length() - suffix.length()) + newSuffix;
    		  }
    	  }
      }
      // step 4
      for(int i = 0; i < step4a.length; i++){
    	  String suffix = step4a[i];
    	  String newSuffix = step4b[i];
    	  if(token.endsWith(suffix)){
    		  if(mGr1.matcher(token.substring(0,token.length()-suffix.length())).find()){
    			  token = token.substring(0,token.length() - suffix.length())+newSuffix;
    		  }
    	  }
      }
      // step 5
      if(token.endsWith("e")&& mGr1.matcher(token.substring(0,token.length()-1)).find()){
    	  token = token.substring(0,token.length()-1);
      }
      else if(token.endsWith("e") && mEq1.matcher(token.substring(0,token.length()-1)).find()
    		  && aME1.matcher(token.substring(0,token.length()-1)).find()){
    	  token = token.substring(0,token.length()-1);
      }
      else if(token.endsWith("ll") && mGr1.matcher(token.substring(0,token.length()-1)).find()){
    	  token = token.substring(0,token.length()-1);
      }
      return token;
   }
}
