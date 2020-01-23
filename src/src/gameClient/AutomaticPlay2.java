package gameClient;

import java.util.ArrayList;

import java.util.List;
import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.Edgedata;
import dataStructure.Nodedata;
import dataStructure.node_data;


public class AutomaticPlay2 {


	public  void test2(WindowMange window) {
		while(window.getId()==-1) {
			System.out.println();;
		}
		int scenario_num=window.getLevelPlay();
		Game_Server.login(window.getId());
		game_service game = Game_Server.getServer(scenario_num); // you have [0,23] games
		String g = game.getGraph();
		DGraph gg = new DGraph();
		gg.init(g);
		ArrayList<Nodedata> roobet = new ArrayList<Nodedata>();
		AlgoGameRooboteStart A=new AlgoGameRooboteStart(game,scenario_num);
		Edgedata []E=A.getEdgedataMaxVal();
		ArrayList<Integer> srcR = new ArrayList<Integer>();
		for (int j = 0; j < E.length; j++) {
			game.addRobot(E[j].getSrc());
			roobet.add(new Nodedata(gg.getNode(E[j].getSrc())));
			srcR.add(E[j].getSrc());
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
		}
		game.startGame();
		thread(game);
		while(game.isRunning()) {
			moveRobots(game, gg,roobet ,window,dests,srcR,scenario_num);
		}
		String results = game.toString();
		System.out.println(results);
	}

	private static void moveRobots(game_service game, DGraph gg, ArrayList<Nodedata> roobet, WindowMange window,int []dests, ArrayList<Integer> src, int scenario_num) {
		ArrayList<List<node_data>> dest1 = new ArrayList<List<node_data>>();		
		int dest;
		for (int j = 0; j < dests.length; j++) {
			while(game.isRunning() &&game.getRobots().get(j).toString().lastIndexOf("-")<0) {
			}

			if(dest1.size()==0 ) {
				AlgoGameWhereTheRoobotsGo R=new AlgoGameWhereTheRoobotsGo(game,src,scenario_num);
				dest1=R.getDest1();

			}
			dest=dest1.get(j).get(0).getKey();
			src.set(j, dest);
			dest1.get(j).remove(0);
			game.chooseNextEdge(j, dest);
			roobet.get(j).copy(new Nodedata(gg.getNode(dest)));
		}
		window.repaint();
		WindowMange.setList(game);
		window.repaint();
	}


	public  static void thread(game_service game) {
		Thread a=new  Thread(new Runnable() {
			@Override
			public void run() {
				String s=game.getRobots().get(0).toString();
				int src1=-1;
				int dest1=-1;
				int src2=-2;
				int dest2=-2;
				int src3=-3;
				int dest3=-3;
				int count=0;
				String change=s.substring(s.indexOf("src")+5,s.indexOf("dest")-2);
				while(game.isRunning()) {
					String t=game.getRobots().get(0).toString();
					String c=game.getRobots().get(1).toString();


					if((t.lastIndexOf("-")<0 ||c.lastIndexOf("-")<0 ) &&game.timeToEnd()>72) {
						if(!change.equals(t.substring(t.indexOf("src")+5,t.indexOf("dest")-2)) &&t.charAt(t.indexOf("dest")+6)!='-') {
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
					}
					try {
						if(noEat(src1,src2,src3,dest1,dest2,dest3))Thread.sleep(100);
						else	Thread.sleep(110);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			private boolean noEat(int src1, int src2, int src3, int dest1, int dest2, int dest3) {

				if(src1==src2 &&dest1==dest2 &&src1==dest3 &&dest1==src3)return true;
				if(src1==src3 &&dest1==dest3 &&src1==dest2 &&dest1==src2)return true;
				if(src2==src3 &&dest2==dest3 &&src1==dest2 &&dest1==src2)return true;
				return false;
			}

		});
		a.start();
	}
}
