package de.osjava.smartcanteen.base;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import de.osjava.smartcanteen.AbstractTest;

public class InputDataProcessingTest extends AbstractTest {

    private InputDataProcessing indp;

    @Before
    public void setUp() {

        indp = new InputDataProcessing();

    }

    @Test
    public void testReadHitlist() {

        URL inputFileUrl = ClassLoader
                .getSystemResource("input/hitliste.csv");
        HitListBase readHitlist = null;
        HitListBase tmpHitListBase = null;

        try {

            readHitlist = indp.readHitlist(inputFileUrl);
        } catch (IOException e) {

            e.printStackTrace();
        }

        tmpHitListBase = createHitListBase();

        assertNotNull(readHitlist);
        assertNotNull(readHitlist.getHitListItems());
        // assertEquals(readHitlist, tmpHitListBase);
    }
}
