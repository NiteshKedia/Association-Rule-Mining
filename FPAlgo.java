import java.io.*;

class FPAlgo
{
	
	final static int numsub=7,numgrade=6;
	final static float tsup=2f;
	int supp,supf;
	float conp,conf;
	PrintWriter pw;
	BufferedReader br;
	
	FPAlgo()
	{
		try
		{
			pw=new PrintWriter(new OutputStreamWriter(new FileOutputStream("itemset.txt")),true);
			br=new BufferedReader(new FileReader("database.txt"));
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	String reverse(String st)
	{
		String r="";
		int len=st.length()-1;
		while(len>=0)
		{
			r+=st.charAt(len);
			len--;
		}
		return r;
	}
	
	void produce(FPTree tree,String suf)
	{
		String suf1="";
		Node li,f;
		float cp,cf;
		for(int i=0;i<numsub;i++)
			for(int j=0;j<numgrade;j++)
			{
				FPTree nt=new FPTree();
				suf1=(char)(i+'1')+""+(char)(j+'A')+suf;
				cp=tree.arr[i][j][0].count+tree.arr[i][j][1].count;
				if(cp>0)
					cp=tree.arr[i][j][0].count*100/cp;
				cf=100-cp;
				if((tree.arr[i][j][0].count>=supp&&cp>=conp)||(tree.arr[i][j][1].count>=supf&&cf>=conf))
				{
					li=tree.arr[i][j][0].next;
					while(li!=null)
					{
						String st="";
						f=li.fat;
						while(f!=tree.root)
						{
							st+=f.data;
							f=f.fat;
						}
						if(!st.equals(""))
						{
							st=reverse(st);
							for(int x=0;x<li.count;x++)
								nt.insert(st,true);
						}
						li=li.next;
					}
					
					li=tree.arr[i][j][1].next;
					while(li!=null)
					{
						String st="";
						f=li.fat;
						while(f!=tree.root)
						{
							st+=f.data;
							f=f.fat;
						}
						if(!st.equals(""))
						{
							st=reverse(st);
							for(int x=0;x<li.count;x++)
								nt.insert(st,false);
						}
						li=li.next;
					}
					if(tree.arr[i][j][0].count>=supp&&cp>=conp)
						pw.println(suf1+"P"+" "+tree.arr[i][j][0].count+" "+cp);	
					else
						pw.println(suf1+"F"+" "+tree.arr[i][j][1].count+" "+cf);	
					produce(nt,suf1);
				}
			}
	}
	
	void mine(FPTree tree)
	{
		String suf="";
		Node li,f;
		float cp,cf;
		for(int i=0;i<numsub;i++)
			for(int j=0;j<numgrade;j++)
			{
				FPTree nt=new FPTree();
				suf=(char)(i+'1')+""+(char)(j+'A');
				cp=tree.arr[i][j][0].count+tree.arr[i][j][1].count;
				if(cp>0)
					cp=tree.arr[i][j][0].count*100/cp;
				cf=100-cp;
				if((tree.arr[i][j][0].count>=supp&&cp>=conp)||(tree.arr[i][j][1].count>=supf&&cf>=conf))
				{
					li=tree.arr[i][j][0].next;
					while(li!=null)
					{
						String st="";
						f=li.fat;
						while(f!=tree.root)
						{
							st+=f.data;
							f=f.fat;
						}
						if(!st.equals(""))
						{
							st=reverse(st);
							for(int x=0;x<li.count;x++)
								nt.insert(st,true);
						}
						li=li.next;
					}
					
					li=tree.arr[i][j][1].next;
					while(li!=null)
					{
						String st="";
						f=li.fat;
						while(f!=tree.root)
						{
							st+=f.data;
							f=f.fat;
						}
						if(!st.equals(""))
						{
							st=reverse(st);
							for(int x=0;x<li.count;x++)
								nt.insert(st,false);
						}
						li=li.next;
					}
					if(tree.arr[i][j][0].count>=supp&&cp>=conp)
						pw.println(suf+"P"+" "+tree.arr[i][j][0].count+" "+cp);	
					else
						pw.println(suf+"F"+" "+tree.arr[i][j][1].count+" "+cf);	
					produce(nt,suf);
				}
			}
	}
	
	FPTree formFPTree()
	{
		int p=0,f=0;
		
		FPTree fp=new FPTree();
		try
		{	
			String s,sa[];
			while((s=br.readLine())!=null)
			{
				sa=s.split(" ");
				if(sa[1].charAt(0)=='P')
				{
					p++;
					fp.insert(sa[0],true);
				}
				else
				{
					f++;
					fp.insert(sa[0],false);
				}	
			}
			conf=p+f;
			conp=p*100/conf;
			conf=100-conp;
			supp=(int)(p*tsup/100);
			supf=(int)(f*tsup/100);;
			//System.out.println(supp+" "+supf+" "+conp+" "+conf);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return fp;
	}
	
	public static void main(String[] arg)
	{
		FPAlgo fpa=new FPAlgo();
		//System.out.println(fpa.formFPTree().arr[1][0][1].count);
		
		
		fpa.mine(fpa.formFPTree());
		Scorer sr=new Scorer();
		sr.filldata();
		sr.score();
		sr.display(sr.head);
	}
	
}

class FPTree
{
	Node root;
	Node arr[][][],end[][][];
	
	
	FPTree()
	{
		root=new Node();
		arr=new Node[FPAlgo.numsub][FPAlgo.numgrade][2];
		for(int i=0;i<FPAlgo.numsub;i++)
			for(int j=0;j<FPAlgo.numgrade;j++)
			{
				arr[i][j][0]=new Node();
				arr[i][j][1]=new Node();
			}
	}
	
	void traverse(Node tr,int lev)
	{
		Node s=tr.son;
		while(s!=null)
		{
			traverse(s,lev+1);
			s=s.sib;
		}
		System.out.println(lev+""+tr.data+" "+tr.count);
	}
	
	void insert(String str,boolean p)
	{
		int len=str.length();
		Node k=root,s,r;
		char c;
		int in=p==true?0:1;
		for(int i=0;i<len;i++)
		{
			c=str.charAt(i);
			s=k.son;
			while(s!=null&&(s.data!=c||s.pass!=p))
				s=s.sib;
			if(s==null)
			{
				s=new Node(c,p);
				s.next=arr[i][c-'A'][in].next;
				arr[i][c-'A'][in].next=s;
				s.fat=k;
				s.sib=k.son;
				k.son=s;
			}
			arr[i][c-'A'][in].count++;
			s.count++;
			k=s;
		}
	}
}

class Node
{
	Node son,sib,fat,next;
	char data;
	int count;
	boolean pass;
	Node()
	{
	}
	Node(char d,boolean p)
	{
		pass=p;
		data=d;
	}
}

class Scorer
{
	Nod head;
	PrintWriter pw;
	
	Scorer()
	{
		try
		{
			pw=new PrintWriter(new OutputStreamWriter(new FileOutputStream("result.txt")),true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	void filldata()
	{
		try
		{
			BufferedReader br=new BufferedReader(new FileReader("presentdb.txt"));
			String st;
			head=new Nod();
			while((st=br.readLine())!=null)
				insert(st);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	void insert(String str)
	{
		int len=str.length();
		Nod s=head.son,f=head;
		for(int i=0;i<len;i++)
		{
			while(s!=null)
			{
				if(s.data==str.charAt(i))
					break;
				s=s.sibling;
			}
			if(s==null)
			{
				Nod n;
				for(int j=i;j<len;j++)
				{
					n=new Nod(str.charAt(j));
					n.sibling=f.son;
					f.son=n;
					f=n;
				}
				break;
			}
			f=s;
			s=f.son;
		}
		f.lf=new Leaf();
	}
	
	void scoreitem(int lev,String st,Nod tr,float con,int sup)//lev=1
	{
		char c=st.charAt(0);
		if(tr.lf!=null)
		{
			if(c=='P')
			{
				tr.lf.WN+=con*sup/3;
				tr.lf.N+=con*sup*(100-con)/3;
			}
			else
			{
				tr.lf.WP+=con*sup;
				tr.lf.P+=con*sup*con;
			}
			return;
		}
		if(lev!=c-'0')
		{
			Nod s=tr.son;
			while(s!=null)
			{
				scoreitem(lev+1,st,s,con,sup);
				s=s.sibling;
			}
		}
		else
		{
			c=st.charAt(1);
			Nod s=tr.son;
			while(s!=null&&s.data!=c)
				s=s.sibling;
			if(s!=null)
				scoreitem(lev+1,st.substring(2),s,con,sup);
		}
	}
	
	char arr[]=new char[15];
	int in;
	void display(Nod tr)
	{
		int count;
		if(tr.lf!=null)
		{
			count=0;
			for(int j=0;j<in;j++)
			{
				pw.print(arr[j]);
				count+=10-(arr[j]-'A');
			}
			if((tr.lf.WP+tr.lf.WN)!=0)
				pw.println(" "+count/in+" "+((tr.lf.P+tr.lf.N)/(tr.lf.WP+tr.lf.WN)));
			else
				pw.println(" 0");
			return;
		}
		Nod s=tr.son;
		while(s!=null)
		{
			arr[in++]=s.data;
			display(s);
			in--;
			s=s.sibling;
		}
	}
	
	void score()
	{
		try
		{
			BufferedReader br=new BufferedReader(new FileReader("itemset.txt"));
			String st,sa[];
			while((st=br.readLine())!=null)
			{
				sa=st.split(" ");
				scoreitem(1,sa[0],head,Float.parseFloat(sa[2]),Integer.parseInt(sa[1]));
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
}

class Nod
{
	Nod(char c)
	{
		data=c;
	}
	Nod()
	{
	}
	char data;
	Nod son,sibling;
	Leaf lf;
}

class Leaf
{
	float P,N,WP,WN;
}
