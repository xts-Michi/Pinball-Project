import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class Wall {

    World world;
    Vec2 start;
    Vec2 end;

    public Wall(World world, Vec2 start, Vec2 end) {
        this.world = world;
        this.start = start;
        this.end = end;
        createWall(world, start, end);
    }

    private void createWall(World world, Vec2 start, Vec2 end) {
        BodyDef wallDef = new BodyDef();
        wallDef.type = BodyType.STATIC;
        Body wall = world.createBody(wallDef);

        EdgeShape edge = new EdgeShape();
        edge.set(start, end);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = edge;
        fixtureDef.friction = 0.3f;

        wall.createFixture(fixtureDef);
        wall.setUserData(this);
    }

    public Vec2 getStart() {
        return start;
    }

    public Vec2 getEnd() {
        return end;
    }
}
