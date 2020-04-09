package academia.tilldawn.screens;

import academia.tilldawn.Beacon;
import academia.tilldawn.EvilDrone;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import static academia.tilldawn.Utilities.*;
import static academia.tilldawn.Utilities.PICTURE_SIZE;

public class GameScreen implements Screen {

    private Game game;
    private TextureRegion background;

    private Texture dronePic;
    private Texture evilDronePic;
    private Texture beaconPic;
    private Texture targetPic;

    private Rectangle drone;
    private Rectangle target;
    private Array<EvilDrone> evilDrones;
    private Beacon beacon;
    private Sprite arrow;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private String yourScoreName;
    private BitmapFont yourBitmapFontName;
    private BitmapFont hp;

    private long lastDropTime;

    public GameScreen(Game game) {
        this.game = game;

        // starts graphic representations
        background = new TextureRegion(new Texture("map-bkg-huge.jpg"), 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
        dronePic = new Texture(Gdx.files.internal("bonnie-drone-32.png"));
        evilDronePic = new Texture(Gdx.files.internal("virus-32.png"));
        beaconPic = new Texture(Gdx.files.internal("arrowRight.png"));
        targetPic = new Texture(Gdx.files.internal("toillete.png"));

        camera = new OrthographicCamera();

        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        batch = new SpriteBatch();

        // starts Player Drone logic position
        drone = new Rectangle();
        drone.x = PICTURE_SIZE * 2;
        drone.y = BACKGROUND_HEIGHT / 2 - PICTURE_SIZE / 2;
        drone.width = PICTURE_SIZE;
        drone.height = PICTURE_SIZE;

        target = new Rectangle();
        target.x = PICTURE_SIZE*25;
        target.y = BACKGROUND_HEIGHT/2 - PICTURE_SIZE*50;
        target.width = PICTURE_SIZE;
        target.height = PICTURE_SIZE;



        evilDrones = new Array<EvilDrone>();
        beacon = new Beacon(camera);

        arrow = new Sprite(beaconPic);

        yourScoreName = "SCORE: 0";
        hp = new BitmapFont();
        yourBitmapFontName = new BitmapFont();
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(dronePic, drone.x, drone.y);
        batch.draw(beaconPic, beacon.getX(), beacon.getY());

        batch.draw(targetPic, target.x, target.y);


        arrow.setSize(20,20);
        arrow.setPosition(drone.x, drone.y - arrow.getHeight()/2 - 25);

        float xInput = target.x;
        float yInput = target.y;

        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(yInput - drone.y, xInput - drone.x +40);

        if(angle < 0){
            angle += 360;
        }
        arrow.setRotation(angle);


        arrow.draw(batch);



        yourBitmapFontName.setColor(Color.GREEN);
        yourBitmapFontName.draw(batch, yourScoreName, camera.position.x - VIEWPORT_WIDTH / 2 + 20, camera.position.y + VIEWPORT_HEIGHT / 2 - 20);
        hp.setColor(Color.GREEN);
        hp.draw(batch, "HEALTH: 100", camera.position.x + VIEWPORT_WIDTH / 2 - 150, camera.position.y + VIEWPORT_HEIGHT / 2 - 20);


        // draws EvilDrones in position and moves them towards PlayerDrone;
        for (EvilDrone raindrop : evilDrones) {
            batch.draw(evilDronePic, raindrop.getRectangle().x, raindrop.getRectangle().y);
            raindrop.moveTowardsPlayer();
        }
        batch.end();

        // Player move left
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            drone.x -= PLAYER_SPEED * Gdx.graphics.getDeltaTime();
        }

        // Player move right
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            drone.x += PLAYER_SPEED * Gdx.graphics.getDeltaTime();
        }

        // Horizontal bounds
        if (drone.x < 0) drone.x = 0;
        if (drone.x > BACKGROUND_WIDTH - PICTURE_SIZE) drone.x = BACKGROUND_WIDTH - PICTURE_SIZE;

        // Player move up
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            drone.y += PLAYER_SPEED * Gdx.graphics.getDeltaTime();
        }

        // Player move down
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            drone.y -= PLAYER_SPEED * Gdx.graphics.getDeltaTime();
        }

        // Vertical bounds
        if (drone.y < 0) drone.y = 0;
        if (drone.y > BACKGROUND_HEIGHT - 64) drone.y = BACKGROUND_HEIGHT - 64;


        //move camera horizontally
        if (drone.x > VIEWPORT_WIDTH / 2 - PICTURE_SIZE / 2 && drone.x < (BACKGROUND_WIDTH - VIEWPORT_WIDTH / 2) - PICTURE_SIZE / 2) {
            camera.position.set(drone.getX() + PICTURE_SIZE / 2, camera.position.y, 0);
        }

        //move camera vertically
        if (drone.y > VIEWPORT_HEIGHT / 2 && drone.y < BACKGROUND_HEIGHT - VIEWPORT_HEIGHT / 2) {
            camera.position.set(camera.position.x, drone.getY(), 0);
        }

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        evilDronePic.dispose();
        dronePic.dispose();
        batch.dispose();
    }

    private void spawnRaindrop() {

        if (evilDrones.size >= 5) {
            return;
        }

        EvilDrone evilDrone = new EvilDrone(drone);
        evilDrones.add(evilDrone);
        lastDropTime = TimeUtils.nanoTime();
    }

}
