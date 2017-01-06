package org.ncibi.lrpath;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Vector;

import javassist.bytecode.Descriptor.Iterator;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.RList;


public final class LRPathOrigianl
{
	private final LRPathRServer rserver;

	public LRPathOrigianl(LRPathRServer rserver)
	{
		this.rserver = rserver;
	}

	public List<LRPathResult> runAnalysis(LRPathArguments data)
	{
		Map<String, String> conceptNameMap = null;
		List<LRPathResult> allResult = new LinkedList<LRPathResult>();
		long rnum = (Math.round(Math.random() * 1000000000));
		ResourceBundle url = ResourceBundle.getBundle("org.ncibi.resource.bundle.url");
		String outFile = "resultImage" + rnum;
		String imageFile = url.getString("rserverOutputDirectory")+outFile+".jpeg";
		
		System.out.println("imageFile :=="+ imageFile);
		
		setupCommonValues(data,imageFile);
		String[] database = data.getDatabase().split(",");
		HashMap<String, Boolean> db = new DatasourceConfig(database, data.getSpecies()).getDbConfiguration();
		List<LRPathResult> result = null;
		String rnaseq = data.getRnaseq();
		
		
				
				
		for (String dbname : db.keySet())
		{
			//System.out.println("decision for database :  with dbname"+ dbname+"  spacies " + data.getSpecies() + "    "+ db.get(dbname));
			if (db.get(dbname))
			{
			   
//			    System.out.println(data.getIdentifiers()[0]);
				conceptNameMap = performAnalysisWithExternalDatabase(data, dbname);
				result = processResultsOfAnalysis(dbname, true, conceptNameMap,rnaseq,imageFile );
				 System.out.println("performAnalysisWithExternalDatabase with map size = "+ conceptNameMap.size()+ conceptNameMap.keySet().toArray()[0] );
				 java.util.Iterator<Entry<String, String>> iterator = conceptNameMap.entrySet().iterator();
					/*while (iterator.hasNext()) {
						Map.Entry mapEntry = (Map.Entry) iterator.next();
						System.out.println("The key is: " + mapEntry.getKey()
							+ ",value is :" + mapEntry.getValue());
					}
					*/
				 
			}
			else
			{
			    System.out.println("performAnalysisWithInternalDatabase = " );
//			    System.out.println(data.getIdentifiers()[0]);
				performAnalysisWithInternalDatabase(data, dbname);
				result = processResultsOfAnalysis(dbname, false, null, rnaseq,imageFile);
			}

			allResult.addAll(result);
		}

		return allResult;
	}

	private void setupCommonValues(LRPathArguments data,String imageFile)
	{

		if (data.getIdentifiers() != null)
		{
			rserver.assignRVariable("geneids", data.getIdentifiers());
		}
		else
		{
			rserver.assignRVariable("geneids", data.getGeneids());
		}
		
		rserver.assignRVariable("filepath",imageFile);
		
		rserver.assignRVariable("readcounts", data.getReadcount());
		System.out.println("Read counts from executor is " + data.getReadcount().length);
		rserver.assignRVariable("sigvals", data.getSigvals());
		rserver.assignRVariable("species", data.getSpecies());
		String rnaseq = data.getRnaseq();
		if(rnaseq.equals("yes"))
		{
			System.out.println("from rnaseq loops with data rnaseqq " + data.getAvgread().length());			
			rserver.assignRVariable("rnaseq", rnaseq);
			rserver.assignRVariable("avg_readcount", data.getReadcount());
			rserver.voidEvalRCommand("sigvals= as.numeric(sigvals)");
			rserver.voidEvalRCommand("avg_readcount= as.numeric(avg_readcount)");
			rserver.voidEvalRCommand("geneids= as.numeric(geneids)");
			rserver.voidEvalRCommand("sigvals[1:10]");
			rserver.voidEvalRCommand("avg_readcount[1:10]");
			rserver.voidEvalRCommand("geneids[1:10]");
			 double[] readcount = data.getReadcount();
			 /*
			 for (int i = 0 ; i < readcount.length ; i++)
			 {
				 System.out.println(readcount[i]);				 
				 
			 }
			 for (int i = 0 ; i < readcount.length ; i++)
			 {
				 System.out.println(readcount[i]);				 
				 
			 }
			 for (int i = 0 ; i < readcount.length ; i++)
			 {
				 System.out.println(readcount[i]);				 
				 
			 }
		*/
			
			
		}
		
	}

