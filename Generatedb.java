import java.io.*;

class Generatedb
{
	PrintWriter pw;
	Generatedb(PrintWriter pw)
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
				if(r>=90)
				{
					c='A';
					count+=10;
				}
				else if(r>=70)
				{
					c='B';
					count+=9;
				}
				else if(r>=40)
				{
					c='C';
					count+=8;
				}
				else if(r>=20)
				{
					c='D';
					count+=7;
				}
				else if(r>=10)
				{
					c='E';
					count+=6;
				}
				else
				{
					c='F';
					count+=5;
				}
				pw.print(c);
			}
			
			if(count/FPAlgo.numsub>=7)
			{
				r=(int)(Math.random()*10);
				if(r>=2)
					pw.println(" P");
				else
					pw.println(" F");
			}
			else
			{
				r=(int)(Math.random()*10);
				if(r>=2)
					pw.println(" F");
				else
					pw.println(" P");
			}
		}
	}
	
	public static void main(String arg[])throws Exception
	{
		PrintWriter pw=new PrintWriter(new OutputStreamWriter(new FileOutputStream("database.txt")),true);
		new Generatedb(pw).generate(200000);
	}
}