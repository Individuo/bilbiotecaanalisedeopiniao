package miner;

//import java.io.FileNotFoundException;
//import ptstemmer.Stemmer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lefoly
 */
public class Miner {

	public static String process(String arg) {

		// Abrir Arquivo
		String t = arg;

		// Tokenizar
		Tokenizer step1 = new Tokenizer();
		t = step1.process(t);

		// Filtrar Stopwords
		Stopwords step2 = new Stopwords();
		t = step2.process(t);
		
		Underline step3 = new Underline();
		t = step3.replaceSpaceByUnderline(t);

		return t;
	}

}
