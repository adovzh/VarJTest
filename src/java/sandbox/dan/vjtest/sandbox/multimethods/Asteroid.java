package dan.vjtest.sandbox.multimethods;

/**
 * @author Alexander Dovzhikov
 */
public class Asteroid extends SpaceObject {
    public void collideWith(SpaceObject spaceObject) {
        spaceObject.collideWithAsteroid(this);
    }

    public void collideWithAsteroid(Asteroid asteroid) {
        Collisions.collide(asteroid, this);
    }

    public void collideWithSpaceship(Spaceship spaceship) {
        Collisions.collide(spaceship, this);
    }
}
