/*
 * Name: Jose Terrones Jr.
 * Purpose: Writes the values of the items into the respective files
 * created in the IndexWriter.
 */

package search_engine_3;

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
