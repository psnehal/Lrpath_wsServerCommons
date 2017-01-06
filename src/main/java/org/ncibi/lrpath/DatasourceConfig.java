package org.ncibi.lrpath;

import java.util.Arrays;
import java.util.HashMap;

public class DatasourceConfig
{
	private String[] database;
	private String species;
	private HashMap<String, Boolean> dbConfiguration;

	public DatasourceConfig(String[] database, String species)
	{
		this.database = database;
		this.species = species;
		this.dbConfiguration = new HashMap<String, Boolean>();
		configure();
	}

	private HashMap<String, Boolean> configure()
	{
		System.out.println("database.length "+ database.length);
		for (int i = 0; i < database.length; i++)
		{
			System.out.println("database is " + database[i] + "species is" + species);
			if(database[i].equals("GO") || database[i].equals("KEGG")|| database[i].contains("GO")|| database[i].contains("reactome")||database[i].equals("custom"))
			{
				System.out.println("database inside internal database loops");
				dbConfiguration.put(database[i], false);
			}
			else
			{
				dbConfiguration.put(database[i], true);
			}

		}
		return dbConfiguration;
	}

	public HashMap<String, Boolean> getDbConfiguration()
	{
		return dbConfiguration;
	}

	public void setDbConfiguration(HashMap<String, Boolean> dbConfiguration)
	{
		this.dbConfiguration = dbConfiguration;
	}

}