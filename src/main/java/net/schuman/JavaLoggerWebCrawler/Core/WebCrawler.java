package net.schuman.JavaLoggerWebCrawler.Core;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/***
 * 
 * This class provides two common ways to search for elements in a series of web pages.
 * 
 * @author Aaron Schuman
 *
 */

public class WebCrawler {

	/***
	 * 
	 * @param iterable The object to return each elements from all of the web sites 
	 * @param returnElements The object that all of the matching elements will be added
	 * @param attribute The attribute to search for
	 * @param attributeValue The value set to the attribute to search for
	 */
	public static void searchWebPageByAttributeValueContaining(Iterable<Elements> iterable, Elements returnElements, String attribute, String attributeValue) {
		for(Elements elements : iterable) {
			for(Element currentElement : elements) {
				returnElements.addAll(currentElement.getElementsByAttributeValueContaining(attribute, attributeValue));
			}
		}	
	}

	public static void searchWebPageContainingText(Iterable<Elements> iterable, Elements returnElements, String text) {
		for(Elements elements : iterable) {
			for(Element currentElement : elements) {
				returnElements.addAll(currentElement.getElementsContainingOwnText(text));
			}
		}
	}

}
