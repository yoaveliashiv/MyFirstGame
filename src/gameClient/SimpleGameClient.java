package gameClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import MyGameGUI.Window;
import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.Edgedata;
import dataStructure.Nodedata;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import oop_dataStructure.OOP_DGraph;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;
/**
 * This class represents a simple example for using the GameServer API:
 * the main file performs the following tasks:
 * 1. Creates a game_service [0,23] (line 36)
 * 2. Constructs the graph from JSON String (lines 37-39)
 * 3. Gets the scenario JSON String (lines 40-41)
 * 4. Prints the fruits data (lines 49-50)
 * 5. Add a set of robots (line 52-53) // note: in general a list of robots should be added
 * 6. Starts game (line 57)
 * 7. Main loop (should be a thread) (lines 59-60)
 * 8. move the robot along the current edge (line 74)
 * 9. direct to the next edge (if on a node) (line 87-88)
 * 10. prints the game results (after "game over"): (line 63)
 *  
 * @author boaz.benmoshe
 *
 */
public class SimpleGameClient {
	public static boolean start;
	public static void main(String[] a) {

		test1();}
	public static void test1() {
		int scenario_num = 11;
		game_service game = Game_Server.getServer(scenario_num); // you have [0,23] games

		String g = game.getGraph();
		DGraph gg = new DGraph();
		gg.init(g);
		ArrayList<Nodedata> roobet = new ArrayList<Nodedata>();
		ArrayList<Fruit> fruit = new ArrayList<Fruit>();
		AlgoGameRooboteStart A=new AlgoGameRooboteStart(game,scenario_num);
		Edgedata []E=A.getEdgedataMaxVal();

		String info = game.toString();

		ArrayList<Integer> srcR = new ArrayList<Integer>();
		for (int j = 0; j < E.length; j++) {
			game.addRobot(E[j].getSrc());
			roobet.add(new Nodedata(gg.getNode(E[j].getSrc())));
			 srcR.add(E[j].getSrc());
			System.out.println("rstart"+E[j].getSrc());
		}




		Window window = new Window(gg,game,roobet,fruit,scenario_num );
		window.setVisible(true);
		int dests[]=new int[E.length];
		for (int i = 0; i < dests.length; i++) {
			dests[i]=E[i].getDest();
		//	System.out.println("dest"+dests[i]);
		}
		start=true;
		// should be a Thread!!!
		game.startGame();
		trehd(game);
		while(game.isRunning()) {
			moveRobots(game, gg,roobet ,window,dests,srcR);
		}
		String results ="lll"+ game.toString();
		System.out.println(results);
	}
	/** 
	 * Moves each of the robots along the edge, 
	 * in case the robot is on a node the next destination (next edge) is chosen (randomly).
	 * @param game
	 * @param gg
	 * @param roobet 
	 * @param window 
	 * @param window 
	 * @param src 
	 * @param log
	 */
	private static void moveRobots(game_service game, DGraph gg, ArrayList<Nodedata> roobet, Window window,int []dests, ArrayList<Integer> src) {

		  ArrayList<List<node_data>> dest1 = new ArrayList<List<node_data>>();		
		int dest;
		

				for (int j = 0; j < dests.length; j++) {
					while(game.isRunning() &&game.getRobots().get(j).toString().lastIndexOf("-")<0) {
						game.move();
					}

					if(dest1.size()==0 ) {
						AlgoGameWhereTheRoobotsGo R=new AlgoGameWhereTheRoobotsGo(game,src);
						dest1=R.getDest1();
						System.out.println(dest1.size()+"size");
					}
					System.out.println(j+"j");
					System.out.println(dest1.size()+"dest1");
					dest=dest1.get(j).get(0).getKey();
					src.set(j, dest);
					dest1.get(j).remove(0);
					System.out.println(dest+"dest");
					game.chooseNextEdge(j, dest);
				
					roobet.get(j).copy(new Nodedata(gg.getNode(dest)));
				}
			
				
				window.repaint();
				Window.setList(game);
				window.repaint();
				//window.paint2();

			
		
	
	

}
/**
 * a very simple random walk implementation!
 * @param g
 * @param src
 * @return
 */
private static int nextNode(DGraph g, int src) {
	int ans = -1;
	Collection<edge_data> ee = g.getE(src);
	Iterator<edge_data> itr = ee.iterator();
	int s = ee.size();
	int r = (int)(Math.random()*s);
	int i=0;
	while(i<r) {itr.next();i++;}
	ans = itr.next().getDest();
	return ans;
}



public static  void trehd(game_service game) {
	Thread a=new  Thread(new Runnable() {
		@Override
		public void run() {
			while(game.isRunning()) {

				game.move();

				try {
					Thread.sleep(100);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}	
			}
		}
	});
	a.start();
}
}