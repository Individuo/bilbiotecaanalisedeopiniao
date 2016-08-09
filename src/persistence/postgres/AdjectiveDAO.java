package persistence.postgres;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import domain.Adjective;

public class AdjectiveDAO {

	private Connection con;
	
	public AdjectiveDAO() {
		con = PostgresConnection.getInstance().getConnection();
	}

	public void save(Adjective adj) {
		try {

			String sql = "INSERT INTO \"Adjetivo\"(palavra,valor) VALUES(?,?);";
			
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setString(1, adj.getAdjective());
			stmt.setInt(2, adj.getValue());

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void save(Set<Adjective> adjectives) {
		try {

			String sql = "INSERT INTO \"Adjetivo\"(palavra,valor) VALUES(?,?);";
			
			PreparedStatement stmt = con.prepareStatement(sql);
			
			for(Adjective adj : adjectives) {
				stmt.setString(1, adj.getAdjective());
				stmt.setInt(2, adj.getValue());

				stmt.execute();
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Adjective> readAdjectives() {
		
		String sql = "SELECT * FROM \"Adjetivo\"";
		List<Adjective> adjectives = new ArrayList<Adjective>();
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			Adjective adj;
			
			while(rs.next()) {
				adj = new Adjective(rs.getString("palavra"));
				adj.setCodAdj(rs.getInt("id"));
				adj.setValue(rs.getInt("valor"));
				
				adjectives.add(adj);
			}
			
			return adjectives;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
}
