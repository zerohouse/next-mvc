package next.mvc.response;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

import next.mvc.dispatch.Dispatcher;
import next.mvc.http.Http;

/**
 * 패스를 지정한 static 파일을 리턴합니다.
 * 
 */
public class StaticFile implements Response {

	private String path;

	public StaticFile(String path) {
		this.path = Dispatcher.CONTEXT_PATH + path;
	}

	@Override
	public void render(Http http) {
		ServletOutputStream out;
		try {
			out = http.getResp().getOutputStream();
			InputStream in = new FileInputStream(path);
			copy(in, out);
			out.flush();
			out.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static int copy(InputStream input, OutputStream output) throws IOException {
		long count = copyLarge(input, output);
		if (count > Integer.MAX_VALUE) {
			return -1;
		}
		return (int) count;
	}

	public static long copyLarge(InputStream input, OutputStream output) throws IOException {

		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		long count = 0;
		int n = 0;
		while (EOF != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

	private static final int EOF = -1;
}
