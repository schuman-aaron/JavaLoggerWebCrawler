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
	Element mockElement;
	
	@Mock
	Iterable<Elements> mockIterable;
	
	@Mock
	Iterator<Elements> mockIterator;
	
	Elements returnElements;
	
	
	@Before
	public void Setup() {
		mockElements = mock(Elements.class);
		mockElement = mock(Element.class);
		mockIterable = mock(Iterable.class);
	}
	
	@Test
	public void testSearchWebPageByAttributeSuccess() {
		
	}
	
	private void SearchWebPageByAttributeDefaultSettings() {
		when(mockIterable.iterator()).thenReturn(mockIterator);
	}
	
}
