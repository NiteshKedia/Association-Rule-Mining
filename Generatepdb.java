import java.io.*;

class Generatepdb
{
	PrintWriter pw;
	Generatepdb(PrintWriter pw)
	{
		this.pw=pw;
	}
	
	void generate(int num)
	{
		char c;
		int r,count;
		for(int i=0;i<num;i++)
		{
			count=0;
			for(int j=0;j<FPAlgo.numsub;j++)
			{
				r=(int)(Math.random()*100);
				c=(char)((int)(Math.random()*6)+'A');
				pw.print(c);
			}
			pw.println();
		}
	}
	
	public static void main(String arg[])throws Exception
	{
		PrintWriter pw=new PrintWriter(new OutputStreamWriter(new FileOutputStream("presentdb.txt")),true);
		new Generatepdb(pw).generate(200);
	}
}