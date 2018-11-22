package laughtracks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get(endpoint).accept(MediaType.APPLICATION_JSON_VALUE));
        MvcResult mvcResult  = resultActions.andReturn();
        Integer status = mvcResult.getResponse().getStatus();
        Integer okStatus = 200;
        Assert.assertEquals(okStatus, status);

        String content = mvcResult.getResponse().getContentAsString();
        Comedian[] comedianList = super.mapFromJson(content, Comedian[].class);
        Assert.assertEquals(2, comedianList.length);
    }
}
