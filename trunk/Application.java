
/**
 * Write a description of class Bootstrapper here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Application
{

    /**
     * Constructor for objects of class Bootstrapper
     */
    public Application()
    {

    }
    
    public void bootstrap(final String[] args) throws Exception {
        if(initInput(args)) {
            startApplication();
        }
    }
    
    private boolean initInput(final String[] args) {
        return true;
    }
    
    private void startApplication() {
    }
}
