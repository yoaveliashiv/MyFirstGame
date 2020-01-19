package gameClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.runner.manipulation.Sortable;

import Server.fruits;
import Server.game_service;
import algorithms.Graph_Algo;
import algorithms.node_data_Comperator;
import dataStructure.DGraph;
import dataStructure.Edgedata;
import dataStructure.edge_data;
import dataStructure.node_data;
import utils.Point3D;

public class AlgoGameWhereTheRoobotsGo {
	ArrayList<ArrayList<Edgedata>> roobotsSrcAndDest=new ArrayList<ArrayList<Edgedata>>() ;
	private  ArrayList<Fruit> fruit = new ArrayList<Fruit>();
	private DGraph g0 = new DGraph();

	private  ArrayList<List<node_data>> dest1 = new ArrayList<List<node_data>>();
	private  ArrayList<Integer> dest2 = new ArrayList<Integer>();
	//private  Edgedata EdgedataMaxVal[];
	public ArrayList<List<node_data>> getDest1(){
		return dest1;
	}
	AlgoGameWhereTheRoobotsGo (game_service game,ArrayList<Integer>src){
		String info=game.toString();
		int numR=Integer.valueOf(""+info.charAt(info.indexOf("graph")-3));

		String g = game.getGraph();
		g0=new DGraph();
		g0.init(g);	
		setList(game);
		Edgedata a[]=new Edgedata[src.size()];
		for (int i = 0; i < numR; i++) {
			roobotsSrcAndDest.add(new ArrayList<Edgedata>());
			for(Fruit f:fruit) {
				//System.out.println("src:"+f.getSrc()+"dest:"+f.getDest());
				roobotsSrcAndDest.get(i).add(g0.getEdgeE(f.getSrc(), f.getDest()));
			}
			//	Sortable(roobotsSrcAndDest.get(i))
			Collections.sort( roobotsSrcAndDest.get(i), new  distance_Comperator(src.get(i),g0));
			a[i]=roobotsSrcAndDest.get(i).get(0);
		//	System.out.println("dest"+a[i].getSrc());
			System.out.println("id:"+i+"dest:"+a[i].getSrc()+"בדיקה");

		}
		if(src.size()>1)sameftuit(src,a);
		
		for (int i = 0; i < a.length; i++) {
			System.out.println("id:"+i+"dest:"+roobotsSrcAndDest.get(i).get(0).getSrc()+"בדיקה");
		}
		findNewDest(src);
		System.out.println();

	}

	private void sameftuit(ArrayList<Integer> src, Edgedata[] a2) {
		//Edgedata a[]=new Edgedata[src.length];
		if(src.size()==2&& a2[0].getSrc()==a2[1].getSrc()) {
			System.out.println("llll");
			double dis0=g0.getNode(src.get(0)).getLocation().distance2D(g0.getNode(a2[0].getSrc()).getLocation());
			double dis1=g0.getNode(src.get(1)).getLocation().distance2D(g0.getNode(a2[1].getSrc()).getLocation());
			//System.out.println(dis0);
			//System.out.println(dis1);
			if(dis1<dis0)roobotsSrcAndDest.get(0).remove(0);
			else if(dis1>dis0)roobotsSrcAndDest.get(1).remove(0);
			else roobotsSrcAndDest.get(0).remove(0);
		}
		
		if(src.size()==3&& (a2[0].getSrc()==a2[1].getSrc()|| a2[0].getSrc()==a2[2].getSrc()|| a2[1].getSrc()==a2[2].getSrc()))  {
			System.out.println("?????????");
		ArrayList<Integer> arrSame=new ArrayList<Integer>();
		for (int i = 0; i < a2.length; i++) {
			for (int j = i; j < a2.length; j++) {
				if(i!=j&&a2[i]==a2[j]) {
					arrSame.add(i);
				}
			}
		}
		if(arrSame.size()==2) {
			
			double dis0=g0.getNode(src.get(arrSame.get(0))).getLocation().distance2D(g0.getNode(a2[arrSame.get(0)].getSrc()).getLocation());
			double dis1=g0.getNode(src.get(arrSame.get(1))).getLocation().distance2D(g0.getNode(a2[arrSame.get(1)].getSrc()).getLocation());
			if(dis1<dis0)roobotsSrcAndDest.get(arrSame.get(0)).remove(0);
			else if(dis1>dis0)roobotsSrcAndDest.get(arrSame.get(1)).remove(0);
			else roobotsSrcAndDest.get(arrSame.get(0)).remove(0);
		}
			if(arrSame.size()==3) {
			double dis0=g0.getNode(src.get(0)).getLocation().distance2D(g0.getNode(a2[0].getSrc()).getLocation());
			double dis1=g0.getNode(src.get(1)).getLocation().distance2D(g0.getNode(a2[1].getSrc()).getLocation());
			double dis2=g0.getNode(src.get(2)).getLocation().distance2D(g0.getNode(a2[2].getSrc()).getLocation());
			if(dis1<dis0 &&dis2<dis0) {
				roobotsSrcAndDest.get(1).remove(0);
				roobotsSrcAndDest.get(2).remove(0);
			}
			else if(dis2<dis1 &&dis0<dis1) {
				roobotsSrcAndDest.get(0).remove(0);
				roobotsSrcAndDest.get(2).remove(0);
			}
			else if(dis1<dis2 &&dis0<dis2) {
					roobotsSrcAndDest.get(0).remove(0);
					roobotsSrcAndDest.get(1).remove(0);
				}
			else {
				roobotsSrcAndDest.get(1).remove(0);
				roobotsSrcAndDest.get(2).remove(0);
			}
			}
		}
			}
	
