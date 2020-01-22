package gameClient;

import java.awt.BorderLayout;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONException;
import org.json.JSONObject;

import MyGameGUI.Point3D;
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


public class WindowMange extends JFrame implements ActionListener, MouseListener {



	private JTextField textField_1;
	private JButton btnNewButton;
	private JPanel panel;
	private JPasswordField idField;
	private char[] id = new char[10];
	private int id2=-1;
	private String levelYouWantToPlay="";
	private int levelYouWantToPlayInt=-1;




	private String results ="";
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
	private String levelMax = "";
	private String w = "";
	private int count = 0;
	private int countSave = 0;
	private int id1 = 0;
	private int numGraph = -1;
	private int countRoboot = 0;
	private int robotTemp =-1;
	private int numRoboot =0;
	private double valWin =0;
	private BufferedImage imgFruit2 = null;
	private 	BufferedImage imgFruit = null;
	private BufferedImage imgGraph  = null;
	private BufferedImage roobetImg1 = null;
	private	BufferedImage roobetImg2 = null;
	private BufferedImage roobetImg3 = null;
	private  JLabel l1;
	private HashMap<Integer, String[]> infoScore=new HashMap<Integer, String[]>();
	private String  numPlayTody;
	private String  myBestScore[]=new String[11];
	private int levelInfoPrint;
	boolean toPrintInfo = false;

