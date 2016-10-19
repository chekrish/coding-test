package com.chetan.test.register.JSON;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <h1>@XmlRootElement() to store the properties</h1> 
 * This class is used to persist String ID and unicode to datastore ,and transport data to and from client.
 * used to produce JSON response and recieve JSON request from client
 * <p>
 * 
 * @author Chethan Krishnamurthy
 * @version 1.0
 * @since 10-19-2016
 */

@XmlRootElement()
public class StringUnicode {
	
	int stringID;
	String unicode;


	public int getStringID() {
		return stringID;
	}

	public void setStringID(int stringID) {
		this.stringID = stringID;
	}

	public String getUnicode() {
		return unicode;
	}

	public void setUnicode(String unicode) {
		this.unicode = unicode;
	}

	@Override
	public String toString() {
		return "String Map [ID=" + stringID + ", Unicode =" + unicode + "]";
	}
}
