package bowling.scoreboard

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
            }
        }

        "/"(view:"/scoreboard/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
