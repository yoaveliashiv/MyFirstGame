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

public class AutomaticPlay2 {
	public static boolean start;
	int src1=-1;
	int dest1=-1;
	int src2=-1;
	int dest2=-1;
	public static void main(String[] a) {
		WindowMange window = new WindowMange();
		window.setVisible(true);
		AutomaticPlay2 s= new AutomaticPlay2();
		s.test2(window);}
	public  void test2(WindowMange window) {

		while(window.getId()==-1) {
			System.out.println();;
		}
	
		int scenario_num=window.getPlay();
		Game_Server.login(window.getId());
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
			//System.out.println("rstart"+E[j].getSrc());
		}



window.setG0Graph(gg);
window.setGame(game);
window.setRoobet(roobet);
window.setGrafh();
window.setList( game);
window.repaint();

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
			moveRobots(game, gg,roobet ,window,dests,srcR,scenario_num);
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
	 * @param scenario_num 
	 * @param log
	 */
	private static void moveRobots(game_service game, DGraph gg, ArrayList<Nodedata> roobet, WindowMange window,int []dests, ArrayList<Integer> src, int scenario_num) {

		ArrayList<List<node_data>> dest1 = new ArrayList<List<node_data>>();		
		int dest;


		for (int j = 0; j < dests.length; j++) {


			while(game.isRunning() &&game.getRobots().get(j).toString().lastIndexOf("-")<0) {

			}

			if(dest1.size()==0 ) {
				AlgoGameWhereTheRoobotsGo R=new AlgoGameWhereTheRoobotsGo(game,src,scenario_num);
				dest1=R.getDest1();
				//System.out.println(dest1.size()+"size");
			}
			//System.out.println(j+"j");
			//System.out.println(dest1.size()+"dest1");
			dest=dest1.get(j).get(0).getKey();
			src.set(j, dest);
			dest1.get(j).remove(0);
			//System.out.println(dest+"dest");
			game.chooseNextEdge(j, dest);

			roobet.get(j).copy(new Nodedata(gg.getNode(dest)));
		}


		window.repaint();
		WindowMange.setList(game);
		window.repaint();







	}
	/**
	 * a very simple random walk implementation!
	 * @param g
	 * @param src
	 * @return
	 */




	public  static void trehd(game_service game) {

		Thread a=new  Thread(new Runnable() {
			@Override
			public void run() {
				String s=game.getRobots().get(0).toString();
				//String s2=game.getRobots().get(1).toString();
			//	String s3=game.getRobots().get(2).toString();

				//System.out.println(s);
				int src1=-1;
			int dest1=-1;
				int src2=-2;
				int dest2=-2;
				int src3=-3;
				int dest3=-3;
				int count=0;
				String change=s.substring(s.indexOf("src")+5,s.indexOf("dest")-2);
				//String t=game.getRobots().get(0).toString();
				while(game.isRunning()) {
					String t=game.getRobots().get(0).toString();
					String c=game.getRobots().get(1).toString();

					
					if((t.lastIndexOf("-")<0 ||c.lastIndexOf("-")<0 ) &&game.timeToEnd()>72) {
						//System.out.println(game.getRobots());
						//System.out.println(change);
						//System.out.println(t.substring(t.indexOf("src")+5,t.indexOf("dest")-2));
				if(!change.equals(t.substring(t.indexOf("src")+5,t.indexOf("dest")-2)) &&t.charAt(t.indexOf("dest")+6)!='-') {
						//System.out.println("k");
					if(count%3==0) {
							src1=Integer.valueOf(""+t.charAt(t.indexOf("src")+5));
							if(t.lastIndexOf("-")<0)	dest1=Integer.valueOf(""+t.charAt(t.indexOf("dest")+6));
								}
								
							if(count%3==1) {
								src2=Integer.valueOf(""+t.charAt(t.indexOf("src")+5));
								if(t.lastIndexOf("-")<0)	dest2=Integer.valueOf(""+t.charAt(t.indexOf("dest")+6));
							}
							if(count%3==2) {
								src3=Integer.valueOf(""+t.charAt(t.indexOf("src")+5));
								if(t.lastIndexOf("-")<0)	dest3=Integer.valueOf(""+t.charAt(t.indexOf("dest")+6));
							}
							count++;
							change=t.substring(t.indexOf("src")+5,t.indexOf("dest")-2);
						}
						game.move();
						//System.out.println(game.getRobots());
						//if()
					}

					try {
						if(noEat(src1,src2,src3,dest1,dest2,dest3))Thread.sleep(100);

//if(src1)
						else
							Thread.sleep(110);


					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			private boolean noEat(int src1, int src2, int src3, int dest1, int dest2, int dest3) {
				//if(src1==src2 &&dest1==dest2 )return true;
				//if(src1==src3 &&dest1==dest3 )return true;
			//	if(src2==src3 &&dest2==dest3 )return true;
				if(src1==src2 &&dest1==dest2 &&src1==dest3 &&dest1==src3)return true;
				if(src1==src3 &&dest1==dest3 &&src1==dest2 &&dest1==src2)return true;
				if(src2==src3 &&dest2==dest3 &&src1==dest2 &&dest1==src2)return true;

				return false;
			}

		});

		a.start();
	}
}
