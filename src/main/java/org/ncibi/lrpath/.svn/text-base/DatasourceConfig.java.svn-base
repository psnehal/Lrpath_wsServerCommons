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
		for (int i = 0; i < database.length; i++)
		{
			if (species.equals("hsa"))
			{
				if (!database[i].equals("GO"))
				{
					dbConfiguration.put(database[i], true);
				}

			}
			else if (species.equals("rno") || species.equals("mmu"))
			{
				if (database[i].equals("Cytoband"))
				{
					dbConfiguration.put(database[i], false);
				}
				else
				{
					dbConfiguration.put(database[i], true);
				}
			}
			else
			{
				if (database[i].equals("KEGG Pathway"))
				{
					dbConfiguration.put("KEGG", false);
				}
				else
				{
					dbConfiguration.put(database[i], false);
				}
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