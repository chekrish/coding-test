package com.chetan.test.register.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringEscapeUtils;

import com.chetan.test.register.JSON.StringCollection;
import com.chetan.test.register.JSON.StringUnicode;

/**
 * <h1>REST API to register unicode strings!</h1> RestService programm
 * provides REST service end points to register a unicode and retrive string using String ID
 * <p>
 * 
 * @author Chethan Krishnamurthy
 * @version 1.0
 * @since 10-19-2016
 */

@Path("/strings")
public class RestService {

	private static int FIRST_CHAR_VAL = 0;
	private static int LAS_CHAR_VAL = 0;
	private static int SUM = 0;
	private static boolean FIRST_CHAR_FLAG = false;

	private static final String DATABASE_FILE = "C:\\DB\\database.txt";
	
	/**
	   * This method is used to get the String using string ID.
	   *  This method takes a string id and return all registered strings that match with the string id
	   * @param id This is the paramter to get the registered strings
	   * @return StringCollection is a List of StringUnicode objects and retruned as JSON to client
	   */

	@GET
	@Path("/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public StringCollection getString(@PathParam("id") String id) {

		List<StringUnicode> list = new ArrayList<StringUnicode>();
		StringCollection strcCol = new StringCollection();
		try {
			list = readDataStore(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		strcCol.setStrUCList(list);

		return strcCol;

	}

	
	/**
	   * This method is used to register a unicode String in the service.
	   *  This method takes a unicode string, generates a string id persists it for later retrieval 
	   * @param unicode This is the paramter to register unicode string. input is take as a JSON 
	   * @return Response is a success message returned after saving the string
	   */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response storeUnicodeString(StringUnicode unicode) {

		FIRST_CHAR_VAL = 0;
		LAS_CHAR_VAL = 0;
		SUM = 0;
		FIRST_CHAR_FLAG = false;

		try {

			File file = new File(DATABASE_FILE);
			
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(addCodePointRecursion(unicode.getUnicode()) + ":" + unicode.getUnicode());
			bw.newLine();
			bw.flush();

			bw.close();

			System.out.println("Write Complete");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(201).entity("Saved").build();

	}

	
	/**
	   * This method is used to read the file where String ID and information is stored.
	   * This method takes string ID as input and returns the list of strings matching that string ID 
	   * @param id This is the paramter String ID to retrieve strings from database 
	   * @return List of StringUnicode objects matching the ID
	   */
	private static List<StringUnicode> readDataStore(String id) throws Exception {
		String line = null;
		List<StringUnicode> suList = new ArrayList<StringUnicode>();

		BufferedReader bufferedReader = new BufferedReader(new FileReader(DATABASE_FILE));

		while ((line = bufferedReader.readLine()) != null) {
			String[] data = line.split(":");
			StringUnicode strUC = new StringUnicode();
			// System.out.println(data[0]+":"+id);
			if (data[0].equals(id)) {
				strUC.setStringID(Integer.parseInt(data[0]));
				strUC.setUnicode(StringEscapeUtils.unescapeJava(data[1]));
				suList.add(strUC);
			}

		}
		bufferedReader.close();
		return suList;
	}
	
	
	
	/**
	   * This method takes unicodes string as input and generates String ID based on a business logic without using loop. 
	   * @param String is the unicode string posted by client 
	   * @return int This method returns a int ID generated using code point of each charectars in the input string
	   */

	private int addCodePointRecursion(String unicodeStr) {

		// Unescapes unicode charectars in the input string
		String str = StringEscapeUtils.unescapeJava(unicodeStr);

		//Return sum if string is empty- also its a recursion exit condition
		if (str.isEmpty()) {
			return FIRST_CHAR_VAL + SUM;
		}

		//Only first character should not be added with any other code point
		if (!FIRST_CHAR_FLAG) {
			FIRST_CHAR_VAL = Character.codePointAt(str, 0);
			LAS_CHAR_VAL = FIRST_CHAR_VAL;
			//setting flag true so that subsequest request should not enter this block
			FIRST_CHAR_FLAG = true;
		} else {
			if (Character.codePointAt(str, 0) != LAS_CHAR_VAL) {
				//If the codepoint of char at i is not same as char at i-1 , add them to calculate codepoint
				SUM = SUM + Character.codePointAt(str, 0) + LAS_CHAR_VAL;
				//reset reference to  LAS_CHAR_VAL
				LAS_CHAR_VAL = Character.codePointAt(str, 0);
			} else {
				//if char at i matches char at i-1 then dont add the code point
				SUM = SUM + Character.codePointAt(str, 0);
				//reset reference to  LAS_CHAR_VAL
				LAS_CHAR_VAL = Character.codePointAt(str, 0);
			}
		}
		return addCodePointRecursion(unicodeStr.substring(1));
	}
}
