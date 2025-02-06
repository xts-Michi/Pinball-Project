import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

public class Windmill {
    private Body cross;
    private RevoluteJoint joint;
    private final float armLength;
    private final float armWidth;

    public Windmill(World world, Vec2 pos, float armLength, float armWidth, float speed) {
        this.armLength = armLength;
        this.armWidth = armWidth;

        // Create the dynamic body for the cross
        BodyDef crossDef = new BodyDef();
        crossDef.type = BodyType.DYNAMIC;
        crossDef.position.set(pos);
        cross = world.createBody(crossDef);

        // Create the first arm fixture
        createArmFixture(cross, new Vec2(0, 0), 0);

        // Create the second arm fixture rotated by 90 degrees
        createArmFixture(cross, new Vec2(0, 0), (float) Math.PI / 2);

        // Create a static body for the pivot
        BodyDef pivotDef = new BodyDef();
        pivotDef.type = BodyType.STATIC;
        pivotDef.position.set(pos);
        Body pivot = world.createBody(pivotDef);

        // Create a revolute joint to rotate the cross
        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.bodyA = pivot;
        jointDef.bodyB = cross;
        jointDef.localAnchorA.set(0, 0);  // anchor at the center of the pivot
        jointDef.localAnchorB.set(0, 0);  // anchor at the center of the cross
        jointDef.enableMotor = true;
        jointDef.motorSpeed = speed;
        jointDef.maxMotorTorque = 10.0f;

        joint = (RevoluteJoint) world.createJoint(jointDef);
        joint.setUserData(this);
        cross.setUserData(this);
    }

    private void createArmFixture(Body body, Vec2 center, float angle) {
        PolygonShape armShape = new PolygonShape();
        armShape.setAsBox(armLength / 2, armWidth / 2, center, angle);

        FixtureDef fixtureDefArm = new FixtureDef();
        fixtureDefArm.shape = armShape;
        fixtureDefArm.density = 1.0f;
        fixtureDefArm.friction = 0.0f;

        body.createFixture(fixtureDefArm);
    }

    public RevoluteJoint getJoint() {
        return joint;
    }

    public float getArmLength() {
        return armLength;
    }

    public float getArmWidth() {
        return armWidth;
    }
}
