package MyGameGUI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.fruits;
import Server.game_service;
import Server.robot;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.Edgedata;
import dataStructure.Nodedata;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import gameClient.Fruit;

import oop_dataStructure.OOP_DGraph;
import oop_dataStructure.oop_graph;
import utils.Point3D;


public class Window extends JFrame implements ActionListener, MouseListener {
	int grafic=1;
	private String results ="";
	private static final int Fruit = 0;
	private static double r_minx=999999998;
	private static double r_maxx=-999999998;
	boolean r;
	boolean t;
	//boolean endGame=false;
	private static double r_miny=999999998;
	private static double r_maxy=-999999998;
	private game_service game=null;
	private static DGraph g0 = new DGraph();
	private ArrayList<node_data> g3 = new ArrayList<node_data>();
	private ArrayList<Integer> g4 = new ArrayList<Integer>();
	private ArrayList<Nodedata> roobet = new ArrayList<Nodedata>();
	private static ArrayList<Fruit> fruit = new ArrayList<Fruit>();
	private String syso = "ches graph";
	private String syso1 = "";
	private String syso2 = "";
	private String syso3 = "";
	private String syso4 = "";
	private String w = "";
	private int src = 0;
	private int dest = 0;
	private int count = 0;
	private int countSave = 0;
	private int scaleN = 0;
	private int scaleF = 0;
	private int scaleR = 0;
	private int numGraph = -1;
	private int countRoboot = 0;
	private int robotTemp =-1;
	private int numRoboot =0;
	private double valWin =0;
	private long timeToEnd =0;

	public  Window(graph p) {
		g0=(DGraph) p;
		initGUI();
		repaint();

	}
	public Window(DGraph p, game_service game, ArrayList<Nodedata> roobet2, ArrayList<gameClient.Fruit> fruit2,int numGame) {
		initGUI();
		numGraph=numGame;
		this.fruit=fruit2;
		this.roobet=roobet2;
		this.game=game;
		game.startGame();
		g0=(DGraph) p;
		setGrafh();
		setList( game);
		//	game.grt
		//setRoboot(x, y);
	

		repaint();


		//paint2();
	}
	private void setGrafh() {
		ArrayList<node_data> a=new ArrayList<node_data>(g0.getV());
		for (int i = 0; i < a.size(); i++) {
			if(r_maxx<a.get(i).getLocation().x())r_maxx=a.get(i).getLocation().x();
			if(r_minx>a.get(i).getLocation().x())r_minx=a.get(i).getLocation().x();

		}
		for (int i = 0; i < a.size(); i++) {
			if(r_maxy<a.get(i).getLocation().y())r_maxy=a.get(i).getLocation().y();
			if(r_miny>a.get(i).getLocation().y())r_miny=a.get(i).getLocation().y();

		}
		ArrayList<node_data> dataNode=new ArrayList<node_data>(g0.getV());
		for (int i = 0; i <dataNode.size(); i++) {
			dataNode.get(i).setLocation(new Point3D(scaleX(r_maxx,r_minx,dataNode.get(i).getLocation().x()),scaleY(r_maxy,r_miny,dataNode.get(i).getLocation().y())));
		}


	}
	public Window() {
		initGUI();
	}


