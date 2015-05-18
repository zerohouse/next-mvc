package next.mvc.setting;

import java.io.FileReader;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Setting {

	private static Reflections reflections;
	private static Mapping mapping;
	private static Gson gson;

	static {
		try {
			mapping = new Gson().fromJson(new FileReader(Setting.class.getResource("/next-mvc.json").getFile()), Mapping.class);
		} catch (Exception e) {
			mapping = new Mapping();
			e.printStackTrace();
		}
		gson = new GsonBuilder().setDateFormat(mapping.getDateFormat()).create();
		reflections = new Reflections(mapping.getBasePackage(), new SubTypesScanner(), new TypeAnnotationsScanner(), new FieldAnnotationsScanner(),
				new MethodAnnotationsScanner());

	}

	public static Reflections getReflections() {
		return reflections;
	}

	public static Mapping getMapping() {
		return mapping;
	}

	public static Gson getGson() {
		return gson;
	}

}
