import java.io.*;
import java.util.*;

public class B {
    static final String filename = "B";

    public static void main(String[] args) throws IOException {
        String finput = filename+".in";
        String foutput = filename+".out";
        Scanner s = new Scanner(new FileInputStream(finput));
        Writer w = new OutputStreamWriter(new FileOutputStream(foutput));
        B solver = new B();
        solver.solve(s, w);
        w.close();
        s.close();
    }
    void solve(Scanner s, Writer w) throws IOException {
        int T = s.nextInt();
        for (int t = 1; t <= T; t++) {
            N=s.nextInt();
            G=new boolean[N+1][N+1];
            for(int i=0;i<N-1;i++){
                int x=s.nextInt();
                int y=s.nextInt();
                G[x][y]=true;
                G[y][x]=true;
            }
            String ans=solve();
            String out="Case #" + t + ": ";
            out+=ans;
            out+="\n";
            w.write(out);
            System.out.print(out);
        }
    }
    int N;
    boolean[][]G;
    int[][]counts;
    int[][]manys;
    String solve(){
        int ans=Integer.MAX_VALUE;
        counts=new int[N+1][N+1];
        manys=new int[N+1][N+1];
        for(int i=0;i<=N;i++)for(int j=0;j<=N;j++){
            counts[i][j]=-1;
            manys[i][j]=-1;
        }
        for(int i=1;i<=N;i++)for(int j=0;j<=N;j++)if(i!=j&&G[i][j])
            count(i,j);

        for(int i=1;i<=N;i++){
            int tmp=howMany(i,0);
            ans=Math.min(ans,tmp);
        }
        return ans+"";
    }
    //Determine the minimum number of nodes that needs to be cut off from n having p as parent to make it full binary tree
    int howMany(int n, int p){
        if(manys[n][p]>=0)return manys[n][p];
        List<Integer>list=new ArrayList<Integer>();
        for(int i=1;i<=N;i++)if(i!=n&&i!=p&&G[n][i])
            list.add(i);
        int best=Integer.MAX_VALUE;
        if(list.size()==0)
            best=0;
        else
            if(list.size()==1)
                best=count(list.get(0),n);
            else
                for(int i=0;i<list.size();i++)for(int j=i+1;j<list.size();j++){
                    int curr=0;
                    curr+=howMany(list.get(i),n);
                    curr+=howMany(list.get(j),n);
                    for(int k=0;k<list.size();k++)if(k!=i&&k!=j)
                        curr+=count(list.get(k),n);
                    best=Math.min(best,curr);
                }
        manys[n][p]=best;
        return best;
    }
    //Count the number of nodes starting at n having p as parent/ancestor
    int count(int n, int p){
        if(counts[n][p]>=0)return counts[n][p];
        int cnt=1;
        for(int i=1;i<=N;i++)if(i!=n&&i!=p&&G[n][i])
            cnt+=count(i,n);
        counts[n][p]=cnt;
        return cnt;
    }

}