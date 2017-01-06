package org.ncibi.lrpath;

import java.util.Arrays;

public enum Species
{
    HUMAN(9606, "hsa"), MOUSE(10090, "mmu"), RAT(10116, "rno"), DROSOPHILA_MELANOGASTER(-1, "dme"), ZEBRAFISH(
            -1, "dre"), CELEGANS(-1, "cel"), YEAST(-1, "sce");

    private final int taxid;
    private final String keggName;

    private Species(int taxid, String keggName)
    {
        this.taxid = taxid;
        this.keggName = keggName;
    }

    public int taxid()
    {
        return this.taxid;
    }

    public String keggName()
    {
        return this.keggName;
    }

    public static Species toSpecies(String keggName)
    {
    	 System.out.println("inside function of Species"+ keggName);
        for (Species s : Species.values())
        {
            if (s.keggName.equalsIgnoreCase(keggName))
            {
            	 System.out.println("inside loop of Species");
                return s;
            }
        }

        throw new IllegalArgumentException("Unknown keggName: " + keggName);
    }
}
