package groovey.didactic.disco.org.didacticdisco.events;


import groovey.didactic.disco.org.didacticdisco.network.Result;

public class OnDrawEvent extends BaseNetworkEvent<Result> {

    public OnDrawEvent(final Result response) {
        super(response);
    }

    public OnDrawEvent(final Throwable throwable) {
        super(throwable);
    }
}
