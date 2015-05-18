package next.mvc.dispatch;

public class UriKey {

	public static final String METHOD = "METHOD";
	private String method;
	private String uri;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UriKey other = (UriKey) obj;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

	public UriKey(String method, String uri) {
		this.method = method;
		this.uri = uri;
	}

	@Override
	public String toString() {
		return "Uri [method:" + method + ", uri:" + uri + "]";
	}


	public String getUri() {
		return uri;
	}

	public String getMethod() {
		return method;
	}

}
