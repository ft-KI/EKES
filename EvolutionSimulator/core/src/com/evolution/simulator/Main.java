package com.evolution.simulator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.evolution.simulator.BackEnd.EvolutionsSimulator;
import com.evolution.simulator.FrontEnd.tilemaprenderer.SimulationsRenderer;

import static com.badlogic.gdx.Gdx.input;

public class Main extends ApplicationAdapter{
	EvolutionsSimulator evolutionsSimulator;
	SimulationsRenderer simulationsRenderer;
	public static OrthographicCamera infocam;
	public static Viewport infoViewport;
	public static SpriteBatch batch;
	public static SpriteBatch InfoBatch;
	public static ShapeRenderer InfoShapeRenderer;
	public static BitmapFont infoFont;
	public static GlyphLayout glyphLayout=new GlyphLayout();
	public static ShapeRenderer shapeRenderer;
	public static Texture img;
	public static Texture watertile;
	public static Texture LandTile;
	public static OrthographicCamera cam;
	public static Viewport viewport;
	public static int simulationbeschleunigen=1;//beschleunigt simulation//so viele schritte pro frame(hat keine auswirkungen auf genauigkeit)


	
	@Override
	public void create () {


		InfoShapeRenderer=new ShapeRenderer();
		shapeRenderer=new ShapeRenderer();
		InfoBatch=new SpriteBatch();
		infoFont=new BitmapFont();
		infocam=new OrthographicCamera();
		cam = new OrthographicCamera((float)Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight());
		viewport = new ScreenViewport(cam);
		evolutionsSimulator=new EvolutionsSimulator();
		simulationsRenderer=new SimulationsRenderer(evolutionsSimulator);
		batch = new SpriteBatch();
		img = new Texture("Land.jpg");
		watertile=new Texture("WaterTile.png");
		LandTile=new Texture("LandTile.jpg");
		cam.position.set((float)(Gdx.graphics.getWidth() / 2), (float)(Gdx.graphics.getHeight() / 2), 0.0F);
		infoViewport=new ScreenViewport(infocam);



	}

	@Override
	public void render () {
		if(input.isKeyJustPressed(Input.Keys.Y)){
			simulationbeschleunigen+=10;
		}
		if(input.isKeyJustPressed(Input.Keys.X)){
			simulationbeschleunigen-=10;
		}
		if(input.isKeyJustPressed(Input.Keys.S)){
			simulationbeschleunigen=1000;
		}
		if(input.isKeyJustPressed(Input.Keys.R)){
			simulationbeschleunigen=1;
		}
		handleInput();

		cam.update();
		infocam.update();
		InfoBatch.setProjectionMatrix(infocam.combined);
		InfoShapeRenderer.setProjectionMatrix(infocam.combined);
		batch.setProjectionMatrix(cam.combined);
		shapeRenderer.setProjectionMatrix(cam.combined);
		for(int i=0;i<simulationbeschleunigen;i++) {
			evolutionsSimulator.dostep();
		}
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			simulationsRenderer.draw();

	}

	private void handleInput() {
		float speed=10*cam.zoom;
		float zoomspeed=0.08f;
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			cam.zoom += zoomspeed;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			cam.zoom -= zoomspeed;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			cam.translate(-speed, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			cam.translate(speed, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			cam.translate(0, -speed, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			cam.translate(0, speed, 0);
		}
		int rotationSpeed = 1;
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			cam.rotate(-rotationSpeed, 0, 0, 1);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			cam.rotate(rotationSpeed, 0, 0, 1);
		}

		cam.zoom = MathUtils.clamp(cam.zoom, 0f, 5f);

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		infoViewport.update(width, height);
		infocam.position.set((float)(Gdx.graphics.getWidth() / 2), (float)(Gdx.graphics.getHeight() / 2), 0.0F);

		super.resize(width, height);
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}


	}