	public void setLevelMax() {
		Game_Server.login(id2);
		game_service game = Game_Server.getServer(0);
		String s=game.toString();
		levelMax="you in level:"+s.substring(s.lastIndexOf("user")+12,s.lastIndexOf("robots")-2);

	}
	public int getNumGraph() {
		return numGraph;
	}
	public void setId(int id) {
		this.id1=id;
		this.setLevelMax();
	}
	public void SetNumGraph(int numG) {
		numGraph=numG;
	}
	public WindowMange(DGraph p, game_service game, ArrayList<Nodedata> roobet2, int numGame) {
		initGUI();
		numGraph=numGame;

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
	public void setGrafh() {
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
	public WindowMange() {
		initGUI();
		setImg();

	}


	private void setImg() {
		roobetImg1 = null;
		try {
			roobetImg1 = ImageIO.read(new File("data\\jon.jpg"));
		} catch (IOException e) {
			syso="no seccs robot";
		}
		roobetImg2 = null;
		try {
			roobetImg2 = ImageIO.read(new File("data\\tiron2.png"));
		} catch (IOException e) {
			syso="no seccs robot";
		}
		roobetImg3 = null;
		try {
			roobetImg3 = ImageIO.read(new File("data\\dianeriz.png"));
		} catch (IOException e) {
			syso="no seccs robot";
		}




		imgFruit = null;
		try {
			imgFruit = ImageIO.read(new File("data\\king3.png"));
		} catch (IOException e) {
			syso="no seccs fruit";
		}


		try {
			imgFruit2 = ImageIO.read(new File("data\\dragon1.png"));
		} catch (IOException e) {
			syso="no seccs fruit";
		}		
	}
	public void setGame(game_service game) {
		this.game=game;
	}
	public void setRoobet(ArrayList<Nodedata> roobet) {
		this.roobet=roobet;
	}
	public void setG0Graph(DGraph p) {
		this.g0=p;
	}
	private void initGUI() {
		this.setSize(1300, 1200);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);





		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);

		JLabel lblNewLabel = new JLabel("id");
		panel.add(lblNewLabel, "cell 0 1,alignx trailing,aligny center");

		idField = new JPasswordField();
		idField.setColumns(13);
		panel.add(idField, "cell 1 1,alignx center");

		JLabel lblNewLabel_1 = new JLabel("level to play");
		panel.add(lblNewLabel_1, "cell 2 1,alignx center,aligny center");

		textField_1 = new JTextField();
		panel.add(textField_1, "cell 3 1,alignx left,aligny center");
		textField_1.setColumns(4);

		btnNewButton = new JButton("play to start");
		//   ListenForButton listener = new ListenForButton();

		btnNewButton.addActionListener(this);
		panel.add(btnNewButton, "cell 4 1");



		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Menu");
		menuBar.add(menu);
		this.setMenuBar(menuBar);

		Menu syso = new Menu();
		menuBar.add(syso);
		this.setMenuBar(menuBar);



		Menu game = new Menu("game");
		menuBar.add(game);
		this.setMenuBar(menuBar);

		Menu Choosegraph = new Menu("Choose a graph");
		menuBar.add(Choosegraph);
		this.setMenuBar(menuBar);


		Menu info = new Menu("Score and game information");
		menuBar.add(info);
		this.setMenuBar(menuBar);

		MenuItem item33;
		int b[]= {0,1,3,5,9,11,13,16,19,20,23};
		for (int i = 0; i < b.length; i++) {
			item33 = new MenuItem("info for level:"+b[i]);
			item33.addActionListener(this);
			info.add(item33);
		}



		MenuItem item17;
		for (int i = 0; i < 24; i++) {
			item17 = new MenuItem(""+i);
			item17.addActionListener(this);
			Choosegraph.add(item17);
		}
		MenuItem item1 = new MenuItem("savePic");
		item1.addActionListener(this);

		MenuItem item2 = new MenuItem("clearAll");
		item2.addActionListener(this);

		MenuItem item3 = new MenuItem("Score and game information");
		item3.addActionListener(this);







		MenuItem item16 = new MenuItem("putRobet");
		item16.addActionListener(this);

		MenuItem item18 = new MenuItem("TimeToEnd");
		item18.addActionListener(this);

		MenuItem item19 = new MenuItem("moveRobetToEdge");
		item19.addActionListener(this);
		menu.add(item1);
		menu.add(item2);
		//info.add(item3);
		game.add(item16);
		game.add(item18);
		game.add(item19);

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



	public void paintComponnet(Graphics g) {
		super.paint(g);
	}


	public void paint(Graphics g) {

		super.paint(g);

		if(toPrintInfo) {
			g.setColor(Color.ORANGE);
			setLevelMax();
			g.drawString(levelMax, 300, 140);

			g.drawString(numPlayTody, 300, 120);
			for (int i = 0,j=0; i < myBestScore.length; i++,j=j+25) {
				g.drawString(myBestScore[i], 50, 150+j);
			}
			g.drawString("in level:"+levelInfoPrint+" the 10 best score:", 930, 150);
			for (int i = 0,j=0; i < myBestScore.length; i++,j=j+25) {
				g.drawString(infoScore.get(levelInfoPrint)[i],930, 175+j);
			}



			toPrintInfo=false;
		}
		if(game!=null&&!game.isRunning()) {
			results="result for graph:"+numGraph+":"+game.toString();
		}

		g.setColor(Color.RED);
		for (int i = 0; i < fruit.size(); i++) {
			if(fruit.get(i).getType()==-1) {
				g.drawImage(imgFruit, fruit.get(i).getPos().ix()-10,fruit.get(i).getPos().iy()+17, 35, 35, this);
				g.drawString(""+fruit.get(i).getValue(),fruit.get(i).getPos().ix()+10,fruit.get(i).getPos().iy()+17);
			}
			else {
				g.drawImage(imgFruit2, fruit.get(i).getPos().ix()-10,fruit.get(i).getPos().iy()+17, 35, 35, this);
				g.drawString(""+fruit.get(i).getValue(),fruit.get(i).getPos().ix()+10,fruit.get(i).getPos().iy()-17);
			}

		}
		g.setColor(Color.ORANGE);
		g.drawString(syso, 99, 73);
		if(!syso2.equals(""))g.drawString(syso2, 99, 85);
		if(!syso1.equals(""))g.drawString(syso1, 99, 97);
		if(!syso3.equals(""))g.drawString(syso3, 99, 109);
		if(!syso4.equals(""))g.drawString(syso4, 99, 121);
		if(!results.equals(""))g.drawString(results, 99, 133);

		for (int i = 0; i < roobet.size(); i++) {
			if(i==0)	g.drawImage(roobetImg1, roobet.get(i).getLocation().ix()-10, roobet.get(i).getLocation().iy()+17, 50, 50, this);
			if(i==1)g.drawImage(roobetImg2, roobet.get(i).getLocation().ix()-10, roobet.get(i).getLocation().iy()+17,50,50, this);
			if(i==2)g.drawImage(roobetImg3, roobet.get(i).getLocation().ix()-10, roobet.get(i).getLocation().iy()+17,50,50, this);
		}


	}


	public void setId(){  
		String s="";
		for (int i = 0; i < id.length; i++) {
			s+=id[i];
		}
		id2= Integer.valueOf(s);            
	}
	public int  getId(){  
		return    id2;        
	}
	public int getPlay(){
		return  levelYouWantToPlayInt;     
	}
	public void setPlay(){
		levelYouWantToPlayInt= Integer.valueOf(levelYouWantToPlay);     
	}
	@Override

	public void actionPerformed(ActionEvent e) {


		if(e.getSource() == btnNewButton){
			System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			if (idField.getPassword().length >0){

				id = idField.getPassword().clone();   
			}

			levelYouWantToPlay = textField_1.getText();
			setId();
			setPlay();
			clean();
			results="";
			numGraph=levelYouWantToPlayInt;


			setGraphBeakgrond();
			System.out.println(id2);
			System.out.println(levelYouWantToPlayInt);
		}





		String str = e.getActionCommand();


		if(str.indexOf("info for level:")==0){
			String s=str.substring(str.indexOf(":")+1, str.length());
			levelInfoPrint=Integer.valueOf(s);
			getInfo();
			toPrintInfo = true;
		}

		if(str.equals("TimeToEnd")) {
			System.out.println(game.timeToEnd());
			if(game.isRunning())results="TimeToEnd:"+game.timeToEnd();
			else results="The game dont start";
		}
		if(str.equals("moveRobetToEdge")) {
			syso="ches roobet to move"; 
			w="moveRobetToEdge";
		}
		if(str.equals("putRobet")) {
			if(roobet.size()<numRoboot)w="putRobet";
			else {
				syso="only"+ numRoboot+" robets in this graph";
				w="";
			}
		}

		if(str.charAt(0)>='0' &&str.charAt(0)<='9') {

			panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);

			JLabel lblNewLabel = new JLabel("id");
			panel.add(lblNewLabel, "cell 0 1,alignx trailing,aligny center");

			idField = new JPasswordField();
			idField.setColumns(13);
			panel.add(idField, "cell 1 1,alignx center");

			JLabel lblNewLabel_1 = new JLabel("level to play");
			panel.add(lblNewLabel_1, "cell 2 1,alignx center,aligny center");

			textField_1 = new JTextField();
			panel.add(textField_1, "cell 3 1,alignx left,aligny center");
			textField_1.setColumns(4);

			btnNewButton = new JButton("play to start");
			//   ListenForButton listener = new ListenForButton();

			btnNewButton.addActionListener(this);
			panel.add(btnNewButton, "cell 4 1");


		}



		if (str.equals("savePic")) {
			Graph_Algo a = new Graph_Algo();
			a.init(g0);
			save_paint("gamePic");
		}
		if (str.equals("clearAll")) {
			clean();
		}

		repaint();
	}