	private void initGUI() {
		this.setSize(1300, 1200);
		//this.getMaximumSize();
		//	this.getMinimumSize();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Menu");
		menuBar.add(menu);
		this.setMenuBar(menuBar);

		Menu syso = new Menu();
		menuBar.add(syso);
		this.setMenuBar(menuBar);


		Menu algo = new Menu("algo");
		menuBar.add(algo);
		this.setMenuBar(menuBar);

		Menu game = new Menu("game");
		menuBar.add(game);
		this.setMenuBar(menuBar);

		Menu Choosegraph = new Menu("Choose a graph");
		menuBar.add(Choosegraph);
		this.setMenuBar(menuBar);

		MenuItem item1 = new MenuItem("savePic");
		item1.addActionListener(this);

		MenuItem item2 = new MenuItem("clearAll");
		item2.addActionListener(this);

		MenuItem item3 = new MenuItem("isconnected");
		item3.addActionListener(this);

		MenuItem item4 = new MenuItem("shortestPathDist");
		item4.addActionListener(this);

		MenuItem item5 = new MenuItem("path");
		item5.addActionListener(this);

		MenuItem item6 = new MenuItem("TSP");
		item6.addActionListener(this);

		MenuItem item8 = new MenuItem("endTSP");
		item8.addActionListener(this);

		MenuItem item7 = new MenuItem("clearWayAns");
		item7.addActionListener(this);

		MenuItem item10 = new MenuItem("saveGraph");
		item10.addActionListener(this);
		MenuItem item11 = new MenuItem("removeEdgeFromGraph");
		item11.addActionListener(this);

		MenuItem item12 = new MenuItem("removeNodeFromGraph");
		item12.addActionListener(this);

		MenuItem item13 = new MenuItem("dataGraphToString");
		item13.addActionListener(this);

		MenuItem item14 = new MenuItem("toGoBackOneClick");
		item14.addActionListener(this);

		MenuItem item15 = new MenuItem("saveGraph");
		item15.addActionListener(this);

		MenuItem item16 = new MenuItem("putRobet");
		item16.addActionListener(this);

		MenuItem item18 = new MenuItem("TimeToEnd");
		item18.addActionListener(this);

		MenuItem item19 = new MenuItem("moveRobetToEdge");
		item19.addActionListener(this);

		MenuItem item20 = new MenuItem("new game");
		item20.addActionListener(this);


		MenuItem item17;
		for (int i = 0; i < 24; i++) {
			item17 = new MenuItem(""+i);
			item17.addActionListener(this);
			Choosegraph.add(item17);
		}
		menu.add(item14);
		menu.add(item10);
		menu.add(item1);
		menu.add(item2);
		menu.add(item7);
		algo.add(item3);
		algo.add(item4);
		algo.add(item5);
		algo.add(item6);
		algo.add(item8);
		menu.add(item11);
		menu.add(item12);
		menu.add(item13);
		algo.add(item15);
		game.add(item16);
		game.add(item18);
		game.add(item19);
		game.add(item20);
		this.addMouseListener(this);

	}
	private static double scaleX(double r_max, double r_min, double data){

		double res = ((data - r_min) / (r_max-r_min)) * (1250- 	30) + 30;
		return res;
	}
	private static double scaleY(double r_max, double r_min, double data){

		double res = ((data - r_min) / (r_max-r_min)) *(650 - 	150) + 150;

		return 815-res;
	}






