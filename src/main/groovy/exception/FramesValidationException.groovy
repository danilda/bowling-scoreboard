package exception

/**
 * Created by danil on 19.08.2017.
 */
class FramesValidationException extends DateValidationException{
    private defaultMessage = "Exception in frames validation "

    FramesValidationException(String message) {
        super(message)
    }
}
