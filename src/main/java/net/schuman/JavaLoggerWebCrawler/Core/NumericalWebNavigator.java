package net.schuman.JavaLoggerWebCrawler.Core;

import java.util.Iterator;
import java.util.logging.Logger;

import org.jsoup.select.Elements;

import net.schuman.JavaMethodLoggerFactory.JavaMethodLoggerFactory.JavaMethodLoggerFactory;

/***
 * An {@link iterable} class that utilizes the {@link JavaLoggerNumericalWebNavigatorIterator}.
 * 
 * @author Aaron Schuman
 *
 */
public class NumericalWebNavigator implements Iterable<Elements> {

	private JavaLoggerNumericalWebNavigatorIterator iterator;
	
	/***
	 * 
	 * @param preIteratedValueUri The part of the URI that comes before the iterated number
	 * @param postIteratedValueUri The part of the URI that comes after the iterated number
	 * @param tag The tags from the URI to add to the returned result
	 * @param startValue The starting value for the iterated number
	 * @param finalValue The maximum value for the iterated number, if the final valid web page is not reached before then
	 * @param logger The logger to be used
	 * @param methodLoggerFactory The method logger factory to be used
	 */
	public NumericalWebNavigator(String preIteratedValueUri, String postIteratedValueUri, String tag, int startValue, int finalValue, Logger logger, JavaMethodLoggerFactory methodLoggerFactory) {
		iterator = new JavaLoggerNumericalWebNavigatorIterator(preIteratedValueUri, postIteratedValueUri, tag, startValue, finalValue, logger, methodLoggerFactory);
	}
	
	@Override
	public Iterator<Elements> iterator() {
		return iterator;
	}

}
