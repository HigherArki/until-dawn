package academia.tilldawn;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class EvilDrone {

    private Rectangle rectangle;
    private Rectangle player;

    public EvilDrone(Rectangle player) {
        this.rectangle = new Rectangle();
        rectangle.x = MathUtils.random(Utilities.BACKGROUND_WIDTH/2);
        rectangle.y = MathUtils.random( Utilities.BACKGROUND_HEIGHT);
        rectangle.width = Utilities.PICTURE_SIZE;
        rectangle.height = Utilities.PICTURE_SIZE;
        this.player = player;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void moveTowardsPlayer(){

        switch (checkHorizontalDirection()){
            case LEFT:
                rectangle.x -= Utilities.ENEMY_SPEED * Gdx.graphics.getDeltaTime();
                break;

            case RIGHT:
                rectangle.x += Utilities.ENEMY_SPEED * Gdx.graphics.getDeltaTime();
                break;
        }

        switch (checkVerticalDirection()){
            case DOWN:
                rectangle.y -= Utilities.ENEMY_SPEED * Gdx.graphics.getDeltaTime();
                break;
            case UP:
                rectangle.y += Utilities.ENEMY_SPEED * Gdx.graphics.getDeltaTime();
        }

    }

    private Directions checkHorizontalDirection(){

        float enemyX = rectangle.x;
        float playerX = player.x;

        int distance = (int) (enemyX - playerX);

        if (distance == 0){
            return Directions.STAY;
        }

        return distance > 0 ? Directions.LEFT : Directions.RIGHT;
    }

    private Directions checkVerticalDirection(){

        float enemyY = rectangle.y;
        float playerY = player.y;

        int distance = (int) (enemyY - playerY);

        if (distance == 0) {
            return Directions.STAY;
        }

        return distance > 0 ? Directions.DOWN : Directions.UP;
    }


}
