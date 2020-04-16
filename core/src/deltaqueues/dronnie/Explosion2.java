package deltaqueues.dronnie;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Explosion2 {

    public static final float FRAME_LENGTH = 0.1f;
    public static final int OFFSET = 8;
    public static final int SIZE = 64;


    private static Animation<TextureRegion> anim = null;
    float x, y;
    float statetime;

    public boolean remove = false;

    public Explosion2 (float x, float y) {
        this.x = x - OFFSET;
        this.y = y - OFFSET;
        statetime = 0;

        if (anim == null) {
            anim = new Animation<TextureRegion>(FRAME_LENGTH, TextureRegion.split(new Texture("explosion6.png"), SIZE, SIZE)[1]);
           // anim = new Animation<TextureRegion>(FRAME_LENGTH, TextureRegion.split(new Texture("explosion6.png"), SIZE, SIZE)[1]);
        }
    }

    public void update (float deltatime) {
        statetime += deltatime;
        if (anim.isAnimationFinished(statetime))
            remove = true;
    }

    public void render (SpriteBatch batch) {
        batch.draw(anim.getKeyFrame(statetime), x, y, SIZE, SIZE);
    }


}
