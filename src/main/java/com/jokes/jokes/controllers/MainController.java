package com.jokes.jokes.controllers;
import com.jokes.jokes.Models.Jokes;
import com.jokes.jokes.Models.JokesRepo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    JokesRepo jokesRepo;

    @RequestMapping("/")
    public ModelAndView doHome(){
        ModelAndView mv = new ModelAndView("index");
        String Value = "";
        String Url = "";
        String complete = "";
        String apikey = "06cb82f2a0msh09e77aea071a151p127e61jsn28327ba0cd99";
        try
        {
            URL url = new URL("https://matchilling-chuck-norris-jokes-v1.p.rapidapi.com/jokes/random");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.setRequestMethod("GET");
            connection.setRequestProperty("x-rapidapi-host", "matchilling-chuck-norris-jokes-v1.p.rapidapi.com");
            connection.setRequestProperty("x-rapidapi-key", apikey);
            connection.connect();
            BufferedReader r  = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                json.append(line);
            }
            JSONObject obj = new JSONObject(json.toString());
            Value = obj.getString("value");
            Url = obj.getString("url");
            complete = Value + ": " + Url;
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        Jokes valueToSave ;
        String id="";

            valueToSave = new Jokes();

        valueToSave.setId(UUID.randomUUID().toString());
        valueToSave.setValue(Value);
        valueToSave.setUrl(Url);
        jokesRepo.save(valueToSave);
        mv.addObject("complete", complete);
        return mv;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(){
        ModelAndView mv = new ModelAndView("save");
        mv.addObject("jokes", jokesRepo.findAll());
        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ModelAndView load(){
        ModelAndView mv = new ModelAndView("redirect:/");
        return mv;
    }

    @RequestMapping( value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable("id") String id){
        ModelAndView mv = new ModelAndView("delete");
        jokesRepo.deleteById(id);
        mv.addObject("jokes", jokesRepo.findAll());
        return mv;
    }

    @RequestMapping( value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable("id") String id){
        ModelAndView mv = new ModelAndView("edit");
        Optional<Jokes> person = jokesRepo.findById(id);
        Jokes jokesToMap = person.get();
        mv.addObject("selectedItem", jokesToMap);
        return mv;
    }
}
