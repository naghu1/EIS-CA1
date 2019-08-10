package com.example.eisebook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.InputStream;

public class Pdf extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, OnErrorListener {

    TextView pagenumber;

    PDFView pdfView;
    Integer pageNumber = 0;
    TextView page_number, msg, error;
    int n = 0;
    Button go;
    EditText page_input;
    Dialog goToDialog;

    InputStream input = null;

    ProgressDialog progressDialog = null;
    ProgressDialog pDialog;
    File currentFile;

    String isbn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        this.pagenumber = (TextView) findViewById(R.id.page_number);

        isbn = getIntent().getStringExtra("isbn");

        progressDialog = new ProgressDialog(Pdf.this);
        progressDialog.setTitle("Message");
        progressDialog.setMessage("Loading ! Please Wait.");
        progressDialog.setCancelable(true);

        pDialog = new ProgressDialog(Pdf.this);
        pDialog.setTitle("Message");
        pDialog.setMessage("Loading");
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(true);

        goToDialog = new Dialog(Pdf.this);
        goToDialog.setTitle("Jump To Page");
        goToDialog.setContentView(R.layout.gotopage);

        pdfView = (PDFView) findViewById(R.id.pdfView);

        page_number = (TextView) findViewById(R.id.page_number);

        go = (Button) goToDialog.findViewById(R.id.go);
        msg = (TextView) goToDialog.findViewById(R.id.msg);
        error = (TextView) goToDialog.findViewById(R.id.error);
        page_input = (EditText) goToDialog.findViewById(R.id.page_input);

        page_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                error.setVisibility(View.GONE);
                page_input.setText("");
                goToDialog.show();
            }
        });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    int go_to_page = Integer.parseInt(page_input.getText().toString());
                    go_to_page--;
                    Log.e("go_to_page", go_to_page + " n " + n);

                    if (go_to_page <= n) {
                        error.setVisibility(View.GONE);
                        goToDialog.hide();
                        Log.e("flag", "A");

                        pdfView.fromAsset(isbn+".pdf")
                                .enableDoubletap(true)
                                .enableAntialiasing(true)
                                .onPageChange(Pdf.this)
                                .spacing(10)
                                .onError(Pdf.this)
                                .onLoad(Pdf.this)
                                .load();

                    } else {
                        error.setVisibility(View.VISIBLE);
                        Log.e("flag", "B");
                        Toast.makeText(Pdf.this, "Invalid Page Number", Toast.LENGTH_LONG);
                    }

                } catch (Exception e) {
                    error.setVisibility(View.VISIBLE);
                    Log.e("flag", "C");

                    Toast.makeText(Pdf.this, "Invalid Page Number", Toast.LENGTH_LONG);
                    Log.e("Exception", e.toString() + "");
                }

            }
        });


        getFile();
    }

    private void getFile() {

        progressDialog.show();


        pdfView.fromAsset(isbn+".pdf")
                .enableDoubletap(true)
                .enableAntialiasing(true)
                .onPageChange(Pdf.this)
                .spacing(10)
                .onError(Pdf.this)
                .onLoad(Pdf.this)
                .load();

    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;

        Log.e("page", page + 1 + " " + pageCount);

        page_number.setText((page + 1) + "/" + pageCount);
    }

    @Override
    public void loadComplete(int nbPages) {

        Log.e("Load Completed", nbPages + "");
        n = nbPages;
        msg.setText("Enter a page number between 1 - " + n);
        progressDialog.dismiss();

    }

    @Override
    public void onError(Throwable t) {

        Log.e("t", t.toString());

        final AlertDialog.Builder builder = new AlertDialog.Builder(Pdf.this);
        builder.setMessage("Unable to open this document").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {

                dialog.dismiss();

            }
        });
        final AlertDialog alert = builder.create();
        alert.setTitle("Message");
        alert.show();
        alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));

    }

}
