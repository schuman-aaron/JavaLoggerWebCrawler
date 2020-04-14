package net.schuman.JavaLoggerWebCrawler.Core;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import net.schuman.JavaMethodLoggerFactory.JavaMethodLoggerFactory.JavaMethodLogger;
import net.schuman.JavaMethodLoggerFactory.JavaMethodLoggerFactory.JavaMethodLoggerFactory;

import static net.schuman.JavaLoggerWebCrawler.Core.Constants.*;

/***
 * 
 * @author Aaron Schuman
 * This {@link iterator} class can be used to navigate to multiple web sites that are only differentiated by an incrementing numeral. In this implementation the Java logger is used.
 *
 */
public class JavaLoggerNumericalWebNavigatorIterator implements Iterator<Elements> {

	private String preIteratedValueUri;
	private String postIteratedValueUri;
	private int iteratedValue;
	private int finalValue;
	private StringBuilderProxy uri;
	private String tag;
	private Document currentDocument;
	private Document nextDocument;
	private Logger logger;
	private JavaMethodLoggerFactory methodLoggerFactory;
	
	/***
	 * 
	 * @param newPreIteratedValueUri The part of the URI that comes before the iterated number
	 * @param newPostIteratedValueUri The part of the URI that comes after the iterated number
	 * @param newTag The tags from the URI to add to the returned result
	 * @param newIteratedValue The starting value for the iterated number
	 * @param newFinalValue The maximum value for the iterated number, if the final valid web page is not reached before then   
	 * @param newLogger The logger to be used
	 * @param newMethodLoggerFactory The method logger factory to be used
	 */
	public JavaLoggerNumericalWebNavigatorIterator(String newPreIteratedValueUri, String newPostIteratedValueUri, String newTag, int newIteratedValue, int newFinalValue, Logger newLogger, JavaMethodLoggerFactory newMethodLoggerFactory) {
		preIteratedValueUri = newPreIteratedValueUri;
		postIteratedValueUri = newPostIteratedValueUri;
		tag = newTag;
		iteratedValue = newIteratedValue;
		finalValue = newFinalValue;
		logger = newLogger;
		methodLoggerFactory = newMethodLoggerFactory;
	}
	
	/***
	 * Determines if there is another element to be selected.
	 * @return Whether or not there is another element that can be selected. 
	 */
	public boolean hasNext() {
		try(JavaMethodLogger methodLogger = methodLoggerFactory.createMethodLoggerAndLogEntry(new Throwable().getStackTrace()[0].getMethodName())) {
			if(hasNotReachedEnd()) {
				buildAndSetNextWebPage(DocumentEnumeration.NEXT_DOCUMENT);
				return verifyWebPage();
			}
			return false;
		} catch(Exception e) {
			logger.finer(e.getMessage());
			return false;
		}
	}

	/***
	 * Selects the next element if it is available
	 * @return The next element if it is available
	 * @throws NoSuchElementException If there is no element available
	 */
	public Elements next() {
		try(JavaMethodLogger methodLogger = methodLoggerFactory.createMethodLoggerAndLogEntry(new Throwable().getStackTrace()[0].getMethodName())) {
			if(verifyWebPage()) {
				currentDocument = nextDocument;
				nextDocument = null;
			} else {
				buildAndSetNextWebPage(DocumentEnumeration.CURRENT_DOCUMENT);
			}
			return currentDocument.select(tag);
		} catch (Exception e) {
			throw new NoSuchElementException(e.getMessage());
		}
	}

	protected Document getCurrentDocument() {
		return currentDocument;
	}

	protected void setCurrentDocument(Document currentDocument) {
		this.currentDocument = currentDocument;
	}

	protected Document getNextDocument() {
		return nextDocument;
	}

	protected void setNextDocument(Document nextDocument) {
		this.nextDocument = nextDocument;
	}

	protected StringBuilderProxy getUri() {
		return uri;
	}

	protected void setUri(StringBuilderProxy uri) {
		this.uri = uri;
	}

	protected void buildAndSetNextWebPage(DocumentEnumeration enumeration) throws Exception {
		try(JavaMethodLogger methodLogger = methodLoggerFactory.createMethodLoggerAndLogEntry(new Throwable().getStackTrace()[0].getMethodName())) {
			logger.finer(	"uri: " + uri + getFormattingCharacter() + 
							"iteratedValue: " + iteratedValue
						);
			buildWebPage();
			setNextWebPage(enumeration);
			
		}
	}
	
	protected void setNextWebPage(DocumentEnumeration enumeration) throws Exception {
		try(JavaMethodLogger methodLogger = methodLoggerFactory.createMethodLoggerAndLogEntry(new Throwable().getStackTrace()[0].getMethodName())) {
			switch(enumeration) {
			case CURRENT_DOCUMENT:
				currentDocument = Jsoup.connect(uri.toString()).get();
				break;
			case NEXT_DOCUMENT:
				nextDocument = Jsoup.connect(uri.toString()).get();
				break;
			}
		}
	}

	private void buildWebPage() {
		uri = new StringBuilderProxy(preIteratedValueUri + (iteratedValue++) + postIteratedValueUri);
	}

	protected boolean hasNotReachedEnd() {
		return iteratedValue <= finalValue;
	}

	protected int getIteratedValue() {
		return iteratedValue;
	}

	public boolean verifyWebPage() {
		return nextDocument != null;
	}

}
