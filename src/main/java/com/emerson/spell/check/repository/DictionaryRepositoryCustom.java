package com.emerson.spell.check.repository;

import java.util.List;
import java.util.Set;

import com.emerson.spell.check.model.Dictionary;

public interface DictionaryRepositoryCustom {

	List<Dictionary> findDictionaryByWords(Set<String> words);
}
