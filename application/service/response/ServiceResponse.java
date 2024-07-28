package application.service.response;

import application.repository.ILogger;
import infrastructure.log.ConsoleLogger;

import javax.ws.rs.core.Response;

public final class ServiceResponse<T> {
    private final T data;
    private final HttpCode httpCode;
    private String message;
    private static final ILogger logger = ConsoleLogger.getInstance();

    // Constructor for void response
    public ServiceResponse(HttpCode httpCode, String message) {
        this.data = null;
        this.httpCode = httpCode;
        this.message = message;

    }
    // Constructor for data response
    public ServiceResponse(T data) {
        this.data = data;
        this.httpCode = HttpCode.OK;
    }

    // Constructor for data with custom http code
    public ServiceResponse(HttpCode code, T data) {
        this.data = data;
        this.httpCode = code;
    }

    public T data() { return data; }
    public HttpCode httpCode() { return httpCode; }
    public String message() { return message; }

    public Response toResponseObject() {
        if(this.httpCode() == HttpCode.NOT_FOUND){
            logger.logError(this.message());
            return Response.status(404).entity(this.message()).build();
        }

        if(this.httpCode() == HttpCode.BAD_REQUEST){
            logger.logError(this.message());
            return Response.status(400).entity(this.message()).build();
        }

        if(this.httpCode() == HttpCode.CREATED){
            logger.logInfo(this.message());
            return Response.status(201).entity(this.data).build();
        }

        // If void type, return message, else return data
        if(this.data() == null) logger.logInfo(this.message());
        else logger.logInfo("retrieved: "+data);

        return Response.ok(this.data() == null ? this.message() : this.data()).build();
    }

}
