package com.example.eisebook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class Navigation extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;

    TextView course, subject, logout;
    ListView booksListView;

    JSONObject books;

    ArrayList<String> subjects = new ArrayList<>();
    ArrayList<Book> bookArrayList = new ArrayList<>();
    BookAdapter bookAdapter;

    int currentSubjectPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        bookAdapter = new BookAdapter(bookArrayList,Navigation.this);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar);

        this.course = (TextView) findViewById(R.id.course);
        this.subject = (TextView) findViewById(R.id.subject);
        this.logout = (TextView) findViewById(R.id.logout);

        booksListView = (ListView) findViewById(R.id.booksListView);

        booksListView.setAdapter(bookAdapter);

        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                showToastMessage("Please wait!");
                startActivity(new Intent(Navigation.this, Pdf.class).putExtra("isbn",bookArrayList.get(i).isbn));

            }
        });

        toolbar.setTitle("EIS Ebooks");
        setSupportActionBar(toolbar);

        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String[] courses = getResources().getStringArray(R.array.courses);

                AlertDialog.Builder builder = new AlertDialog.Builder(Navigation.this);
                builder.setTitle("Pick a Semester");
                builder.setItems(courses, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        course.setText(courses[which]);
                        getSubjects();
                        getBooks();

                    }
                });
                builder.show();
            }
        });

        subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSubjects();

                AlertDialog.Builder builder = new AlertDialog.Builder(Navigation.this);
                builder.setTitle("Pick a Subject");
                builder.setItems(subjects.toArray(new String[0]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        subject.setText(subjects.get(which));
                        currentSubjectPosition = which;
                        getBooks();
                    }
                });
                builder.show();

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Navigation.this, Splash.class);
                startActivity(intent);
                finish();

            }
        });

        InputStream is = getResources().openRawResource(R.raw.books);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];

        try {

            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }

            is.close();

            books = new JSONObject(writer.toString());


        } catch (Exception e) {

            Log.e("Exception", e.toString());
            showToastMessage("Error Occured");

        }



        getSubjects();
        getBooks();

    }

    private void getSubjects() {

        try
        {
            JSONArray subjectsArray = books.getJSONArray(course.getText().toString().replaceAll("[^0-9]", ""));

            Log.e("subjectsArray",subjectsArray.toString());


            subjects.clear();

            for (int i = 0; i < subjectsArray.length(); i++) {

                Log.e("subjects",subjectsArray.getJSONObject(i).get("name").toString());
                subjects.add(subjectsArray.getJSONObject(i).get("name").toString());

            }

            subject.setText(subjects.get(0));

        }catch (Exception e)
        {
            Log.e("Exception", e.toString());
            showToastMessage("Error Occured");
        }
    }

    private void getBooks()
    {
        try
        {
            JSONArray subjectsArray = books.getJSONArray(course.getText().toString().replaceAll("[^0-9]", ""));
            JSONArray booksArray = subjectsArray.getJSONObject(currentSubjectPosition).getJSONArray("books");

            bookArrayList.clear();

            for (int i = 0; i < booksArray.length(); i++) {

                bookArrayList.add(new Book(booksArray.getJSONObject(i).get("title").toString(),
                        booksArray.getJSONObject(i).get("author").toString(),
                        booksArray.getJSONObject(i).get("isbn").toString(),
                        booksArray.getJSONObject(i).get("edition").toString(),
                        booksArray.getJSONObject(i).get("publisher").toString(),
                        booksArray.getJSONObject(i).get("date").toString(),
                        booksArray.getJSONObject(i).get("url").toString()));

            }

            bookAdapter.notifyDataSetChanged();

            showToastMessage(bookArrayList.size()+" books");

        }catch (Exception e)
        {
            Log.e("Exception", e.toString());
            showToastMessage("Error Occured");
        }
    }

    private void showToastMessage(String msg) {

        Toast.makeText(Navigation.this, msg, Toast.LENGTH_SHORT).show();
    }

}
