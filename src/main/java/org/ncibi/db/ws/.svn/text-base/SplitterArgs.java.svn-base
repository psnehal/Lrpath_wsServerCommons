package org.ncibi.db.ws;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "ws.Splitter")
@Table(name = "Splitter")
public class SplitterArgs
{
    private int id;
    private String uuid; 
    private String sentences;

    @Id
    @Column(name="id", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
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

    @Column(name = "sentences")
    public String getSentences()
    {
        return this.sentences;
    }

    public void setSentences(String sentences)
    {
        this.sentences = sentences;
    }

    @Override
    public String toString()
    {
        return "SplitterArgs [id=" + id + ", uuid=" + uuid + ", sentences=" + sentences + "]";
    }
}
