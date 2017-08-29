package exception

/**
 * Created by danil on 19.08.2017.
 */
class FramesValidationException extends RuntimeException{
    private defaultMessage = "Exception in frames validation "

    FramesValidationException(String message) {
        super(message)
    }

    FramesValidationException() {
        super()
    }
}
