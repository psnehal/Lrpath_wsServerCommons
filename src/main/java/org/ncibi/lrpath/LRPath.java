/*LRpath results is now changed . So instead of sending entire object to the web application 
 * only the file path and certain parameters are passed
 * So :setNumUniqueGenes paramater now actually contains the size of entire result set
 * and conceptName contains the list of the warning:* 
 */


package org.ncibi.lrpath;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Vector;

import javassist.bytecode.Descriptor.Iterator;

import org.apache.commons.lang.ArrayUtils;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.RList;


public final class LRPath
{
	private final LRPathRServer rserver;

	public LRPath(LRPathRServer rserver)
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
		//System.out.println("imageFile"+url.getString("rserverOutputDirectory"));
		String imageFile = url.getString("rserverOutputDirectory")+outFile+".jpeg";
		String resultfile = url.getString("resultDirectory")+outFile+".txt";
		DataWriter write = new DataWriter(); 		
		System.out.println("imageFile :=="+ imageFile);		
		System.out.println("CustomDatabaseFilename :=="+ data.getCustomfile());		
		System.out.println("OutName :=="+ data.getOutname());		
		setupCommonValues(data,imageFile,resultfile);
		String[] database = data.getDatabase().split(",");
		HashMap<String, Boolean> db = new DatasourceConfig(database, data.getSpecies()).getDbConfiguration();
		List<LRPathResult> result = null;
		String rnaseq = data.getRnaseq();
		String warning ="Warning is";
				
				
		for (String dbname : db.keySet())
		{
			System.out.println("decision for database :  with dbname"+ dbname+"  spacies " + data.getSpecies() + "    "+ db.get(dbname));
			int dbFlag = 0; //1 is for warnign and 0 for done
			if (db.get(dbname))
			{
			   
			    System.out.println("dbname  =="+dbname);
				conceptNameMap = performAnalysisWithExternalDatabase(data, dbname);
				String command = performAnalysisWithExternalDatabaseReturnCommand(data,dbname);
				result = processResultsOfAnalysis(dbname, true, conceptNameMap,rnaseq,imageFile,command,resultfile );
				 System.out.println("performAnalysisWithExternalDatabase with map size = " + result.size() );
				 //System.out.println("warning"+result.get(0).getStatus());
				 if(result.size() > 0)
				 {
					 //System.out.println("warning inside"+result.get(0).getStatus());
					if(!result.get(0).getStatus().contains("Done")) 
					{
						warning = warning+ ";"+result.get(0).getStatus();
						dbFlag =1;
					}
					 
					 
				 }
				 
				/* java.util.Iterator<Entry<String, String>> iterator = conceptNameMap.entrySet().iterator();
					while (iterator.hasNext()) {
						Map.Entry mapEntry = (Map.Entry) iterator.next();
						System.out.println("The key is: " + mapEntry.getKey()
							+ ",value is :" + mapEntry.getValue());
					}
					*/
				 
			}
			else
			{
 
				String command= performAnalysisWithInternalDatabase(data, dbname);
				result = processResultsOfAnalysis(dbname, false, null, rnaseq,imageFile,command,resultfile );
				 System.out.println("performAnalysisWithInternalDatabase = " + result.size());
				// System.out.println("warning"+result.get(0).getStatus());
				 if(result.size() > 0)
				 {
					 //System.out.println("warning inside"+result.get(0).getStatus());
					if(!result.get(0).getStatus().contains("Done")) 
					{
						warning = warning+ ";"+result.get(0).getStatus();
						dbFlag =1;
					}
					 
					 
				 }
				 
			}
			if(dbFlag==0)
			{
				allResult.addAll(result);
			}
			else
			{
				System.out.println("its in the error warning loop dbFlag "+ result.get(0).getStatus());
			}
		}

