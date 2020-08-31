package com.emerson.spell.check.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.emerson.spell.check.model.Dictionary;

public interface DictionaryRepository extends CrudRepository<Dictionary, Long>, DictionaryRepositoryCustom {

	List<Dictionary> findByWord(String word);

	Dictionary findById(long id);
}
