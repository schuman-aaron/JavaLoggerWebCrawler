package net.schuman.JavaLoggerWebCrawler.Core;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawler {

	public static void searchWebPageByAttributeSuccess(Iterable<Elements> iterable, Elements returnElements, String attribute) {
		for(Elements elements : iterable) {
			for(Element currentElement : elements) {
				returnElements.addAll(currentElement.getElementsByAttribute(attribute));
			}
		}		
	}

	public static void searchWebPageContainingText(Iterable<Elements> iterable, Elements returnElements, String text) {
		for(Elements elements : iterable) {
			for(Element currentElement : elements) {
				returnElements.addAll(currentElement.getElementsContainingText(text));
			}
		}
	}

}
