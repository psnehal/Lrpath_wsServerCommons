package org.ncibi.lrpath;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.ncibi.lrpath.LRPathResult;

public class DataWriter
{

	public String writeToFile(LRPathResult[] obj, String directionLabel)
	{
		String directionField = directionLabel;
		String directionValue1 = "enriched";
		String directionValue2 = "depleted";
		long rnum = (Math.round(Math.random() * 1000000000));
		
		String outFileName = "file" + rnum  + ".txt";
		String outFileName2 = "download" + rnum  + ".txt";
		
		String outFile = ResourceUtil.getResultsDirectoryUrl() + outFileName;
		String outFile2 = ResourceUtil.getResultsDirectoryUrl() + outFileName2;
		
		if (directionField.equals("Direction"))
		{
			directionValue1 = "up";
			directionValue2 = "down";
		}

		try
		{
			BufferedWriter out = new BufferedWriter(new FileWriter(outFile));
			BufferedWriter out2 = new BufferedWriter(new FileWriter(outFile2));

			out.write("Id" + "\t" + "Name" + "\t" + "ConceptType" + "\t" + "#Genes" + "\t" + "Coeff" + "\t" + "OddsRatio" + "\t"
					+ "P-Value" + "\t" + "FDR" + "\t" + directionField + "\t" + "SigGenes" + "\n");
			
			out2.write("Name" + "\t" + "ConceptType" + "\t" + "#Genes" + "\t" + "Coeff" + "\t" + "OddsRatio" + "\t"
					+ "P-Value" + "\t" + "FDR" + "\t" + directionField + "\t" + "SigGenes" + "\n");
			System.out.println("length from the write pro"+obj.length);
			for (int i = 0; i < obj.length; i++)
			{				
				System.out.println("00");
				//System.out.println("Ob length " + obj[i] + "is null" + obj[i].equals("null"));				
				LRPathResult lrp = (LRPathResult) obj[i];
				//sSystem.out.println("01" + lrp.getStatus());
				String direc = directionValue1;							
				String genes = lrp.getSigGenes().toString();				
				genes = genes.replaceAll("\\[", "");				
				genes = genes.replaceAll("\\]", "");	
				//System.out.println("2");
				if (lrp.getOddsRatio() < 1)
				{					
					direc = directionValue2;
				}
				//System.out.println("3");
				//System.out.println("3" + lrp.getConceptId());
				String conType=Character.toUpperCase(lrp.getConceptType().charAt(0)) + lrp.getConceptType().substring(1);
				out.write(lrp.getConceptId() + "\t" + lrp.getConceptName() + "\t" +conType + "\t" + lrp.getNumUniqueGenes()
						+ "\t" + lrp.getCoeff() + "\t" + lrp.getOddsRatio() + "\t" + lrp.getPValue() + "\t" + lrp.getFdr() + "\t" + direc
						+ "\t" + genes + "\n");
				out2.write(lrp.getConceptName() + "\t" + conType + "\t" + lrp.getNumUniqueGenes()
						+ "\t" + lrp.getCoeff() + "\t" + lrp.getOddsRatio() + "\t" + lrp.getPValue() + "\t" + lrp.getFdr() + "\t" + direc
						+ "\t" + genes + "\n");
			}
			
			out.close();
			out2.close();
			
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		
		return String.valueOf(rnum);
	}

}
