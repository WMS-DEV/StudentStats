package pl.wmsdev.universities.exception;

// Should be thrown only when someone uses external way to send request
public class UnsupportedUniversityException extends RuntimeException {

	public UnsupportedUniversityException(String universityId) {
		super("Couldn't find university with id: " + universityId);
	}
}