	private Map<String, String> performAnalysisWithExternalDatabase(LRPathArguments data, String database)
	{
		ConceptData conceptData = new ConceptData();
		try
		{
			Map<String, String> conceptNameMap = new HashMap<String, String>();

			String dictionaryId = conceptData.getDictionaryId(database);
			String taxid = Integer.toString(Species.toSpecies(data.getSpecies()).taxid());
			String conceptList = conceptData.getConceptListR(dictionaryId, taxid, data.getMing(), data.getMaxg());
			conceptNameMap = conceptData.getConceptName(dictionaryId);
			double[] nullsetList = conceptData.getDictionaryElementList(dictionaryId, taxid);
		
			//System.out.println("conceptNameMap" + conceptNameMap);
			//System.out.println("nullsetList" + nullsetList[1]);
			String command = "";

			rserver.assignRVariable("nullsetList", nullsetList);
			rserver.assignRVariable("conceptList", conceptList);
			rserver.voidEvalRCommand(conceptList);
			rserver.assignRVariable("direction", data.getDirection());

			if (data.getDirection().length > 1)
			{
				String rnaseq = data.getRnaseq();
				if(rnaseq.equals("yes"))
				{
		//res2_kegg = rna_enrich(sigvals=sigvals, geneids=geneids, avg_readcount=avg_readcount, species='mmu', direction=direction, min.g=10, max.g=50000, sig.cutoff=0.05, database='KEGG',adj_readcount=TRUE, read_lim=5)						
					System.out.println("its in the performAnalysisWithExternalDatabase with direction loops" + data.getAvgread());
					command = "LRResults <- rna_enrich(sigvals=sigvals, geneids=geneids, avg_readcount=avg_readcount, species=species, direction, min.g=" + data.getMing() + ", max.g=" + data.getMaxg()
							+ ", sig.cutoff=" + data.getSigcutoff() + ", database=\"External\",adj_readcount=TRUE, read_lim=5,filename=filepath)";					
				}
				else
				{
				
				command = "LRResults <- LRpath(sigvals, geneids, species, direction, min.g=" + data.getMing() + ", max.g=" + data.getMaxg()
						+ ", sig.cutoff=" + data.getSigcutoff() + ", database=\"External\", conceptList, nullsetList,  odds.min.max=c("
						+ data.getOddsmin() + "," + data.getOddsmax() + "))";
				}
			}
			else
			{
				String rnaseq = data.getRnaseq();
				if(rnaseq.equals("yes"))
				{
					command = "LRResults <- rna_enrich(sigvals=sigvals, geneids=geneids, avg_readcount=avg_readcount, species=species, direction=NULL, min.g=" + data.getMing() + ", max.g=" + data.getMaxg()
							+ ", sig.cutoff=" + data.getSigcutoff() + ", database=\"External\",adj_readcount=TRUE, read_lim=5,filename=filepath)";
					
				}
				else
				{
				command = "LRResults <- LRpath(sigvals, geneids, species, direction=NULL, min.g=" + data.getMing() + ", max.g="
						+ data.getMaxg() + ", sig.cutoff=" + data.getSigcutoff()
						+ ", database=\"External\", conceptList, nullsetList,  odds.min.max=c(" + data.getOddsmin() + ","
						+ data.getOddsmax() + "))";
				}
			}
			System.out.println("Command is" + command);
			rserver.voidEvalRCommand(command);
			return conceptNameMap;
		}
		catch (SQLException e)
		{
			throw new IllegalStateException("Failed SQL Command");
		}
	}

