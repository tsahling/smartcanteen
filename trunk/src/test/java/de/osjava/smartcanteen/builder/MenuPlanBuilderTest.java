package de.osjava.smartcanteen.builder;

import static org.junit.Assert.assertNotNull;

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

        for (Canteen canteen : canteens) {
            assertNotNull(canteen.getMenuPlan());
            assertNotNull(canteen.getMenuPlan().getMeals());
        }
    }

    @After
    public void tearDown() {

    }
}
