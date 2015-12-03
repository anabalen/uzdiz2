package State;

import Element.Element;
import java.util.List;

/**
 *
 * @author Ana-Marija
 */
public class Context implements State {

    private State state;

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    @Override
    public Element doAction(Element element) {
        state.doAction(element);
        return element;
    }
}
