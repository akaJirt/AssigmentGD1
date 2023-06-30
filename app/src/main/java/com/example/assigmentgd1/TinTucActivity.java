package com.example.assigmentgd1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.assigmentgd1.database.XMLDOMParser;
import com.example.assigmentgd1.model.TinTuc;
import com.squareup.picasso.Picasso;

import org.apache.commons.text.StringEscapeUtils;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class TinTucActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    ProgressBar progressBar;

    private ArrayList<TinTuc> arrTinTuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tin_tuc);
        ListView lstTieuDe = findViewById(R.id.listViewNews);
        progressBar = findViewById(R.id.progressBar);

        arrTinTuc = new ArrayList<>();

        progressBar.setVisibility(View.VISIBLE);

        new ReadRSS().execute("https://vnexpress.net/rss/giao-duc.rss");

        adapter = new ArrayAdapter<TinTuc>(this, R.layout.item_listview_tintuc, arrTinTuc) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = convertView;
                if (view == null) {
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater.inflate(R.layout.item_listview_tintuc, null);
                }

                TinTuc listItem = arrTinTuc.get(position);

                TextView textViewTitle = view.findViewById(R.id.textViewTitle);
                TextView textViewDescription = view.findViewById(R.id.textViewDescription);
                TextView textViewPubDate = view.findViewById(R.id.textViewPubDate);
                TextView textViewLink = view.findViewById(R.id.textViewLink);
                ImageView imageView = view.findViewById(R.id.imageView);

                textViewTitle.setText(listItem.getTitle());
                textViewDescription.setText(listItem.getDescription());
                textViewPubDate.setText(listItem.getPubDate());
                textViewLink.setText(listItem.getLink());

                String url = listItem.getImageURL();
                if (url != null && !url.isEmpty()) {
                    Picasso.get().load(url).into(imageView);
                } else {
                    // Xử lý khi không có đường dẫn hình ảnh
                    // Ví dụ: Đặt một hình ảnh mặc định cho imageView
                    imageView.setImageResource(R.drawable.ic_teacher);
                }

                return view;
            }

        };

        lstTieuDe.setAdapter(adapter);

        lstTieuDe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TinTuc tinTuc = arrTinTuc.get(position);
                Intent intent = new Intent(TinTucActivity.this, WebViewActivity.class);
                intent.putExtra("linkNews", tinTuc.getLink());
                startActivity(intent);
            }
        });

    }

    private class ReadRSS extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String title = parser.getValue(element, "title");
                String pubDate = parser.getValue(element, "pubDate");
                String link = parser.getValue(element, "link");
                String description = getCharacterDataFromElement(element, "description");

//                // Trích xuất imageURL từ description
                String imageURL = "";
                int index = description.indexOf("src=");
                if (index != -1) {
                    imageURL = description.substring(index + 5);
                    int index2 = imageURL.indexOf("\"");
                    imageURL = imageURL.substring(0, index2);
                }

                description = description.substring(description.indexOf("</br>") + 5);

                description = StringEscapeUtils.unescapeHtml4(description);
                description = Html.fromHtml(description).toString();

                arrTinTuc.add(new TinTuc(title, description, pubDate, link, imageURL));
            }

            adapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        }

    }

    public String getCharacterDataFromElement(Element e, String str) {
        NodeList n = e.getElementsByTagName(str);
        Element e1 = (Element) n.item(0);

        Node child = e1.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }

}