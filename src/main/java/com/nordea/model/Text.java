package com.nordea.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;


@Component
@XmlRootElement(namespace = "text")
@XmlAccessorType(XmlAccessType.FIELD)
public class Text {

	@XmlElement(name = "sentence")
	private List<Sentence> sentence;

	public List<Sentence> getSentence() {
		return sentence;
	}

	public void setSentence(List<Sentence> sentence) {
		this.sentence = sentence;
	}
	
	
}
