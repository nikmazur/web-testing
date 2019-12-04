package helpers;

import org.aeonbits.owner.Config;

@Config.Sources("file:application.properties")
public interface Properties extends Config {
    boolean remote();
    String selenoidUrl();
    boolean headless();
}
