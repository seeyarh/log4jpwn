package com.sensepost.log4jpwn;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.google.gson.Gson;


import static spark.Spark.*;


public class App {
    public class User {
    private String id;
    // constructors, getters and setters
    }

    public interface UserService {

        public void addUser (User user);
    }

    static final Logger logger = LogManager.getLogger(App.class.getName());

    public static void main(String[] args) {

        port(8080);

        get("/*", (req, res) -> {

            String ua = req.headers("User-Agent");
            String pwn = req.queryParams("pwn");
            String pth = req.pathInfo();

            System.out.println("logging ua: " + ua);
            System.out.println("logging pwn: " + pwn);
            System.out.println("logging pth: " + pth);

            // trigger
            logger.error(ua);
            logger.error(pwn);
            logger.error(pth);

            return "ok: ua: " + ua + " " + "pwn: " + pwn + " pth:" + pth;
        });

        post("/json", (request, response) -> {
            User user = new Gson().fromJson(request.body(), User.class);

            logger.error(user.id);

            return "ok";
        });

        post("/form", (request, response) -> {
            String id = request.queryParams("id");
            logger.error(id);
            return "ok";
        });

    }
}
