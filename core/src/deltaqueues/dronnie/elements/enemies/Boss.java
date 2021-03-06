package deltaqueues.dronnie.elements.enemies;

import deltaqueues.dronnie.Utilities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class Boss extends AbstractEnemy {

    private long lastShootTime = 0;

    public Boss(Rectangle player) {

        body = new Rectangle();
        body.x = MathUtils.random(Utilities.BACKGROUND_WIDTH/2);
        body.y = MathUtils.random( Utilities.BACKGROUND_HEIGHT);
        body.width = Utilities.PICTURE_SIZE;
        body.height = Utilities.PICTURE_SIZE;

        bodyPic = new Texture(Gdx.files.internal("b-johnson-drone-32.png"));

        this.player = player;
    }

    public void dispose(){
        bodyPic.dispose();
    }

    public void shoot(){
        lastShootTime = TimeUtils.nanoTime();
    }

    public long getLastShootTime(){
        return lastShootTime;
    }

}
