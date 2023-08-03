package game;

import edu.monash.fit2099.engine.positions.Location;
import game.actors.*;
import game.grounds.*;
import game.items.PowerStar;
import game.items.SuperMushroom;
import game.items.YoshiEgg;
import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.FancyGroundFactory;
import edu.monash.fit2099.engine.positions.GameMap;
import edu.monash.fit2099.engine.positions.World;

/**
 * The main class for the Mario World game.
 */
public class Application {

	public static void main(String[] args) {

		World world = new World(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Sprout(),
				new HealthFountain(), new PowerFountain());

		FancyGroundFactory groundFactory1 = new FancyGroundFactory(new Lava(), new Dirt(), new Wall(), new Floor(),
				new Sprout(), new HealthFountain());

		List<String> map = Arrays.asList(
			".....+..................+.................##..........+..........+........A.....",
			"............+.....................+.........#......................+.....+......",
			"............................................#...................................",
			"...........H..........+......................##......................+..........",
			"......+........................................#..........+.....................",
			"................................................#...............................",
			".................+....................+...........#.............................",
			".................................................##............+............+...",
			"................................................##..............................",
			".........+.....................+........+#____####.................+............",
			"...................+...................+#_____###++.............................",
			".......................................+#______###..............................",
			"........................................+#_____###..............................",
			".....+..................+........................##.............+.......+.......",
			"...................................................#............................",
			"....####............................................#...........................",
			"....#__#...........+......................+..........#..............+...........",
			"....####..............................+...............#.........................",
			"........+.....H........................................##.................+.....");

		List<String> map1 = Arrays.asList(
				"............H........#___#...+..................+..........",
				"......+..........L...#___#................L................",
				".........L...........####L..................+.....L........",
				"...................................L.......................",
				".......+......L...........+.........................L......",
				"...........................................................",
				"....L.................L..........L............L............",
				"................................L.........+................",
				"..........L.....................L..........................",
				"..................L................................L.......",
				"...........+...............................................",
				"........L..................................................",
				"L..........................................L.......L.......",
				"....................+........L.............................",
				"..............L..................................+.........");

		GameMap forestZone = new GameMap(groundFactory, map);
		GameMap lavaZone = new GameMap(groundFactory1, map1);

		world.addGameMap(forestZone);
		world.addGameMap(lavaZone);

		Location playerStart = forestZone.at(42,10);
		Actor mario = new Player("Player", 'm', 100, playerStart);

		//Forest Zone
		world.addPlayer(mario, playerStart);
		forestZone.at(42, 10).addItem(new PowerStar());
		forestZone.at(42, 10).addItem(new SuperMushroom());
		forestZone.at(43,11).addActor(new Toad());

		//WarpPipe in Forest Zone
		Location destinationPipe = lavaZone.at(0,0);	//destination to teleport to
		forestZone.at(1,1).setGround(new WarpPipe(destinationPipe));
		forestZone.at(28,11).setGround(new WarpPipe(destinationPipe));
		forestZone.at(58, 2).setGround(new WarpPipe(destinationPipe));
		//spawn a new Yoshi Egg randomly in Forest Zone
		YoshiEgg egg = new YoshiEgg();
		egg.spawnEgg(forestZone);

		//Lava Zone
		lavaZone.at(20,10).addActor(new PrincessPeach());
		lavaZone.at(19,10).addActor(new Bowser(lavaZone.at(19, 10)));
		//WarpPipe in Lava Zone
		lavaZone.at(0,0).setGround(new WarpPipe());

		world.run();
	}
}
