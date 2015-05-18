package next.mvc.dispatch.support;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class DateParser implements JsonDeserializer<Date> {

	private List<String> dateformats;

	public DateParser() {
		dateformats = new ArrayList<String>();
	}

	public void addFormat(String format) {
		dateformats.add(format);
	}

	@Override
	public Date deserialize(JsonElement jsonElement, Type typeOF, JsonDeserializationContext context) throws JsonParseException {
		for (String format : dateformats) {
			try {
				return new SimpleDateFormat(format).parse(jsonElement.getAsString());
			} catch (ParseException e) {
			}
		}
		throw new JsonParseException("Unparseable date: \"" + jsonElement.getAsString() + "\". Supported formats: " + dateformats);
	}
}
