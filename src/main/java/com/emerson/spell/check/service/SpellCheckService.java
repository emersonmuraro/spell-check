package com.emerson.spell.check.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emerson.spell.check.model.Dictionary;
import com.emerson.spell.check.model.Typo;
import com.emerson.spell.check.repository.DictionaryRepository;
import com.emerson.spell.check.repository.TypoRepository;

@Service
public class SpellCheckService {
	
	@Autowired
    private DictionaryRepository dictionaryRepository;
	
	@Autowired
    private TypoRepository typoRepository;
	
	@Autowired
    private ExtraCharacterTypoService extraCharacterTypoService;
	
	/**
	 * Word spell check method 
	 * @param word it a single word to verify
	 * @return the correct word if found or a empty string if not
	 * @throws Exception if the method receive invalid parameter 
	 */
	public String verifyWord(String word) throws Exception {
		
		if (word == null || word.isEmpty()) {
			throw new Exception("Invalid parameter");
		}
		
		//verifying on the Dictionary if the word is already correct
		List<Dictionary> dictionaryList =  dictionaryRepository.findByWord(word);
		if (!dictionaryList.isEmpty()) {
			//the word is correct and it was found on the dictionary
			return dictionaryList.get(0).getWord();
		}
		
		//If this typo was solved before, it should be on the typo table associated with the correct word
		List<Typo> typoList = typoRepository.findByTypo(word);
		if (!typoList.isEmpty() 
				&& typoList.get(0).getDictionary() != null 
				&& !typoList.get(0).getDictionary().getWord().isEmpty()) {
			return typoList.get(0).getDictionary().getWord();
		}
		
		Map<Integer, Set<String>> wordVariatios = getSuggestion(word);
		
		Dictionary correctWord = verifyDictionary(wordVariatios);
		
		if (correctWord != null) {
			saveSuggestion(word, correctWord);
		}
		
		return correctWord.getWord();
	}
	
	
	private Map<Integer, Set<String>> getSuggestion(String word){
		/*
		 * The idea is to verify the word based on common errors and each one of this strategy could be developed in a their specific class
		 * with their logic and here we could call each one in a different thread, waiting the result of each one   
		 */
		Map<Integer, Set<String>> wordVariatios = extraCharacterTypoService.getSugestionsMultipleAdjacentDuplicateTypo(word);
		
		return wordVariatios;
	}
	
	private Dictionary verifyDictionary(Map<Integer, Set<String>> suggestions) {
		for (Map.Entry<Integer, Set<String>> entry : suggestions.entrySet()) {
			List<Dictionary> result = dictionaryRepository.findDictionaryByWords(entry.getValue());
			if (!result.isEmpty() && !result.get(0).getWord().isEmpty()) {
				return result.get(0);
			}
		}
		return null;
	}
	
	private void saveSuggestion(String typo, Dictionary dictionary){
		typoRepository.save(new Typo(typo, dictionary));
	}

}
