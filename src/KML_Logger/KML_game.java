package KML_Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;

import javax.swing.text.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import MyGameGUI.*;
import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.Edgedata;
import dataStructure.edge_data;
import dataStructure.node_data;
import utils.Point3D;
import org.json.JSONObject;

import Server.game_service;

import utils.Point3D;

/**
 * This example generates a KML file with a placemark and a chart for each continent. The chart is generated with the Google Chart API and
 * show the area (surface of the earth) of each continent.
 * 
 * Google Chart API example: http://chart.apis.google.com/chart?cht=p3&chd=t:60,40&chs=250x100&chl=Hello|World
 */
public class KML_game {

	private graph graph;
	game_service game;
	Document doc;
	Kml myKml;

	public KML_game(graph graph) {
		this.graph = graph;
	}

	public void setGame (game_service game) {
		this.game = game;
	}

}
