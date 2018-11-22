package laughtracks;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import laughtracks.models.Comedian;
import main.java.laughtracks.exceptions.comedian.ComedianNotfoundException;


@RestController
public class ComediansController {
    @Autowired
    private ComedianRepository comedianRepository;
    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value="/comedians")
    public ResponseEntity<Object> getComedians() {
        return new ResponseEntity<>(comedianRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value="/comedians/{id}")
    public ResponseEntity<Object> getComedian(@PathVariable("id") Integer id) {
        Optional<Comedian> optionalComedian = comedianRepository.findById(id);
        if (!optionalComedian.isPresent()) {
            throw new ComedianNotfoundException();
        }
        Comedian comedian = optionalComedian.get();
        return new ResponseEntity<>(comedian, HttpStatus.OK);
    }

    @RequestMapping(value="/comedians", method=RequestMethod.POST)
    public ResponseEntity<Object> postComedian(@RequestBody Comedian comedian) {
        comedianRepository.save(comedian);
        return new ResponseEntity<>("Comedian added successfully", HttpStatus.CREATED);
    }

    @RequestMapping(value="/comedians/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Object> putComedian(@PathVariable("id") Integer id, @RequestBody Comedian comedian) {
        Optional<Comedian> optionalComedian = comedianRepository.findById(id);
        if (!optionalComedian.isPresent()) {
            throw new ComedianNotfoundException();
        }
        Comedian orginalComedian = optionalComedian.get();
        if (comedian.getAge() != null) {
            orginalComedian.setAge(comedian.getAge());
        }
        if (comedian.getName() != null) {
            orginalComedian.setName(comedian.getName());
        }
        if (comedian.getCity() != null) {
            orginalComedian.setCity(comedian.getCity());
        }
        comedianRepository.save(orginalComedian);
        return new ResponseEntity<>("Comedian updated successfully", HttpStatus.OK);
    }

    @RequestMapping(value="/comedians/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Object> deleteComedian(@PathVariable("id") Integer id) {
        Optional<Comedian> optionalComedian = comedianRepository.findById(id);
        if (!optionalComedian.isPresent()) {
            throw new ComedianNotfoundException();
        }
        Comedian comedian = optionalComedian.get();
        comedianRepository.delete(comedian);
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
