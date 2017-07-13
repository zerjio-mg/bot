package jurl.designpatterns.proxy.plugin;

public class PluginManagerDummy implements PluginManager {

    @Override
    public void subscribe(PluginBeforeMethod plugin, String method) {}

    @Override
    public void unsubscribe(PluginBeforeMethod plugin, String method) {}

    @Override
    public void subscribe(PluginAfterMethod plugin, String method) {}

    @Override
    public void unsubscribe(PluginAfterMethod plugin, String method) {}

    @Override
    public <T> void beforeCall(String method, T subject, Object... args) {}

    @Override
    public <T, R> R afterCall(String method, T subject, R result, Object... args) { return null; }
}
