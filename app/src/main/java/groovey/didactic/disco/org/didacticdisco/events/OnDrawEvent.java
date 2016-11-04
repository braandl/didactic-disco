package groovey.didactic.disco.org.didacticdisco.events;


import groovey.didactic.disco.org.didacticdisco.network.DrawResponse;

public class OnDrawEvent extends BaseNetworkEvent<DrawResponse> {

    public OnDrawEvent(final DrawResponse response) {
        super(response);
    }

    public OnDrawEvent(final Throwable throwable) {
        super(throwable);
    }
}
