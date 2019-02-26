package com.gans.server.constants;

/**
 *
 */
public enum HttpStatus {


	OK(200, "OK"),
	CREATED(201, "Created"),
    NO_CONTENT(204,"deleted"),
	BAD_REQUEST(400, "Bad request"),
	FORBIDDEN(403, "Forbidden"),
	NOT_FOUND(404, "Not found"),
    METHOD_NOT_SUPPORTED(405, "Not found"),
	REQUEST_TIMEOUT(408, "Request timeout"),
	INTERNAL_SERVER_ERROR(500, "Internal server error"),
	HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version not supported");

	private final int code;
	private final String reason;

	private HttpStatus(int code, String reason) {
		this.code = code;
		this.reason = reason;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getReason() {
		return reason;
	}
}
