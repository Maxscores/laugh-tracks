package laughtracks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.Ignore;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import laughtracks.models.Comedian;
import test.java.laughtracks.controllers.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;


public class ComediansControllerTest extends TestTemplate {
    @Autowired
    private ComedianRepository comedianRepository;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        Comedian jerrySeinfeld = new Comedian();
        jerrySeinfeld.setName("Jerry Seinfeld");
        jerrySeinfeld.setAge(45);
        jerrySeinfeld.setCity("New York City");
        comedianRepository.save(jerrySeinfeld);

        Comedian azizAnsari = new Comedian();
        azizAnsari.setName("Aziz Ansari");
        azizAnsari.setAge(31);
        azizAnsari.setCity("New York City");
        comedianRepository.save(azizAnsari);
    }

    @Test
    public void getComediansList() throws Exception {
        String endpoint = "/comedians";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(endpoint).accept(MediaType.APPLICATION_JSON_VALUE);

        ResultActions resultActions = mvc.perform(request);
        MvcResult result  = resultActions.andReturn();
        Integer status = result.getResponse().getStatus();
        Integer okStatus = 200;
        Assert.assertEquals(okStatus, status);

        String content = result.getResponse().getContentAsString();
        Comedian[] comedianList = super.mapFromJson(content, Comedian[].class);
        Assert.assertTrue(comedianList.length > 1);
    }

    @Test
    public void getComedian() throws Exception {
        String endpoint = "/comedians/1";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(endpoint);
        request.accept(MediaType.APPLICATION_JSON_VALUE);

        ResultActions resultActions = mvc.perform(request);
        MvcResult result = resultActions.andReturn();
        Integer status = result.getResponse().getStatus();
        Integer okStatus = 200;
        Assert.assertEquals(okStatus, status);

        String content = result.getResponse().getContentAsString();
        Comedian comedianOne = super.mapFromJson(content, Comedian.class);
        Integer expectedId = 1;
        Assert.assertEquals(expectedId, comedianOne.getId());
    }

    @Test
    public void postComedian() throws Exception {
        String endpoint = "/comedians";
        Comedian newComedian = new Comedian();
        newComedian.setName("Jimmy Chang");
        newComedian.setAge(37);
        newComedian.setCity("Boston");

        String inputJson = super.mapToJson(newComedian);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(endpoint);
        request.contentType(MediaType.APPLICATION_JSON_VALUE);
        request.content(inputJson);

        ResultActions resultActions = mvc.perform(request);
        MvcResult result = resultActions.andReturn();

        Integer status = result.getResponse().getStatus();
        Integer createdStatus = 201;
        Assert.assertEquals(createdStatus, status);

        String content = result.getResponse().getContentAsString();
        Assert.assertEquals("Comedian added successfully", content);
    }

    @Test
    public void putComedian() throws Exception {
        String endpoint = "/comedians/1";
        Comedian updatedComedian = new Comedian();
        updatedComedian.setName("Gerald Seinfeld");
        updatedComedian.setAge(48);
        updatedComedian.setCity("Manhattan");

        String inputJson = super.mapToJson(updatedComedian);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(endpoint);
        request.contentType(MediaType.APPLICATION_JSON_VALUE);
        request.content(inputJson);

        ResultActions resultActions = mvc.perform(request);
        MvcResult result = resultActions.andReturn();

        Integer status = result.getResponse().getStatus();
        Integer okStatus = 200;
        Assert.assertEquals(okStatus, status);

        String content = result.getResponse().getContentAsString();
        Assert.assertEquals("Comedian updated successfully", content);
    }
}
