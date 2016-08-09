/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package miner;

public class Tokenizer {

	private String text;
	private String[] tokens;

	public String process(String text) {
		tokens = text.split(" ");
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = tokens[i].replace('.', ' ');
			tokens[i] = tokens[i].replace(',', ' ');
			tokens[i] = tokens[i].replace(';', ' ');
			tokens[i] = tokens[i].replace(':', ' ');
			tokens[i] = tokens[i].replace('?', ' ');
			tokens[i] = tokens[i].replace('!', ' ');
			tokens[i] = tokens[i].replace('(', ' ');
			tokens[i] = tokens[i].replace(')', ' ');
			tokens[i] = tokens[i].replace('"', ' ');
			tokens[i] = tokens[i].replace('\'', ' ');
			tokens[i] = tokens[i].replace('/', ' ');
			tokens[i] = tokens[i].replace('[', ' ');
			tokens[i] = tokens[i].replace(']', ' ');
			tokens[i] = tokens[i].replace("-", "");
			tokens[i] = tokens[i].toLowerCase();
			tokens[i] = tokens[i].trim();
		}
		return listTokens();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String[] getTokens() {
		return tokens;
	}

	public void setTokens(String[] tokens) {
		this.tokens = tokens;
	}

	public String listTokens() {
		String list = "";
		for (int i = 0; i < tokens.length; i++) {
			list = list + tokens[i] + "\n";
		}
		return list;
	}

}
