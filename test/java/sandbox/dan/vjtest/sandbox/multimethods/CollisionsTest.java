package dan.vjtest.sandbox.multimethods;

import org.junit.Test;

/**
 * @author Alexander Dovzhikov
 */
public class CollisionsTest {

    @Test
    public void collisions() {
        SpaceObject[] spaceObjects = new SpaceObject[2];
        spaceObjects[0] = new Asteroid();
        spaceObjects[1] = new Spaceship();

        for (SpaceObject firstObject : spaceObjects)
            for (SpaceObject secondObject : spaceObjects)
                firstObject.collideWith(secondObject);
    }
}
