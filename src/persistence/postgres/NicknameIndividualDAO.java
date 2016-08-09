package persistence.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.Individual;
import domain.Nickname;

public class NicknameIndividualDAO {
	
	private Connection con;
	
	public void save(Nickname nick, Individual individual) throws SQLException {
		try {

			String sql = "INSERT INTO \"Apelido_Individuo\"(apelido,id_individuo) VALUES(?,?);";

			con = PostgresConnection.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setString(1, nick.getNickname());
			stmt.setInt(2, getIdIndividual(individual.getName()));
			
			stmt.execute();
			
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private int getIdIndividual(String name) throws SQLException {
		
		String sql = "SELECT id FROM \"Individuo\" WHERE nome='"+name+"'";

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next())
				return rs.getInt("id");
			else 
				throw new RuntimeException();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public List<Nickname> readNicknames() {

		String sql = "SELECT * FROM \"Apelido_Individuo\"";
		List<Nickname> nicks = new ArrayList<Nickname>();
		con = PostgresConnection.getInstance().getConnection();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			Nickname nick;

			while (rs.next()) {
				nick = new Nickname(rs.getInt("id"), rs.getString("apelido"));
				nicks.add(nick);
			}
			return nicks;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
}
