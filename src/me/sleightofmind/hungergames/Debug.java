package me.sleightofmind.hungergames;

public class Debug {
	public static boolean debug = true;
	public static void debug(String message){
		if(debug)System.out.println("Debug: " + message);
	}
}