		System.out.println("lrpath list created"+ allResult.size());
		System.out.println("warning total"+ warning);
		String rnaseq2="";
		String outFileNum="";
		if(allResult.size()> 1)
		{
					LRPathResult[] res = new LRPathResult[allResult.size()];
					//System.out.println("Rna decision is "+ allResult.get(1).getRnaseq());
					//System.out.println("filename is "+ result.get(1).getImageFilePath());
					
					
					for(int i=0; i<allResult.size(); i++)
					{
						//System.out.println("Concept id from the reults"+allResult.get(i).getStatus());
						if(allResult.get(i).getStatus().contains("Done"))
						{
						//System.out.println("Concept id from the reults inside"+allResult.get(i).getStatus());
						res[i] = allResult.get(i);
						}
						else
						{
						System.out.println("Status from else loop"+allResult.get(i).getStatus());
						}

					}
					
					System.out.println("res length is "+res.length);
					//Arrays.sort(res);
					System.out.println("status before write pogram "+  res[0].getStatus());
					
					String df = "";
					if(data.getDirection().length > 1)
					{
						df = "Direction";
					}
					else
					{
						df = "Enriched";
					}
					outFileNum = write.writeToFile(res, df);
					String val = String.valueOf(res.length);
					rnaseq2= allResult.get(1).getRnaseq();	
					//System.out.println("outFileNum from the Lrpath executor :+===" + outFileNum );
					
		}			
		else if(allResult.size() ==0 )
		{
			 rnaseq2 ="zero";
		}
		
		System.out.println("outFileNum from the Lrpath executor :+===" + outFileNum );
		
		/*
		List<LRPathResult> justResult = new LinkedList<LRPathResult>();
		
		justResult.get(0).setImageFilePath(imageFile);
		justResult.get(0).setImageFilePath(outFileNum);
		justResult.get(0).setRnaseq(allResult.get(1).getRnaseq());
		justResult.get(0).setPValue(allResult.size());
		
		return justResult;
		*/
		
		//LrpathResult is changed. Previously it used to send data to the Lrpath but now it just sends RnaEnrich decision , image and text data , and allResult  size
		//setNUmUniquegenes is used to pass the result size .
		LRPathResult justResult = new LRPathResult();
		justResult.setImageFilePath(imageFile);
		justResult.setResultFilePath(outFileNum);
		justResult.setRnaseq(rnaseq2);
		justResult.setNumUniqueGenes(allResult.size());
		justResult.setStatus(warning);
		System.out.println("warning is " + warning);		
		List<LRPathResult> resultData = new LinkedList<LRPathResult>();
		resultData.add(justResult);
		
