package org.ncibi.lrpath;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

final class TextParser
{
	public String createCommaDelimitedText(Vector<String> text)
	{
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < text.size(); i++)
		{
			if (i != 0)
			{
				temp.append(", " + text.get(i).toString());
			}
			else
			{
				temp.append(text.get(i).toString());
			}
		}
		return temp.toString();
	}

	public String createNewLineDelimitedText(ArrayList<String> text)
	{
		StringBuffer temp = new StringBuffer();
		for (int i = 0; i < text.size(); i++)
		{
			if (i != 0)
			{
				temp.append("\n" + text.get(i).toString().trim());
			}
			else
			{
				temp.append(text.get(i).toString().trim());
			}
		}
		return temp.toString();
	}

	public List<String> createArrayFromCommaDelimitedText(String text)
	{
		List<String> array = new ArrayList<String>();
		String[] tmp = text.split(",");

		for (int i = 0; i < tmp.length; i++)
		{
			array.add(tmp[i]);
		}

		return array;
	}

}
