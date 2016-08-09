package domain;

import java.util.ArrayList;
import java.util.List;

import miner.Underline;
import persistence.mongodb.EntityDao;
import persistence.postgres.AdjectiveDAO;
import persistence.postgres.IndividualDAO;
import persistence.postgres.SetOfIndividualsDAO;
import twitter.util.Converter;

public class Application {

	private List<Adjective> adjectives;
	private List<SetOfIndividuals> teams;
	private List<Tweet> tweets;
	private List<Individual> individuals;
	private List<Integer> positions = new ArrayList<Integer>();

	public void rankSetOfIndividuals(String collection) {
		AdjectiveDAO adjDAO = new AdjectiveDAO();
		this.adjectives = adjDAO.readAdjectives();

		SetOfIndividualsDAO teamDAO = new SetOfIndividualsDAO();
		Underline u = new Underline();
		this.teams = u.getSetsWithUnderline();

		EntityDao<Tweet> mongoDAO = new EntityDao<Tweet>(collection);

		this.tweets = Converter.convertToList(mongoDAO.findAll());
		int m = 0;

		long tempo = System.currentTimeMillis();
		for (Tweet tweet : this.tweets) {
			m++;
			String[] words = tweet.getStatus().split(" ");
			List<SetOfIndividuals> team = findTeam(words);
			if (team != null) {
				Adjective a = findAdjective(words);
				if (a != null) {
					for (SetOfIndividuals t : team) {
						t.addAdjective(tweet.getDateTime(), a);
					}
				}
			}
			this.positions.clear();
		}

		for (SetOfIndividuals t : this.teams) {
			t.setScore();
		}

		teamDAO.setOccurrence(teams);
		teamDAO.saveScore(teams);

		for (SetOfIndividuals t : this.teams) {
			System.out.println("---------------------------");
			System.out.println(t.getNameSet());
			System.out.println("---------------------------");
			for (Adjective a : t.getAllAdjectives().values()) {
				System.out.println(a.getAdjective());
			}
			System.out.println(t.getAllAdjectives().values().size());

		}

		System.out.println(m);
		System.out.println(("Tempo total: " + (System.currentTimeMillis() - tempo) / 1000));
	}

	public void rankIndividual(String collection) {
		AdjectiveDAO adjDAO = new AdjectiveDAO();
		this.adjectives = adjDAO.readAdjectives();

		IndividualDAO individualDAO = new IndividualDAO();
		Underline u = new Underline();
		this.individuals = u.getIndividualWithUnderline();

		EntityDao<Tweet> mongoDAO = new EntityDao<Tweet>("collection");

		this.tweets = Converter.convertToList(mongoDAO.findAll());
		int m = 0;

		long tempo = System.currentTimeMillis();
		for (Tweet tweet : this.tweets) {
			m++;
			String[] words = tweet.getStatus().split(" ");
			List<Individual> individual = findIndividual(words);
			if (individual != null) {
				Adjective a = findAdjective(words);
				if (a != null) {
					for (Individual g : individual) {
						g.addAdjective(tweet.getDateTime(), a);
					}
				}
			}
			this.positions.clear();
		}

		for (Individual g : this.individuals) {
			g.setScore();
		}

		individualDAO.setOccurrence(individuals);
		individualDAO.saveScore(individuals);

		for (Individual g : this.individuals) {
			System.out.println("---------------------------");
			System.out.println(g.getName());
			System.out.println("---------------------------");
			for (Adjective a : g.getAllAdjectives().values()) {
				System.out.println(a.getAdjective());
			}
			System.out.println(g.getAllAdjectives().values().size());

		}

		System.out.println(m);
		System.out.println(("Tempo total: " + (System.currentTimeMillis() - tempo) / 1000));
	}

	private List<Individual> findIndividual(String[] words) {
		List<Individual> individuals = new ArrayList<>();
		for (int i = 0; i < words.length; i++) {
			for (Individual g : this.individuals) {
				if ((words[i].equals(g.getName())) || (words[i].equals(g.getTwitterIndividual()))) {
					individuals.add(g);
					this.positions.add(i);
				}
				for (Nickname nick : g.getAllNicknames()) {
					if (words[i].equals(nick.getNickname())) {
						individuals.add(g);
						this.positions.add(i);
					}
				}
			}
		}
		// Não encontrou ou entrou na Restrição 1
		if (individuals.size() == 0 || individuals.size() > 2) {
			return null;
		}
		return individuals;
	}

	private List<SetOfIndividuals> findTeam(String[] words) {
		List<SetOfIndividuals> teams = new ArrayList<SetOfIndividuals>();
		for (int i = 0; i < words.length; i++) {
			for (SetOfIndividuals t : this.teams) {
				if ((words[i].equals(t.getNameSet())) || (words[i].equals(t.getTwitterSet()))) {
					teams.add(t);
					this.positions.add(i);
				}
				for (Nickname nick : t.getAllNicknames()) {
					if (words[i].equals(nick.getNickname())) {
						teams.add(t);
						this.positions.add(i);
					}
				}
			}
		}
		// Não encontrou ou entrou na Restrição 1
		if (teams.size() == 0 || teams.size() > 2) {
			return null;
		}
		return teams;
	}

	private Adjective findAdjective(String[] words) {
		List<Adjective> adjReturn = new ArrayList<>();
		Adjective maior = new Adjective("maior", -4);
		Adjective menor = new Adjective("menor", 4);

		for (int i = 0; i < words.length; i++) {
			for (Adjective a : this.adjectives) {
				if (words[i].equals(a.getAdjective())) {
					// Regra 4
					if (this.positions.size() == 2) {
						// Verificando se o adjetivo está no meio de dois adjetivos
						if ((i > this.positions.get(0)) && (i < this.positions.get(1))) {
							return null;
						}
					}
					adjReturn.add(a);
				}
			}
		}
		if (adjReturn.size() == 1) {
			return adjReturn.get(0);
		} else if (adjReturn.size() > 1) {
			for (Adjective adj : adjReturn) {
				if (adj.getValue() > maior.getValue()) {
					maior = adj;
				}
				if (adj.getValue() < menor.getValue()) {
					menor = adj;
				}
			}
			// Regra 3
			if (menor.getValue() < 0) {
				return menor;
			}
			// Regra 1
			if (menor.getValue() > 0) {
				return maior;
			}
			// Regra 2
			if (maior.getValue() < 0) {
				return menor;
			}
		}
		return null;
	}
}
