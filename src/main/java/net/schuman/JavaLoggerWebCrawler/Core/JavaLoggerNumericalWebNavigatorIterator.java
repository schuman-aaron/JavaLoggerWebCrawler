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

public class JavaLoggerNumericalWebNavigatorIterator implements Iterator<Elements> {

	private String preIteratedValueUri;
	private String postIteratedValueUri;
	private int iteratedValue;
	private StringBuilderProxy uri;
	private String tag;
	private Document currentDocument;
	private Document nextDocument;
	private Logger logger;
	private JavaMethodLoggerFactory methodLoggerFactory;
	
	public JavaLoggerNumericalWebNavigatorIterator(String newPreIteratedValueUri, String newPostIteratedValueUri, String newTag, int newIteratedValue, Logger newLogger, JavaMethodLoggerFactory newMethodLoggerFactory) {
		preIteratedValueUri = newPreIteratedValueUri;
		postIteratedValueUri = newPostIteratedValueUri;
		tag = newTag;
		iteratedValue = newIteratedValue;
		uri = new StringBuilderProxy(preIteratedValueUri + iteratedValue + postIteratedValueUri);
		logger = newLogger;
		methodLoggerFactory = newMethodLoggerFactory;
	}
	
	public boolean hasNext() {
		try(JavaMethodLogger methodLogger = methodLoggerFactory.createMethodLoggerAndLogEntry(new Throwable().getStackTrace()[0].getMethodName())) {
			buildAndSetNextWebPage(nextDocument);
			return verifyWebPage();
		} catch(Exception e) {
			logger.finer(e.getMessage());
			return false;
		}
	}

	public Elements next() {
		try(JavaMethodLogger methodLogger = methodLoggerFactory.createMethodLoggerAndLogEntry(new Throwable().getStackTrace()[0].getMethodName())) {
			if(nextDocument == null) {
				buildAndSetNextWebPage(currentDocument);
			} else {
				currentDocument = nextDocument;
				nextDocument = null;
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

	protected void buildAndSetNextWebPage(Document document) throws Exception {
		try(JavaMethodLogger methodLogger = methodLoggerFactory.createMethodLoggerAndLogEntry(new Throwable().getStackTrace()[0].getMethodName())) {
			logger.finer("uri: " + uri + getFormattingCharacter() 
			+ "iteratedValue: " + (iteratedValue - 1 < 0? preIteratedValueUri.length() + iteratedValue : iteratedValue - 1));
			uri.replace(preIteratedValueUri.length(), iteratedValue - 1 < 0? preIteratedValueUri.length() + iteratedValue : iteratedValue - 1,  String.valueOf(iteratedValue));
			iteratedValue++;
			document = Jsoup.connect(uri.toString()).get();
		}
	}

	protected int getIteratedValue() {
		return iteratedValue;
	}

	public boolean verifyWebPage() {
		return nextDocument != null;
	}

}