	public void paint(Graphics g) {

		super.paint(g);

		if(numGraph!=-1) {
		//	if(numGraph>=0&& numGraph<=3)numGraph=0;
//			if(numGraph>=4&& numGraph<=7)numGraph=1;
//			if(numGraph>=8&& numGraph<=11)numGraph=2;
//			if(numGraph>=12&& numGraph<=15)numGraph=3;
//			if(numGraph>=16&& numGraph<=19)numGraph=4;
//			if(numGraph>=20&& numGraph<=23)numGraph=5;
			BufferedImage imgGraph  = null;
			try {
				imgGraph = ImageIO.read(new File( "data\\imageG"+numGraph+".jpg"));
			} catch (IOException e) {
				syso="no seccs fruit";
			}

			g.drawImage(imgGraph , 0, 0, 1297 , 737, this);
		}
		BufferedImage imgFruit = null;
		try {
			imgFruit = ImageIO.read(new File("fruit.jpg"));
		} catch (IOException e) {
			syso="no seccs fruit";
		}

		BufferedImage imgFruit2 = null;
		try {
			imgFruit2 = ImageIO.read(new File("appel.jpg"));
		} catch (IOException e) {
			syso="no seccs fruit";
		}
		for (int i = 0; i < fruit.size(); i++) {
			if(fruit.get(i).getType()==-1) {
				g.drawImage(imgFruit, fruit.get(i).getPos().ix(),fruit.get(i).getPos().iy(), 15, 15, this);
				g.drawString(""+fruit.get(i).getValue(),fruit.get(i).getPos().ix()+18,fruit.get(i).getPos().iy());

			}
			else {
				g.drawImage(imgFruit2, fruit.get(i).getPos().ix(),fruit.get(i).getPos().iy(), 15, 15, this);
				g.drawString(""+fruit.get(i).getValue(),fruit.get(i).getPos().ix()+18,fruit.get(i).getPos().iy());
			}

		}
		g.setColor(Color.BLACK);
	//	g.drawLine(0, 144, 2000, 144);
		g.drawString(syso, 99, 73);
		if(!syso2.equals(""))g.drawString(syso2, 99, 85);
		if(!syso1.equals(""))g.drawString(syso1, 99, 97);
		if(!syso3.equals(""))g.drawString(syso3, 99, 109);
		if(!syso4.equals(""))g.drawString(syso4, 99, 121);
		if(!results.equals(""))g.drawString(results, 99, 133);

		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("roboot.jpg"));
		} catch (IOException e) {
			syso="no seccs robot";
		}
		for (int i = 0; i < roobet.size(); i++) {
			g.drawImage(img, roobet.get(i).getLocation().ix(), roobet.get(i).getLocation().iy(), 18, 18, this);
			//g.fillOval(roobet.get(i).ix(),roobet.get(i).iy(), 12, 12);
		}


	}


	private Point3D Towards(Point3D px, Point3D py, Point3D dest) {
		if (px.distance2D(dest) < py.distance2D(dest))
			return px;
		return py;
	}

	private Point3D TowardsX(Point3D p1, Point3D p2) {
		double chnge;
		boolean x = Math.abs(p1.x() - p2.x()) > 12;
		if (x)
			chnge = 7;
		else
			chnge = 0.5;
		double m = (p1.y() - p2.y()) / (p1.x() - p2.x());
		double x1 = p1.x() + chnge;
		double x2 = p1.x() - chnge;// (y-y1=m(x-x1.//y-mx+mx1=y1
		double y1 = p1.y() - (m * p1.x()) + (m * x1);
		double y2 = p1.y() - (m * p1.x()) + (m * x2);
		Point3D one = new Point3D(x1, y1);
		Point3D tow = new Point3D(x2, y2);
		if (one.distance2D(p2) < tow.distance2D(p2))
			return one;
		else
			return tow;
	}

	private Point3D TowardsY(Point3D p1, Point3D p2) {
		double chnge;
		boolean y = Math.abs(p1.y() - p2.y()) > 22;
		if (Math.abs(p1.y() - p2.y()) < 2)
			chnge = 0.25;
		if (y)
			chnge = 6;
		else
			chnge = 0.5;

		double m = (p1.y() - p2.y()) / (p1.x() - p2.x());
		double y1 = p1.y() + chnge;
		double y2 = p1.y() - chnge;// (y-y1=m(x-x1.//y-mx+mx1=y1//x1=(-y+y1-xm)/m
		double x1 = (-p1.y() + y1 - (p1.x() * m)) / m;
		double x2 = (-p1.y() + y2 - (p1.x() * m)) / m;
		Point3D one = new Point3D(x1, y1);
		Point3D tow = new Point3D(x2, y2);
		if (one.distance2D(p2) < tow.distance2D(p2))
			return one;
		else
			return tow;

	}

	@Override

	public void actionPerformed(ActionEvent e) {
		//if(game!=null &&game.isRunning())results="TimeToEnd:"+game.timeToEnd();
//		if(endGame) {
//			setVisible(false); //you can't see me!
//			dispose();
//		}
		String str = e.getActionCommand();
		if(str.equals("toGoBackOneClick")) {
			Graph_Algo c = new Graph_Algo();
			c.init("return.txt");
			g0 =new DGraph(c.copy());
		}

		saveGraph("return.txt");
		if(str.equals("TimeToEnd")) {
			System.out.println(game.timeToEnd());
			if(game.isRunning())results="TimeToEnd:"+game.timeToEnd();
			else results="The game dont start";
		}
		if(str.equals("moveRobetToEdge")) {
			syso="ches roobet to move"; 
			w="moveRobetToEdge";
		}
		if(str.equals("new game")) {
			setVisible(false); //you can't see me!
			dispose();
			Window window = new Window();
			window.setVisible(true);
		}
		if(str.equals("putRobet")) {
			if(roobet.size()<numRoboot)w="putRobet";
			else {
				syso="only"+ numRoboot+" robets in this graph";
				w="";

			}
		}

		if(str.charAt(0)>='0' &&str.charAt(0)<='9') {
			clean();
			results="";
			numGraph=Integer.valueOf(str);
			game = Game_Server.getServer(numGraph); // you have [0,23] games
			//results=""+	game.getGraph();
			String g = game.getGraph();
			g0=new DGraph();
			g0.init(g);	
			setGrafh();

			setList(game);

			setNumRoobot();

			syso="ches were put roobots";
			repaint();
			grafic=0;

		}

		if(!str.equals("savePic")) {
			syso1="";
			syso2="";
			syso3="";
			syso4="";
		}
		Graph_Algo a = new Graph_Algo();
		if (str.equals("savePic")) {
			a.init(g0);
			//save_paint();
		}
		if (str.equals("clearAll")) {
			valWin=0;
			fruit = new ArrayList<Fruit>();
			w = "";
			grafic=1;
			count = 0;
			src = 0;
			dest = 0;
			g3 = new ArrayList<node_data>();
			g0 = new DGraph();
			g4 = new ArrayList<Integer>();
			syso = "All clear";
			syso1="";
			syso2="";
			syso3="";
			syso4="";
			roobet =new ArrayList<Nodedata>();
		}
		if (str.equals("dataGraphToString")) {
			syso = g0.toStringDataNode();
			syso1=g0.toStringedgedataNode();
			syso2="size DataNode:"+g0.nodeSize();
			syso3="size edgedataNode:"+g0.edgeSize();
			syso4="MC:"+g0.getMC();
			System.out.println(syso);
			System.out.println(syso2);
			System.out.println(syso1);
			System.out.println(syso3);
			System.out.println(syso4);	
		}
		if (str.equals("clearWayAns")) {
			w = "";
			count = 0;
			src = 0;
			dest = 0;
			g3 = new ArrayList<node_data>();
			g4 = new ArrayList<Integer>();
			syso1="";
			syso2="";
			syso3="";
			syso4="";
			syso="the wayAns clear";


		}

		if (str.equals("isconnected")) {
			a.init(g0);
			boolean cv = a.isConnected();
			syso = "" + cv;
			System.out.println(cv);
		}
		if (str.equals("shortestPathDist")) {
			w = str;
			syso = "Choose a src and dest";
		}

		if (str.equals("path")) {
			syso = "Choose a src and dest";
			w = str;
		}
		if (str.equals("endTSP")) {
			a.init(g0);
			g3 = new ArrayList<node_data>(a.TSP(g4));
			ArrayList<Integer> g2 = new ArrayList<Integer>();
			for (int i = 0; i < g3.size(); i++)
				g2.add(g3.get(i).getKey());
			syso = "TSP targets:"+g4.toString();
			syso1=".TSP ans:" + g2.toString();
			System.out.println("TSP ans:" + g2.toString());
			g4 = new ArrayList<Integer>();
			w = "";
		}
		if (str.equals("TSP")) {
			w = str;
			syso = "Choose a point or end";
		}
		if (str.equals("saveGraph")) {
			a.init(g0);
			a.save("graph1" + countSave + ".txt");
			countSave++;
			syso = "Graph save";
		}
		if (str.equals("removeEdgeFromGraph")) {
			syso = "Choose a src and dest";
			w = str;
		}
		if (str.equals("removeNodeFromGraph")) {
			syso = "Choose a node key";
			w = str;
		}
repaint();
	}


	private void clean() {
if(game!=null)game.stopGame();
		grafic=1;
		countRoboot=0;
		valWin=0;
		fruit = new ArrayList<Fruit>();
		w = "";
		count = 0;
		src = 0;
		dest = 0;
		g3 = new ArrayList<node_data>();
		g0 = new DGraph();
		g4 = new ArrayList<Integer>();
		syso = "ches graph";
		syso1="";
		syso2="";
		syso3="";
		syso4="";
		roobet =new ArrayList<Nodedata>();

	}
	private void setNumRoobot() {
		String info=""+game.toString();
		numRoboot=Integer.valueOf(""+info.charAt(info.indexOf("robots")+8));
		syso=""+numRoboot;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// System.out.println("mouseClicked");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		saveGraph("return.txt");
		syso = "";
		syso1="";
		syso2="";
		syso3="";
		syso4="";
		if (w.equals("putRobet")) {
			if(roobet.size()<numRoboot) {
				double x = e.getX();
				double y = e.getY();

				Nodedata n=new Nodedata( setnode(x, y));
				game.addRobot(n.getKey());

				roobet.add(n);
				game.startGame();
if(game.isRunning()) {
				
				syso2="game start";
				trehd1(game);
}else {
	syso2="put mor roobot";
}
			}
			else {
				w="";
				syso="only"+ numRoboot+" robets in this graph";
			}
		}

		else if (w.equals("path") || w.equals("shortestPathDist")||w.equals("removeEdgeFromGraph")||w.equals("removeNodeFromGraph")) {
			double x = e.getX();
			double y = e.getY();
			ArrayList<node_data> g1 = new ArrayList<node_data>(g0.getV());
			for (node_data a : g1) {
				Point3D p1 = new Point3D((int) a.getLocation().x(), (int) a.getLocation().y());
				if (x < p1.x() + 15 && x > p1.x() - 15 && y < p1.y() + 15 && y > p1.y() - 15) {
					if (count == 0)src = a.getKey();
					else dest = a.getKey();
				}
			}
			count++;
			if (count == 1) {
				if(w.equals("removeNodeFromGraph")) {
					g0.removeNode(src);
					syso="remove node:"+src;
					System.out.println(syso);
					w = "";
					count = 0;
					src = 0;
					dest = 0;
				}
				else {
					syso = "in list: " + src + ", choose dest";
					System.out.println("in list: " + src + "," + dest);
				}
			} else {
				if(w.equals("removeEdgeFromGraph")) {
					g0.removeEdge(src, dest);
					syso="remove edge from :"+src+" to "+dest;
					System.out.println(syso);
				}
				else if (w.equals("shortestPathDist")) {
					Graph_Algo a = new Graph_Algo();
					a.init(g0);
					double ans = a.shortestPathDist(src, dest);
					syso = "from src:"+src+"  to dest:"+dest+"  dist=" + ans;
					System.out.println(ans);
				}
				else if (w.equals("path")) {
					Graph_Algo a = new Graph_Algo();
					a.init(g0);
					g3 = new ArrayList<node_data>(a.shortestPath(src, dest));
					ArrayList<Integer> g2 = new ArrayList<Integer>();
					for (int i = 0; i < g3.size(); i++)
						g2.add(g3.get(i).getKey());
					syso = "from src:"+src+" to dest:"+dest;
					syso1="Path ans:" + g2.toString();
					System.out.println("Path ans:" + g2);

				}
				w = "";
				count = 0;
				src = 0;
				dest = 0;
			}
		} else if (w.equals("TSP")) {
			double x = e.getX();
			double y = e.getY();
			ArrayList<node_data> g1 = new ArrayList<node_data>(g0.getV());
			for (node_data a : g1) {
				Point3D p1 = new Point3D((int) a.getLocation().x(), (int) a.getLocation().y());
				if (x < p1.x() + 15 && x > p1.x() - 15 && y < p1.y() + 15 && y > p1.y() - 15)
					g4.add(a.getKey());
			}
			syso = "in list:" + g4;
			System.out.println("in list:" + g4.get(g4.size() - 1));
		}
		else if(w.equals("moveRobetToFruit")) {
			double x = e.getX();
			double y = e.getY();
			if(countRoboot==0) {
				setRoboot(x,y);
				syso="chas whre fuirt to move";
			}
			else {

				//	moveRoobotToFruit(x, y);
				countRoboot=0;
				w="";
			}
		}
		else if(w.equals("moveRobetToEdge")) {
			double x = e.getX();
			double y = e.getY();
			if(countRoboot==0) {
				setRoboot(x,y);
				syso="chas whre Edge to move";
			}
			else {

				moveRoobotToEdge(x, y);
				countRoboot=0;
				w="";
			}
			repaint();
		}
		repaint();
	}

	private void moveRoobotToEdge(double x, double y) {
	
		node_data nodeTemp=new Nodedata();
		nodeTemp=setnode(x, y);
		if(nodeTemp.getKey()!=-1) {
			int robootPlace=roobet.get(robotTemp).getKey();
			node_data d=g0.getNode(nodeTemp.getKey());
			roobet.get(robotTemp).copy(d);
			way(robootPlace,nodeTemp.getKey(),nodeTemp.getKey());
		}
		syso="fruit valiGame:"+game.getRobots();
		syso2="time to end:"+game.timeToEnd();;
		w="";
		if(!game.isRunning()) {
			results="result for graph:"+numGraph+":"+game.toString();
			clean();
		}
	}
	//	private void moveRoobotToFruit(double x, double y) {
	//		int furitTemp=-1;
	//		boolean isFruit=false;
	//		for (int i = 0; i < fruit.size(); i++) {
	//			if (x<fruit.get(i).getPos().x()+20 && x>fruit.get(i).getPos().x()-20 && y<fruit.get(i).getPos().y()+20 && y>fruit.get(i).getPos().y()-20) {
	//				furitTemp=i;
	//				isFruit=true;
	//			}
	//
	//			if(furitTemp!=-1) {
	//				int robootPlace=roobet.get(robotTemp).getKey();
	//				node_data d=g0.getNode(fruit.get(furitTemp).getDest());
	//				roobet.get(robotTemp).copy(d);;
	//				way(robootPlace,fruit.get(furitTemp).getSrc(),fruit.get(furitTemp).getDest());
	//
	//
	//				furitTemp=-1;
	//			}
	//		}
	//		syso="fruit valiGame:"+getVal();
	//		syso2="time to end:"+timeToEnd;
	//		w="";
	//	
	//
	//	}
	private double getVal() {
		double sum=0;
		for (String r: game.getRobots()) {
			String s=r.substring(r.indexOf("value")+7, r.indexOf("src")-2);
			sum+=Double.valueOf(s);
		}
		return sum;
	}
	private void way(int src, int srcFruit, int destFruit) {

		//Iterator<String> f_iter = game.getFruits().iterator();
		//while(f_iter.hasNext()) {
		//	String s=""+f_iter.next();
		//	System.out.println(s);
		//}

		Graph_Algo b=new Graph_Algo();
		b.init(g0);
		g3=(ArrayList<node_data>) b.shortestPath(src, srcFruit);
		if(srcFruit!= destFruit	)g3.add(g0.getNode(destFruit));//only to furit
		ArrayList<Integer> g2 = new ArrayList<Integer>();
		for (int i = 0; i < g3.size(); i++)   g2.add(g3.get(i).getKey());
		syso1="Path ans:" + g2.toString();
		for (int i = 0; i < g3.size()-1; i++) {
			Edgedata e=g0.getEdgeE(g3.get(i).getKey(),g3.get(i+1).getKey());
			if(e.getFruit().size()!=0)t=true;
			else t=false;
			timeToEnd-=e.getWeight()*1000;
			if(!game.isRunning()) {
				return;
			}
			r=true;
			valWin=getVal();
			game.move();
			while(r&&game.isRunning()) 		moveRobots(e.getDest());
			System.out.println(t+"t");
			if(t&&valWin!=getVal())t=false;
			ArrayList<Fruit> f=new ArrayList<Fruit>(e.getFruit());
			for (Fruit j: f) 	valWin+=j.getValue();
			fuirtRandom(e.getDest());

		}

	}
	private  void moveRobots(int nodeDest) {

		while(game.getRobots().get(robotTemp).toString().lastIndexOf("-")<0) {
			//System.out.println();
			game.move();
			//System.out.println(game.getRobots());
	}
	//	MyThread a=new  MyThread("g");

		// a1.run(game, robotTemp, nodeDest,20);
		// a2.run(game, robotTemp, nodeDest,-100);
		// a3.run(game, robotTemp, nodeDest,+100);
		// a4.run(game, robotTemp, nodeDest,-200);
		 game.chooseNextEdge(robotTemp, nodeDest);
	//	a.run(game, robotTemp, nodeDest,-20);
		
		 trehd(game);
		r=false;	

	}

	private void fuirtRandom(int i) {
		//	if(t) {
		//game.chooseNextEdge(robotTemp,i);
		//game.move();
		//t=false;
		//	}
		for (Fruit j: fruit) {
			g0.getEdgeE(j.getSrc(),j.getDest()).deletFruit();
		}

		fruit=new ArrayList<Fruit>();
		//if(8>0)return;


		setList(game);


		//		boolean type=true;
		//		int randomSrc=0;
		//		int randomDest=0;
		//		while(type) {
		//		 randomSrc=(int)(Math.random()*g0.nodeSize());
		//		ArrayList<edge_data>e =new 	ArrayList<edge_data>(g0.getE(randomSrc));
		//		 randomDest=(int)(Math.random()*e.size());
		//		randomDest=e.get( randomDest).getDest();
		//		if(g0.getNode(randomSrc).getLocation().y()-g0.getNode(randomDest).getLocation().y()>0) {
		//			if(j.getType()==1) type=false;
		//		 }
		//		 else {
		//			 if(j.getType()==-1) type=false;
		//		 }
		//		}
		//		int randomSLocausn=(int)(Math.random()*4+1);
		//		double xx=g0.getNode(randomSrc).getLocation().x()+g0.getNode(randomDest).getLocation().x();
		//		double yy=g0.getNode(randomSrc).getLocation().y()+g0.getNode(randomDest).getLocation().y();
		//		 
		//		
		//		g0.getEdgeE(j.getSrc(),j.getDest()).deletFruit();
		//		//	g0.getEdgeE(fruit.get(furitTemp).getSrc(),fruit.get(furitTemp).getDest()).deletFruit();
		//		g0.getEdgeE(randomSrc,randomDest).setFruit(j);
		//
		//		
		//
		//		Point3D p=new Point3D(xx/2, yy/2);
		//		j.setLocation(p);
		//


	}
	@Override
	public void mouseReleased(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		Point3D p = new Point3D(x, y);
		ArrayList<node_data> g1 = new ArrayList<node_data>(g0.getV());
		if (g1.size() > 1) {
			if (e.getX() != g1.get(g1.size() - 1).getLocation().x()&& e.getY() != g1.get(g1.size() - 1).getLocation().y()) {
				if (setnode(x, y) != null) {
					for (node_data a : g1) {
						if (a.getTag() == 10) {
							if (setnode(x, y) == a) {
							} else {
								g0.connect(a.getKey(), setnode(x, y).getKey(), g0.getNode(setnode(x, y).getKey()).getLocation().distance2D(a.getLocation()));
								syso = "concet:" + a.getKey() + " to " + setnode(x, y).getKey();
								System.out.println("concet:" + a.getKey() + " to:" + setnode(x, y).getKey());
							}
						}
						a.setTag(0);
					}
				}
				repaint();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// System.out.println("mouseExited");
	}
	public void setRoboot(double x, double y) {

		for (int i = 0; i < roobet.size(); i++) {
			if (x<roobet.get(i).getLocation().x()+10 && x>roobet.get(i).getLocation().x()-10 && y<roobet.get(i).getLocation().y()+10 && y>roobet.get(i).getLocation().y()-10) {
				robotTemp=i;
				countRoboot=1;
				break;
			}
		}
		syso1=""+robotTemp;
	}
	public node_data setnode(Double x, Double y) {
		ArrayList<node_data> g1= new ArrayList<node_data>(g0.getV());
		for (node_data a: g1) {
			if (x<a.getLocation().x()+10 && x>a.getLocation().x()-10 && y<a.getLocation().y()+10 && y>a.getLocation().y()-10) {
				return a;
			}
		}
		return null;
	}
	public void save_paint(String a) {
		try {
			BufferedImage image = new BufferedImage(1300, 1200, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = image.createGraphics();
			this.paint(graphics2D);
			File outputfile = new File("ima"+a+".jpg");
			ImageIO.write(image, "jpg", outputfile);
			System.out.println("save_paint ok");
			syso="save_paint ok";
			syso1="";
			syso2="";
			syso3="";
			syso4="";
		} catch (Exception exception) {
			System.out.println("no image");
			syso="no image";
			syso1="";
			syso2="";
			syso3="";
			syso4="";
		}
	}
	public void saveGraph(String file_name) {
		try  {    
			FileOutputStream file = new FileOutputStream(file_name); 
			ObjectOutputStream out = new ObjectOutputStream(file); 
			out.writeObject(g0);
			out.close(); 
			file.close(); 
		}   
		catch(IOException ex)   { 
		} 
	} 

	private void setFruitOne(Fruit f) {
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
	private static void setFruit() {
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
	private static boolean findOn(edge_data e, Fruit f) {
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
	private boolean findOn1(edge_data e, Fruit f) {
		//		double x1=g0.getNode(e.getSrc()).getLocation().x();
		//		double x2=g0.getNode(e.getDest()).getLocation().x();
		//		double y1=g0.getNode(e.getSrc()).getLocation().y();
		//		double y2=g0.getNode(e.getDest()).getLocation().y();//;y-y1=mx-mx1             y=y1-mx1
		//		double m=(y1-y2)/(x1-x2); //(y-y1=m(x-x1
		//		double c=y1-(m*x1);


		return false;
	}

	public static  void setList( game_service game ) {
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
		for (int i = 0; i <fruit.size(); i++) {
			fruit.get(i).setLocation(new Point3D(scaleX(r_maxx,r_minx,fruit.get(i).getPos().x()),scaleY(r_maxy,r_miny,fruit.get(i).getPos().y())));
		}
		setFruit();
	
	}
	public  void trehd(game_service game) {
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
	public  void trehd1(game_service game) {
		Thread a=new  Thread(new Runnable() {
			@Override
			public void run() {
				while(game.isRunning()) {
					results="TimeToEnd:"+game.timeToEnd();
					
					repaint();
					try {
						Thread.sleep(2250);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}	
				}
			}
		});
		a.start();
	}

	public static void main(String[] args) {

		Window window = new Window();
		window.setVisible(true);


	}
}
