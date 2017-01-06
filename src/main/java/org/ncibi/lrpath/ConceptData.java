package org.ncibi.lrpath;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import org.ncibi.commons.db.JDBCExecuter;

final class ConceptData
{

	private JDBCExecuter db = new JDBCExecuter(ResourceBundle.getBundle("org.ncibi.resource.bundle.msdatabase"));
	private ResourceBundle sql = ResourceBundle.getBundle("org.ncibi.resource.bundle.mssql");
	private TextParser parse = new TextParser();
	

	public Map<String, Vector<String>> getConceptGenes(String database, String species) throws SQLException
	{
		String query = sql.getString("selectConceptElement");
		query = query.replaceFirst("\\?", database);
		query = query.replaceFirst("\\?", species);
		return db.vectorMap(query);
	}

	public Map<String, String> getConceptName(String database) throws SQLException
	{

		String query = sql.getString("selectConceptName");
		query = query.replaceFirst("\\?", database);
		System.out.println("inside getConceptName " + query);
		return db.hashResult(query);
	}

	public List<String> getConceptIds(String database, int minCutoff, int maxCutoff) throws SQLException
	{
		System.out.println("inside getgetConceptIds server common with database " + database);
		String query = sql.getString("selectConceptId");
		query = query.replaceFirst("\\?", database);
		query = query.replaceFirst("\\?", Integer.toString(minCutoff));
		query = query.replaceFirst("\\?", Integer.toString(maxCutoff));
		System.out.println("query is "+ query);
		return db.selectSingleList(query);
	}

	public String getDictionaryId(String database) throws SQLException
	{
		System.out.println("inside getDict id from server coomon with database " + database);
		System.out.println("inside "+ResourceBundle.getBundle("org.ncibi.resource.bundle.msdatabase"));
		String query = sql.getString("selectDictionaryId");
		query = query.replaceFirst("\\?", database);
		System.out.println("query is "+ query);
		return db.selectSingleValue(query);
	}

	public double[] getDictionaryElementList(String database, String species) throws SQLException
	{
		String query = sql.getString("selectNullSet");
		query = query.replaceFirst("\\?", database);
		query = query.replaceFirst("\\?", species);

		List<String> data = db.selectSingleList(query);
		double[] list = new double[data.size()];

		for (int i = 0; i < data.size(); i++)
		{
			list[i] = Double.parseDouble((String) data.get(i));
		}

		return list;
	}

	public String getConceptListR(String database, String species, int minCutoff, int maxCutoff) throws SQLException
	{
		String conceptList = "conceptList = list(";
		List<String> concept_id = getConceptIds(database, minCutoff, maxCutoff);
		System.out.println("inside getConceptListR " + concept_id.size());
		
		Map<String, Vector<String>> map = getConceptGenes(database, species);

		for (int i = 0; i < concept_id.size(); i++)
		{
			if (map.get(concept_id.get(i)) != null)
			{
				String value = parse.createCommaDelimitedText(map.get(concept_id.get(i)));

				if (i == 0)
				{
					conceptList += "\"" + concept_id.get(i) + "\"=c(" + value + ")";
				}
				else
				{
					conceptList += ",\"" + concept_id.get(i) + "\"=c(" + value + ")";
				}
			}
		}

		conceptList += ")";
		return conceptList;
	}
}
