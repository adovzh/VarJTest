package dan.vjtest.sandbox.multimethods;

/**
 * @author Alexander Dovzhikov
 */
public class Spaceship extends SpaceObject {
    @Override
    public void accept(SpaceVisitor visitor) {
        visitor.visitSpaceship(this);
    }

    public void visitAsteroid(Asteroid asteroid) {
        Collisions.collide(asteroid, this);
    }

    public void visitSpaceship(Spaceship spaceship) {
        Collisions.collide(spaceship, this);
    }
}
