package bowling.scoreboard

import grails.converters.JSON

class BootStrap {

    def init = { servletContext ->
        JSON.registerObjectMarshaller(Date) {
            return it?.format("yyyy-MM-dd'T'HH:mm:ssZ")
        }
    }
    def destroy = {
    }
}
