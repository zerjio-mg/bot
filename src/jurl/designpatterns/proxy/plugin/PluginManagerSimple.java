package jurl.designpatterns.proxy.plugin;

import jurl.designpatterns.proxy.Interface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginManagerSimple implements PluginManager {

    Map<String, List<Plugin>> beforeMethodPlugins;
    Map<String, List<Plugin>> afterMethodPlugins;

    public PluginManagerSimple(Interface subject) {
        beforeMethodPlugins = new HashMap();
        afterMethodPlugins = new HashMap();
    }

    @Override
    public void subscribe(PluginBeforeMethod plugin, String method) {
//        lookupPluginsByMethod(beforeMethodPlugins, )
//        beforeMethodPlugins.put(method, plugin);
    }

    @Override
    public void unsubscribe(PluginBeforeMethod plugin, String method) {

    }

    @Override
    public void subscribe(PluginAfterMethod plugin, String method) {
//        afterMethodPlugins.put(method, plugin);
    }

    @Override
    public void unsubscribe(PluginAfterMethod plugin, String method) {

    }

    @Override
    public <T> void beforeCall(String method, T subject, Object... args) {

    }

    @Override
    public <T, R> R  afterCall(String method, T subject, R result, Object... args) {
        return null;
    }

    private List<Plugin> lookupPluginsByMethod(Map<String, List<Plugin>> plugins, String method) {

        if (!plugins.containsKey(method)) {
            plugins.put(method, new ArrayList());
        }

        return plugins.get(method);
    }
}
