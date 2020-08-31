package com.emerson.spell.check.service;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.emerson.spell.check.repository.DictionaryRepository;
import com.emerson.spell.check.repository.TypoRepository;

@SpringBootTest
public class SpellCheckServiceTests {
	
	@Mock
	DictionaryRepository dictionaryRepository;
	
	@Mock
	TypoRepository typoRepository;
 
	@InjectMocks
	SpellCheckService spellCheckService;
 

	@Test
	public void testSpellCheck() {
		
	}
}
