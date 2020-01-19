package gameClient;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import Server.game_service;
import dataStructure.Nodedata;
import utils.Point3D;

public class Fruit {
	private double value=0;
	private int type=0;
	private int id=-1;
	private Point3D pos;
	private int src=-1;
	private int dest=-1;
	public Fruit() {
	}

	public Fruit(double value,int type ,Point3D pos) {
		this.value=value;
		this.type=type;
		this.pos=pos;
	}
	public Fruit(String s){
		int count=0;
		double x=0;
		double y=0;
for (int i = 0; i < s.length(); i++) {	
			int key=0;
			String t="";
			while((s.charAt(i)>='0'&&s.charAt(i)<='9')||s.charAt(i)=='.'||s.charAt(i)=='-') {
				t+=s.charAt(i);
				i++;
			}
			if(!t.equals("")) {
				count++;
				if(count==1) value=Double.valueOf(t);
				if(count==2) {
					type=Integer.valueOf(t);
				}
				if(count==3) 	x=Double.valueOf(t);
				if(count==4) {
					y=Double.valueOf(t);
					 pos =new Point3D(x, y);
					break;	
				}
			}
		}
	}
	public void setLocation(Point3D p) {
		pos=p;
	}
	
	public void setDestAndSrc(int src,int dest) {
		this.src=src;
		this.dest=dest;
	}
	public double getValue() {
		return value;
	}
	public int getSrc() {
		return src;
	}
	public int getDest() {
		return dest;
	}
	public int getType() {
		return type;
	}
	public Point3D getPos() {
		return pos;
	}
}
