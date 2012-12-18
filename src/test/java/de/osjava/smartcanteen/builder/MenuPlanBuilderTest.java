package de.osjava.smartcanteen.builder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.osjava.smartcanteen.AbstractTest;
import de.osjava.smartcanteen.data.Canteen;

public class MenuPlanBuilderTest extends AbstractTest {

    private MenuPlanBuilder menuPlanBuilder;

    @Before
    public void setUp() {
        menuPlanBuilder = new MenuPlanBuilder(createProviderBase(), createRecipeBase());
    }

    @Test
    public void testBuildMenuPlan() {

        Canteen[] canteens = menuPlanBuilder.buildMenuPlan();

    }

    @After
    public void tearDown() {

    }
}
