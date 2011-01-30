package dan.vjtest.sandbox.multimethods;

/**
 * @author Alexander Dovzhikov
 */
public class Collisions {

    private Collisions() {
    }

    public static void collide(Asteroid a1, Asteroid a2) {
        System.out.printf("Collision: Asteroid %d with Asteroid %d%n", a1.hashCode(), a2.hashCode());
    }

    public static void collide(Asteroid a, Spaceship s) {
        System.out.printf("Collision: Asteroid %d with Spaceship %d%n", a.hashCode(), s.hashCode());
    }

    public static void collide(Spaceship s, Asteroid a) {
        System.out.printf("Collision: Spaceship %d with Asteroid %d%n", s.hashCode(), a.hashCode());
    }

    public static void collide(Spaceship s1, Spaceship s2) {
        System.out.printf("Collision: Spaceship %d with Spaceship %d%n", s1.hashCode(), s2.hashCode());
    }
}
