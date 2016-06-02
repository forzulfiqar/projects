package game;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class CardsClientListener {
//extends Thread {
	BufferedReader in = null;
	PrintWriter out = null;

	public CardsClientListener(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
	}

	/*
	public void run() {

		while (true) {
			try {
				String input = in.readLine();
				out.println(input.toUpperCase());
			} catch (Exception e) {

			}
		}
	}
	*/

	public BufferedReader getIn() {
		return in;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public PrintWriter getOut() {
		return out;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}
	
	
	
	
}

