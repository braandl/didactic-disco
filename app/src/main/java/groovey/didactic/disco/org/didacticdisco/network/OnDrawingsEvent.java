package groovey.didactic.disco.org.didacticdisco.network;


public class OnDrawingsEvent extends BaseNetworkEvent<DrawingsResponse> {

    public OnDrawingsEvent(final DrawingsResponse response) {
        super(response);
    }

    public OnDrawingsEvent(final Throwable throwable) {
        super(throwable);
    }
}
