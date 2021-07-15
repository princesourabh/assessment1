package com.nordea.processor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nordea.model.Sentence;
import com.nordea.model.Text;
import com.nordea.utils.NordeaUtils;

@Component
public class StatementProcessor {

	private static Logger LOG = LoggerFactory.getLogger(StatementProcessor.class);

	@Autowired
	public NordeaUtils nordeaUtils;

	@Value("${csvinputpath}")
	String csvinputpath;

	public String[] process(String line) {
		try {
			LOG.debug("Input received for Statement Processor :" + line);
			return nordeaUtils.removeSpecialCharAndArrangeOrder(line);
		} catch (Exception e) {
			LOG.error("Error while processing input line :" + e.getMessage());
		}
		return null;

	}

	public Sentence getObjectForXML(String[] wordList, Sentence sentence) {
		try {
			LOG.debug("Inputword list received for Statement Processor :" + wordList);
			return nordeaUtils.getStatementObject(wordList, sentence);
		} catch (FileNotFoundException | JAXBException e) {
			LOG.error("Error while processing input line :" + e.getMessage());
		} catch (Exception e) {
			LOG.error("Error while processing input line :" + e.getMessage());
		}
		return sentence;
	}

	public String generateXML(Text text) {
		try {
			LOG.debug("Entry for generating xml :" + text.getSentence());
			JAXBContext context;
			context = JAXBContext.newInstance(Text.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(text, System.out);
			LOG.debug("Exit from generating xml");
		} catch (Exception e) {
			// e.printStackTrace(); //uncomment to see the trace
			LOG.error("Error while generating XML :" + e.getMessage());
		}
		return "XML generated done";
	}

	public String generatecsv(List<String[]> sentenceList) {
		try {
			Long currentDate = new Date().getTime();
			File csvOutputFile = new File(csvinputpath + currentDate + ".csv");
			try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
				sentenceList.stream().map(this::convertToCSV).forEach(pw::println);

				System.out.println("final submit");
			} catch (FileNotFoundException e) {
				// e.printStackTrace(); //uncomment to see the trace
				LOG.error("CSV file not found :" + e.getMessage());
			}
		} catch (Exception e) {
			// e.printStackTrace(); //uncomment to see the trace
			LOG.error("Error while generating CSV file :" + e.getMessage());
		}
		return "CSV generated done";
	}

	public String convertToCSV(String[] sentenceList) {
		try {
			return Stream.of(sentenceList).map(this::escapeSpecialCharacters).collect(Collectors.joining(", "));
		} catch (Exception e) {
			// e.printStackTrace(); //uncomment to see the trace
			LOG.error("Error while converting to csv :" + e.getMessage());
		}
		return null;
	}

	public String escapeSpecialCharacters(String sentenceList) {
		String escapedData = sentenceList.replaceAll("\\R", " ");
		if (sentenceList.contains(",") || sentenceList.contains("\"")) { // || sentenceList.contains("'")
			sentenceList = sentenceList.replace("\"", "\"\"");
			escapedData = "\"" + sentenceList + "\"";
		}
		if (sentenceList.contains("PreSentence")) {
			String number = escapedData.substring(11, sentenceList.length());
			escapedData = escapedData.substring(3, 11) + " " + number;
		}
		return escapedData;
	}
}
