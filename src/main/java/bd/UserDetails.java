package bd;

public class UserDetails {

	private String userName;
	private String contraseña;
	private String nameRol;
	private boolean encrypt;
	private String name;

	public UserDetails(String userName, String contraseña, String nameRol, boolean encrypt, String name) {
		this.userName = userName;
		this.contraseña = contraseña;
		this.nameRol = nameRol;
		this.encrypt = encrypt;
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public String getContraseña() {
		return contraseña;
	}

	public String getNameRol() {
		return nameRol;
	}

	public boolean isEncrypt() {
		return encrypt;
	}

	public String getName() {
		return name;
	}
	
}
