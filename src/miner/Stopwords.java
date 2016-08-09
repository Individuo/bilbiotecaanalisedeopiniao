package miner;

public class Stopwords {

	public String process(String e) {
		int i, j;
		// int k = 0;
		String list = "";

		String[] entrada = e.split("\\s");

		String listS = "o; a; os; as; um; uma; uns; umas; em; para; de; da; das; do; dos; com; sem; é; são; "
				+ "até; ainda; quando; como; onde; mas; porém; contudo; todavia; e; ou; à ; às; ao; a; as; "
				+ "algum; alguma; alguns; algumas; seu; seus; sua; suas; no; nos; na; nas; num; "
				+ "aí; lá; este; estes; esta; estas; esse; esses; "
				+ "essa; essas; aquele; aqueles; aquela; aquelas; porque; porquê; quê; que; por; "
				+ "portanto; assim; então; desse; dessa; deste; desta; "
				+ "desses; dessas; destes; destas; entre; pelo; pelos; pela; pelas; se; "
				+ "cada; qual; quais; já; aonde; entretanto; sobre";

		String[] stopwords = listS.split("; ");

		for (i = 0; i < entrada.length; i++) {
			// System.out.println("i = " + i + " : " + entrada[i]);
			j = 0;
			if (entrada[i].length() > 0) {
				while ((j < stopwords.length) && (!entrada[i].toLowerCase().equals(stopwords[j]))) {
					// System.out.println(" j = " + j + " : " + stopwords[j]);
					if (j == stopwords.length - 1) {
						list = list + entrada[i] + " ";
					}
					j++;
				}
			}
		}
		return list;
	}

}
