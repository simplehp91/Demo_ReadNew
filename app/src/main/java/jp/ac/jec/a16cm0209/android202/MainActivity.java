package jp.ac.jec.a16cm0209.android202;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ReadRSSAdapter readRSSAdapter;
    ArrayList<ReadRSS>arrayNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        arrayNew = new ArrayList<ReadRSS>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadData().execute("http://vnexpress.net/rss/the-gioi.rss");
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(MainActivity.this, WebActivity.class);
                        intent.putExtra("link", arrayNew.get(i).link);
                        startActivity(intent);
                    }
                });
            }
        });
    }
    class ReadData extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... strings) {
            return ReadData_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeListdescription = document.getElementsByTagName("description");
//            String image = "";
//            String title = "";
//            String link = "";
            for (int i = 0; i < nodeList.getLength(); i++){
                String cdata = nodeListdescription.item(i + 1).getTextContent();
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher = p.matcher(cdata);
                String image = "";
                if (matcher.find()){
                    image = matcher.group(1);
                }
                Element element = (Element) nodeList.item(i);

                String title = parser.getValue(element, "title");
                String link = parser.getValue(element, "link");
                arrayNew.add(new ReadRSS(title, link, image));
            }
            readRSSAdapter = new ReadRSSAdapter(MainActivity.this, android.R.layout.simple_list_item_1, arrayNew);
            listView.setAdapter(readRSSAdapter);
            super.onPostExecute(s);
        }
    }
    private static String ReadData_URL(String theUrl)
    {
        StringBuilder content = new StringBuilder();

        try
        {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }
}