	private void performAnalysisWithInternalDatabase(LRPathArguments data, String database)
	{
	    System.out.println("data.getIdentifiers().length = " + data.getIdentifiers().length);
	    System.out.println("data.getDirection().length = " + data.getDirection().length);
	    System.out.println("data.getSigvals().length = " + data.getSigvals().length);
	    String rnaseq = data.getRnaseq();
	    String command = "";
	    System.out.println("its in the performAnalysisWithInternalDatabase with direction loops : ==" + data.getRnaseq());
		if (data.getDirection().length > 1)
		{
			rserver.assignRVariable("direction", data.getDirection());
			if(rnaseq.equals("yes"))
			{
				command = "LRResults <- rna_enrich(sigvals=sigvals, geneids=geneids, avg_readcount=avg_readcount, species=species, direction, min.g=" + data.getMing() + ", max.g=" + data.getMaxg()
						+ ", sig.cutoff=" + data.getSigcutoff() + ", database=\"" + database+ "\",adj_readcount=TRUE, read_lim=5,filename=filepath)";	
			}
			else
			{
		
				command ="LRResults <- LRpath(sigvals, geneids, species, direction, min.g=" + data.getMing() + ", max.g="
					+ data.getMaxg() + ", sig.cutoff=" + data.getSigcutoff() + ", database=\"" + database + "\", odds.min.max=c("
					+ data.getOddsmin() + "," + data.getOddsmax() + "))";
			}
		}
		else
		{
			

			if(rnaseq.equals("yes"))
			{
				command = "LRResults <- rna_enrich(sigvals=sigvals, geneids=geneids, avg_readcount=avg_readcount, species=species, direction=NULL, min.g=" + data.getMing() + ", max.g=" + data.getMaxg()
						+ ", sig.cutoff=" + data.getSigcutoff() + ", database=\"" + database + "\",adj_readcount=TRUE, read_lim=5,filename=filepath)";					
				System.out.println("its in the performAnalysisWithInternalDatabase with direction loops" + data.getAvgread());

				
			}
			else
			{
				command = "LRResults <- LRpath(sigvals, geneids, species, direction=NULL, min.g=" + data.getMing() + ", max.g="
					+ data.getMaxg() + ", sig.cutoff=" + data.getSigcutoff() + ", database=\"" + database + "\", odds.min.max=c("
					+ data.getOddsmin() + "," + data.getOddsmax() + "))";
			}
		}
		
		System.out.println("Command is" + command);
		rserver.voidEvalRCommand(command);
	}

