package gameClient;

import java.util.List;

import Server.game_service;

public class Game {
	game_service game=new game_service() {
		
		@Override
		public long timeToEnd() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public long stopGame() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public long startGame() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public List<String> move() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public boolean isRunning() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public List<String> getRobots() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String getGraph() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public List<String> getFruits() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public long chooseNextEdge(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		@Override
		public boolean addRobot(int arg0) {
			// TODO Auto-generated method stub
			return false;
		}
	};
	

}
