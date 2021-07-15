package com.nordea.utils;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nordea.model.Sentence;



@Component
public class NordeaUtils {
	
//	@Value("${specialChar}")
//	String specialChar;
//	
//	@Value("${charSpace}")
//	String charSpace;
//	
//	@Value("${extraSpace}")
//	String extraSpace;

	private static Logger LOG = LoggerFactory.getLogger(NordeaUtils.class);

	public String[] removeSpecialCharAndArrangeOrder(String inputStatement) {

		LOG.debug("Input recived for arrangingorder :"+inputStatement);
		inputStatement = inputStatement.replaceAll("[\\.$|;|:|!|?|=|*|^|¨|(|)|<|>|\\-|_|{|}|#|~]", "");
		inputStatement = inputStatement.replaceAll(",", " ");
		inputStatement = inputStatement.replaceAll("\\s+", " ");

		String[] statementWord = inputStatement.split("\\s");
		//Arrays.sort(statementWord, String.CASE_INSENSITIVE_ORDER);
		Arrays.sort(statementWord, 1, statementWord.length,String.CASE_INSENSITIVE_ORDER);
		LOG.debug("statementWord sorted :" + Arrays.toString(statementWord));
		return statementWord;
	}
	
	
	public Sentence getStatementObject(String[] token, Sentence sentence) throws JAXBException, FileNotFoundException {
		LOG.debug("convertObjectToXMLinput");
		List<String> word = new ArrayList<String>();
	    sentence = new Sentence();
		int i = 1;
		while (i < token.length) {
			word.add(token[i]);
			i++;
		}
		sentence.setWord(word);

		return sentence;
	}
	

	public String[] generateHeader(int sizeOfHeader) {
		LOG.debug("create header :"+sizeOfHeader);
		String[] statementWord = new String[sizeOfHeader];
		
		statementWord[0]="";
		int i=1;
		while(i<sizeOfHeader) {
			statementWord[i]= "Word "+i;
			i++;
		}
		
		LOG.debug("create header statementWord :"+Arrays.toString(statementWord));
		LOG.debug("create header exit ");
		return statementWord;
		
	}
	

}
