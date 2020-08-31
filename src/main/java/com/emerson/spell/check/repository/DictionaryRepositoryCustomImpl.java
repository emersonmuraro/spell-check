package com.emerson.spell.check.repository;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Value;

import com.emerson.spell.check.model.Dictionary;

public class DictionaryRepositoryCustomImpl implements DictionaryRepositoryCustom{

	@Value("${db_limit_operator_in}")
	private int db_limit_operator_in;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Dictionary> findDictionaryByWords(Set<String> words){

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Dictionary> query = cb.createQuery(Dictionary.class);
		Root<Dictionary> dictionary = query.from(Dictionary.class);
		In<String> in = cb.in(dictionary.get("word"));
		words.forEach(word -> in.value(word));

		/*
		 * The idea here is to be able to brake in multiples sets of words,
		 * it is possible to send it in chunks that the DB accept and combine the result
		 * Other option could be a dynamic SQL query with union all, but because of the
		 * time of this test I limited it to send only on set of words
		 */
		int count = 0; 
		for (String word : words) {
			if (count > db_limit_operator_in) {
				break;
			}
			in.value(word);
			count++;
		}
		query.select(dictionary)
		.where(in);

		return entityManager.createQuery(query)
				.getResultList();
	}
}
