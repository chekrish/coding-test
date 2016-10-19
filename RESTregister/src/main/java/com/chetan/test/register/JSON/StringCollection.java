package com.chetan.test.register.JSON;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * <h1>@XmlRootElement() to hold StringUnicode objects</h1> 
 * This class is used as a parent to hold StringUnicode objects.
 * This is the object returned to client in the form of JSON to client
 * <p>
 * 
 * @author Chethan Krishnamurthy
 * @version 1.0
 * @since 10-19-2016
 */

@XmlRootElement(name = "StringCollection")
public class StringCollection {
	
	List<StringUnicode> strUCList;

	public List<StringUnicode> getStrUCList() {
		return strUCList;
	}

	public void setStrUCList(List<StringUnicode> strUCList) {
		this.strUCList = strUCList;
	}
	
}
