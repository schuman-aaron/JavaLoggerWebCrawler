package net.schuman.JavaLoggerWebCrawler.Core;

import java.util.Iterator;
import java.util.logging.Logger;

import org.jsoup.select.Elements;

import net.schuman.JavaMethodLoggerFactory.JavaMethodLoggerFactory.JavaMethodLoggerFactory;

public class NumericalWebNavigator implements Iterable<Elements> {

	private JavaLoggerNumericalWebNavigatorIterator iterator;
	
	public NumericalWebNavigator(String preIteratedValueUri, String postIteratedValueUri, String tag, int iteratedValue, Logger logger, JavaMethodLoggerFactory methodLoggerFactory) {
		iterator = new JavaLoggerNumericalWebNavigatorIterator(preIteratedValueUri, postIteratedValueUri, tag, iteratedValue, logger, methodLoggerFactory);
	}
	
	@Override
	public Iterator<Elements> iterator() {
		return iterator;
	}

}
