package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Individual {
	private int codIndividual;
	private String name;
	private String twitterIndividual;
	private SetOfIndividuals set;
	private Map<Date, Adjective> individualAdjectives;
	private List<Nickname> nicknames;
	private int score;
	private int positives;
	private int negatives;

	public Individual(String name, String twitter, SetOfIndividuals set) {
		this.name = name;
		this.twitterIndividual = twitter;
		this.set = set;
		this.individualAdjectives = new HashMap<>();
		this.nicknames = new ArrayList<Nickname>();
	}

	public Individual(String name, SetOfIndividuals set) {
		this.name = name;
		this.set = set;
		this.individualAdjectives = new HashMap<>();
		this.nicknames = new ArrayList<Nickname>();
	}

	public Individual(String name) {
		this.name = name;
		this.individualAdjectives = new HashMap<>();
		this.nicknames = new ArrayList<Nickname>();
	}

	public int getCodIndividual() {
		return codIndividual;
	}

	public void setCodIndividual(int individual) {
		this.codIndividual = individual;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTwitterIndividual() {
		return twitterIndividual;
	}

	public void setTwitterIndividual(String twitterIndividual) {
		this.twitterIndividual = twitterIndividual;
	}

	public void setTeam(SetOfIndividuals set) {
		this.set = set;
	}

	public SetOfIndividuals getSet() {
		return this.set;
	}

	public void addAdjective(Date tweetDate, Adjective adjective) {
		this.individualAdjectives.put(tweetDate, adjective);
	}

	public Map<Date, Adjective> getAllAdjectives() {
		return Collections.unmodifiableMap(this.individualAdjectives);
	}

	public void addNickName(Nickname nick) {
		this.nicknames.add(nick);
	}

	public List<Nickname> getAllNicknames() {
		return Collections.unmodifiableList(this.nicknames);
	}

	public void setScore() {

		for (Adjective adj : individualAdjectives.values()) {
			if(adj.getValue() > 0) {
				this.positives++;
			}
			else if(adj.getValue() < 0) {
				this.negatives++;
			}
			this.score += adj.getValue();
		}
		System.out.println("score:" + this.getScore());
	}

	public int getScore() {
		return this.score;
	}
	
	public int getPositives() {
		return this.positives;
	}
	
	public int getNegatives() {
		return this.negatives;
	}
}
