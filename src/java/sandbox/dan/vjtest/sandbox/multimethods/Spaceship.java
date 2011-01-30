package dan.vjtest.sandbox.multimethods;

/**
 * @author Alexander Dovzhikov
 */
public class Spaceship extends SpaceObject {
    public void collideWith(SpaceObject spaceObject) {
        spaceObject.collideWithSpaceship(this);
    }

    public void collideWithAsteroid(Asteroid asteroid) {
        Collisions.collide(asteroid, this);
    }

    public void collideWithSpaceship(Spaceship spaceship) {
        Collisions.collide(spaceship, this);
    }
}
