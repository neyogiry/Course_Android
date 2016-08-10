package neyo.demo.androidchat.lib;

/**
 * Created by Neyo
 */
public interface EventBus {

    void register(Object subscriber);
    void unregister(Object subscriber);
    void post(Object event);

}
