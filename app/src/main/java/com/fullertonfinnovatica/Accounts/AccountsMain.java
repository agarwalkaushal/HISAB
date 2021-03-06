package com.fullertonfinnovatica.Accounts;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputFilter;
import android.text.style.TtsSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fullertonfinnovatica.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountsMain extends AppCompatActivity {

    CardView journalCard, ledgerCard, pnlCard, trialCard;
    Boolean clickedFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Accounts</font>"));

        Intent intent = getIntent();
        clickedFrom = intent.getBooleanExtra("Ratio",false);

        if(clickedFrom)
            adjustmentsDialog();

        journalCard = findViewById(R.id.journal);
        ledgerCard = findViewById(R.id.ledger);
        pnlCard = findViewById(R.id.pnl);

        trialCard = findViewById(R.id.trialBalance);

        journalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getBaseContext(), JournalRetrieve.class);
                startActivity(intent);

            }
        });

        ledgerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), Ledger.class);
                startActivity(intent);

            }
        });

        pnlCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adjustmentsDialog();

            }
        });

        trialCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), Trialbalance.class);
                startActivity(intent);

            }
        });

    }

    public void adjustmentsDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.adjustments_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText os = (EditText) dialogView.findViewById(R.id.os);
        final EditText cs = (EditText) dialogView.findViewById(R.id.cs);
        final EditText cap = (EditText) dialogView.findViewById(R.id.cap);
        final EditText bl = (EditText) dialogView.findViewById(R.id.bl);
        final EditText inv = (EditText) dialogView.findViewById(R.id.inv);
        final EditText mc = (EditText) dialogView.findViewById(R.id.mc);
        final EditText bd = (EditText) dialogView.findViewById(R.id.bd);
        final EditText mr = (EditText) dialogView.findViewById(R.id.mr);
        final EditText dd = (EditText) dialogView.findViewById(R.id.dd);
        final EditText dc = (EditText) dialogView.findViewById(R.id.dc);
        final EditText ic = (EditText) dialogView.findViewById(R.id.ic);
        final EditText ibl = (EditText) dialogView.findViewById(R.id.ibl);
        final EditText id = (EditText) dialogView.findViewById(R.id.id);
        final EditText ii = (EditText) dialogView.findViewById(R.id.ii);

        mr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    if(Integer.parseInt(mr.getText().toString())>100) {
                        mr.setError("Percentage cannnot exceed 100");
                    }
                }
            }
        });

        dd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    if(Integer.parseInt(dd.getText().toString())>100) {
                        dd.setError("Percentage cannnot exceed 100");
                    }
                }
            }
        });

        dc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    if(Integer.parseInt(dc.getText().toString())>100) {
                        dc.setError("Percentage cannnot exceed 100");
                    }
                }
            }
        });

        ic.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    if(Integer.parseInt(ic.getText().toString())>100) {
                        ic.setError("Percentage cannnot exceed 100");
                    }
                }
            }
        });

        ibl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    if(Integer.parseInt(ibl.getText().toString())>100) {
                        ibl.setError("Percentage cannnot exceed 100");
                    }
                }
            }
        });

        id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    if(Integer.parseInt(id.getText().toString())>100) {
                        id.setError("Percentage cannnot exceed 100");
                    }
                }
            }
        });

        ii.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    if(Integer.parseInt(ii.getText().toString())>100) {
                        ii.setError("Percentage cannnot exceed 100");
                    }
                }
            }
        });

        dialogBuilder.setTitle("Adjustments");

        dialogBuilder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                int c = 0;

                if(Integer.parseInt(mr.getText().toString())>100) {
                    mr.setError("Percentage cannnot exceed 100");
                    c+=1;
                }
                if(Integer.parseInt(dd.getText().toString())>100) {
                    dd.setError("Percentage cannnot exceed 100");
                    c+=1;
                }
                if(Integer.parseInt(dc.getText().toString())>100) {
                    dc.setError("Percentage cannnot exceed 100");
                    c+=1;
                }
                if(Integer.parseInt(ic.getText().toString())>100) {
                    ic.setError("Percentage cannnot exceed 100");
                    c+=1;
                }
                if(Integer.parseInt(ibl.getText().toString())>100) {
                    ibl.setError("Percentage cannnot exceed 100");
                    c+=1;
                }
                if(Integer.parseInt(id.getText().toString())>100) {
                    id.setError("Percentage cannnot exceed 100");
                    c+=1;
                }
                if(Integer.parseInt(ii.getText().toString())>100) {
                    ii.setError("Percentage cannnot exceed 100");
                    c+=1;
                }

                if(c>0)
                    Toast.makeText(getApplicationContext(),"Percentage cannot exceed 100. Please correct the error!",Toast.LENGTH_SHORT).show();

                if(c==0) {
                    ArrayList<String> list = new ArrayList<String>();
                    list.add(os.getText().toString());
                    list.add(cs.getText().toString());
                    list.add(cap.getText().toString());
                    list.add(bl.getText().toString());
                    list.add(inv.getText().toString());
                    list.add(mc.getText().toString());
                    list.add(bd.getText().toString());
                    list.add(mr.getText().toString());
                    list.add(dd.getText().toString());
                    list.add(dc.getText().toString());
                    list.add(ic.getText().toString());
                    list.add(ibl.getText().toString());
                    list.add(id.getText().toString());
                    list.add(ii.getText().toString());

                    Intent intent;
                    intent = new Intent(getBaseContext(), Pnl.class);
                    intent.putStringArrayListExtra("Adjustments", list);
                    intent.putExtra("Ratio",clickedFrom);
                    startActivity(intent);

                    if(clickedFrom)
                        finish();
                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });

        AlertDialog b = dialogBuilder.create();

        b.show();

        Button nbutton = b.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(Color.BLACK);
        Button pbutton = b.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(Color.BLACK);

    }


}
