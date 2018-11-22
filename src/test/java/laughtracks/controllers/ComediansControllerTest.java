package laughtracks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import main.java.laughtracks.models.Comedian;
import test.java.laughtracks.controllers.TestTemplate;

public class ComediansControllerTest extends TestTemplate {
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getComdeiansList() throws Exception {
        String endpoint = "/comedians";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(endpoint).accept(MediaType.APPLICATION_JSON_VALUE);

        ResultActions resultActions = mvc.perform(request);
        MvcResult result  = resultActions.andReturn();
        Integer status = result.getResponse().getStatus();
        Integer okStatus = 200;
        Assert.assertEquals(okStatus, status);

        String content = result.getResponse().getContentAsString();
        Comedian[] comedianList = super.mapFromJson(content, Comedian[].class);
        Assert.assertEquals(2, comedianList.length);
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
        Assert.assertEquals("1", comedianOne.getId());
        Assert.assertEquals("Jerry Seinfeld", comedianOne.getName());
        Assert.assertEquals("New York City", comedianOne.getCity());

    }
}