	private List<LRPathResult> processResultsOfAnalysis(String db, boolean isDbExternal, Map<String, String> conceptNameMap, String rnaseq,String imageFile)
	{
		try
		{
			System.out.println("its processResultsOfAnalysis");
			RList list = rserver.evalRCommand("LRResults").asList();
			List<LRPathResult> resultData = new LinkedList<LRPathResult>();
			if(rnaseq.equals("yes"))
			{
				System.out.println("its inside rna seq database");
				String[] conceptId = ((REXP) list.get(0)).asStrings();
				String[] conceptName = ((REXP) list.get(1)).asStrings();
				System.out.println("Concpetname from the R"+ conceptName);
				String[] database = ((REXP) list.get(2)).asStrings();
				String[] numUniqueGenes = ((REXP) list.get(3)).asStrings();
				String[] coeff = ((REXP) list.get(4)).asStrings();
				String[] oddsRatio = ((REXP) list.get(5)).asStrings();
				String[] status = ((REXP) list.get(6)).asStrings();
				String[] pvalue = ((REXP) list.get(7)).asStrings();
				String[] fdr = ((REXP) list.get(8)).asStrings();
				String[] sigGenes = ((REXP) list.get(9)).asStrings();
				if (conceptId.length > 1)
				{
					for (int i = 0; i < conceptId.length; i++)
					{
						Vector<String> genes = new Vector<String>(Arrays.asList(sigGenes[i].split(",")));
						LRPathResult result = new LRPathResult();
						result.setConceptId(conceptId[i]);
						result.setConceptType(db);
						result.setNumUniqueGenes(Integer.parseInt(numUniqueGenes[i]));
						result.setCoeff(Double.parseDouble(coeff[i]));
						result.setOddsRatio(Double.parseDouble(oddsRatio[i]));
						result.setFdr(Double.parseDouble(fdr[i]));
						result.setPValue(Double.parseDouble(pvalue[i]));
						result.setSigGenes(genes);
						result.setImageFilePath(imageFile);
						result.setRnaseq("yes");
						if (isDbExternal)
						{
							result.setConceptName(conceptNameMap.get(conceptId[i]));
							//System.out.println("concept name from external" + conceptNameMap.get(conceptId[i]) + "concept id:" + conceptId[i] );
						}
						else
						{
							result.setConceptName(conceptName[i]);
							//System.out.println("concept name from internal" + conceptName[i] );
						}

						resultData.add(result);
					}
					
					
				}
			
				
				
				
				
			}
			else
			{
				String[] conceptId ;
				String[] conceptName ;
				String[] database; 
				String[] numUniqueGenes;
				String[] coeff ;
				String[] oddsRatio; 
				String[] pvalue;
				String[] fdr ;
				String[] sigGenes ;
				
				if(db.contains("GO"))
				{
				 System.out.println("its in Go loops of Lrpath with length" +  list.size());
				 conceptId = ((REXP) list.get(0)).asStrings();
				 conceptName = ((REXP) list.get(1)).asStrings();
				 database = ((REXP) list.get(2)).asStrings();
				 numUniqueGenes = ((REXP) list.get(3)).asStrings();
				 coeff = ((REXP) list.get(4)).asStrings();
				 oddsRatio = ((REXP) list.get(5)).asStrings();
				 pvalue = ((REXP) list.get(6)).asStrings();
				 fdr = ((REXP) list.get(7)).asStrings();
				 sigGenes = ((REXP) list.get(8)).asStrings();
				}
				else
				{
					System.out.println("its in other loops of Lrpath with length" +  list.size());
					conceptId = ((REXP) list.get(0)).asStrings();
					 conceptName = ((REXP) list.get(1)).asStrings();					
					 numUniqueGenes = ((REXP) list.get(2)).asStrings();
					 coeff = ((REXP) list.get(3)).asStrings();
					 oddsRatio = ((REXP) list.get(4)).asStrings();
					 pvalue = ((REXP) list.get(5)).asStrings();
					 fdr = ((REXP) list.get(6)).asStrings();
					 sigGenes = ((REXP) list.get(7)).asStrings();
					
					
				}
				if (conceptId.length > 1)
				{
					for (int i = 0; i < conceptId.length; i++)
					{
						Vector<String> genes = new Vector<String>(Arrays.asList(sigGenes[i].split(",")));
						LRPathResult result = new LRPathResult();
						result.setConceptId(conceptId[i]);
						result.setConceptType(db);
						//System.out.println("conceptId[i] is +++"+ conceptId[i]);
						result.setNumUniqueGenes(Integer.parseInt(numUniqueGenes[i]));
						result.setCoeff(Double.parseDouble(coeff[i]));
						result.setOddsRatio(Double.parseDouble(oddsRatio[i]));
						result.setFdr(Double.parseDouble(fdr[i]));
						result.setPValue(Double.parseDouble(pvalue[i]));
						result.setSigGenes(genes);
						result.setImageFilePath("n/a");
						result.setRnaseq("no");
						if (isDbExternal)
						{
							result.setConceptName(conceptNameMap.get(conceptName[i]));
						}
						else
						{
							result.setConceptName(conceptName[i]);
						}

						resultData.add(result);
					}
				}
				
				
				
			}
			return resultData;
		
		}
		catch (REXPMismatchException e)
		{
			throw new IllegalArgumentException("Unable to convert objects.");
		}
	}

}
