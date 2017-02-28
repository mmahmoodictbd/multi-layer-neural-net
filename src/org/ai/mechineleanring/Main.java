package org.ai.mechineleanring;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;


public class Main {

	public static void main(String[] args) throws Exception {

		File file  = new File("log");
		PrintStream printStream = new PrintStream(new FileOutputStream(file));
		System.setOut(printStream);

		DynamicNNGenerator dynNNGen = new DynamicNNGenerator();
		dynNNGen.run();
	}


}
