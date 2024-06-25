import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class Ball {
    private Body body;

    public Ball(World world, Vec2 position, float radius) {
        BodyDef ballDef = new BodyDef();
        ballDef.type = BodyType.DYNAMIC;
        ballDef.position.set(position);
        body = world.createBody(ballDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(radius); // Radius des Balls (0,25 m)

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1.0f;
        fixtureDef.restitution = 0.9f; // Elastizität für perfektes Abprallen
        fixtureDef.friction = 0.0f;

        body.createFixture(fixtureDef);
    }

    public void setLinearVelocity(Vec2 velocity) {
        body.setLinearVelocity(velocity);
    }

    public Vec2 getPosition() {
        return body.getPosition();
    }

    public float getRadius() {
        CircleShape circle = (CircleShape) body.getFixtureList().getShape();
        return circle.getRadius();
    }

    public Vec2 getVelocity() {
        return body.getLinearVelocity();
    }
}
