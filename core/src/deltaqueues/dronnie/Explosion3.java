package deltaqueues.dronnie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Explosion3 {

    private static final int FRAME_COLS = 8, FRAME_ROWS = 8;
    private static final float OFFSET = 8;
    Animation<TextureRegion> walkAnimation;
    Texture walkSheet;
    private final static int STARTING_X = 50;
    private final static int STARTING_Y = 50;
    TextureRegion reg;
    float stateTime;
    public boolean remove;
    float x, y;


    public Explosion3(float x, float y){
        this.x = x - OFFSET;
        this.y = y - OFFSET;
        createIdleAnimation();
    }

    private void createIdleAnimation() {
        walkSheet = new Texture(Gdx.files.internal("explosion2.png"));

        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth()/FRAME_COLS,
                walkSheet.getHeight()/FRAME_ROWS);

        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        //walkAnimation = new Animation<TextureRegion>(0.025f, walkFrames);
        walkAnimation = new Animation<TextureRegion>(0.1f,walkFrames);
        stateTime = 0f;
        reg=walkAnimation.getKeyFrame(0);
    }


    public void update (float deltatime) {
        stateTime += deltatime;
        if (walkAnimation.isAnimationFinished(stateTime))
            remove = true;
    }

    public void render (SpriteBatch batch) { batch.draw(walkAnimation.getKeyFrame(stateTime), x, y, 256, 256);
    }






}
