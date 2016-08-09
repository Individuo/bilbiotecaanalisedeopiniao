package persistence.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import domain.Individual;
import domain.Nickname;
import domain.SetOfIndividuals;

public class IndividualDAO {

	private Connection con;

	public void save(Individual individual) {
		try {

			String sql = "INSERT INTO \"Individuo\"(nome,nick_twitter,conjunto) VALUES(?,?,?);";

			con = PostgresConnection.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setString(1, individual.getName());

			if (individual.getTwitterIndividual() != null) {
				stmt.setString(2, individual.getTwitterIndividual());
			} else {
				stmt.setString(2, null);
			}
			stmt.setInt(3, individual.getSet().getIdSet());

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Individual> readIndividuals() {

		String sqlIndividual = "select ind.id as \"id_individuo\", nome, nick_twitter, conjunto, apelido.id as \"id_apelido\", apelido from \"Individuo\" ind left outer join \"Apelido_Individuo\" apelido on (apelido.id_individuo = ind.id) order by id_individuo";
		List<Individual> individuals = new ArrayList<Individual>();

		try {

			SetOfIndividualsDAO dao = new SetOfIndividualsDAO();
			List<SetOfIndividuals> sets = dao.readSetOfIndividuals();

			con = PostgresConnection.getInstance().getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sqlIndividual);
			Individual individual = null;

			while (rs.next()) {

				if (individual == null) {
					individual = new Individual(rs.getString("nome"));
					individual.setCodIndividual(rs.getInt("id_individuo"));
					individual.setTwitterIndividual(rs.getString("nick_twitter"));

					for (SetOfIndividuals set : sets) {
						if (rs.getInt("conjunto") == set.getIdSet()) {
							individual.setTeam(set);
							// System.out.println(rs.getString("nome"));
							break;
						}
					}
				}
				if (rs.getInt("id_individuo") == individual.getCodIndividual()) {
					Nickname nick = new Nickname(rs.getInt("id_apelido"), rs.getString("apelido"));
					individual.addNickName(nick);
					individuals.add(individual);
				} else {
					individual = null;
					rs.previous();
				}
			}
			return individuals;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public void setOccurrence(List<Individual> individuals) {
		try {

			String sql = "INSERT INTO \"Ocorrencia_Individuo\"(data,id_individuo,id_adjetivo) VALUES(?,?,?);";

			con = PostgresConnection.getInstance().getConnection();
			con.setAutoCommit(false);
			PreparedStatement stmt = con.prepareStatement(sql);

			for (Individual ind : individuals) {
				for (Date date : ind.getAllAdjectives().keySet()) {
					stmt.setTimestamp(1, new Timestamp(date.getTime()));
					stmt.setInt(2, ind.getCodIndividual());
					stmt.setInt(3, ind.getAllAdjectives().get(date).getCodAdj());

					stmt.execute();
				}
			}
			con.commit();
			con.setAutoCommit(true);
			stmt.close();
		} catch (SQLException e) {
			try {
				System.err.println("Error ao executar o SQL");
				e.printStackTrace();
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void saveScore(List<Individual> individuals) {
		try {
			con = PostgresConnection.getInstance().getConnection();
			for (Individual ind : individuals) {
				String sql = "UPDATE \"Individuo\" SET pontuacao =" + ind.getScore() + ",positivos ="
						+ ind.getPositives() + ",negativos =" + ind.getNegatives() + " WHERE id ="
						+ ind.getCodIndividual() + ";";

				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.executeUpdate();
				stmt.close();
			}
		} catch (SQLException e) {
			try {
				System.err.println("Error ao executar o SQL");
				e.printStackTrace();
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
