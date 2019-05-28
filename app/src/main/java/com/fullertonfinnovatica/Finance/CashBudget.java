package com.fullertonfinnovatica.Finance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fullertonfinnovatica.R;

public class CashBudget extends AppCompatActivity {

    private EditText ob,
            cs, cd, interest, sfa, bor, si,
            cp, pc, pw, pe, pb, pfa, pt,
            cs1, cd1, interest1, sfa1, bor1, si1,
            cp1, pc1, pw1, pe1, pb1, pfa1, pt1,
            cs2, cd2, interest2, sfa2, bor2, si2,
            cp2, pc2, pw2, pe2, pb2, pfa2, pt2;

    private TextView cb, ob1, cb1, ob2, cb2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_budget);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>Cash Budget</font>"));

        ob = findViewById(R.id.ob);

        cs = findViewById(R.id.cs);
        cd = findViewById(R.id.cd);
        interest = findViewById(R.id.interest);
        sfa = findViewById(R.id.sfa);
        bor = findViewById(R.id.bor);
        si = findViewById(R.id.si);

        cp = findViewById(R.id.cp);
        pc = findViewById(R.id.pc);
        pw = findViewById(R.id.pw);
        pe = findViewById(R.id.pe);
        pb = findViewById(R.id.pb);
        pfa = findViewById(R.id.pfa);
        pt = findViewById(R.id.pt);

        cs1 = findViewById(R.id.cs1);
        cd1 = findViewById(R.id.cd1);
        interest1 = findViewById(R.id.interest1);
        sfa1 = findViewById(R.id.sfa1);
        bor1 = findViewById(R.id.bor1);
        si1 = findViewById(R.id.si1);

        cp1 = findViewById(R.id.cp1);
        pc1 = findViewById(R.id.pc1);
        pw1 = findViewById(R.id.pw1);
        pe1 = findViewById(R.id.pe1);
        pb1 = findViewById(R.id.pb1);
        pfa1 = findViewById(R.id.pfa1);
        pt1 = findViewById(R.id.pt1);

        cs2 = findViewById(R.id.cs2);
        cd2 = findViewById(R.id.cd2);
        interest2 = findViewById(R.id.interest2);
        sfa2 = findViewById(R.id.sfa2);
        bor2 = findViewById(R.id.bor2);
        si2 = findViewById(R.id.si2);

        cp2 = findViewById(R.id.cp2);
        pc2 = findViewById(R.id.pc2);
        pw2 = findViewById(R.id.pw2);
        pe2 = findViewById(R.id.pe2);
        pb2 = findViewById(R.id.pb2);
        pfa2 = findViewById(R.id.pfa2);
        pt2 = findViewById(R.id.pt2);

        cb = findViewById(R.id.cb);
        ob1 = findViewById(R.id.ob1);
        cb1 = findViewById(R.id.cb1);
        ob2 = findViewById(R.id.ob2);
        cb2 = findViewById(R.id.cb2);

        updateValues();

        ob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });


        cs.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        cd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        interest.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        sfa.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        bor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        si.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        cp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        pc.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        pw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        pe.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        pb.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        pfa.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        pt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        cs1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        cd1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        interest1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        sfa1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        bor1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        si1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        cp1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        pc1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        pw1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        pe1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        pb1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        pfa1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        pt1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        cs2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        cd2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        interest2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        sfa2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        bor2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        si2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        cp2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        pc2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        pw2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        pe2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        pb2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        pfa2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });

        pt2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                /* When focus is lost check that the text field
                 * has valid values.
                 */
                if (!hasFocus) {
                    updateValues();
                }
            }
        });
    }

    private void updateValues() {

        cb.setText(String.valueOf(Integer.parseInt(ob.getText().toString())+
        Integer.parseInt(cs.getText().toString())+
        Integer.parseInt(cd.getText().toString())+
        Integer.parseInt(interest.getText().toString())+
        Integer.parseInt(sfa.getText().toString())+
        Integer.parseInt(bor.getText().toString())+
        Integer.parseInt(si.getText().toString())-
        Integer.parseInt(cp.getText().toString())-
        Integer.parseInt(pc.getText().toString())-
        Integer.parseInt(pw.getText().toString())-
        Integer.parseInt(pe.getText().toString())-
        Integer.parseInt(pb.getText().toString())-
        Integer.parseInt(pfa.getText().toString())-
        Integer.parseInt(pt.getText().toString())));

        ob1.setText(cb.getText());

        cb1.setText(String.valueOf(Integer.parseInt(ob1.getText().toString())+
        Integer.parseInt(cs1.getText().toString())+
        Integer.parseInt(cd1.getText().toString())+
        Integer.parseInt(interest1.getText().toString())+
        Integer.parseInt(sfa1.getText().toString())+
        Integer.parseInt(bor1.getText().toString())+
        Integer.parseInt(si1.getText().toString())-
        Integer.parseInt(cp1.getText().toString())-
        Integer.parseInt(pc1.getText().toString())-
        Integer.parseInt(pw1.getText().toString())-
        Integer.parseInt(pe1.getText().toString())-
        Integer.parseInt(pb1.getText().toString())-
        Integer.parseInt(pfa1.getText().toString())-
        Integer.parseInt(pt1.getText().toString())));

        ob2.setText(cb1.getText());

        cb2.setText(String.valueOf(Integer.parseInt(ob2.getText().toString())+
        Integer.parseInt(cd2.getText().toString())+
        Integer.parseInt(interest2.getText().toString())+
        Integer.parseInt(sfa2.getText().toString())+
        Integer.parseInt(bor2.getText().toString())+
        Integer.parseInt(si2.getText().toString())-
        Integer.parseInt(cp2.getText().toString())-
        Integer.parseInt(pc2.getText().toString())-
        Integer.parseInt(pw2.getText().toString())-
        Integer.parseInt(pe2.getText().toString())-
        Integer.parseInt(pb2.getText().toString())-
        Integer.parseInt(pfa2.getText().toString())-
        Integer.parseInt(pt2.getText().toString())));

    }
}
