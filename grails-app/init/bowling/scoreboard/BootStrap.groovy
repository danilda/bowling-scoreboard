package bowling.scoreboard

import grails.converters.JSON
import static constants.GameConstants.DATE_FORMAT_FOR_JSON

class BootStrap {

    def init = { servletContext ->
        JSON.registerObjectMarshaller(Date) {
            return it?.format(DATE_FORMAT_FOR_JSON)
        }
    }
    def destroy = {
    }
}