	private void findNewDest(ArrayList<Integer> src) {
		for (int i = 0; i <  src.size(); i++) {
			List<node_data> n=new ArrayList<node_data>();
			//System.out.println("src"+src.get(i));
			Graph_Algo a=new Graph_Algo (g0);
			Edgedata c=roobotsSrcAndDest.get(i).get(0);
			n=	a.shortestPath(src.get(i),  c.getSrc());
			dest1.add(n);
			dest1.get(i).remove(0);
			dest1.get(i).add(g0.getNode(c.getDest()));
		//	System.out.println(roobotsSrcAndDest.get(i).get(0).getSrc()+"בדיקה");
			//System.out.println("id:"+i+"dest:"+dest1.get(0).getKey()+"בדיקה");

			//	System.out.println(roobotsSrcAndDest.get(0).get(0).getSrc()+"בדיקה");

			//for(node_data n:dest1) {
			//	System.out.println(n.getKey()+"list");
			//}
			//System.out.println("בין לבין");
			//dest1.add( q.get(1).getKey());
			//dest2.add( roobotsSrcAndDest.get(i).get(0).getDest());
		}
System.out.println(dest1.size());
	}

	public void setFruit() {
		for (Fruit f:fruit) {
			ArrayList<node_data> a=new ArrayList<node_data>( g0.getV());
			boolean noFound=true;
			for (int i = 0; i < a.size()&&noFound; i++) {
				int node_id=a.get(i).getKey();
				ArrayList<edge_data> b=new ArrayList<edge_data>(g0.getE(node_id));
				for(edge_data e:b) {
					if(findOn(e,f)) {
						e.setFruit(f);
						noFound=false;
						break;
					}
				}
			}
		}
	}
	public boolean findOn(edge_data e, Fruit f) {
		if(Math.abs(g0.getNode(e.getSrc()).getLocation().y())-Math.abs(g0.getNode(e.getDest()).getLocation().y())>0) {
			if(f.getType()==1) return false;
		}
		else {
			if(f.getType()==-1) return false;
		}

		double distSrc=f.getPos().distance2D(g0.getNode(e.getSrc()).getLocation());
		double distDest=f.getPos().distance2D(g0.getNode(e.getDest()).getLocation());
		double distEdge=g0.getNode(e.getDest()).getLocation().distance2D(g0.getNode(e.getSrc()).getLocation());
		if(distDest+distSrc-distEdge >=-0.01&&distDest+distSrc-distEdge <=0.00001)return true;

		return false;
	}

	public   void setList( game_service game ) {
		Iterator<String> f_iter = game.getFruits().iterator();
		String sF="";
		while(f_iter.hasNext()) {
			sF=""+f_iter.next();	
			JSONObject line;
			try {
				line = new JSONObject(sF);
				JSONObject ttt = line.getJSONObject("Fruit");
				double value = ttt.getDouble("value");
				int type = ttt.getInt("type");
				String p = ttt.getString("pos");
				Point3D pos = new Point3D(p);
				fruit.add(new Fruit(value, type, pos));
			}
			catch (JSONException r) {r.printStackTrace();}

		}
		setFruit();

	}

}
