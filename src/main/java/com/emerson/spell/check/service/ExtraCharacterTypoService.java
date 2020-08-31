package com.emerson.spell.check.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ExtraCharacterTypoService {

	@Value("${max_adjacente_repetiton}")
	private int max_adjacente_repetiton;

	/**
	 * Suggest some words if the word has a typo of extra adjacent duplicated character
	 * @param word
	 * @return
	 */
	private Set<String> removeOneAdjacentDuplicateTypo(String word, Set<String> suggestions){

		int numberOfLetters = word.length();
		Set<String> newSuggestions = new HashSet<>();

		if (numberOfLetters > 1) {
			char[] wordChar = word.toLowerCase().toCharArray();
			char previous = '\0';
			for (int i=1; i < numberOfLetters; i++) {
				if (wordChar[i-1] == wordChar[i] && wordChar[i] != previous ) {
					StringBuilder sbWord = new StringBuilder(word);
					String newWord = sbWord.deleteCharAt(i).toString();
					if (!suggestions.contains(newWord)) {
						newSuggestions.add(newWord);
					}
				}
				previous = wordChar[i-1];
			}
		}
		//suggestions is empty in case the word doens't have this kind of typo 
		return newSuggestions;
	}

	public Map<Integer, Set<String>> getSugestionsMultipleAdjacentDuplicateTypo(String word){

		Map<Integer, Set<String>> suggestions = new HashMap<>();
		if (word == null) {
			return suggestions;
		}

		//get suggestions removing only one character of an adjacent duplicate typo 
		Set<String> suggestionsSet = removeOneAdjacentDuplicateTypo(word, new HashSet<>());
		// round variable is to count the number of characters that we removed from the original word
		// that is a safety measure to not allow a bad case to brake the performance.
		Integer round = 1;
		
		//if suggestionsSet is empty it means that there is no duplicated character
		if (suggestionsSet.isEmpty()) {
			return suggestions;
		}
		
		suggestions.put(round, suggestionsSet);

		while (round < max_adjacente_repetiton) {
			//Create a new Set to store each round suggestions separately
			//it is necessary for optimization during the search on the dictionary
			suggestionsSet = new HashSet<>();
			for (String suggestedWord : suggestions.get(round)) {
				suggestionsSet.addAll(removeOneAdjacentDuplicateTypo(suggestedWord, suggestionsSet));
			}
			round++;
			if (suggestionsSet.isEmpty()) {
				//if this round doesn't have any suggestion that means that there is no more duplicated char to remove
				return suggestions;
			}
			suggestions.put(round, suggestionsSet);
		} 
		return suggestions;
	}

}
