package jurl.designpatterns.proxy.plugin;

public interface PluginBeforeMethod extends Plugin {

    void beforeCall(String method, Object... arguments);
}
