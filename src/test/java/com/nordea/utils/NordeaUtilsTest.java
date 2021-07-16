package com.nordea.utils;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import javax.xml.bind.JAXBException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nordea.model.Sentence;

@ExtendWith(MockitoExtension.class)
class NordeaUtilsTest {
	
	
	@InjectMocks
	NordeaUtils nordeaUtils;
	
	@InjectMocks
	Sentence sentence;
	
	@Mock
	NordeaUtils nordeaUtilsMock;
	

	
	@Test
	void getSortedStringArrayWithoutSpecialCharSizeCheck() {
		String inputStatement = "Why was he directing  his  anger at me? Little did I know 	about 	that.";
		String[] inputArray = nordeaUtils.removeSpecialCharAndArrangeOrder(inputStatement);
		assertEquals(inputArray.length, 14);

	}
	
	
	@Test
	void getSortedStringArrayWithoutSpecialCharContentCheck() {
		String inputStatement = "Why was he directing  his  anger at me? Little did I know 	about 	that.";
		String[] inputArray = nordeaUtils.removeSpecialCharAndArrangeOrder(inputStatement);
		
		assertEquals(Arrays.toString(inputArray), "[Why, about, anger, at, did, directing, he, his, I, know, Little, me, that, was]");
		
	}
	
	
	@Test
	void getSortedStringArrayWithoutSpecialCharContentNotMatchCheck() {
		String inputStatement = "Why was he directing  his  anger at me? Little did I know 	about 	that.";
		String[] inputArray = nordeaUtils.removeSpecialCharAndArrangeOrder(inputStatement);
		assertNotEquals(Arrays.toString(inputArray), "[NotMatchTest,Why, about, anger, at, did, directing, he, his, I, know, Little, me, that, was]");
		
		
	}
	
	
	
	
	@Test
	public void getStatementObjectCheckSize() throws FileNotFoundException, JAXBException{
		String inputStatement = "Why was he directing  his  anger at me? Little did I know 	about 	that.";
		String[] output = {"Why", "about", "anger", "at", "did", "directing", "he", "his", "I", "know", "Little", "me", "that", "was"};
		when(nordeaUtilsMock.removeSpecialCharAndArrangeOrder(inputStatement)).thenReturn(output);
	    Assertions.assertThrows(FileNotFoundException.class, () ->  new Scanner(new File("file.txt")));
		sentence = nordeaUtils.getStatementObject(nordeaUtilsMock.removeSpecialCharAndArrangeOrder(inputStatement), sentence);
		assertEquals(sentence.getWord().size(),13);
	}
	
	
	@Test
	public void getStatementObjectCheckData() throws FileNotFoundException, JAXBException{
		String inputStatement = "Why was he directing  his  anger at me? Little did I know 	about 	that.";
		String[] output = {"Why", "about", "anger", "at", "did", "directing", "he", "his", "I", "know", "Little", "me", "that", "was"};
		when(nordeaUtilsMock.removeSpecialCharAndArrangeOrder(inputStatement)).thenReturn(output);
	    Assertions.assertThrows(FileNotFoundException.class, () ->  new Scanner(new File("file.txt")));
		sentence = nordeaUtils.getStatementObject(nordeaUtilsMock.removeSpecialCharAndArrangeOrder(inputStatement), sentence);
		String expectedOutput= "[about, anger, at, did, directing, he, his, I, know, Little, me, that, was]";
		assertEquals(sentence.getWord().toString(),expectedOutput);
	}
	
	
	
	@Test
	public void generateHeaderSize() throws FileNotFoundException, JAXBException {

		int sizeHeader = 5;
		String[] nordeaHeader = nordeaUtils.generateHeader(sizeHeader);
		assertEquals(nordeaHeader.length, sizeHeader);

	}
	
	@Test
	public void generateHeaderSizeNotMatch() throws FileNotFoundException, JAXBException {

		int sizeHeader = 6;
		String[] nordeaHeader = nordeaUtils.generateHeader(sizeHeader);
		assertNotEquals(nordeaHeader.length, 5);

	}
	
	@Test
	public void generateHeaderContentcheck() throws FileNotFoundException, JAXBException {

		int sizeHeader = 5;
		String[] nordeaHeader = nordeaUtils.generateHeader(sizeHeader);
		assertEquals(Arrays.toString(nordeaHeader), "[, Word 1, Word 2, Word 3, Word 4]");

	}

}
