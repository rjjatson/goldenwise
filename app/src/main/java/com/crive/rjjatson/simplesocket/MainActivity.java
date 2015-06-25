package com.crive.rjjatson.simplesocket;

import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class MainActivity extends AppCompatActivity {

    private static final int portNumber=17;
    private static String iPNumber="192.168.137.1";
    static TextView outText;
    static TextView statText;
    Button buttonGetQ;
    EditText ipInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
		//test
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outText = (TextView)findViewById(R.id.textView2);
        statText = (TextView)findViewById(R.id.textView);
        ipInput = (EditText) findViewById(R.id.editText);
        buttonGetQ = (Button) findViewById(R.id.button);
        buttonGetQ.setOnClickListener(buttonSendOnClickListener);
        outText.setText("What is today's Quote?");

    }

    Button.OnClickListener buttonSendOnClickListener = new Button.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {

            if(ipInput.getText().toString().matches("")) {
                Toast.makeText(getApplicationContext(),"Isi IP dulu", Toast.LENGTH_SHORT).show();
                return;
            }
            iPNumber=ipInput.getText().toString();

            new Thread(new ClientThread()).start();

        }

    };

    public class ClientThread implements Runnable
    {

        @Override
        public  void run()
        {
            Socket _socket=null;
            try
            {
                _socket = new Socket(iPNumber,portNumber);
                statText.setText("connected!");

                BufferedReader input;
                input = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
                outText.setText(input.readLine());
            }
            catch (Exception exc)
            {
                statText.setText(exc.toString());
            }
            finally
            {
                if(_socket!=null)
                {
                    try
                    {
                        _socket.close();
                    }
                    catch (Exception exc)
                    {
                        statText.setText(exc.toString());
                    }
                }

            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
