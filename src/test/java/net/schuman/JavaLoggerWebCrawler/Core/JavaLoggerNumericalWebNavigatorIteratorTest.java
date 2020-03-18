package net.schuman.JavaLoggerWebCrawler.Core;

import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.MockAwareVerificationMode;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.mockito.internal.mockcreation.RuntimeExceptionProxy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import net.schuman.JavaLoggerWebCrawler.Core.JavaLoggerNumericalWebNavigatorIterator;
import net.schuman.JavaMethodLoggerFactory.JavaMethodLoggerFactory.JavaMethodLogger;
import net.schuman.JavaMethodLoggerFactory.JavaMethodLoggerFactory.JavaMethodLoggerFactory;

import org.mockito.BDDMockito;
import org.mockito.BDDMockito.*;

import static net.schuman.JavaLoggerWebCrawler.Core.TestConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.PatternSyntaxException;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector.SelectorParseException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Jsoup.class})
public class JavaLoggerNumericalWebNavigatorIteratorTest {

	@Mock
	private Document mockCurrentDocument;
	@Mock
	private Document mockNextDocument;
	@Mock
	private Logger mockLogger;
	@Mock
	private JavaMethodLoggerFactory mockMethodLoggerFactory;
	@Mock
	private JavaMethodLogger mockMethodLogger;
	@Mock
	private StringBuilderProxy mockString;
	@Mock
	private Elements mockElements;
	
	private Connection mockConnection;
	
	private JavaLoggerNumericalWebNavigatorIterator webNavigatorIterator;
	
	private boolean hasNextResult;
	
	private Elements returnElements;
	
	@Before
	public void Setup() {
		mockCurrentDocument = mock(Document.class);
		mockNextDocument = mock(Document.class);
		mockLogger = mock(Logger.class);
		mockMethodLoggerFactory = mock(JavaMethodLoggerFactory.class);
		mockMethodLogger = mock(JavaMethodLogger.class);
		mockString = mock(StringBuilderProxy.class);
		mockConnection = mock(Connection.class);
		PowerMockito.mockStatic(Jsoup.class, Mockito.RETURNS_MOCKS);
		
		webNavigatorIterator = new JavaLoggerNumericalWebNavigatorIterator(getDefaultString(), getDefaultString(), getDefaultString(), getDefaultInt(), mockLogger, mockMethodLoggerFactory);
		webNavigatorIterator.setCurrentDocument(mockCurrentDocument);
		webNavigatorIterator.setNextDocument(mockNextDocument);
		webNavigatorIterator.setUri(mockString);
	}
	
	@Test
	public void testBuildAndSetNextWebPageSuccess() {
		try {
			buildAndSetWebPageDefaultSettings();
			webNavigatorIterator.buildAndSetNextWebPage(mockNextDocument);
			verify(mockMethodLoggerFactory, times(1)).createMethodLoggerAndLogEntry(anyString());
			verify(mockString, times(1)).replace(anyInt(), anyInt(), anyString());
			//PowerMockito.verifyStatic(Jsoup.class, times(1));
			Jsoup.connect(getDefaultString());
			verify(mockConnection, times(1)).get();
			assertTrue("Unexpected iterated value.\nExpected: " + (getDefaultInt() + 1) + "\nActual: " +  webNavigatorIterator.getIteratedValue(),webNavigatorIterator.getIteratedValue() == getDefaultInt() + 1);
		} catch (Exception e) {
			throw new AssertionError(e.getMessage());
		}
	}
	
