package jurl.designpatterns.proxy.plugin;

public interface PluginManager {

    void subscribe(PluginBeforeMethod plugin, String method);

    void unsubscribe(PluginBeforeMethod plugin, String method);

    void subscribe(PluginAfterMethod plugin, String method);

    void unsubscribe(PluginAfterMethod plugin, String method);

    <T> void beforeCall(String method, T subject, Object... args);

    <T, R> R afterCall(String method, T subject, R result, Object... args);
}
