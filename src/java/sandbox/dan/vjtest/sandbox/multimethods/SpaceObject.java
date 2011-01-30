package dan.vjtest.sandbox.multimethods;

/**
 * @author Alexander Dovzhikov
 */
abstract public class SpaceObject implements SpaceVisitor {

    public void collideWith(SpaceObject spaceObject) {
        accept(spaceObject);
    }

    public abstract void accept(SpaceVisitor visitor);
}
