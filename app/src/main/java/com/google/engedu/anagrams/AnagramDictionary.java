package com.google.engedu.anagrams;

import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import static java.util.Arrays.sort;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
   public  ArrayList<String> wordList = new ArrayList<String>();
   public HashSet<String> wordset = new HashSet<String>();
   // public HashMap<String> lettertoword = new HashMap();
    //public  HashMap<String,ArrayList<String> > lettertoword=new HashMap<String,ArrayList<String>>();
    public  HashMap<String,ArrayList<String> > lettertoword=new HashMap<String,ArrayList<String>>();
    private int wordLength = DEFAULT_WORD_LENGTH;
    public HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<Integer, ArrayList<String>>();


    private Random random = new Random();
    private List<String> sorted = new ArrayList<String>();

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();

            wordset.add(word);
            wordList.add(word);

            if(lettertoword.containsKey(sortLetters(word))){
                ArrayList<String> anagram = lettertoword.get(sortLetters(word));
                anagram.add(word);
                lettertoword.put(sortLetters(word),anagram);

            }else
            {
                ArrayList<String> anagram = new ArrayList<String>();
                anagram.add(word);
                lettertoword.put(sortLetters(word),anagram);
            }
            if (sizeToWords.containsKey(word.length())) {
                sizeToWords.get(word.length()).add(word);
            } else {
                ArrayList<String> temp = new ArrayList<>();
                temp.add(word);
                sizeToWords.put(word.length(), temp);
            }




        }
        Log.d("keyset",lettertoword.keySet().toString());
    }

    public boolean isGoodWord(String word, String base) {
        if (word.contains(base)){
            return false;
        }
        if(wordset.contains(word)){
            return true;
        }

        return false;
    }

    public List<String> getAnagrams(String targetWord) {
      ArrayList<String> result = new ArrayList<String>();
        String temp = sortLetters(targetWord);
      /*  for(int i=0;i<wordList.size();i++)
        {
            if(wordList.get(i).length() == targetWord.length())
            {
                if(temp.equals(sortLetters(wordList.get(i))))
                    result.add(wordList.get(i));
            }
        }*/
        return lettertoword.get(temp);

    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for(char ch = 'a'; ch <= 'z';ch++) {
            String addch=word + ch;
           String temp= sortLetters(addch);
            if (lettertoword.containsKey(temp)) {
                ArrayList<String> listAnagrams = lettertoword.get(temp);
                for (int i = 0; i < listAnagrams.size(); i++) {
                    if (isGoodWord(listAnagrams.get(i),word)) {
                        result.add(listAnagrams.get(i));
                    }
                     }

            }

        }

        return result;
    }

        public String pickGoodStarterWord() {
           String tempStartWord = null;
            String sortTempStartWord = null;
            int numberOfAnagram = 0;
            ArrayList<String> StarterWords;
            StarterWords = new ArrayList<String>();
            do{

                StarterWords = sizeToWords.get(wordLength);
                tempStartWord = StarterWords.get(random.nextInt(StarterWords.size()));
                sortTempStartWord = sortLetters(tempStartWord);
                numberOfAnagram = getAnagramsWithOneMoreLetter(tempStartWord).size();

            tempStartWord = wordList.get(random.nextInt(wordList.size()));
            sortTempStartWord = sortLetters(tempStartWord);
            numberOfAnagram = lettertoword.get(sortTempStartWord).size();

            }while (numberOfAnagram <= MIN_NUM_ANAGRAMS);
            Log.d("test", "numberofAnagram: "+numberOfAnagram);
            if(wordLength <= MAX_WORD_LENGTH){
                wordLength++;
            }
            return tempStartWord;
        }

    public String sortLetters(String word)
    {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

}