	@Test
	public void testBuildAndSetNextWebPageStringIndexOutOfBoundsFailure() {
		try {
			buildAndSetWebPageDefaultSettings();
			when(mockString.replace(anyInt(), anyInt(), anyString())).thenThrow(new IndexOutOfBoundsException());
			webNavigatorIterator.buildAndSetNextWebPage(mockNextDocument);
		} catch (IndexOutOfBoundsException e) {
			verify(mockMethodLoggerFactory, times(1)).createMethodLoggerAndLogEntry(anyString());
			//PowerMockito.verifyStatic(Jsoup.class, times(1));
			Jsoup.connect(getDefaultString());
			try {
				verify(mockConnection, times(0)).get();
			} catch (IOException e1) {
				throw new AssertionError(e1.getMessage());
			}
			assertTrue("Unexpected iterated value.\nExpected: " + (getDefaultInt()) + "\nActual: " +  webNavigatorIterator.getIteratedValue(),webNavigatorIterator.getIteratedValue() == getDefaultInt());
		} catch (Exception e) {
			throw new AssertionError(e.getMessage());
		}
	}
	
	@Test
	public void testBuildAndSetNextWebPageMalformedUrlFailure() {
		try {
			buildAndSetWebPageDefaultSettings();
			when(mockConnection.get()).thenThrow(new MalformedURLException());
			webNavigatorIterator.buildAndSetNextWebPage(mockNextDocument);
		} catch (MalformedURLException e) {
			verify(mockMethodLoggerFactory, times(1)).createMethodLoggerAndLogEntry(anyString());
			//PowerMockito.verifyStatic(Jsoup.class, times(1));
			Jsoup.connect(getDefaultString());
			verify(mockString, times(1)).replace(anyInt(), anyInt(), anyString());
			assertTrue("Unexpected iterated value.\nExpected: " + (getDefaultInt() + 1) + "\nActual: " +  webNavigatorIterator.getIteratedValue(),webNavigatorIterator.getIteratedValue() == getDefaultInt() + 1);
		} catch (Exception e) {
			throw new AssertionError(e.getMessage());
		}
	}
	
	@Test
	public void testBuildAndSetNextWebPageHttpStatusFailure() {
		try {
			buildAndSetWebPageDefaultSettings();
			when(mockConnection.get()).thenThrow(new HttpStatusException(getDefaultString(), getDefaultInt(), getDefaultString()));
			webNavigatorIterator.buildAndSetNextWebPage(mockNextDocument);
		} catch (HttpStatusException e) {
			verify(mockMethodLoggerFactory, times(1)).createMethodLoggerAndLogEntry(anyString());
			//PowerMockito.verifyStatic(Jsoup.class, times(1));
			Jsoup.connect(getDefaultString());
			verify(mockString, times(1)).replace(anyInt(), anyInt(), anyString());
			assertTrue("Unexpected iterated value.\nExpected: " + (getDefaultInt() + 1) + "\nActual: " +  webNavigatorIterator.getIteratedValue(),webNavigatorIterator.getIteratedValue() == getDefaultInt() + 1);
		} catch (Exception e) {
			throw new AssertionError(e.getMessage());
		}
	}
	
	@Test
	public void testBuildAndSetNextWebPageUnsupportedMimeTypeFailure() {
		try {
			buildAndSetWebPageDefaultSettings();
			when(mockConnection.get()).thenThrow(new UnsupportedMimeTypeException(getDefaultString(), getDefaultString(), getDefaultString()));
			webNavigatorIterator.buildAndSetNextWebPage(mockNextDocument);
		} catch (UnsupportedMimeTypeException e) {
			verify(mockMethodLoggerFactory, times(1)).createMethodLoggerAndLogEntry(anyString());
			//PowerMockito.verifyStatic(Jsoup.class, times(1));
			Jsoup.connect(getDefaultString());
			verify(mockString, times(1)).replace(anyInt(), anyInt(), anyString());
			assertTrue("Unexpected iterated value.\nExpected: " + (getDefaultInt() + 1) + "\nActual: " +  webNavigatorIterator.getIteratedValue(),webNavigatorIterator.getIteratedValue() == getDefaultInt() + 1);
		} catch (Exception e) {
			throw new AssertionError(e.getMessage());
		}
	}
	
