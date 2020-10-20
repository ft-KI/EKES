package com.evolution.simulator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.evolution.simulator.BackEnd.EvolutionsSimulator;
import com.evolution.simulator.BackEnd.virtualtileworld.LandType;
import com.evolution.simulator.FrontEnd.tilemaprenderer.SimulationsRenderer;

import static com.badlogic.gdx.Gdx.input;

public class Main extends ApplicationAdapter{
	EvolutionsSimulator evolutionsSimulator;
	SimulationsRenderer simulationsRenderer;
	public static SpriteBatch batch;
	public static Texture img;
	public static Texture watertile;
	public static Texture LandTile;
	public static OrthographicCamera cam;
	public static Viewport viewport;

	
	@Override
	public void create () {





		cam = new OrthographicCamera((float)Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight());
		viewport = new ScreenViewport(cam);
		evolutionsSimulator=new EvolutionsSimulator();
		simulationsRenderer=new SimulationsRenderer(evolutionsSimulator);
		batch = new SpriteBatch();
		img = new Texture("Land.jpg");
		watertile=new Texture("WaterTile.png");
		LandTile=new Texture("LandTile.jpg");
		cam.position.set((float)(Gdx.graphics.getWidth() / 2), (float)(Gdx.graphics.getHeight() / 2), 0.0F);

	}

	@Override
	public void render () {

		handleInput();
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		evolutionsSimulator.dostep();
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

		super.resize(width, height);
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}


	}

