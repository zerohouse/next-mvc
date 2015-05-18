package next.mvc.parameter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.http.Part;

import next.mvc.dispatch.Dispatcher;
import next.mvc.setting.Setting;

public class UploadFile implements Part {

	Part part;

	private String fileName;
	private String extention;
	private String path;

	public UploadFile(Part part) {
		this.part = part;
		extention = part.getSubmittedFileName().replaceFirst("\\S+\\.(\\w+)", "$1");
		fileName = part.getSubmittedFileName().replaceFirst("(\\S+)\\.\\w+", "$1");
		path = Setting.getMapping().getUpload().getLocation();
		pathEndCheck();
	}

	private void pathEndCheck() {
		if (path.charAt(path.length() - 1) != '/')
			path = path + '/';
	}

	public String getFileName() {
		return fileName;
	}
	
	/**
	 * 파일명을 지정합니다. 확장자는 유지됩니다.
	 * <p>
	 *
	 * @param fileName
	 *            업데이트할 파일명
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

	/**
	 * 상대경로를 리턴합니다.
	 * <p>
	 *
	 * @return String 상대경로
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 서버내의 절대경로를 리턴합니다.
	 * <p>
	 *
	 * @return String 절대경로
	 */
	public String getFullPath() {
		return Dispatcher.CONTEXT_PATH + path + getFullName();
	}

	/**
	 * 확장자를 포함한파일명을 리턴합니다.
	 * <p>
	 *
	 * @return String 파일명
	 */
	public String getFullName() {
		return fileName + DOT + extention;
	}

	private static final char DOT = '.';

	/**
	 * 파일의 경로를 리턴합니다.
	 * <p>
	 *
	 * @return String 경로
	 */
	public String getUriPath() {
		return path + getFullName();
	}
	
	/**
	 * 서버의 Uri주소를 합친 경로를 리턴합니다.
	 * <p>
	 *
	 * @return String 경로
	 */
	public String getUriFullPath() {
		return Setting.getMapping().getUrl() + path + getFullName();
	}

	public void save() throws IOException {
		part.write(getFullPath());
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return part.getInputStream();
	}

	@Override
	public String getContentType() {
		return part.getContentType();
	}

	@Override
	public String getName() {
		return part.getName();
	}

	@Override
	public String getSubmittedFileName() {
		return part.getSubmittedFileName();
	}

	@Override
	public long getSize() {
		return part.getSize();
	}

	@Override
	public void write(String fileName) throws IOException {
		part.write(Dispatcher.CONTEXT_PATH + Setting.getMapping().getUpload().getTempSaveLocation() + fileName);
	}

	@Override
	public void delete() throws IOException {
		part.delete();
	}

	@Override
	public String getHeader(String name) {
		return part.getHeader(name);
	}

	@Override
	public Collection<String> getHeaders(String name) {
		return part.getHeaders(name);
	}

	@Override
	public Collection<String> getHeaderNames() {
		return part.getHeaderNames();
	}

}
