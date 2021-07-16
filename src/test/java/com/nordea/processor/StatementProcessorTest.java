package com.nordea.processor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import com.nordea.model.Sentence;
import com.nordea.model.Text;
import com.nordea.utils.NordeaUtils;

@ExtendWith(MockitoExtension.class)
class StatementProcessorTest {

	@InjectMocks
	StatementProcessor statementProcessor;
	
	@InjectMocks
	Text text;
	
	@InjectMocks
	Sentence sentence;
	
	@Mock
	NordeaUtils nordeaUtilsMock;
	

	
	
	
	@Test
	void processGeneratesArrayCheck() {
		String inputStatement = "Why was he directing  his  anger at me? Little did I know 	about 	that.";
		String[] output = {"Why", "about", "anger", "at", "did", "directing", "he", "his", "I", "know", "Little", "me", "that", "was"};
		when(nordeaUtilsMock.removeSpecialCharAndArrangeOrder(inputStatement)).thenReturn(output);
	    String[] inputArray = statementProcessor.process(inputStatement);
	    assertEquals(Arrays.toString(inputArray), "[Why, about, anger, at, did, directing, he, his, I, know, Little, me, that, was]");
		
	}
	
	@Test
	void processGeneratesArrayReturnNullWithoutMock() {
		String inputStatement = "Why was he directing  his  anger at me? Little did I know 	about 	that.";
		String[] output = {"Why", "about", "anger", "at", "did", "directing", "he", "his", "I", "know", "Little", "me", "that", "was"};
		//when(nordeaUtilsMock.removeSpecialCharAndArrangeOrder(inputStatement)).thenReturn(output);
	    String[] inputArray = statementProcessor.process(inputStatement);
	    assertEquals(inputArray, null);
		
	}
	
	    @Test
		void generateXMLTest() {
			List<String> word = new ArrayList<String>();
			word.add("Hi");
			word.add("I");
			word.add("am");
			word.add("testing");
			word.add("for");
			word.add("Norea");
			sentence.setWord(word);
			List<Sentence> sentenceObj = new ArrayList<Sentence>();
			sentenceObj.add(sentence);
			text.setSentence(sentenceObj);
			String generateXmlOutput = statementProcessor.generateXML(text);
			assertEquals(generateXmlOutput, "XML generated done");
			
		}
		
		@Test
		void generateCSVTest() {
			String[] input1 = {"about", "anger", "at", "did", "directing"};
			String[] input2 = {"he", "his", "I", "know", "Little", "me", "that", "was"};
			List<String[]> sentenceList = new ArrayList<String[]>();
			sentenceList.add(input1);
			sentenceList.add(input2);
			String generateCSVOutput = statementProcessor.generatecsv(sentenceList);
			assertEquals(generateCSVOutput, "CSV generated done");
			
		}
	
	@Test
	void checkEscapeSpecialChar() {
		String inputStatement = "you’-!*,'.?d";
		String withoutSpecialChar = statementProcessor.escapeSpecialCharacters(inputStatement);
	    System.out.println(withoutSpecialChar);
	    assertEquals(withoutSpecialChar, "\"you’-!*,'.?d\"");
		
	}
	
	@Test
	void checkCsvStream() {
		String[] inputStatement = {"Why", "about", "anger", "at", "did", "directing", "he", "his", "I", "know", "Little", "me", "that", "was"};
		String withoutSpecialChar = statementProcessor.convertToCSV(inputStatement);
		String outputExp = "Why, about, anger, at, did, directing, he, his, I, know, Little, me, that, was";
	    assertEquals(withoutSpecialChar, outputExp);
		
	}
	
	

}
