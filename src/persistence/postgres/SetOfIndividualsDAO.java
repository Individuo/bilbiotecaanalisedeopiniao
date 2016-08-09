package persistence.postgres;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import domain.Adjective;
import domain.Nickname;
import domain.SetOfIndividuals;
import domain.Tweet;

public class SetOfIndividualsDAO {

	private Connection con;

	public void save(SetOfIndividuals set) throws SQLException {
		try {

			String sql = "INSERT INTO \"Conjunto\"(nome,conjunto_twitter) VALUES(?,?);";

			con = PostgresConnection.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setString(1, set.getNameSet());

			if (set.getTwitterSet() != null) {
				stmt.setString(2, set.getTwitterSet());
			} else {
				stmt.setString(2, null);
			}

			stmt.execute();
			set.setIdSet(getIdTeam(set.getNameSet()));
			stmt.close();

		} catch (SQLException e) {
			set.setIdSet(getIdTeam(set.getNameSet()));
		}
	}

	public List<SetOfIndividuals> readSetOfIndividuals() {

		String sql = "select conj.id as \"id_conjunto\", nome, conjunto_twitter, apelido.id as \"id_apelido\", apelido from \"Conjunto\" conj left outer join \"Apelido_Conjunto\" apelido on (apelido.id_conjunto = conj.id) order by id_conjunto";
		List<SetOfIndividuals> sets = new ArrayList<SetOfIndividuals>();

		try {
			con = PostgresConnection.getInstance().getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			SetOfIndividuals set = null;

			while (rs.next()) {
				if (set == null) {
					set = new SetOfIndividuals(rs.getString("nome"));
					set.setIdSet(rs.getInt("id_conjunto"));
					set.setTwitterSet(rs.getString("conjunto_twitter"));
				}

				if (rs.getInt("id_conjunto") == set.getIdSet()) {
					Nickname nick = new Nickname(rs.getInt("id_apelido"), rs.getString("apelido"));
					set.addNickName(nick);
					if (!sets.contains(set)) {
						sets.add(set);
					}
				} else {
					set = null;
					rs.previous();
				}
			}
			return sets;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private int getIdTeam(String name) throws SQLException {

		String sql = "SELECT id FROM \"Conjunto\" WHERE nome='" + name + "'";

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next())
				return rs.getInt("id");
			else
				throw new RuntimeException();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public void setOccurrence(SetOfIndividuals set, Adjective adj, Tweet t) {
		try {
			String sql = "INSERT INTO \"Ocorrencia_Conjunto\"(data,id_conjunto,id_adjetivo) VALUES(?,?,?);";

			con = PostgresConnection.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);

			java.sql.Date date = new java.sql.Date(t.getDateTime().getTime());
			stmt.setDate(1, date);
			stmt.setInt(2, set.getIdSet());
			stmt.setInt(3, adj.getCodAdj());

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setOccurrence(List<SetOfIndividuals> sets) {
		try {

			String sql = "INSERT INTO \"Ocorrencia_Conjunto\"(data,id_conjunto,id_adjetivo) VALUES(?,?,?);";

			con = PostgresConnection.getInstance().getConnection();
			con.setAutoCommit(false);
			PreparedStatement stmt = con.prepareStatement(sql);

			for (SetOfIndividuals t : sets) {
				for (Date date : t.getAllAdjectives().keySet()) {
					stmt.setTimestamp(1, new Timestamp(date.getTime()));
					stmt.setInt(2, t.getIdSet());
					stmt.setInt(3, t.getAllAdjectives().get(date).getCodAdj());

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

	public void saveScore(List<SetOfIndividuals> sets) {
		try {
			con = PostgresConnection.getInstance().getConnection();
			for (SetOfIndividuals t : sets) {
				String sql = "UPDATE \"Conjunto\" SET pontuacao =" + t.getScore() + ",positivos =" + t.getPositives()
						+ ",negativos =" + t.getNegatives() + " WHERE id =" + t.getIdSet() + ";";

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
