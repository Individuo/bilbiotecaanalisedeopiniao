import java.sql.SQLException;
import java.text.ParseException;
import java.util.Set;

import domain.Adjective;
import domain.Application;
import domain.Dictionary;
import persistence.postgres.AdjectiveDAO;
import twitter.ManagerTwitter;

public class Main {

	public static void main(String[] args) throws SQLException, ParseException {
		/*
		 * SetOfIndividuals team = new SetOfIndividuals("Teste", 98); SetOfIndividualsDAO d = new SetOfIndividualsDAO();
		 * d.save(team);
		 * 
		 * 
		 * Nickname nick = new Nickname("Meu nome"); NicknameSetOfIndividualsDAO dd = new
		 * NicknameSetOfIndividualsDAO(); dd.save(nick, team);
		 */
		 //ManagerTwitter manager = new ManagerTwitter();
		 //manager.execute();
		 //manager.clearStopWords("Split1");
		/*SetOfIndividualsDAO dao = new SetOfIndividualsDAO();
		List<SetOfIndividuals> teams = dao.readTeams();
		
		IndividualDAO dao2 = new IndividualDAO();
		List<Individual> gamers = dao2.readGamer();
		Individual g = gamers.get(3);
		System.out.println(g.getTeam().getNameTeam());
		
		AdjectiveDAO dao3 = new AdjectiveDAO();
		List<Adjective> adjetivos = dao3.readAdjectives();
		for(Adjective a : adjetivos) {
			System.out.println("Nome: "+a.getAdjective()+" Valor: "+a.getValue());
		}*/
		
		/*EntityDao<Tweet> dao = new EntityDao<>(null, "cdLol1Limpo");
		
		List<DBObject> tweets = dao.findAll();
		List<Tweet> ts = Converter.convertToList(tweets);
		//System.out.println(new Date("11/11/2011 11:11"));
		int i = 0;
		for(Tweet t : ts) {
			System.out.println(t.getDateTime());
			System.out.println(t.getStatus());
			i++;
		}
		System.out.println(i);*/
		
		/*Dictionary dicionario = new Dictionary("adjetivos.txt");
		System.out.println(dicionario.getDictionary().size());
		
		AdjectiveDAO dao = new AdjectiveDAO();
		dao.save(dicionario.getDictionary());*/
		
		
		//ManagerTwitter manager = new ManagerTwitter();
		//manager.clearStopWords("cblol2");
		Application app = new Application();
		app.rankSetOfIndividuals("cblol2Limpo");
	
		/*EntityDao<Tweet> d = new EntityDao<>(null, "cbLol1Limpo");
		List<Tweet> t = Converter.convertToList(d.findAll());
		for(Tweet tt : t)
			System.out.println(tt.getDateTime());
			*/
		
		//String str = "ola tudo bem";
		//System.out.println(str.contains("ola tudo"));
	}
}
