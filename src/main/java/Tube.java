import org.jbox2d.collision.shapes.ChainShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

public class Tube {
    private Body tubeBody;
    private Vec2[] outerVertices;
    private Vec2[] innerVertices;
    private boolean clockWise; // determines the calculation of the inner vertices

    public Tube(World world, Vec2 pos, Vec2 p0, Vec2 p1, Vec2 p2, Vec2 p3, int segments, float thickness, boolean clockWise) {
        this.clockWise = clockWise;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;
        bodyDef.position.set(pos);
        tubeBody = world.createBody(bodyDef);
        tubeBody.setUserData(this);

        // Create the outer ChainShape
        outerVertices = calculateOuterVertices(p0, p1, p2, p3, segments);
        innerVertices = calculateInnerVertices(outerVertices, thickness);

        // Create and attach the outer chain shape
        ChainShape outerChainShape = new ChainShape();
        outerChainShape.createChain(outerVertices, outerVertices.length);
        tubeBody.createFixture(outerChainShape, 0.0f);

        // Create and attach the inner chain shape
        ChainShape innerChainShape = new ChainShape();
        innerChainShape.createChain(innerVertices, innerVertices.length);
        tubeBody.createFixture(innerChainShape, 0.0f);
    }

    private Vec2[] calculateOuterVertices(Vec2 p0, Vec2 p1, Vec2 p2, Vec2 p3, int segments) {
        Vec2[] vertices = new Vec2[segments + 1];
        for (int i = 0; i <= segments; i++) {
            float t = (float) i / segments;
            float x = (float) (Math.pow(1 - t, 3) * p0.x + 3 * Math.pow(1 - t, 2) * t * p1.x + 3 * (1 - t) * Math.pow(t, 2) * p2.x + Math.pow(t, 3) * p3.x);
            float y = (float) (Math.pow(1 - t, 3) * p0.y + 3 * Math.pow(1 - t, 2) * t * p1.y + 3 * (1 - t) * Math.pow(t, 2) * p2.y + Math.pow(t, 3) * p3.y);
            vertices[i] = new Vec2(x, y);
        }
        return vertices;
    }

    private Vec2[] calculateInnerVertices(Vec2[] outerVertices, float thickness) {
        Vec2[] innerVertices = new Vec2[outerVertices.length];
        for (int i = 0; i < outerVertices.length; i++) {
            Vec2 current = outerVertices[i];
            Vec2 previous = i > 0 ? outerVertices[i - 1] : outerVertices[i];
            Vec2 next = i < outerVertices.length - 1 ? outerVertices[i + 1] : outerVertices[i];

            // Calculate tangent vector
            Vec2 tangent = next.sub(previous);
            // Calculate normal vector (perpendicular to tangent)
            Vec2 normal = clockWise ? new Vec2(-tangent.y, tangent.x) : new Vec2(tangent.y, -tangent.x);
            normal.normalize();

            Vec2 inward = normal.mul(thickness);
            innerVertices[i] = current.add(inward);
        }
        return innerVertices;
    }

    public Body getTubeBody() {
        return tubeBody;
    }

    public Vec2[] getInnerVertices() {
        return innerVertices;
    }

    public Vec2[] getOuterVertices() {
        return outerVertices;
    }
}
