import java.net.*;
import java.io.*;

public class mangaLookUpLine implements Runnable{
	private String url;
	private String accept;
	private String exclude;
	private boolean hasUpdate=false;
	
	public mangaLookUpLine(String Url,String Accept,String Exclude){
		url=Url;
		accept=Accept;
		exclude=Exclude;
	}
	
	public boolean result(){
		return hasUpdate;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void run(){
		try{
			URL website = new URL(url);
			HttpURLConnection con = (HttpURLConnection) website.openConnection();
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			boolean hasExclude=false;
			while ((line = reader.readLine()) != null)
			{
				if(line.contains(accept)){
					String[] tokensVal = line.split("\"");
					url=tokensVal[5];
					System.out.println(url);
					hasUpdate=true;
					hasExclude=true;
					break;
				}else if(line.contains(exclude)){
					hasExclude=true;
					break;
				}
			}
			if(!hasExclude){
				System.out.println(url+" missing exclude...");
			}
			reader.close();
		}catch(Exception e){
			System.out.println("Error accsessing"+url);
		}
	}
}