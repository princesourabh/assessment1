package com.nordea.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.nordea.model.Sentence;
import com.nordea.model.Text;
import com.nordea.processor.StatementProcessor;
import com.nordea.utils.NordeaConstants;
import com.nordea.utils.NordeaUtils;

import ch.qos.logback.core.net.SyslogOutputStream;

@Component
public class MyExecutionController implements CommandLineRunner{
//public class MyExecutionController {  //while making jar build uncomment this line
	

	private static Logger LOG = LoggerFactory.getLogger(MyExecutionController.class);
	
	public static int statement_n =2;
	
	public static int wordLen =0;
	
	@Autowired
	protected StatementProcessor statementProcessor;
	
	@Autowired
	protected Sentence sentence;
	
	@Autowired
	protected Text text;
	
	@Autowired
	public NordeaUtils nordeaUtilsObject;
	
	@Value( "${endOfProcessing}" )
	String endOfProcessing;
	
	@Override  //comment this line while making jar
	public void run(String... args) throws Exception {
		LOG.info("Input statements needed to produce xml/csv.Once you are done. Enter \"nordea sourabh\" to see the xml and csv ");
		System.out.println("\n Kindly provide the input statements to produce xml/csv.");
		System.out.println("Once you are done. Enter \""+endOfProcessing+"\" to see the xml and csv or end the process");
        
		List<Sentence> sentenceList = new ArrayList<Sentence>();
		List<String[]> dataLines = new ArrayList<>();

		try {
			StringBuilder inputStatement = new StringBuilder();
			StringBuilder partialStatement = new StringBuilder();
			String inputLine = "";
			partialStatement.append("PreSentence1 ");   //PreSentence1 Used internally to build logic
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));   //Read all input
			while ((inputLine = br.readLine()) != null) {

				Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
				
				Matcher reMatcher = re.matcher(inputLine);
				while (reMatcher.find()) {
					inputLine = reMatcher.group();
					LOG.debug("Statements received : " + inputLine);
					
					if(inputLine.equalsIgnoreCase(endOfProcessing)) {
						LOG.info("Statements execution in progress...");
						System.out.println("Statements execution in progress...");
						statementProcessor.generateXML(text);
						dataLines.add(0, nordeaUtilsObject.generateHeader(wordLen));
						statementProcessor.generatecsv(dataLines);
						
						sentenceList = new ArrayList<Sentence>();
						dataLines = new ArrayList<>();
						partialStatement.append("PreSentence1 ");
						statement_n =2;
						wordLen =0;
						
						LOG.info("\n Statements execution completed.\n To execute again enter the input lines again.\n Then Enter \""+endOfProcessing+"\" to see the xml and csv or end the process");
						
					}
					else if (inputLine.endsWith(NordeaConstants.DOT) || inputLine.endsWith(NordeaConstants.Question)
							|| inputLine.endsWith(NordeaConstants.Esclamation)) {
						inputStatement = partialStatement.append(inputLine);
						LOG.debug("Statements to be processed : " + inputStatement);
						
						String[] wordList = statementProcessor.process(inputStatement.toString());
						dataLines.add(wordList);
						if(wordList.length>wordLen) {
							wordLen=wordList.length;
						}
						sentenceList.add(statementProcessor.getObjectForXML(wordList,sentence));
						partialStatement.setLength(0);
						partialStatement.append("PreSentence");
						partialStatement.append(statement_n+" ");
						statement_n++;
						text.setSentence(sentenceList);
						
					} else {
						partialStatement = partialStatement.append(inputLine);
						LOG.debug("Partial Statements needs to complete : " + partialStatement);
					}
				}
			}

		} catch (Exception e) {
			// e.printStackTrace(); //uncomment to see error trace
			LOG.error("Error occured while reading and processing input :" + e.getMessage());
		}

	}

	
}
