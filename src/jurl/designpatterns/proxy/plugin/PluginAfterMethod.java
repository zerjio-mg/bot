package jurl.designpatterns.proxy.plugin;

public interface PluginAfterMethod extends Plugin {

    void afterCall(String method, Object result, Object... arguments);
}
