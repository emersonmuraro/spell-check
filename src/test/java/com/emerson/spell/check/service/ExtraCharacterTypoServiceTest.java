package com.emerson.spell.check.service;



import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExtraCharacterTypoServiceTest{

	@Autowired 
	private ExtraCharacterTypoService extraCharacterTypoService;
	
	
	@Test
	void when_emptyOrNullString_should_returnEmpty() {
		Map<Integer, Set<String>> suggestions = extraCharacterTypoService.getSugestionsMultipleAdjacentDuplicateTypo("");
		assertTrue(suggestions.isEmpty());
		
		suggestions = extraCharacterTypoService.getSugestionsMultipleAdjacentDuplicateTypo(null);
		assertTrue(suggestions.isEmpty());
	}
	
	@Test
	void when_noAdjacentDuplicateTypo_should_returnEmpty() {
		Map<Integer, Set<String>> suggestions = extraCharacterTypoService.getSugestionsMultipleAdjacentDuplicateTypo("test");
		assertTrue(suggestions.isEmpty());
	}
	
	@Test
	void when_onlyOneCharWord_should_returnEmpty() {
		Map<Integer, Set<String>> suggestions = extraCharacterTypoService.getSugestionsMultipleAdjacentDuplicateTypo("a");
		assertTrue(suggestions.isEmpty());
	}
	
	@Test
	void when_onlyOneAdjacentDuplicateTypo_should_returnOnlyOneSuggestion() {
		Map<Integer, Set<String>> suggestions = extraCharacterTypoService.getSugestionsMultipleAdjacentDuplicateTypo("tesst");
		assertTrue(!suggestions.isEmpty());
		assertTrue(suggestions.size()==1);
		assertTrue(suggestions.get(1).size()==1);
		assertTrue(suggestions.get(1).contains("test"));
	}
	
	@Test
	void when_twoDifferentAdjacentDuplicateTypo_should_returnTwoSuggestions() {
		Map<Integer, Set<String>> suggestions = extraCharacterTypoService.getSugestionsMultipleAdjacentDuplicateTypo("tesstt");
		assertTrue(!suggestions.isEmpty());
		assertTrue(suggestions.size()==2);
		assertTrue(suggestions.get(1).size()==2);
		assertTrue(suggestions.get(1).contains("testt"));
		assertTrue(suggestions.get(1).contains("tesst"));
		assertTrue(suggestions.get(2).size()==1);
		assertTrue(suggestions.get(2).contains("test"));
		
		suggestions = extraCharacterTypoService.getSugestionsMultipleAdjacentDuplicateTypo("appplicationn");
		assertTrue(!suggestions.isEmpty());
		assertTrue(suggestions.size()==3);
		assertTrue(suggestions.get(1).size()==2);
		assertTrue(suggestions.get(1).contains("applicationn"));
		assertTrue(suggestions.get(1).contains("appplication"));
		assertTrue(suggestions.get(2).size()==2);
		assertTrue(suggestions.get(2).contains("aplicationn"));
		assertTrue(suggestions.get(2).contains("application"));
		assertTrue(suggestions.get(3).size()==1);
		assertTrue(suggestions.get(3).contains("aplication"));
	}	
}
