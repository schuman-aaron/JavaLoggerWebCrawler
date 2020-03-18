package net.schuman.JavaLoggerWebCrawler.Core;

public class StringBuilderProxy {
	private StringBuilder stringBuilder;
	
	public StringBuilderProxy(String str) {
		stringBuilder = new StringBuilder(str);
	}
	
	public String toString() {
		return stringBuilder.toString();
	}
	
	public StringBuilderProxy replace(int start, int end, String str) {
		stringBuilder.replace(start, end, str);
		return this;
	}
}
