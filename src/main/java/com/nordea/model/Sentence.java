package com.nordea.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlAccessType;

@Component
@XmlRootElement(namespace = "sentence")
@XmlAccessorType(XmlAccessType.FIELD)
public class Sentence {

	@XmlElement(name = "words")
	private List<String> word;

	public List<String> getWord() {
		return word;
	}

	public void setWord(List<String> word) {
		this.word = word;
	}

}
