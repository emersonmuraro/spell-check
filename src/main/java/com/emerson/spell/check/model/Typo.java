package com.emerson.spell.check.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Typo {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String typo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dictionary_id")
	private Dictionary dictionary;

	protected Typo() {}

	public Typo(String typo, Dictionary dictionary) {
		this.typo = typo;
		this.dictionary = dictionary;
	}

	@Override
	public String toString() {
		return String.format(
				"Customer[id=%d, typo='%s', dictionary='%s']",
				id, typo, dictionary);
	}

	public Long getId() {
		return id;
	}

	public String getTypo() {
		return typo;
	}

	public Dictionary getDictionary() {
		return dictionary;
	}
}