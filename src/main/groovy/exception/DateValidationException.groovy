package exception

/**
 * Created by danil on 19.08.2017.
 */
class DateValidationException extends Exception {
    private defaultMessage = "Exception in bowling board date validation"
    DateValidationException(String message) {
        super(message);
    }
}
