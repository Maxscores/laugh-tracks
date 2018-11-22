package laughtracks;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import laughtracks.models.Comedian;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ComedianTests {
    @Test
    public void testCanInitializeObject() {
        Comedian seinfeld = new Comedian();

        Assert.assertEquals(Comedian.class, seinfeld.getClass());
        Assert.assertNull(seinfeld.getName());
        Assert.assertNull(seinfeld.getAge());
        Assert.assertNull(seinfeld.getCity());

        seinfeld.setName("Jerry Seinfeld");
        Assert.assertEquals("Jerry Seinfeld", seinfeld.getName());

        seinfeld.setAge(67);
        Integer expected = 67;
        Assert.assertEquals(expected, seinfeld.getAge());

        seinfeld.setCity("New York City");
        Assert.assertEquals("New York City", seinfeld.getCity());
    }
}
