/*
 * Name: Jose Terrones Jr.
 * Purpose: This file will handle the creation of the inverted 
 * index by using hashmaps that will contain the word and its
 * postings or chapters they are found in.
 */

package search_engine_2;

import java.util.*;

public class NaiveInvertedIndex {
   private HashMap<String, List<Integer>> mIndex;
   
   public NaiveInvertedIndex() {
      mIndex = new HashMap<String, List<Integer>>();
   }
   
   public void addTerm(String term, int documentID) {
	   if(mIndex.containsKey(term)){
		   if(getPostings(term).contains(documentID)){
			   return;
		   }
		   else{
			   getPostings(term).add(documentID);
		   }
	   }
	   else{
		   ArrayList<Integer> docID = new ArrayList();
		   docID.add(documentID);
		   mIndex.put(term, docID);
	   }
   }
   
   public List<Integer> getPostings(String term) {
      return mIndex.get(term);
   }
   
   public int getTermCount() {
      return mIndex.size();
   }
   
   public String[] getDictionary() {
	   String[] hashKeys;
	   hashKeys = mIndex.keySet().toArray(new String[0]);
	   Arrays.sort(hashKeys);
      return hashKeys;
   }
}
