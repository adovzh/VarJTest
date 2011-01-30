package dan.vjtest.sandbox.multimethods;

/**
 * @author Alexander Dovzhikov
 */
public class Asteroid extends SpaceObject {
    @Override
    public void accept(SpaceVisitor visitor) {
        visitor.visitAsteroid(this);
    }

    public void visitAsteroid(Asteroid asteroid) {
        Collisions.collide(asteroid, this);
    }

    public void visitSpaceship(Spaceship spaceship) {
        Collisions.collide(spaceship, this);
    }
}
