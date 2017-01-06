package org.ncibi.db.ws;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "ws.ServiceArguments")
@Table(name = "ServiceArguments")
public class ServiceArguments {

	private int id;
	private String uuid;
	private String className;
	private String argsXml;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "uuid")
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "className")
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Column(name = "argsXml")
	public String getArgsXml() {
		return argsXml;
	}

	public void setArgsXml(String argsXml) {
		this.argsXml = argsXml;
	}

}
