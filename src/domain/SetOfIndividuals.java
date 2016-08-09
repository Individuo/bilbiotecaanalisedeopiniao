package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetOfIndividuals {
	private int idSet;
	private String nameSet;
	private String twitterSet;
	private int score;
	private int positives;
	private int negatives;
	private List<Individual> individualsSet;
	private Map<Date, Adjective> teamAdjectives;
	private List<Nickname> nicknames;

	public SetOfIndividuals(String name, String twitter) {
		this.nameSet = name;
		this.twitterSet = twitter;
		this.individualsSet = new ArrayList<Individual>();
		this.teamAdjectives = new HashMap<Date, Adjective>();
		this.nicknames = new ArrayList<>();
	}

	public SetOfIndividuals(String name) {
		this.nameSet = name;
		this.individualsSet = new ArrayList<Individual>();
		this.teamAdjectives = new HashMap<Date, Adjective>();
		this.nicknames = new ArrayList<>();
	}

	public int getIdSet() {
		return idSet;
	}

	public void setIdSet(int id) {
		this.idSet = id;
	}

	public String getNameSet() {
		return nameSet;
	}

	public void setNameSet(String name) {
		this.nameSet = name;
	}

	public String getTwitterSet() {
		return twitterSet;
	}

	public void setTwitterSet(String twitter) {
		this.twitterSet = twitter;
	}

	public void addIndividual(Individual individual) {
		if (!this.individualsSet.contains(individual)) {
			this.individualsSet.add(individual);
		}
	}

	public void removeIndividual(Individual individual) {
		if (this.individualsSet.contains(individual)) {
			this.individualsSet.remove(individual);
		}

	}

	public List<Individual> getAllIndividuals() {
		return Collections.unmodifiableList(this.individualsSet);
	}

	public void addAdjective(Date tweetDate, Adjective adjective) {
		this.teamAdjectives.put(tweetDate, adjective);
	}

	public Map<Date, Adjective> getAllAdjectives() {
		return Collections.unmodifiableMap(this.teamAdjectives);
	}

	public void addNickName(Nickname nick) {
		this.nicknames.add(nick);
	}

	public List<Nickname> getAllNicknames() {
		return this.nicknames;
	}

	public void setScore() {

		for (Adjective adj : teamAdjectives.values()) {
			if (adj.getValue() > 0) {
				this.positives++;
			} else if (adj.getValue() < 0) {
				this.negatives++;
			}
			this.score += adj.getValue();
		}
		System.out.println("score:" + getScore());
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
