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
		int scenario_num = 0;
		game_service game = Game_Server.getServer(scenario_num); // you have [0,23] games
		//System.out.println(game.getFruits()+"fruit");
		String g = game.getGraph();
		DGraph gg = new DGraph();
		gg.init(g);
		 ArrayList<Nodedata> roobet = new ArrayList<Nodedata>();
		 ArrayList<Fruit> fruit = new ArrayList<Fruit>();
		 AlgoGameRooboteStart A=new AlgoGameRooboteStart(game,scenario_num);
			Edgedata []E=A.getEdgedataMaxVal();
		
		String info = game.toString();
		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("robots");
			
			// the list of fruits should be considered in your solution
			Iterator<String> f_iter = game.getFruits().iterator();
			//while(f_iter.hasNext()) {System.out.println(f_iter.next());}	
			
			int src_node = 0;  // arbitrary node, you should start at one of the fruits
			for (int j = 0; j < E.length; j++) {
				game.addRobot(E[j].getSrc());
				roobet.add(new Nodedata(gg.getNode(E[j].getSrc())));
				System.out.println(game.getRobots());
			}
			
			
		}
		catch (JSONException e) {e.printStackTrace();}
		Window window = new Window(gg,game,roobet,fruit,scenario_num );
		window.setVisible(true);
	int dests[]=new int[E.length];
	for (int i = 0; i < dests.length; i++) {
		dests[i]=E[i].getDest();
	}
	start=true;
		// should be a Thread!!!
	game.startGame();
	trehd(game);
		while(game.isRunning()) {
			moveRobots(game, gg,roobet ,window,dests,start);
		}
		String results = game.toString();
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
	 * @param start 
	 * @param log
	 */
	private static void moveRobots(game_service game, DGraph gg, ArrayList<Nodedata> roobet, Window window,int []dests, boolean start) {
		
		List<node_data> dest1=new ArrayList<node_data>();
		List<String> log = game.move();
		if(log!=null) {
			long t = game.timeToEnd();
			for(int i=0;i<log.size();i++) {
				String robot_json = log.get(i);
				try {
					JSONObject line = new JSONObject(robot_json);
					JSONObject ttt = line.getJSONObject("Robot");
					int rid = ttt.getInt("id");
					int src = ttt.getInt("src");
					int dest = ttt.getInt("dest");
					
					if(dest==-1) {	
						for (int j = 0; j < dests.length; j++) {
							
							
							if(dest1.size()==0) {
							AlgoGameWhereTheRoobotsGo R=new AlgoGameWhereTheRoobotsGo(game,dests);
							dest1=R.getDest1();
							System.out.println(dest1.size()+"size");
							}
							dest=dest1.get(0).getKey();
							dests[j]=dest;
							dest1.remove(0);
						System.out.println(dest+"dest");
							
							
						System.out.println(dest+"dest");
						game.chooseNextEdge(j, dest);
						System.out.println("????????");
						roobet.get(j).copy(new Nodedata(gg.getNode(dest)));
						}
						
					System.out.println(game.getRobots());
						Iterator<String> f_iter = game.getFruits().iterator();
						while(f_iter.hasNext()) {System.out.println(f_iter.next());}	
						Window.setList(game);
						window.repaint();
						//window.paint2();
						
					}
				} 
				catch (JSONException e) {e.printStackTrace();}
			}
		}
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