	@Test
	public void testBuildAndSetNextWebPageSocketTimeoutFailure() {
		try {
			buildAndSetWebPageDefaultSettings();
			when(mockConnection.get()).thenThrow(new SocketTimeoutException());
			webNavigatorIterator.buildAndSetNextWebPage(mockNextDocument);
		} catch (SocketTimeoutException e) {
			verify(mockMethodLoggerFactory, times(1)).createMethodLoggerAndLogEntry(anyString());
			//PowerMockito.verifyStatic(Jsoup.class, times(1));
			Jsoup.connect(getDefaultString());
			verify(mockString, times(1)).replace(anyInt(), anyInt(), anyString());
			assertTrue("Unexpected iterated value.\nExpected: " + (getDefaultInt() + 1) + "\nActual: " +  webNavigatorIterator.getIteratedValue(),webNavigatorIterator.getIteratedValue() == getDefaultInt() + 1);
		} catch (Exception e) {
			throw new AssertionError(e.getMessage());
		}
	}
	
	@Test
	public void testBuildAndSetNextWebPageIOFailure() {
		try {
			buildAndSetWebPageDefaultSettings();
			when(mockConnection.get()).thenThrow(new IOException());
			webNavigatorIterator.buildAndSetNextWebPage(mockNextDocument);
		} catch (IOException e) {
			verify(mockMethodLoggerFactory, times(1)).createMethodLoggerAndLogEntry(anyString());
			//PowerMockito.verifyStatic(Jsoup.class, times(1));
			Jsoup.connect(getDefaultString());
			verify(mockString, times(1)).replace(anyInt(), anyInt(), anyString());
			assertTrue("Unexpected iterated value.\nExpected: " + (getDefaultInt() + 1) + "\nActual: " +  webNavigatorIterator.getIteratedValue(),webNavigatorIterator.getIteratedValue() == getDefaultInt() + 1);
		} catch (Exception e) {
			throw new AssertionError(e.getMessage());
		}
	}
	
	private void buildAndSetWebPageDefaultSettings() throws Exception {
		when(mockString.replace(anyInt(), anyInt(), anyString())).thenReturn(mockString);
		PowerMockito.doReturn(mockConnection).when(Jsoup.class,"connect", anyString());
		when(mockConnection.get()).thenReturn(mockNextDocument);
		when(mockMethodLoggerFactory.createMethodLoggerAndLogEntry(anyString())).thenReturn(mockMethodLogger);
	}
	
	@Test
	public void testVerifyWebPageTrueSuccess() {
		hasNextResult = webNavigatorIterator.verifyWebPage();
		assertTrue("Unexpected boolean.\nExpected: true\nActual: " + hasNextResult, hasNextResult );
	}
	
	@Test
	public void testVerifyWebPageFalseSuccess() {
		webNavigatorIterator.setNextDocument(null);
		hasNextResult = webNavigatorIterator.verifyWebPage();
		Assert.assertFalse("Unexpected boolean.\nExpected: false\nActual: " + hasNextResult, hasNextResult);
	}
	
	@Test
	public void testNextSuccess() {
		nextDefaultSettings();
		returnElements = webNavigatorIterator.next();
		verify(mockNextDocument, times(1)).select(anyString());
		assertTrue("Return elements was not mockElements", returnElements == mockElements);
		assertTrue("Current document was not set to next document",webNavigatorIterator.getCurrentDocument().equals(mockNextDocument));
		assertTrue("Next document was not set to null", webNavigatorIterator.getNextDocument() == null);
		verify(mockMethodLoggerFactory, times(1)).createMethodLoggerAndLogEntry(anyString());
	}
	
	@Test
	public void testNextSelectorParseFailure() {
		nextDefaultSettings();
		returnElements = webNavigatorIterator.next();
		verify(mockNextDocument, times(1)).select(anyString());
		assertTrue("Return elements was not mockElements", returnElements == mockElements);
		assertTrue("Current document was not set to next document",webNavigatorIterator.getCurrentDocument().equals(mockNextDocument));
		assertTrue("Next document was not set to null", webNavigatorIterator.getNextDocument() == null);
		verify(mockMethodLoggerFactory, times(1)).createMethodLoggerAndLogEntry(anyString());
	}
	
	public void nextDefaultSettings() {
		when(mockNextDocument.select(anyString())).thenReturn(mockElements);
	}
}
