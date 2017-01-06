package org.ncibi.db.ws;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "ws.ChipEnrichUrlLinkName")
@Table(name = "ChipEnrichUrlLinkName")
public class ChipEnrichUrlLinkName
{
    private int id;
    private String name;
    private String uuid; 
    private String email;
    
     public ChipEnrichUrlLinkName()
     {
    	 
     }
     
     public ChipEnrichUrlLinkName(int id, String uuid, String name, String email)
     {
    	 this.id = id;
    	 this.uuid =uuid;
    	 this.name=name;
    	 this.email=email;
     }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Column(name = "name")
    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Column(name = "uuid")
    public String getUuid()
    {
        return this.uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }
    @Column(name = "email")
    public String getEmail()
    {
        return this.email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "ChipEnrichUrlLinkName [id=" + this.id + ", uuid=" + this.uuid + ", name="
                    + this.name + ", email=" + this.email+ "]";
    }
}
