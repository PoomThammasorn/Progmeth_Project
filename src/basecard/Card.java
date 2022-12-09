package basecard;

public class Card {

	private String name;
	private String keyword;

	public Card(String name, String keyword) {
		// TODO Auto-generated constructor stub
		setName(name);
		setKeyword(keyword);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}