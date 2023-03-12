package recruitment.hashapi.exceptions;

public abstract class HashApiException extends RuntimeException {

    public HashApiException(String message) {
        super(message);
    }
}
