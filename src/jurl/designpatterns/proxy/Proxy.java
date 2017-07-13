package jurl.designpatterns.proxy;

import jurl.designpatterns.proxy.plugin.Plugin;
import jurl.designpatterns.proxy.plugin.PluginManager;
import jurl.designpatterns.proxy.plugin.PluginManagerDummy;


public class Proxy implements Interface {

    public static String METHOD_ONE = "serviceOne";
    public static String METHOD_TWO = "serviceTwo";

    private Interface subject;

    private PluginManager pluginManager;

    public Proxy(Interface subject) {
        this(subject, new PluginManagerDummy());
    }

    public Proxy(Interface subject, PluginManager pluginManager) {
        setSubject(subject);
        setPluginManager(pluginManager);
    }

    public void setSubject(Interface subject) {
        this.subject = subject;
    }

    public void setPluginManager(PluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    public Interface getSubject() {
        return subject;
    }

    public int serviceOne() {

        pluginManager.beforeCall(METHOD_ONE, getSubject());

        int result = getSubject().serviceOne();

        result = pluginManager.afterCall(METHOD_TWO, getSubject(), result);

        return result;
    }

    public String serviceTwo(int value, String data) {

        return getSubject().serviceTwo(value, data);
    }
}
