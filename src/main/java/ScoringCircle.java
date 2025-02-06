import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

import javax.swing.*;
import java.awt.*;

public class ScoringCircle {
    Body body;
    float radius;
    int points;
    final Image circleGraphic = new ImageIcon("images/scoring_circle_new.png").getImage();

    public ScoringCircle(World world, Vec2 pos, float radius, int points) {
        BodyDef circleDef = new BodyDef();
        circleDef.type = BodyType.STATIC;
        circleDef.position.set(pos);
        body = world.createBody(circleDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;

        body.createFixture(fixtureDef);
        body.setUserData(this);

        this.radius = radius;
        this.points = points;
    }

    private Image getCircleGraphic() {
        return circleGraphic;
    }
}