		return resultData;
		
		
		
	}

	private void setupCommonValues(LRPathArguments data,String imageFile,String resultfile )
	{

		if (data.getIdentifiers() != null)
		{
			rserver.assignRVariable("geneids", data.getIdentifiers());
		}
		else
		{
			rserver.assignRVariable("geneids", data.getGeneids());
		}
		
		//System.out.println(" image file is :  " + imageFile + "  Result file is :="+ resultfile);
		
		rserver.assignRVariable("filepath",imageFile);
		rserver.assignRVariable("results_file",resultfile);
		rserver.assignRVariable("readcounts", data.getReadcount());
		System.out.println("Read counts from executor is " + data.getReadcount().length);
		rserver.assignRVariable("sigvals", data.getSigvals());
		rserver.assignRVariable("species", data.getSpecies());
		String rnaseq = data.getRnaseq();
		if(rnaseq.equals("yes"))
		{
			//System.out.println("from rnaseq loops with data rnaseqq " + data.getAvgread().length());			
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
			 */
			
		
			
			
			
		}
		
	}

	private Map<String, String> performAnalysisWithExternalDatabase(LRPathArguments data, String database) 
	{
		System.out.println("inside performAnalysisWithExternalDatabase");
		ConceptData conceptData = new ConceptData();
		try
		{
			System.out.println("1");
			Map<String, String> conceptNameMap = new HashMap<String, String>();
			System.out.println("2");
			String dictionaryId = conceptData.getDictionaryId(database);
			System.out.println("3");
			String taxid = Integer.toString(Species.toSpecies(data.getSpecies()).taxid());
			System.out.println("4");
			String conceptList = conceptData.getConceptListR(dictionaryId, taxid, data.getMing(), data.getMaxg());
			System.out.println("5");
			conceptNameMap = conceptData.getConceptName(dictionaryId);
			System.out.println("6");
			double[] nullsetList = conceptData.getDictionaryElementList(dictionaryId, taxid);
		
			System.out.println("1:::" + conceptList.length());
		    //System.out.println("nullsetList" + nullsetList[1]);
			String command = "";
			rserver.assignRVariable("nullsetList", nullsetList);			
			rserver.assignRVariable("conceptList", conceptList);
			rserver.voidEvalRCommand(conceptList);
			rserver.assignRVariable("direction", data.getDirection());
			System.out.println("nullsetList" + nullsetList[1]);
				
			return conceptNameMap;
		}
		catch (SQLException e)
		{
			throw new IllegalStateException("Failed SQL Command");
		}
	}
	
	
	private String performAnalysisWithExternalDatabaseReturnCommand(LRPathArguments data, String database)
	{
	
			String command = "";		
			System.out.println("inside performAnalysisWithExternalDatabaseReturnCommand");
			if (data.getDirection().length > 1)
			{
				String rnaseq = data.getRnaseq();
				if(rnaseq.equals("yes"))
				{
		//res2_kegg = rna_enrich(sigvals=sigvals, geneids=geneids, avg_readcount=avg_readcount, species='mmu', direction=direction, min.g=10, max.g=50000, sig.cutoff=0.05, database='KEGG',adj_readcount=TRUE, read_lim=5)						
					System.out.println("its in the performAnalysisWithExternalDatabase with direction loops" + data.getAvgread());
					command = "LRResults <- rna_enrich(sigvals=sigvals, geneids=geneids, avg_readcount=avg_readcount, species=species, direction, min.g=" + data.getMing() + ", max.g=" + data.getMaxg()
							+ ", sig.cutoff=" + data.getSigcutoff() +", database=\"External\",conceptList=conceptList,nullsetList=nullsetList,adj_readcount=TRUE, read_lim=5,filename=filepath,results_file=results_file)";					
				}
				else
				{				
				command = "LRResults <- LRpath(sigvals, geneids, species, direction,results_file=results_file, min.g=" + data.getMing() + ", max.g=" + data.getMaxg()
						+ ", sig.cutoff=" + data.getSigcutoff() + ", database=\"External\",conceptList=conceptList,nullsetList=nullsetList, odds.min.max=c("
						+ data.getOddsmin() + "," + data.getOddsmax() + "))";
				}
			}
			else
			{
				String rnaseq = data.getRnaseq();
				if(rnaseq.equals("yes"))
				{
					command = "LRResults <- rna_enrich(sigvals=sigvals, geneids=geneids, avg_readcount=avg_readcount, species=species, direction=NULL, min.g=" + data.getMing() + ", max.g=" + data.getMaxg()
							+ ", sig.cutoff=" + data.getSigcutoff() +", database=\"External\",conceptList=conceptList,nullsetList=nullsetList,adj_readcount=TRUE, read_lim=5,filename=filepath,results_file=results_file)";
					
				}
				else
				{
				command = "LRResults <- LRpath(sigvals, geneids, species, direction=NULL,results_file=results_file,min.g=" + data.getMing() + ", max.g="
						+ data.getMaxg() + ", sig.cutoff=" + data.getSigcutoff()
						+ ", database=\"External\", conceptList=conceptList,nullsetList=nullsetList,  odds.min.max=c(" + data.getOddsmin() + ","
						+ data.getOddsmax() + "))";
				}
			}
			System.out.println("Command is" + command);
			return command;
		
	}

	private String performAnalysisWithInternalDatabase(LRPathArguments data, String database)
	{
	    System.out.println("database is  :"+ database);
	    System.out.println("inside performAnalysisWithInternalDatabase");
	    if (database.equals("custom"))
	    {
	    	
	    	FileInputStream fstream;
			try {
				fstream = new FileInputStream(data.getCustomfile());
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String strLine;
				//String conceptList = "c(";
				String nullset = "";
				
				
				ArrayList<String> list = new ArrayList<String>();
				int i = 0,j =0;
				
				//avaoid header
				br.readLine();
				
				ArrayList<Double> nullsetList = new ArrayList<Double>();
				ArrayList<String> conceptList = new ArrayList<String>();
				
				
				while ((strLine = br.readLine()) != null)
				{
					String [] tokens=strLine.split("\t");
					//System.out.println(" tokens length = "+tokens.length);
					//conceptList += "\""+tokens[0]+"\""+",";		
					conceptList.add(tokens[0]);				
					nullsetList.add(Double.parseDouble(tokens[1]));
					
				}
				
				Double[] d = new Double[nullsetList.size()];
				nullsetList.toArray(d);
				double[] d1 = ArrayUtils.toPrimitive(d);
			  
			  
			  String[] s = new String[conceptList.size()];
				conceptList.toArray(s);
				
			    
			    	
				
				//int pos = conceptList.lastIndexOf(",");
				//conceptList = conceptList.substring(0, pos)+")";				
				//System.out.println ("arraylist ::"+ conceptList);	
					
				
				rserver.assignRVariable("nullsetList", d1);			
				rserver.assignRVariable("conceptList", s);
				
				int leg = rserver.evalRCommand("length(conceptList)").asInteger();				
				System.out.println(" int of conceptlist" + leg + " nullsetlist:=="+   rserver.evalRCommand("length(nullsetList)").asInteger());
				//rserver.parseAndEvalCommand("save.image('/home/snehal/nullset4.Rdata')");
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (REXPMismatchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	    	
	    	
	    	
	    	
	    }
	    
	    
	    
	    
	   // System.out.println("data.getDirection().length = " + data.getDirection().length);
	    //System.out.println("data.getSigvals().length = " + data.getSigvals().length);
	    String rnaseq = data.getRnaseq();
	    String command = "";
	    System.out.println("its in the performAnalysisWithInternalDatabase with direction loops : ==" + data.getRnaseq());
		if (data.getDirection().length > 1)
		{
			rserver.assignRVariable("direction", data.getDirection());
			
			if(rnaseq.equals("yes"))
			{
				if (database.equals("custom"))
				{
					command = "LRResults <- rna_enrich(sigvals=sigvals, geneids=geneids, avg_readcount=avg_readcount, species=species, conceptList=conceptList,nullsetList=nullsetList, direction, min.g=" + data.getMing() + ", max.g=" + data.getMaxg()
							+ ", sig.cutoff=" + data.getSigcutoff() + ", database=\"" + database+ "\",adj_readcount=TRUE, read_lim=5,filename=filepath,results_file=results_file)";
					
					
				}
				else
				{
				command = "LRResults <- rna_enrich(sigvals=sigvals, geneids=geneids, avg_readcount=avg_readcount, species=species, direction, min.g=" + data.getMing() + ", max.g=" + data.getMaxg()
						+ ", sig.cutoff=" + data.getSigcutoff() + ", database=\"" + database+ "\",adj_readcount=TRUE, read_lim=5,filename=filepath,results_file=results_file)";
				}
			}
			else
			{
				if (database.equals("custom"))
				{
					command ="LRResults <- LRpath(sigvals, geneids, species, direction,results_file=results_file, conceptList=conceptList,nullsetList=nullsetList, min.g=" + data.getMing() + ", max.g="
							+ data.getMaxg() + ", sig.cutoff=" + data.getSigcutoff() + ", database=\"" + database + "\", odds.min.max=c("
							+ data.getOddsmin() + "," + data.getOddsmax() + "))";
					
				}
				else{
				command ="LRResults <- LRpath(sigvals, geneids, species, direction,results_file=results_file, min.g=" + data.getMing() + ", max.g="
					+ data.getMaxg() + ", sig.cutoff=" + data.getSigcutoff() + ", database=\"" + database + "\", odds.min.max=c("
					+ data.getOddsmin() + "," + data.getOddsmax() + "))";
				}
			}
		}
		else
		{
			

			if(rnaseq.equals("yes"))
			{
				if (database.equals("custom"))
				{
					command = "LRResults <- rna_enrich(sigvals=sigvals, geneids=geneids, avg_readcount=avg_readcount, species=species, conceptList=conceptList,nullsetList=nullsetList, direction=NULL, min.g=" + data.getMing() + ", max.g=" + data.getMaxg()
							+ ", sig.cutoff=" + data.getSigcutoff() + ", database=\"" + database + "\",adj_readcount=TRUE, read_lim=5,filename=filepath,results_file=results_file)";					
					System.out.println("its in the performAnalysisWithInternalDatabase with direction loops" + data.getAvgread());
					
				}
				else
				{
				command = "LRResults <- rna_enrich(sigvals=sigvals, geneids=geneids, avg_readcount=avg_readcount, species=species, direction=NULL, min.g=" + data.getMing() + ", max.g=" + data.getMaxg()
						+ ", sig.cutoff=" + data.getSigcutoff() + ", database=\"" + database + "\",adj_readcount=TRUE, read_lim=5,filename=filepath,results_file=results_file)";					
				System.out.println("its in the performAnalysisWithInternalDatabase with direction loops" + data.getAvgread());
				}
				
			}
			else
			{
				if (database.equals("custom"))
				{
					command = "LRResults <- LRpath(sigvals, geneids, species, direction=NULL,results_file=results_file, conceptList=conceptList,nullsetList=nullsetList, min.g=" + data.getMing() + ", max.g="
							+ data.getMaxg() + ", sig.cutoff=" + data.getSigcutoff() + ", database=\"" + database + "\", odds.min.max=c("
							+ data.getOddsmin() + "," + data.getOddsmax() + "))";
					
				}
				else
				{
				command = "LRResults <- LRpath(sigvals, geneids, species, direction=NULL,results_file=results_file, min.g=" + data.getMing() + ", max.g="
					+ data.getMaxg() + ", sig.cutoff=" + data.getSigcutoff() + ", database=\"" + database + "\", odds.min.max=c("
					+ data.getOddsmin() + "," + data.getOddsmax() + "))";
				}
			}
		}
		
		System.out.println("Command is" + command);
		return command;
	}

	private List<LRPathResult> processResultsOfAnalysis(String db, boolean isDbExternal, Map<String, String> conceptNameMap, String rnaseq,String imageFile,String command,String resultfile )
	{
		System.out.println("inside processResultsOfAnalysis");
			String resultFromR = "test";
			try {
				resultFromR = rserver.parseAndEvalCommand(command);
				
			} catch (REngineException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (REXPMismatchException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			System.out.println("Inside processResultsOfAnalysis  " + resultfile);
			System.out.println("Result FromR is " + resultFromR);
			
			
			   FileInputStream fstream;
			   List<LRPathResult> resultData = new LinkedList<LRPathResult>();
			   if(resultFromR.contains("Error:"))
					   {
				   LRPathResult result2 = new LRPathResult();
				   result2.setStatus(resultFromR);
					
					resultData.add(result2);
				   
					   }
			   else
			   {
			   
					try {
						
						fstream = new FileInputStream(resultfile);
						DataInputStream in = new DataInputStream(fstream);
						BufferedReader br = new BufferedReader(new InputStreamReader(in));
					   	String strLine;
					   	int i=1;
					   	br.readLine();
						        			//System.out.println("4"+strLine);
								    		//***************************************************************************************************************************
				    		if(rnaseq.equals("yes"))
							{
				    			
				    			
				    			
					    			while ((strLine = br.readLine()) != null)
									{
								      		if(!strLine.startsWith("Concept.ID"))
								      		{
				    				         String tokens[] = strLine.split("\t");
								      		// System.out.println("its in RNA-Enrich+ tokens"+ tokens.length);						      		
											/*	
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
												*/
																
												LRPathResult result = new LRPathResult();
												result.setConceptId(tokens[0]);
												result.setConceptType(db);
												result.setNumUniqueGenes(Integer.parseInt(tokens[3].replaceAll("NA", "0")));
												result.setCoeff(Double.parseDouble(tokens[4].replaceAll("NA", "0")));
												result.setOddsRatio(Double.parseDouble(tokens[5].replaceAll("NA", "0")));
												result.setFdr(Double.parseDouble(tokens[8].replaceAll("NA", "0")));
												result.setPValue(Double.parseDouble(tokens[7].replaceAll("NA", "0")));
												//System.out.println("lenght is "+ tokens.length +" tokens[9].isEmpty():: " );									
											    if(tokens.length == 10)
													{		
														Vector<String> genes = new Vector<String>(Arrays.asList(tokens[9].split(",")));
														result.setSigGenes(genes);
													}	
											    else
											    {
											    	Vector<String> genes =new Vector<String>(10,2);
											    	genes.add("N/A");
											    	result.setSigGenes(genes);
											    	
											    	
											    }
												result.setImageFilePath(imageFile);											
												result.setResultFilePath(resultfile);
												result.setRnaseq("yes");
												String status = db+":"+resultFromR;
												result.setStatus(status);
												
												String conceptId = tokens[0];
												if (isDbExternal)
												{
													result.setConceptName(conceptNameMap.get(conceptId));
													//System.out.println("concept name from external" + conceptNameMap.get(conceptId) + "concept id:" + conceptId );
												}
												else
												{
													result.setConceptName(tokens[1]);
													//System.out.println("concept name from internal" + tokens[1] );
												}	
												resultData.add(result);
									      		}
									}//While
					    			
								}//Yes rnaseq
				    		
				    		
				    		else{
									System.out.println("its LRpath");								
									if(db.contains("GO"))
									{
										//System.out.println("Its database from LRpath GO loop"+ db );
										while ((strLine = br.readLine()) != null)
										{
											if(!strLine.startsWith("Concept.ID"))
									      		{
										      	String tokens[] = strLine.split("\t");
										      //	System.out.println("its in Go LRpath+ tokens"+ tokens.length);
												 /*
												 conceptId = ((REXP) list.get(0)).asStrings();
												 conceptName = ((REXP) list.get(1)).asStrings();
												 database = ((REXP) list.get(2)).asStrings();
												 numUniqueGenes = ((REXP) list.get(3)).asStrings();
												 coeff = ((REXP) list.get(4)).asStrings();
												 oddsRatio = ((REXP) list.get(5)).asStrings();
												 pvalue = ((REXP) list.get(6)).asStrings();
												 fdr = ((REXP) list.get(7)).asStrings();
												 sigGenes = ((REXP) list.get(8)).asStrings();*/
												 	LRPathResult result = new LRPathResult();
												 	result.setConceptId(tokens[0]);										 	
												 	result.setConceptType(db);
												 	//System.out.println("numUniuqGenes"+ tokens[3]);
													result.setNumUniqueGenes(Integer.parseInt(tokens[3].replaceAll("NA", "0")));
													result.setCoeff(Double.parseDouble(tokens[4].replaceAll("NA", "0")));
													result.setOddsRatio(Double.parseDouble(tokens[5].replaceAll("NA", "0")));
													result.setFdr(Double.parseDouble(tokens[7].replaceAll("NA", "0")));
													result.setPValue(Double.parseDouble(tokens[6].replaceAll("NA", "0")));
													if(tokens.length == 9)
													{
													Vector<String> genes = new Vector<String>(Arrays.asList(tokens[8].split(",")));
													result.setSigGenes(genes);
													}
													else
												    {
												    	Vector<String> genes =new Vector<String>(9,2);
												    	genes.add("N/A");
												    	result.setSigGenes(genes);							    	
												    	
												    }
													result.setImageFilePath(imageFile);											
													result.setResultFilePath(resultfile);
													result.setRnaseq("no");
													String status = db+":"+resultFromR;
													result.setStatus(status);
													
													String conceptname = tokens[1];
													if (isDbExternal)
													{
														result.setConceptName(conceptNameMap.get(conceptname));
														//System.out.println("its in Go LRpath" + conceptNameMap.get(conceptname));
													}
													else
													{
														result.setConceptName(conceptname);
													}
													resultData.add(result);
										}
										}
									}//Database Go
									if(db.contains("custom"))
									{
										System.out.println("Its in LRpath custom loop" );
										while ((strLine = br.readLine()) != null)
										{
											if(!strLine.startsWith("Concept.ID"))
									      		{
										      	String tokens[] = strLine.split("\t");
										      	//System.out.println("Its database from LRpath custom loop"+ db );
												 /*
												 conceptId = ((REXP) list.get(0)).asStrings();
												 conceptName = ((REXP) list.get(1)).asStrings();
												 database = ((REXP) list.get(2)).asStrings();
												 numUniqueGenes = ((REXP) list.get(3)).asStrings();
												 coeff = ((REXP) list.get(4)).asStrings();
												 oddsRatio = ((REXP) list.get(5)).asStrings();
												 pvalue = ((REXP) list.get(6)).asStrings();
												 fdr = ((REXP) list.get(7)).asStrings();
												 sigGenes = ((REXP) list.get(8)).asStrings();*/
												 	LRPathResult result = new LRPathResult();
												 	result.setConceptId(tokens[0]);										 	
												 	result.setConceptType(db);
												 	//System.out.println("numUniuqGenes"+ tokens[1]);
													result.setNumUniqueGenes(Integer.parseInt(tokens[2].replaceAll("NA", "0")));
													result.setCoeff(Double.parseDouble(tokens[3].replaceAll("NA", "0")));
													result.setOddsRatio(Double.parseDouble(tokens[4].replaceAll("NA", "0")));
													result.setFdr(Double.parseDouble(tokens[6].replaceAll("NA", "0")));
													result.setPValue(Double.parseDouble(tokens[5].replaceAll("NA", "0")));
													if(tokens.length == 8)
													{
													Vector<String> genes = new Vector<String>(Arrays.asList(tokens[7].split(",")));
													result.setSigGenes(genes);
													}
													else
												    {
												    	Vector<String> genes =new Vector<String>(9,2);
												    	genes.add("N/A");
												    	result.setSigGenes(genes);							    	
												    	
												    }
													result.setImageFilePath(imageFile);											
													result.setResultFilePath(resultfile);
													result.setRnaseq("no");
													String status = db+":"+resultFromR;
													result.setStatus(status);
													
													
													String conceptname = tokens[1];
													if (isDbExternal)
													{
														result.setConceptName(conceptNameMap.get(conceptname));
														//System.out.println("its in Go LRpath" + conceptNameMap.get(conceptname));
													}
													else
													{
														result.setConceptName(conceptname);
													}
													resultData.add(result);
										}
										}
									}//Database custom
										else{
											while ((strLine = br.readLine()) != null)
											{
											 
											  // System.out.println("its in other LRpath+ tokens"+  strLine);
													if(!strLine.startsWith("Concept.ID"))
										      		{
														String tokens[] = strLine.split("\t");
														//System.out.println("Its database from LRpath other loop"+ db + "line is " +  strLine);
														//System.out.println("its in Go LRpath+ tokens"+ tokens.length);
														//System.out.println("its in other loops of Lrpath with length" +  tokens.length);
														/*
														conceptId = ((REXP) list.get(0)).asStrings();
														 conceptName = ((REXP) list.get(1)).asStrings();					
														 numUniqueGenes = ((REXP) list.get(2)).asStrings();
														 coeff = ((REXP) list.get(3)).asStrings();
														 oddsRatio = ((REXP) list.get(4)).asStrings();
														 pvalue = ((REXP) list.get(5)).asStrings();
														 fdr = ((REXP) list.get(6)).asStrings();
														 sigGenes = ((REXP) list.get(7)).asStrings(); */
														 	
														    LRPathResult result = new LRPathResult();
														 	result.setConceptId(tokens[0]);
														 	String conceptname = tokens[1];
														 	result.setConceptType(db);
															//System.out.println("tokens[2] =="+tokens[2]);
															result.setNumUniqueGenes(Integer.parseInt(tokens[2].replaceAll("NA", "0").replaceAll("n.genes", "0")));
															result.setCoeff(Double.parseDouble(tokens[3].replaceAll("NA", "0").replaceAll("coeff", "0")));
															result.setOddsRatio(Double.parseDouble(tokens[4].replaceAll("NA", "0")));
															result.setFdr(Double.parseDouble(tokens[6].replaceAll("NA", "0")));
															result.setPValue(Double.parseDouble(tokens[5].replaceAll("NA", "0")));
															if(tokens.length == 8)
															{
															Vector<String> genes = new Vector<String>(Arrays.asList(tokens[7].split(",")));
															result.setSigGenes(genes);
															}
															else
														    {
														    	Vector<String> genes =new Vector<String>(9,2);
														    	genes.add("N/A");
														    	result.setSigGenes(genes);
														    }
															result.setImageFilePath(imageFile);											
															result.setResultFilePath(resultfile);
															result.setRnaseq("no");	
															String status = db+":"+resultFromR;
															result.setStatus(status);
															
															
															if (isDbExternal)
															{
																result.setConceptName(conceptNameMap.get(conceptname));
																//System.out.println("its in Go LRpath" + conceptNameMap.get(conceptname));
															}
															else
															{
																result.setConceptName(conceptname);
															}										
															resultData.add(result);
										      		}
													else
													{
														System.out.println("Line is blank");
													}
												}									
											
											}
								    		
				    			}    	//For else	
				    		}//For try
					
								    		
								    		
								    		//****************************************************************************************************************************
							
					            catch (FileNotFoundException e1) {
					        		e1.printStackTrace(); 
					        		} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
					
			   }//finish else 
			   //System.out.println("at the end" +resultData.get(0).getStatus());
				    return resultData;
		    }
}
