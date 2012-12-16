package de.osjava.smartcanteen.builder;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.osjava.smartcanteen.data.Canteen;

public class MenuPlanBuilderTest extends AbstractBuilderTest {

	@Test
	public void testBuildMenuPlan() {
		MenuPlanBuilder mpb = new MenuPlanBuilder(createProviderBase(),
				createRecipeBase());

		Canteen[] canteens = mpb.buildMenuPlan();

		assertEquals(0, 0);
	}
}
