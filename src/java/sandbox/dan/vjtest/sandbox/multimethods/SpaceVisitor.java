package dan.vjtest.sandbox.multimethods;

/**
 * @author Alexander Dovzhikov
 */
public interface SpaceVisitor {

    void visitAsteroid(Asteroid asteroid);

    void visitSpaceship(Spaceship spaceship);
}
