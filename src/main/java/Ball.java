import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import javax.swing.*;
import java.awt.*;

public class Ball {
    private Body body;
    final Image ballGraphic = new ImageIcon("images/ball_new.png").getImage();

    public Ball(World world, Vec2 position, float radius) {
        BodyDef ballDef = new BodyDef();
        ballDef.type = BodyType.DYNAMIC;
        ballDef.position.set(position);
        body = world.createBody(ballDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1.0f;
        fixtureDef.restitution = 0.8f;
        fixtureDef.friction = 0.0f;

        body.createFixture(fixtureDef);
        body.setUserData(this);
    }

    public void setLinearVelocity(Vec2 velocity) {
        body.setLinearVelocity(velocity);
    }

    public Vec2 getPosition() {
        return body.getPosition();
    }

    public void setPosition(Vec2 position) {
        body.setTransform(position, body.getAngle());
    }

    public float getRadius() {
        CircleShape circle = (CircleShape) body.getFixtureList().getShape();
        return circle.getRadius();
    }

    public Vec2 getVelocity() {
        return body.getLinearVelocity();
    }
}
