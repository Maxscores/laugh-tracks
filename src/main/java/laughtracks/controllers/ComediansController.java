package laughtracks;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import main.java.laughtracks.models.Comedian;
import main.java.laughtracks.exceptions.comedian.ComedianNotfoundException;


@RestController
public class ComediansController {
    private static Map<String, Comedian> comedianRepo = new HashMap<>();
    private RestTemplate restTemplate = new RestTemplate();

    static {
        Comedian jerrySeinfeld = new Comedian();
        jerrySeinfeld.setId("1");
        jerrySeinfeld.setName("Jerry Seinfeld");
        jerrySeinfeld.setAge(45);
        jerrySeinfeld.setCity("New York City");
        comedianRepo.put(jerrySeinfeld.getId(), jerrySeinfeld);

        Comedian azizAnsari = new Comedian();
        azizAnsari.setId("2");
        azizAnsari.setName("Aziz Ansari");
        azizAnsari.setAge(31);
        azizAnsari.setCity("New York City");
        comedianRepo.put(azizAnsari.getId(), azizAnsari);
    }

    @RequestMapping(value="/comedians")
    public ResponseEntity<Object> getComedians() {
        return new ResponseEntity<>(comedianRepo.values(), HttpStatus.OK);
    }

    @RequestMapping(value="/comedians/{id}")
    public ResponseEntity<Object> getComedian(@PathVariable("id") String id) {
        if (!comedianRepo.containsKey(id)) {
            throw new ComedianNotfoundException();
        }
        return new ResponseEntity<>(comedianRepo.get(id), HttpStatus.OK);
    }

    @RequestMapping(value="/comedians", method=RequestMethod.POST)
    public ResponseEntity<Object> postComedian(@RequestBody Comedian comedian) {
        comedianRepo.put(comedian.getId(), comedian);
        return new ResponseEntity<>("Comedian added successfully: " + comedian, HttpStatus.CREATED);
    }

    @RequestMapping(value="/comedians/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Object> putComedian(@PathVariable("id") String id, @RequestBody Comedian comedian) {
        if (!comedianRepo.containsKey(id)) {
            throw new ComedianNotfoundException();
        }
        Comedian orgComedian = comedianRepo.get(id);
        if (comedian.getAge() != null) {
            orgComedian.setAge(comedian.getAge());
        }
        if (comedian.getName() != null) {
            orgComedian.setName(comedian.getName());
        }
        if (comedian.getCity() != null) {
            orgComedian.setCity(comedian.getCity());
        }
            return new ResponseEntity<>("Comedian Updated successfully: " + orgComedian, HttpStatus.OK);
    }

    @RequestMapping(value="/comedians/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Object> deleteComedian(@PathVariable("id") String id) {
        comedianRepo.remove(id);
        return new ResponseEntity<>("Comedian removed successfully", HttpStatus.OK);
    }

    @RequestMapping(value="/getComedians", method=RequestMethod.GET)
    public ResponseEntity<Object> requestComedians() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        return restTemplate.exchange("http://localhost:8080/comedians", HttpMethod.GET, entity, Object.class);
    }
}
