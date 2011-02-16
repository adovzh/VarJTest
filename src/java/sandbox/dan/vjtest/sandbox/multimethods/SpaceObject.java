package dan.vjtest.sandbox.multimethods;

/**
 * @author Alexander Dovzhikov
 */
public abstract class SpaceObject {

    public abstract void collideWith(SpaceObject spaceObject);

    public abstract void collideWithAsteroid(Asteroid asteroid);

    public abstract void collideWithSpaceship(Spaceship spaceship);
}
