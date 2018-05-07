package com.senhotel.project;

import javax.servlet.ServletException;

public class RequestInvalidException extends ServletException {

	private static final long serialVersionUID = 1L;

	public RequestInvalidException() {
		// TODO Auto-generated constructor stub
	}

	public RequestInvalidException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public RequestInvalidException(Throwable rootCause) {
		super(rootCause);
		// TODO Auto-generated constructor stub
	}

	public RequestInvalidException(String message, Throwable rootCause) {
		super(message, rootCause);
		// TODO Auto-generated constructor stub
	}

}
