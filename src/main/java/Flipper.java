import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

public class Flipper {

    private World world;
    private Body circleStaticBody;
    private Body rectangleBat;
    private RevoluteJoint joint;
    private float circleRadius;
    private float batLength;
    private Vec2 pos;

    public RevoluteJoint getJoint() {
        return joint;
    }

    public Flipper(
            World world,
            float circleRadius,
            float batLength,
            float maxAngle,
            float initialAngle,
            float speed,
            float gravityScale,
            boolean clockWise,
            Vec2 pos) {
        // create static circle
        BodyDef circleDef = new BodyDef();
        circleDef.type = BodyType.STATIC;
        circleDef.position.set(pos);
        circleStaticBody = world.createBody(circleDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(circleRadius);

        FixtureDef fixtureDefCircle = new FixtureDef();
        fixtureDefCircle.shape = circle;
        fixtureDefCircle.density = 1.0f;
        fixtureDefCircle.friction = 0.0f;
        circleStaticBody.createFixture(fixtureDefCircle);
        circleStaticBody.setUserData(this);

        // create bat (which will rotate)
        BodyDef batDef = new BodyDef();
        batDef.type = BodyType.DYNAMIC;
        batDef.position.set(pos);
        batDef.angle = (float) Math.toRadians(initialAngle);
        batDef.gravityScale = gravityScale;
        rectangleBat = world.createBody(batDef);

        PolygonShape batShape = new PolygonShape();
        batShape.setAsBox(batLength / 2, circleRadius);

        FixtureDef fixtureDefBat = new FixtureDef();
        fixtureDefBat.shape = batShape;
        fixtureDefBat.density = 1.0f;
        fixtureDefBat.friction = 0.0f;
        rectangleBat.createFixture(fixtureDefBat);
        rectangleBat.setUserData(this);

        // create revolute joint
        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.bodyA = circleStaticBody;
        jointDef.bodyB = rectangleBat;
        jointDef.localAnchorA.set(0,0);
        jointDef.localAnchorB.set(-batLength / 2, 0);
        jointDef.enableMotor = false;
        jointDef.motorSpeed = clockWise ? speed : -speed;
        jointDef.maxMotorTorque = 20f;
        jointDef.enableLimit = true;
        jointDef.lowerAngle = clockWise ? (float) Math.toRadians(initialAngle) : (float) (Math.toRadians(initialAngle) - Math.toRadians(maxAngle));
        jointDef.upperAngle = clockWise ? (float) (Math.toRadians(initialAngle) + Math.toRadians(maxAngle)) : (float) Math.toRadians(initialAngle);

        joint = (RevoluteJoint) world.createJoint(jointDef);

        this.pos = pos;
        this.circleRadius = circleRadius;
        this.batLength = batLength;
    }

    public void flip() {
        joint.enableMotor(true);
    }

    public void deflip() {
        joint.enableMotor(false);
    }

    public Vec2 getPos() {
        return this.pos;
    }

    public float getCircleRadius() {
        return this.circleRadius;
    }

    public Body getRectangleBat() {
        return rectangleBat;
    }

    public float getBatLength() {
        return batLength;
    }
}