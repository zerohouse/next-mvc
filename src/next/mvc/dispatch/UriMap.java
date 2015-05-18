package next.mvc.dispatch;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import next.mvc.dispatch.support.PatternAndKeys;
import next.mvc.http.Http;

public class UriMap {

	private Map<UriKey, Queue<MethodWrapper>> uriMap;
	private Map<String, Queue<PatternAndKeys>> regexMap;
	private Map<PatternAndKeys, Queue<MethodWrapper>> patternMap;
	private final Pattern pattern;

	public UriMap() {
		uriMap = new ConcurrentHashMap<UriKey, Queue<MethodWrapper>>();
		regexMap = new ConcurrentHashMap<String, Queue<PatternAndKeys>>();
		patternMap = new ConcurrentHashMap<PatternAndKeys, Queue<MethodWrapper>>();
		pattern = Pattern.compile(PatternAndKeys.REGEX);
	}

	public void put(UriKey key, Queue<MethodWrapper> methodList) {
		Matcher matcher = pattern.matcher(key.getUri());
		if (!key.getUri().contains("*") && !matcher.find()) {
			uriMap.put(key, methodList);
			return;
		}
		Queue<PatternAndKeys> regexList = regexMap.get(key.getMethod());
		if (regexList == null) {
			regexList = new ConcurrentLinkedQueue<PatternAndKeys>();
			regexMap.put(key.getMethod(), regexList);
		}
		PatternAndKeys pak = new PatternAndKeys(key.getUri());
		regexList.add(pak);
		patternMap.put(pak, methodList);
	}

	public Queue<MethodWrapper> get(UriKey key, Http http) {
		Queue<MethodWrapper> methodArray = uriMap.get(key);
		if (methodArray != null)
			return methodArray;
		Queue<PatternAndKeys> regexList = regexMap.get(key.getMethod());
		if (regexList == null)
			return null;
		Iterator<PatternAndKeys> keys = regexList.iterator();
		while (keys.hasNext()) {
			PatternAndKeys pak = keys.next();
			if (pak.find(key.getUri(), http)) {
				methodArray = patternMap.get(pak);
				return methodArray;
			}
		}
		return null;
	}

}
