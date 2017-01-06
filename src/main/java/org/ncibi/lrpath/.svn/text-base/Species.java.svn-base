package org.ncibi.lrpath;

public enum Species
{
    HUMAN(9606, "hsa"), MOUSE(10090, "mmu"), RAT(10116, "rno"), DROSOPHILA_MELANOGASTER(-1, "dm"), ZEBRAFISH(
            -1, "dr"), CELEGANS(-1, "ce"), YEAST(-1, "sc");

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
        for (Species s : Species.values())
        {
            if (s.keggName.equalsIgnoreCase(keggName))
            {
                return s;
            }
        }

        throw new IllegalArgumentException("Unknown keggName: " + keggName);
    }
}
