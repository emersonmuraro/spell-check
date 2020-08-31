package com.emerson.spell.check.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.emerson.spell.check.model.Typo;

public interface TypoRepository extends CrudRepository<Typo, Long> {

	List<Typo> findByTypo(String typo);

	Typo findById(long id);
}
