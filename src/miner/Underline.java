package miner;

import java.util.List;

import domain.Individual;
import domain.Nickname;
import domain.SetOfIndividuals;
import persistence.postgres.IndividualDAO;
import persistence.postgres.SetOfIndividualsDAO;

public class Underline {

	private List<SetOfIndividuals> getSets() {
		return new SetOfIndividualsDAO().readSetOfIndividuals();
	}

	private List<Individual> getIndividuals() {
		return new IndividualDAO().readIndividuals();
	}

	public String replaceSpaceByUnderline(String e) {
		String str = e;
		List<SetOfIndividuals> sets = getSets();
		List<Individual> individuals = getIndividuals();

		for (SetOfIndividuals t : sets) {
			String nameSet = "";
			if (str.contains(t.getNameSet())) {
				String[] split = t.getNameSet().split(" ");

				for (int i = 0; i < split.length - 1; i++) {
					nameSet += split[i] + "_";
				}
				nameSet += split[split.length - 1];
				str = str.replace(t.getNameSet(), nameSet).trim();
			}
			for (Nickname nick : t.getAllNicknames()) {
				String nicknameSet = "";
				if (nick.getNickname() != null && str.contains(nick.getNickname())) {
					String[] split = nick.getNickname().split(" ");

					for (int i = 0; i < split.length - 1; i++) {
						nicknameSet += split[i] + "_";
					}
					nicknameSet += split[split.length - 1];
					str = str.replace(t.getNameSet(), nicknameSet).trim();
				}
			}
		}

		for (Individual ind : individuals) {
			String nameIndividual = "";
			if (str.contains(ind.getName())) {
				String[] split = ind.getName().split(" ");

				for (int i = 0; i < split.length - 1; i++) {
					nameIndividual += split[i] + "_";
				}
				nameIndividual += split[split.length - 1];
				str = str.replace(ind.getName(), nameIndividual).trim();
			}
			for (Nickname nick : ind.getAllNicknames()) {
				String nicknameSet = "";
				if (nick.getNickname() != null && str.contains(nick.getNickname())) {
					String[] split = nick.getNickname().split(" ");

					for (int i = 0; i < split.length - 1; i++) {
						nicknameSet += split[i] + "_";
					}
					nicknameSet += split[split.length - 1];
					str = str.replace(ind.getName(), nicknameSet).trim();
				}
			}
		}
		return str;
	}

	public List<SetOfIndividuals> getSetsWithUnderline() {
		List<SetOfIndividuals> sets = getSets();

		for (SetOfIndividuals t : sets) {
			String nameSet = t.getNameSet().replaceAll(" ", "_");
			t.setNameSet(nameSet);

			for (Nickname nick : t.getAllNicknames()) {
				if (nick.getNickname() != null) {
					String nicknameSet = nick.getNickname().replaceAll(" ", "_");
					nick.setNickname(nicknameSet);
					System.out.println(nick.getNickname());
				}
			}

		}
		return sets;
	}

	public List<Individual> getIndividualWithUnderline() {
		List<Individual> individuals = getIndividuals();

		for (Individual g : individuals) {
			String name = g.getName().replaceAll(" ", "_");
			g.setName(name);

			for (Nickname nick : g.getAllNicknames()) {
				if (nick.getNickname() != null) {
					String nicknameSet = nick.getNickname().replaceAll(" ", "_");
					nick.setNickname(nicknameSet);
					System.out.println(nick.getNickname());
				}
			}

		}
		return individuals;
	}
}
