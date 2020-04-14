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


public class WebCrawlerTest {
	
	@Mock
	Elements mockElements;
	
	@Mock
	Elements mockReturnElements;

	@Mock
	Element mockElement;
	
	@Mock
	Iterable<Elements> mockElementsIterable;
	
	@Mock
	Iterator<Elements> mockElementsIterator;
	
	@Mock
	Iterator<Element> mockElementIterator;
	
	
	@Before
	public void Setup() {
		mockElements = mock(Elements.class);
		mockElement = mock(Element.class);
		mockElementsIterable = mock(Iterable.class);
		mockElementsIterator = mock(Iterator.class);
		mockElementIterator = mock(Iterator.class);
		mockReturnElements = mock(Elements.class);
	}

	@Test
	public void testSearchWebPageByAttributeSuccess() {
		searchWebPageByAttributeDefaultSettings();
		WebCrawler.searchWebPageByAttributeValueContaining(mockElementsIterable, mockReturnElements, getDefaultString(), getDefaultString());
		verify(mockElementsIterable, times(1)).iterator();
		verify(mockElementsIterator, times(2)).hasNext();
		verify(mockElementsIterator, times(1)).next();
		verify(mockElements, times(1)).iterator();
		verify(mockElementIterator, times(2)).hasNext();
		verify(mockElementIterator, times(1)).next();
		verify(mockElement, times(1)).getElementsByAttribute(anyString());
		verify(mockReturnElements, times(1)).addAll(any(Elements.class));
	}
	
	@Test
	public void testSearchWebPageByAttributeIterableNullPointerFailure() {
		searchWebPageByAttributeDefaultSettings();
		try {
			WebCrawler.searchWebPageByAttributeValueContaining(null, mockReturnElements, getDefaultString(), getDefaultString());
			throw new AssertionError("Unexpected successful execution");
		} catch(NullPointerException e) {
			verify(mockElementsIterable, times(0)).iterator();
			verify(mockElementsIterator, times(0)).hasNext();
			verify(mockElementsIterator, times(0)).next();
			verify(mockElements, times(0)).iterator();
			verify(mockElementIterator, times(0)).hasNext();
			verify(mockElementIterator, times(0)).next();
			verify(mockElement, times(0)).getElementsByAttribute(anyString());
			verify(mockReturnElements, times(0)).addAll(any(Elements.class));
		}
	}
	
	@Test
	public void testSearchWebPageByAttributeElementsAddsAllNullPointerFailure() {
		searchWebPageByAttributeDefaultSettings();
		when(mockReturnElements.addAll(any(Elements.class))).thenThrow(new NullPointerException());
		try {
			WebCrawler.searchWebPageByAttributeValueContaining(mockElementsIterable, mockReturnElements, getDefaultString(), getDefaultString());
			throw new AssertionError("Unexpected successful execution");
		} catch(NullPointerException e) {
			verify(mockElementsIterable, times(1)).iterator();
			verify(mockElementsIterator, times(1)).hasNext();
			verify(mockElementsIterator, times(1)).next();
			verify(mockElements, times(1)).iterator();
			verify(mockElementIterator, times(1)).hasNext();
			verify(mockElementIterator, times(1)).next();
			verify(mockElement, times(1)).getElementsByAttribute(anyString());
			verify(mockReturnElements, times(1)).addAll(any(Elements.class));
		}
	}
	
	private void searchWebPageByAttributeDefaultSettings() {
		when(mockElementsIterable.iterator()).thenReturn(mockElementsIterator);
		when(mockElementsIterator.hasNext()).thenReturn(true).thenReturn(false);
		when(mockElementsIterator.next()).thenReturn(mockElements);
		when(mockElements.iterator()).thenReturn(mockElementIterator);
		when(mockElementIterator.hasNext()).thenReturn(true).thenReturn(false);
		when(mockElementIterator.next()).thenReturn(mockElement);
		when(mockReturnElements.addAll(any(Elements.class))).thenReturn(true);
		when(mockElement.getElementsByAttribute(anyString())).thenReturn(mockElements);
	}
	
	
	@Test
	public void testSearchWebPageContainingTextSuccess() {
		searchWebPageContainingTextDefaultSettings();
		WebCrawler.searchWebPageContainingText(mockElementsIterable, mockReturnElements, getDefaultString());
		verify(mockElementsIterable, times(1)).iterator();
		verify(mockElementsIterator, times(2)).hasNext();
		verify(mockElementsIterator, times(1)).next();
		verify(mockElements, times(1)).iterator();
		verify(mockElementIterator, times(2)).hasNext();
		verify(mockElementIterator, times(1)).next();
		verify(mockElement, times(1)).getElementsContainingText(anyString());
		verify(mockReturnElements, times(1)).addAll(any(Elements.class));
	}
	
	@Test
	public void testSearchWebPageContainingTextIterableNullPointerFailure() {
		searchWebPageContainingTextDefaultSettings();;
		try {
			WebCrawler.searchWebPageContainingText(null, mockReturnElements, getDefaultString());
			throw new AssertionError("Unexpected successful execution");
		} catch(NullPointerException e) {
			verify(mockElementsIterable, times(0)).iterator();
			verify(mockElementsIterator, times(0)).hasNext();
			verify(mockElementsIterator, times(0)).next();
			verify(mockElements, times(0)).iterator();
			verify(mockElementIterator, times(0)).hasNext();
			verify(mockElementIterator, times(0)).next();
			verify(mockElement, times(0)).getElementsContainingText(anyString());
			verify(mockReturnElements, times(0)).addAll(any(Elements.class));
		}
	}
	
	@Test
	public void testSearchWebPageContainingTextElementsAddsAllNullPointerFailure() {
		searchWebPageByAttributeDefaultSettings();
		when(mockReturnElements.addAll(any(Elements.class))).thenThrow(new NullPointerException());
		try {
			WebCrawler.searchWebPageByAttributeValueContaining(mockElementsIterable, mockReturnElements, getDefaultString(), getDefaultString());
			throw new AssertionError("Unexpected successful execution");
		} catch(NullPointerException e) {
			verify(mockElementsIterable, times(1)).iterator();
			verify(mockElementsIterator, times(1)).hasNext();
			verify(mockElementsIterator, times(1)).next();
			verify(mockElements, times(1)).iterator();
			verify(mockElementIterator, times(1)).hasNext();
			verify(mockElementIterator, times(1)).next();
			verify(mockElement, times(1)).getElementsContainingText(anyString());
			verify(mockReturnElements, times(1)).addAll(any(Elements.class));
		}
	}
	
	private void searchWebPageContainingTextDefaultSettings() {
		when(mockElementsIterable.iterator()).thenReturn(mockElementsIterator);
		when(mockElementsIterator.hasNext()).thenReturn(true).thenReturn(false);
		when(mockElementsIterator.next()).thenReturn(mockElements);
		when(mockElements.iterator()).thenReturn(mockElementIterator);
		when(mockElementIterator.hasNext()).thenReturn(true).thenReturn(false);
		when(mockElementIterator.next()).thenReturn(mockElement);
		when(mockReturnElements.addAll(any(Elements.class))).thenReturn(true);
		when(mockElement.getElementsContainingText(anyString())).thenReturn(mockElements);
	}
}
