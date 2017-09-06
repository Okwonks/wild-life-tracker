import java.util.Map;
import java.util.HashMap;

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String layout = "templates/layout.vtl";

        /* For deployment to herokuapp */
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        setPort(port);

        /**
         * Logic for the routes that will hold the business logic
         * This helps in displaying data from the DB
         */
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
             model.put("template", "templates/index.vtl");
             return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        
         //Routes for adding new sightings or animals to the sight.
        get("/sightings/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("sightings", Sighting.all());
            model.put("template", "templates/sighting-form.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/animals/new", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("template", "templates/animal-sighted-form.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/animals/", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();

            String age = request.queryParams("age"); //This will help in filtering out the animals.
            if (age == null) {
                String animalName = request.queryParams("animal");
                String health = request.queryParams("health");
                SafeAnimal newAnimal = new SafeAnimal(animalName, health);
                newAnimal.save();
            } else {
                String animalName = request.queryParams("animal");
                String health = request.queryParams("health");
                EndangeredAnimal newAnimal = new EndangeredAnimal(animalName, health, age);
                newAnimal.save();
            }
            model.put("template", "templates/sighted-animal.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //Route to display all the animals.
        get("/animals", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("endangeredAnimals", EndangeredAnimal.all());
            model.put("safeAnimals", SafeAnimal.all());
            model.put("template", "templates/animals.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //Route for specific animals
        get("/animals/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            EndangeredAnimal endangeredAnimal = EndangeredAnimal.find(Integer.parseInt(request.params(":id")));
            SafeAnimal safeAnimal = SafeAnimal.find(Integer.parseInt(request.params(":id")));
            model.put("endangeredAnimal", endangeredAnimal);
            model.put("safeAnimal", safeAnimal);
            model.put("template", "templates/animal.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        //Post method for the sighting of an animal
        post("/sighting", (request, response) -> {
            
        });
    }
}