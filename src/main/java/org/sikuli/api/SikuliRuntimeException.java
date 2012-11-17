package org.sikuli.api;

public class SikuliRuntimeException extends RuntimeException{

	public SikuliRuntimeException() {
		super();
	}

	public SikuliRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public SikuliRuntimeException(String message) {
		super(message);
	}

	public SikuliRuntimeException(Throwable cause) {
		super(cause);
	}

}
