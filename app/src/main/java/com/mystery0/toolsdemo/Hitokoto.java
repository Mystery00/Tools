package com.mystery0.toolsdemo;

/**
 * Created by myste.
 */

public class Hitokoto
{
	private String content;
	private String source;

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getSource()
	{
		return source;
	}

	public void setSource(String source)
	{
		this.source = source;
	}

	@Override
	public String toString()
	{
		return "Hitokoto{" +
				"content='" + content + '\'' +
				", source='" + source + '\'' +
				'}';
	}
}
