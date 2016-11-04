package groovey.didactic.disco.org.didacticdisco.events;

import groovey.didactic.disco.org.didacticdisco.network.Result;

/**
 * Created by sbrandt on 04.11.16.
 */

public class NewOnDrawEvent {

    private Result result;

    public  NewOnDrawEvent(Result r) {
        result = r;

    }

    public Result getResult() {
        return result;
    }
}
