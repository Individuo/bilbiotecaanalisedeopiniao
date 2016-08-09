/**
 * 
 */
package domain;

/**
 * Apelido
 */
public class Nickname {

	private int idNickName;
	private String nickname;

	public Nickname(int id, String nickname) {
		this.idNickName = id;
		this.nickname = nickname;
	}

	public Nickname(String nickname) {
		this.nickname = nickname;
	}

	public int getIdNickName() {
		return idNickName;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
