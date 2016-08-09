package domain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Dictionary {

	private Set<Adjective> listAdjectives;
	private Set<Adjective> newAdjectives;
	private String path;

	// "dicionarioLol.txt"
	public Dictionary(String path) {
		this.path = path;
		this.listAdjectives = new HashSet<Adjective>();
		this.newAdjectives = new HashSet<Adjective>();
	}

	public Set<Adjective> getDictionary() {
		FileReader fileReader;
		BufferedReader file;

		try {
			fileReader = new FileReader(this.path);
			file = new BufferedReader(fileReader);

			String line = "";
			Adjective adj;

			while ((line = file.readLine()) != null) {
				String[] split = line.split("\\.");
				adj = new Adjective(split[0].trim(), Integer.parseInt(split[1].trim()));

				listAdjectives.add(adj);
			}
			fileReader.close();

			return listAdjectives;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public void addAdjective(Adjective adj) {
		if (this.listAdjectives.add(adj)) {
			this.newAdjectives.add(adj);
		}
	}

	public void removeAdjective(Adjective adj) {
		this.newAdjectives.remove(adj);
		this.listAdjectives.remove(adj);
	}

	public void updateDictionary() {
		try {
			FileWriter writer = new FileWriter(path, true);
			BufferedWriter file = new BufferedWriter(writer);

			for (Adjective adj : this.newAdjectives) {
				file.write("\n" + adj.getAdjective() + "." + adj.getValue());
			}
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
