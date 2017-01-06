package org.ncibi.lrpath;

import java.util.Arrays;
import java.util.HashMap;

public class DatasourceConfigOld
{
	private String[] database;
	private String species;
	private HashMap<String, Boolean> dbConfiguration;

	public DatasourceConfigOld(String[] database, String species)
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
			if (species.equals("hsa"))
			{
				System.out.println("database inside hsa loops");
				if (!database[i].equals("GO"))
				{
					System.out.println("2");
					System.out.println("database inside go hsa loops");
					dbConfiguration.put(database[i], true);
				}
				else
				{
					System.out.println("7");
					System.out.println("database inside"+database[i]+ "  loops");
					dbConfiguration.put(database[i], false);
				}

			}
			else if (species.equals("rno") || species.equals("mmu"))
			{
				if (database[i].equals("Cytoband"))
				{
					//System.out.println("3");
					System.out.println("database inside cytoband rno mmu loops");
					dbConfiguration.put(database[i], false);
				}
				else
				{
					System.out.println("4");
					System.out.println("database inside rno mmu loops");
					dbConfiguration.put(database[i], true);
				}
			}
			else
			{
				System.out.println("5");
				if (database[i].equals("KEGG"))
				{
					//System.out.println("6");
					System.out.println("database inside kegg loops");
					dbConfiguration.put("KEGG", false);
				}
				else
				{
					System.out.println("7");
					System.out.println("database inside"+database[i]+ "  loops");
					dbConfiguration.put(database[i], false);
				}
			}
			System.out.println("8");

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