	private void getInfo() {
		SimpleDB1 a=new SimpleDB1();
		//	allUsers(id1);
		a.printLog(id2);
		//a.notSameId();
		int playTody=a.getNumPlayTody();
		HashMap<Integer, Integer> arrMyBest=a.getArrMyBest();
		HashMap<Integer, ArrayList<Integer[]>> arrAllBest=a.getArrAllBest();

		numPlayTody="youPlay:"+playTody+" in the game";
		int b[]= {0,1,3,5,9,11,13,16,19,20,23};

		for (int i = 0; i < b.length; i++) {
			myBestScore[i]="in level:"+b[i]+"- you best score:"+arrMyBest.get(b[i])+".\n";	 
		}
		for (int i = 0; i < b.length; i++) {

			String arrAll[]=new String[10];
			for (int j = 0; j < 10&&j<arrAllBest.get(b[i]).size(); j++) {
				int place=j+1;
				arrAll[j]="place:"+place+"- id:"+arrAllBest.get(b[i]).get(j)[0]+" score:"+arrAllBest.get(b[i]).get(j)[1]+" move:"+arrAllBest.get(b[i]).get(j)[2];
			}
			infoScore.put(b[i], arrAll);
		}
	}
	private void setGraphBeakgrond() {
		System.out.println("?????????????????????????????");
		remove(panel);
		//remove(this);
		new JFrame();
		JLabel background=new JLabel(new ImageIcon("data\\imageGR"+levelYouWantToPlayInt+".jpg"));
		getContentPane().add(background);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 // setUndecorated(true);
		pack();
		setLocationRelativeTo(null);
		setBackground(new Color(0, 0, 0, 0));
		setVisible(true);


	}
	private void clean() {
		if(game!=null)game.stopGame();
		countRoboot=0;
		valWin=0;
		fruit = new ArrayList<Fruit>();
		w = "";
		count = 0;
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

	private double getVal() {
		double sum=0;
		for (String r: game.getRobots()) {
			String s=r.substring(r.indexOf("value")+7, r.indexOf("src")-2);
			sum+=Double.valueOf(s);
		}
		return sum;
	}
	private void way(int src, int srcFruit, int destFruit) {


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
			game.move();
		}

		game.chooseNextEdge(robotTemp, nodeDest);
		trehd(game);
		r=false;	

	}

	private void fuirtRandom(int i) {

		for (Fruit j: fruit) {
			g0.getEdgeE(j.getSrc(),j.getDest()).deletFruit();
		}

		fruit=new ArrayList<Fruit>();


		setList(game);

	}
	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

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
			BufferedImage image = new BufferedImage(this.getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
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
						Thread.sleep(60);
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

		WindowMange window = new WindowMange();
		window.setVisible(true);


	}
}
