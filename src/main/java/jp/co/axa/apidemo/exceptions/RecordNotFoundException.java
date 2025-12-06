package jp.co.axa.apidemo.exceptions;

/**
 * Not found exception. Return 404 HTTP code.
 */
public class RecordNotFoundException extends Exception {

    public RecordNotFoundException(Long employeeId) {
        super(employeeId.toString());
    }
}
